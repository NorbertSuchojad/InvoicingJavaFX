/**
 * 
 */
package invoicing.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author norbert
 *
 */
public class OtherCompanyController {

	    @FXML
	    private ComboBox<?> comboOtherFirma;

	    @FXML
	    private TextField tfOtcherCompanyName;

	    @FXML
	    private TextField tfOtherNIP;

	    @FXML
	    private TextField tfOtherREGON;

	    @FXML
	    private TextField tfOtherAdress;

	    @FXML
	    private TextField tfOtherPostCode;

	    @FXML
	    private TextField tfOtherPost;

	    @FXML
	    private TextField tfOtherBankAccount;

	    @FXML
	    private Button btnSaveAndClose;

	    @FXML
	    private Button btnSave;

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

	    @FXML
	    void save(MouseEvent event) {

	    }

	    @FXML
	    void saveAndClose(MouseEvent event) {

	    }

	}
