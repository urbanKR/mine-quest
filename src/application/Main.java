package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
	    // --- Game model ---
	    GameModel model = new GameModel();

	    // --- GridPane for map ---
	    GridPane gridPane = new GridPane();
	    MapView view = new MapView(gridPane, model);

	    // --- Layout ---
	    BorderPane root = new BorderPane();
	    root.setCenter(gridPane);

	    Scene scene = new Scene(root, 800, 600);

	    // --- Key controls ---
	    scene.setOnKeyPressed(event -> {
	        switch (event.getCode()) {
	            case UP -> model.moveMiner(GameModel.Direction.UP);
	            case DOWN -> model.moveMiner(GameModel.Direction.DOWN);
	            case LEFT -> model.moveMiner(GameModel.Direction.LEFT);
	            case RIGHT -> model.moveMiner(GameModel.Direction.RIGHT);
	        }
	        view.revealAroundMiner();
	        view.updateView();
	    });

	    primaryStage.setTitle("Miner's Game");
	    primaryStage.setScene(scene);
	    primaryStage.show();

	    gridPane.requestFocus(); 
	}


    public static void main(String[] args) {
        launch(args);
    }
}
