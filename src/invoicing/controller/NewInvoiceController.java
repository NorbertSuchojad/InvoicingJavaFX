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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * @author norbert
 *
 */
public class NewInvoiceController {
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
}
