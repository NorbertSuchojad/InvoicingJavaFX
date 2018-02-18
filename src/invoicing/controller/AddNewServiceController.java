package invoicing.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import invoicing.database.DBConnector;
import invoicing.model.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddNewServiceController {
	
	private DBConnector db;
	
    @FXML
    private ComboBox<String> comboServices;

    @FXML
    private TextField tfServiceName;

    @FXML
    private TextField tfSalesTyp;

    @FXML
    private TextField tfSalesValue;

    @FXML
    private TextField tfSalesPrice;

    @FXML
    private Button btnSaveAndClose;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnBack;
    
    @FXML
	private void back(MouseEvent event) throws IOException {
		Stage stage = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/mainView.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("FakturyITflow");
		stage.show();
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	private void save(MouseEvent event) {
		if (isNotCompleted()) {
			showAlertNotCompleted();
		} else {
			Connection connection = null;
			try {
				connection = db.connection();
				PreparedStatement ps = connection
						.prepareStatement("INSERT INTO services (service_unit, service_value, service_price, service_name) "
								+ "VALUES (?,?,?,?) ");

				Services services = mapToServices();
				ps.setString(1, services.getService_unit());
				ps.setLong(2, services.getService_value());
				ps.setLong(3, services.getService_price());
				ps.setString(4, services.getService_name());

				ps.executeUpdate();
				showAlertSaveComplete();
				clearAll();

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

	private void clearAll() {
		tfServiceName.clear();
		tfSalesTyp.clear();
		tfSalesValue.clear();
		tfSalesPrice.clear();
		comboServices.setValue(null);
	}

	private Services mapToServices() {
		String serviceName = tfServiceName.getText();
		String serviceUnit = tfSalesTyp.getText();
		int serviceValue =  Integer.parseInt(tfSalesValue.getText());
		int servicePrice =  Integer.parseInt(tfSalesPrice.getText());
		Services services = new Services(serviceName, serviceUnit, serviceValue, servicePrice);
		return services;
	}

	@FXML
	private void saveAndClose(MouseEvent event) throws IOException {
		if (isNotCompleted()) {
			showAlertNotCompleted();
		} else {
			save(event);
			back(event);
		}
	}

	private boolean isNotCompleted() {
		return "".equals(tfServiceName.getText()) || "".equals(tfSalesPrice.getText()) 
				|| "".equals(tfSalesValue.getText()) || "".equals(tfSalesTyp.getText());
	}

	private void showAlertNotCompleted() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Błąd");
		alert.setContentText("Nie można kontynuować");
		alert.setTitle("Należy uzupełnić wszystkie pola!");
		alert.showAndWait();
	}

	private void showAlertSaveComplete() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Sukces!");
		alert.setContentText("Gratulacje!");
		alert.setTitle("Poprawnie dodano dane firmy do bazy!");
		alert.showAndWait();
	}
    
    @FXML
    void enterPressed(KeyEvent event) throws IOException, SQLException {
    	if (event.getCode().equals(KeyCode.ENTER)) {
    		//something
		}
    }
    
    public void initialize() throws SQLException {
		db = new DBConnector();
		Connection connection = db.connection();

		ObservableList<String> optionsComboService = null;
		List<String> serviceNames = new ArrayList<String>();

		String selectServiceName = "SELECT service_name FROM services ;";
		PreparedStatement ps = connection.prepareStatement(selectServiceName);
		ResultSet rs = ps.executeQuery();
		try {
			while (rs.next()) {
				serviceNames.add(rs.getString("service_name"));
			}
			optionsComboService = FXCollections.observableArrayList(serviceNames);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		if (optionsComboService.isEmpty()) {

		} else {
			comboServices.setItems(optionsComboService);
		}

	}

}
