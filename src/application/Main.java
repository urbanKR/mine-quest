package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Miner's Quest");

		showMenuScreen(stage);
	}

	private void showMenuScreen(Stage primaryStage) {
		VBox menuLayout = new VBox(20);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.setStyle("-fx-background-color: #F0F0F0;");

		Text title = new Text("MINER'S QUEST");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 48));

		Button startButton = new Button("START");
		startButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		startButton.setMinWidth(200);
		startButton.setMinHeight(50);
		startButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;");

		Button quitButton = new Button("QUIT");
		quitButton.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		quitButton.setMinWidth(200);
		quitButton.setMinHeight(50);
		quitButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;");

		startButton.setOnMouseEntered(e -> startButton
				.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: black; -fx-border-width: 3px;"));
		startButton.setOnMouseExited(e -> startButton
				.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;"));

		quitButton.setOnMouseEntered(e -> quitButton
				.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: black; -fx-border-width: 3px;"));
		quitButton.setOnMouseExited(e -> quitButton
				.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;"));

		startButton.setOnAction(e -> showGameScreen(primaryStage));
		quitButton.setOnAction(e -> primaryStage.close());

		menuLayout.getChildren().addAll(title, startButton, quitButton);

		Scene menuScene = new Scene(menuLayout, 800, 600);
		primaryStage.setScene(menuScene);
		primaryStage.show();
	}

	private void showGameScreen(Stage stage) {
		// --- Game model ---
		GameModel model = new GameModel();

		// --- GridPane for map ---
		GridPane gridPane = new GridPane();
		MapView view = new MapView(gridPane, model);

		// --- Layout ---
		BorderPane root = new BorderPane();
		root.setCenter(gridPane);

		Scene gameScene = new Scene(root, 800, 600);

		// --- Key controls ---
		gameScene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case UP -> model.moveMiner(GameModel.Direction.UP);
			case DOWN -> model.moveMiner(GameModel.Direction.DOWN);
			case LEFT -> model.moveMiner(GameModel.Direction.LEFT);
			case RIGHT -> model.moveMiner(GameModel.Direction.RIGHT);
			}
			view.revealAroundMiner();
			view.updateView();
		});

		stage.setScene(gameScene);
		gridPane.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}
}