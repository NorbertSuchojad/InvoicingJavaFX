/**
 * 
 */
package invoicing.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import invoicing.database.DBConnector;
import invoicing.model.MyCompany;
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

/**
 * @author norbert
 *
 */

public class MyCompanyController {
	

	private DBConnector db;
	
    @FXML
    private ComboBox<String> comboFirma;

    @FXML
    private TextField tfCompanyName;

    @FXML
    private TextField tfNIP;

    @FXML
    private TextField tfREGON;

    @FXML
    private TextField tfAdress;

    @FXML
    private TextField tfPostCode;

    @FXML
    private TextField tfPost;

    @FXML
    private TextField tfBankAccount;

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
    	((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void save(MouseEvent event) {
		if (isNotCompleted()) {
			showAlertNotCompleted();
		} else {
			Connection connection = null;
			try {
				connection = db.connection();
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO my_company (my_nip, my_regon, my_company_name, my_address, my_postcode, my_post, my_company_account) "
								+ "VALUES (?,?,?,?,?,?,?) ");

				MyCompany myCompany = mapToMyCompany();
				ps.setLong(1, myCompany.getMy_nip());
				ps.setLong(2, myCompany.getMy_regon());
				ps.setString(3, myCompany.getMy_company_name());
				ps.setString(4, myCompany.getMy_address());
				ps.setString(5, myCompany.getMy_postcode());
				ps.setString(6, myCompany.getMy_post());
				ps.setString(7, myCompany.getMy_company_account());

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
    	tfAdress.clear();
		tfBankAccount.clear();
		tfCompanyName.clear();
		tfNIP.clear();
		tfPost.clear();
		tfPostCode.clear();
		tfREGON.clear();
		comboFirma.setValue(null);
	}

	private MyCompany mapToMyCompany() {
    	int nip = Integer.parseInt(tfNIP.getText());
		int regon = Integer.parseInt(tfREGON.getText());
		String companyName = tfCompanyName.getText();
		String adress = tfAdress.getText();
		String bankAccount = tfBankAccount.getText();
		String postCity = tfPost.getText();
		String postCode = tfPostCode.getText();
		MyCompany myCompany = new MyCompany(
											nip, regon, companyName, adress, 
											postCode, postCity, bankAccount);

		return myCompany;
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
		return "".equals(tfNIP.getText()) 
				|| "".equals(tfREGON.getText()) 
				|| "".equals(tfCompanyName.getText())
				|| "".equals(tfPostCode.getText())
				|| "".equals(tfPost.getText()) 
				|| "".equals(tfAdress.getText());
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
	
	public void initialize() throws SQLException  {
		db = new DBConnector();
		Connection connection = db.connection();
		
		ObservableList<String> optionsComboFirma = null;
	    List<String> companyNames = new ArrayList<String>();
	    
		String selectCompanyName = "SELECT my_company_name FROM my_company ;";
		PreparedStatement ps = connection.prepareStatement(selectCompanyName);
	    ResultSet rs = ps.executeQuery();
		try {
	        while (rs.next()) {
	        	companyNames.add(rs.getString("my_company_name"));
	        }
	        optionsComboFirma = FXCollections.observableArrayList(companyNames);
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        if (connection != null) { 
	        	connection.close(); 
	        }
	    }
		comboFirma.setItems(optionsComboFirma);
    }
}
