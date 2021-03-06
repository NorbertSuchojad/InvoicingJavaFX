/**
 * 
 */
package invoicing.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import invoicing.database.DBConnector;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
/**
 * @author NorbertSuchojad
 *
 */
public class LoginController {

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField pf_pass;

    @FXML
    private TextField tf_pass;

    @FXML
    private Button btn_show;
    
    @FXML
    private Button btn_login;
    
    @FXML
    private Button btn_quit;
    
    static boolean flag = true;
    
    DBConnector db;
    
    @FXML
    void enterPressed(KeyEvent event) throws IOException, SQLException {
    	if (event.getCode().equals(KeyCode.ENTER)) {
    		loginAction(event);
		}
    }
    
    private void loginAction(KeyEvent event) throws IOException, SQLException {
    	Connection connection = db.connection();
    	String sql = "Select * from user where login=? and pass=?;";
    	PreparedStatement ps = connection.prepareStatement(sql);
    	ps.setString(1, tf_login.getText());
    	ps.setString(2, tf_pass.isVisible() ? tf_pass.getText():pf_pass.getText());
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()) {
    		// jeśli użytkownik jest w bazie - pojawia się kolejne okno
    		Stage stage = new Stage();
        	Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/mainView.fxml"));
        	Scene scene = new Scene(parent);
        	stage.setScene(scene);
        	stage.setTitle("FakturyITflow");
        	stage.show();
        	((Node)event.getSource()).getScene().getWindow().hide();
    	} else {
    		Alert error = new Alert(AlertType.ERROR);
    		error.setHeaderText("Wrong login or password");
    		error.setContentText("Please enter valid login details");
    		error.setTitle("Error!");
    		error.showAndWait();
    	}
		
	}

	@FXML
    void btnLogin(MouseEvent event) throws SQLException, IOException {
    	loginAction(event);
    }

	private void loginAction(MouseEvent event) throws SQLException, IOException {
		Connection connection = db.connection();
    	String sql = "Select * from user where login=? and pass=?;";
    	PreparedStatement ps = connection.prepareStatement(sql);
    	ps.setString(1, tf_login.getText());
    	ps.setString(2, tf_pass.isVisible() ? tf_pass.getText():pf_pass.getText());
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()) {
    		// jeśli użytkownik jest w bazie - pojawia się kolejne okno
    		Stage stage = new Stage();
        	Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/mainView.fxml"));
        	Scene scene = new Scene(parent);
        	stage.setScene(scene);
        	stage.setTitle("FakturyITflow");
        	stage.show();
        	((Node)event.getSource()).getScene().getWindow().hide();
    	} else {
    		Alert error = new Alert(AlertType.ERROR);
    		error.setHeaderText("Wrong login or password");
    		error.setContentText("Please enter valid login details");
    		error.setTitle("Error!");
    		error.showAndWait();
    	}
	}

    @FXML
    void btnShow(MouseEvent event) {
    	if (flag) {
    		String pf_text = pf_pass.getText();
    		pf_pass.setVisible(false);
    		tf_pass.setVisible(true);
    		tf_pass.setText(pf_text);
    		flag = false;
    		btn_show.setText("hide");
    	}else {
    		String tf_text = tf_pass.getText();
    		pf_pass.setVisible(true);
    		tf_pass.setVisible(false);
    		pf_pass.setText(tf_text);
    		flag = true;
    		btn_show.setText("show");
    	}

    }
    
    @FXML
    void btnQuit(MouseEvent event) {
    	Platform.exit();
    }
    
    public void initialize() {
    	db = new DBConnector();
    }

}
