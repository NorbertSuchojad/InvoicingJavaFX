/**
 * 
 */
package invoicing.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author norbert
 *
 */

public class MainController {

    @FXML
    private Button btnMyCompanyData;

    @FXML
    private Button btnOtherCompanyData;

    @FXML
    private Button btnNewInvoice;

    @FXML
    private Button btnShowInvoicing;

    @FXML
    private Button btnExit;

    @FXML
    void exitAction(MouseEvent event) {
    	Platform.exit();
    }

    @FXML
    void prepareNewInvoice(MouseEvent event) {

    }

    @FXML
    void showInvoices(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/ShowInvoicesView.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("FakturyITflow");
    	stage.show();
    	((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void showMyCompanyData(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/myCompanyView.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("FakturyITflow");
    	stage.show();
    	((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void showOtherCompanyData(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/otherCompanyView.fxml"));
    	Scene scene = new Scene(parent);
    	stage.setScene(scene);
    	stage.setTitle("FakturyITflow");
    	stage.show();
    	((Node)event.getSource()).getScene().getWindow().hide();

    }

}

