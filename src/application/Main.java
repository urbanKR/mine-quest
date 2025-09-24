package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// -- LOAD FILE --
		FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
		
        // -- CREATE GAME WINDOW --
		Scene scene = new Scene(root, 1280, 960);
		
        // -- CREATE MAIN SCENE --		
		stage.setTitle("Minesweeper");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}