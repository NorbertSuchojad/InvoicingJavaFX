package invoicing;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		// dodanie zasobu widoku aplikacji
		Parent parent = FXMLLoader.load(getClass().getResource("/invoicing/view/loginView.fxml"));
		// osadzenie zasobu na scenie
		Scene scene = new Scene(parent);
		// osadzenie sceny na stage
		primaryStage.setScene(scene);
		// dodanie tytulu
		primaryStage.setTitle("Signing in");
		primaryStage.setResizable(false);
		// ustawienie widocznosci aplikacji
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
