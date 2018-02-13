package invoicing.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import invoicing.database.DBConnector;
import invoicing.model.Invoices;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ShowInvoicesController {

	@FXML
	private TableView<Invoices> table_view;

	@FXML
	private TableColumn<Invoices, Integer> col_id;

	@FXML
	private TableColumn<Invoices, String> col_name;

	@FXML
	private TableColumn<Invoices, String> col_lastname;

	@FXML
	private TableColumn<Invoices, String> col_mail;

	@FXML
	private TableColumn<Invoices, String> col_phone;

	@FXML
	private TableColumn<Invoices, Boolean> col_java;

	@FXML
	private TableColumn<Invoices, Boolean> col_python;

	@FXML
	private TableColumn<Invoices, String> col_other;

	@FXML
	private TableColumn<Invoices, String> col_language;

	@FXML
	private TableColumn<Invoices, String> col_course;

	@FXML
	private Button btn_select;
	
    @FXML
    private Button btnBack;

    @FXML
    void back(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/mainView.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("FakturyITflow");
    	stage.show();
    	((Node)event.getSource()).getScene().getWindow().hide();
    }

	// obiekt połączenia z bazą danych
	private DBConnector db;

	// obiekt do przechowywania listy elementów z bazy danych typy Ankieta
	private ObservableList<Invoices> data = FXCollections.observableArrayList();

	@FXML
	private TextField tf_filtr_mail;

	@FXML
	private ComboBox<String> cmb_filtr_java;

	@FXML
	private ComboBox<String> cmb_filtr_lang;

	@FXML
	private Button btn_filtr;

	@FXML
	private Button btn_delete;

	ObservableList<String> javaValue = FXCollections.observableArrayList("Select", "Yes", "No");

	ObservableList<String> langValue = FXCollections.observableArrayList("Select", "Basic", "Intermediate", "Advanced");

	@FXML
	void doFiltr(MouseEvent event) {

		Connection connection = null;
		try {
			connection = db.connection();

			StringBuilder sql = new StringBuilder("SELECT * FROM ankieta");
			if (isFiltrExists()) {
				sql.append(" WHERE ");
				boolean isValue = false;

				if (isFiltrMailExists()) {
					sql.append(" mail = ?");
					isValue = true;
				}
				if (isFiltrJavaExists()) {
					if (isValue) {
						sql.append(" AND");
					}
					sql.append(" java = ?");
					isValue = true;
				}
				if (isFiltrLanguageExists()) {
					if (isValue) {
						sql.append(" AND");
					}
					sql.append(" language = ?");
				}
				System.out.println("FILTR SQL: " + sql);
			}
			// Clrl+2, l - automatyczne przypisane do zmiannej
			PreparedStatement ps = connection.prepareStatement(sql.toString());
			if (isFiltrExists()) {
				int i = 1;
				if (isFiltrMailExists()) {
					ps.setString(i, tf_filtr_mail.getText());
					i++;
				}
				if (isFiltrJavaExists()) {
					ps.setBoolean(i, "Yes".equalsIgnoreCase(cmb_filtr_java.getValue()));
					i++;
				}
				if (isFiltrLanguageExists()) {
					ps.setString(i, cmb_filtr_lang.getValue());
				}
			}

			ResultSet rs = ps.executeQuery();
			setTableValue(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isFiltrExists() {
		return isFiltrMailExists() || isFiltrJavaExists() || isFiltrLanguageExists();
	}

	private boolean isFiltrMailExists() {
		return !tf_filtr_mail.getText().isEmpty();
	}

	private boolean isFiltrJavaExists() {
		return !"Select".equalsIgnoreCase(cmb_filtr_java.getValue());
	}

	private boolean isFiltrLanguageExists() {
		return !"Select".equalsIgnoreCase(cmb_filtr_lang.getValue());
	}

	@FXML
	void onSelect(MouseEvent event) throws SQLException {

		Connection connection = null;
		try {
			connection = db.connection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ankieta;");
			setTableValue(rs);

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	// mapowanie elementów z bazy danych do obiektu modelu Ankiete i ustawienie ich
	// w table view
	private void setTableValue(ResultSet rs) throws SQLException {
		data.clear();
		while (rs.next()) {
			data.add(new Invoices(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getBoolean(6), rs.getBoolean(7), rs.getString(8), rs.getString(9), rs.getString(10)));
		}

		table_view.setItems(null);
		table_view.setItems(data);
	}

	// metoda uruchamiana przez wyświetlaniem widoku
	public void initialize() {
		db = new DBConnector();

		// dodawNo wartości do wyboru z pola combobox filtr java
		cmb_filtr_java.setItems(javaValue);
		// wartość domyślna
		cmb_filtr_java.setValue("Select");

		// dodawNo wartości do wyboru z pola combobox filtr language
		cmb_filtr_lang.setItems(langValue);
		cmb_filtr_lang.setValue("Select");

		setCellValue();

		// włączenie edytowania w tabeli
		table_view.setEditable(true);

		// metoda odpowiedzialne za obsługę edycji naposzczególnych polach
		editCalls();
	}

	// metoda do określenia wartości z modelu do kolumn table view
	private void setCellValue() {
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		col_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
		col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		col_java.setCellValueFactory(new PropertyValueFactory<>("java"));
		col_python.setCellValueFactory(new PropertyValueFactory<>("python"));
		col_other.setCellValueFactory(new PropertyValueFactory<>("other"));
		col_language.setCellValueFactory(new PropertyValueFactory<>("language"));
		col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
	}

	private void editCalls() {
		// edycja kolumny name
		editNameCell();

		// edycja kolumny last name
		editLastNameCell();

		// edycja kolumny mail
		editMailCell();

		// edycja kolumny phone
		editPhoneCell();

		// edycja kolumny java
		editJavaCell();

		// edycja kolumny python
		editPythonCell();

		// edycja kolumny other
		editOtherCell();

		// edycja kolumny Language
		editLanguageCell();

		// edycja kolumny course
		editCourseCell();
	}

	private void editNameCell() {
		col_name.setCellFactory(TextFieldTableCell.forTableColumn());
		col_name.setOnEditCommit(new EventHandler<CellEditEvent<Invoices, String>>() {
			@Override
			public void handle(CellEditEvent<Invoices, String> t) {
				((Invoices) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());

				Invoices selectedItem = table_view.getSelectionModel().getSelectedItem();
				// update wiersza po edycji pola
				updateCell(selectedItem);
			}
		});
	}

	private void editLastNameCell() {
		col_lastname.setCellFactory(TextFieldTableCell.forTableColumn());
		col_lastname.setOnEditCommit(new EventHandler<CellEditEvent<Invoices, String>>() {
			@Override
			public void handle(CellEditEvent<Invoices, String> t) {
				((Invoices) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
				Invoices selectedItem = table_view.getSelectionModel().getSelectedItem();
				// update wiersza po edycji pola
				updateCell(selectedItem);
			}
		});
	}

	private void editMailCell() {
		col_mail.setCellFactory(TextFieldTableCell.forTableColumn());
		col_mail.setOnEditCommit(new EventHandler<CellEditEvent<Invoices, String>>() {
			@Override
			public void handle(CellEditEvent<Invoices, String> t) {
				((Invoices) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMail(t.getNewValue());
				Invoices selectedItem = table_view.getSelectionModel().getSelectedItem();
				// update wiersza po edycji pola
				updateCell(selectedItem);
			}
		});
	}

	private void editPhoneCell() {
		col_phone.setCellFactory(TextFieldTableCell.forTableColumn());
		col_phone.setOnEditCommit(new EventHandler<CellEditEvent<Invoices, String>>() {
			@Override
			public void handle(CellEditEvent<Invoices, String> t) {
				((Invoices) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhone(t.getNewValue());
				Invoices selectedItem = table_view.getSelectionModel().getSelectedItem();
				// update wiersza po edycji pola
				updateCell(selectedItem);
			}
		});
	}

	private void editJavaCell() {
		col_java.setCellValueFactory(new Callback<CellDataFeatures<Invoices, Boolean>, ObservableValue<Boolean>>() {

			@Override
			public ObservableValue<Boolean> call(CellDataFeatures<Invoices, Boolean> param) {
				Invoices ankieta = param.getValue();

				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ankieta.isJava());
				// When "Java?" column change.
				booleanProp.addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						ankieta.setJava(newValue);

						// update wiersza po edycji pola
						updateCell(ankieta);
					}
				});
				return booleanProp;
			}
		});

		col_java.setCellFactory(new Callback<TableColumn<Invoices, Boolean>, TableCell<Invoices, Boolean>>() {
			@Override
			public TableCell<Invoices, Boolean> call(TableColumn<Invoices, Boolean> p) {
				CheckBoxTableCell<Invoices, Boolean> cell = new CheckBoxTableCell<Invoices, Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});
	}

	private void editPythonCell() {
		col_python.setCellValueFactory(new Callback<CellDataFeatures<Invoices, Boolean>, ObservableValue<Boolean>>() {

			@Override
			public ObservableValue<Boolean> call(CellDataFeatures<Invoices, Boolean> param) {
				Invoices ankieta = param.getValue();

				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ankieta.isPython());
				// When "Python?" column change.
				booleanProp.addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						ankieta.setPython(newValue);

						// update wiersza po edycji pola
						updateCell(ankieta);
					}
				});
				return booleanProp;
			}
		});

		col_python.setCellFactory(new Callback<TableColumn<Invoices, Boolean>, TableCell<Invoices, Boolean>>() {
			@Override
			public TableCell<Invoices, Boolean> call(TableColumn<Invoices, Boolean> p) {
				CheckBoxTableCell<Invoices, Boolean> cell = new CheckBoxTableCell<Invoices, Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});
	}

	private void editOtherCell() {
		col_other.setCellFactory(TextFieldTableCell.forTableColumn());
		col_other.setOnEditCommit(new EventHandler<CellEditEvent<Invoices, String>>() {
			@Override
			public void handle(CellEditEvent<Invoices, String> t) {
				((Invoices) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOther(t.getNewValue());
				Invoices selectedItem = table_view.getSelectionModel().getSelectedItem();
				// update wiersza po edycji pola
				updateCell(selectedItem);
			}
		});
	}

	private void editCourseCell() {
		ObservableList<String> courses = FXCollections.observableArrayList("Back-end", "Front-end");

		col_course.setCellValueFactory(new Callback<CellDataFeatures<Invoices, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Invoices, String> param) {
				Invoices ankieta = param.getValue();

				String course = ankieta.getCourse();
				return new SimpleObjectProperty<String>(course);
			}
		});
		col_course.setCellFactory(ComboBoxTableCell.forTableColumn(courses));
		col_course.setOnEditCommit((CellEditEvent<Invoices, String> event) -> {
			TablePosition<Invoices, String> pos = event.getTablePosition();

			String newCourse = event.getNewValue();
			int row = pos.getRow();
			Invoices ankieta = event.getTableView().getItems().get(row);
			ankieta.setCourse(newCourse);

			// update wiersza po edycji pola
			updateCell(ankieta);
		});
	}

	private void editLanguageCell() {
		ObservableList<String> langueages = FXCollections.observableArrayList("Basic", "Intermediate", "Advanced");

		col_language.setCellValueFactory(new Callback<CellDataFeatures<Invoices, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Invoices, String> param) {
				Invoices ankieta = param.getValue();

				String course = ankieta.getLanguage();
				return new SimpleObjectProperty<String>(course);
			}
		});
		col_language.setCellFactory(ComboBoxTableCell.forTableColumn(langueages));
		col_language.setOnEditCommit((CellEditEvent<Invoices, String> event) -> {
			TablePosition<Invoices, String> pos = event.getTablePosition();

			String newLanguage = event.getNewValue();
			int row = pos.getRow();
			Invoices ankieta = event.getTableView().getItems().get(row);
			ankieta.setLanguage(newLanguage);
			// update wiersza po edycji pola
			updateCell(ankieta);
		});
	}

	@FXML
	void onDelete(MouseEvent event) throws SQLException {

		Connection connection = null;
		try {
			connection = db.connection();
			String sql = "DELETE FROM ankieta WHERE id= ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			int selectedId = table_view.getSelectionModel().getSelectedItem().getId();
			ps.setInt(1, selectedId);
			ps.executeUpdate();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		// wywołanie metody Select
		onSelect(event);
	}

	private void updateCell(Invoices selectedItem) {
		Connection connection = null;
		try {
			connection = db.connection();

			PreparedStatement ps = connection.prepareStatement(
					"UPDATE ankieta SET id=?, name =?, last_name=?, mail=?, phone=?, java=?, python=?, other=?, language=?, course=? "
							+ "WHERE id = ?");

			ps.setInt(1, selectedItem.getId());
			ps.setString(2, selectedItem.getName());
			ps.setString(3, selectedItem.getLastName());
			ps.setString(4, selectedItem.getMail());
			ps.setString(5, selectedItem.getPhone());
			ps.setBoolean(6, selectedItem.isJava());
			ps.setBoolean(7, selectedItem.isPython());
			ps.setString(8, selectedItem.getOther());
			ps.setString(9, selectedItem.getLanguage());
			ps.setString(10, selectedItem.getCourse());
			// ustawienie id dla WHERE id = ?
			ps.setInt(11, selectedItem.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
