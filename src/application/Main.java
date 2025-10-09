package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	MapView view;

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

		model.setCallback(() -> updateVisuals());
		model.setWinCallback(() -> showWinDialog(stage));

		// --- GridPane for map ---
		GridPane gridPane = new GridPane();
		view = new MapView(gridPane, model);

		// --- ScrollPane to make map scrollable ---
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPane);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(false);
		scrollPane.setPannable(true);

		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setPadding(new Insets(0));
		scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		// --- Layout ---
		BorderPane root = new BorderPane();
		root.setCenter(scrollPane);

		Scene gameScene = new Scene(root, 816, 600);

		// --- Key controls ---
		gameScene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case UP -> {
				model.moveMiner(GameModel.Direction.UP);
				scrollToMiner(scrollPane, gridPane, model.getMiner());
			}
			case DOWN -> {
				model.moveMiner(GameModel.Direction.DOWN);
				scrollToMiner(scrollPane, gridPane, model.getMiner());
			}
			case LEFT -> model.moveMiner(GameModel.Direction.LEFT);
			case RIGHT -> model.moveMiner(GameModel.Direction.RIGHT);
			}
			view.revealAroundMiner();
			view.updateView();
		});

		gameScene.setOnMouseReleased(event -> {
			view.revealAroundMiner();
			view.updateView();
		});

		stage.setScene(gameScene);
		gridPane.requestFocus();
	}

	private void updateVisuals() {
		view.revealAroundMiner();
		view.updateView();
	}

	private void scrollToMiner(ScrollPane scrollPane, GridPane gridPane, Miner miner) {
		double cellHeight = 40;
		double viewportHeight = scrollPane.getViewportBounds().getHeight();
		double totalHeight = gridPane.getHeight();

		if (totalHeight > viewportHeight) {
			double minerY = miner.getRow() * cellHeight;
			double minerScreenPos = minerY - (viewportHeight * 0.33);
			double targetVvalue = minerScreenPos / (totalHeight - viewportHeight);

			scrollPane.setVvalue(Math.max(0, Math.min(1, targetVvalue)));
		}
	}

	private void showWinDialog(Stage stage) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Congratulations!");
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.getDialogPane().getButtonTypes().clear();

		VBox dialogLayout = new VBox(20);
		dialogLayout.setAlignment(Pos.CENTER);
		dialogLayout.setStyle("-fx-background-color: #F0F0F0; -fx-padding: 30;");
		dialog.getDialogPane().getScene().getRoot().setStyle("-fx-border-color: black; -fx-border-width: 3px;");

		Text title = new Text("YOU WIN!");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 36));

		Text message = new Text("You collected all the secret keys\nand reached the final chest!");
		message.setFont(Font.font("Arial", 18));
		message.setTextAlignment(TextAlignment.CENTER);

		HBox buttonBox = new HBox(20);
		buttonBox.setAlignment(Pos.CENTER);

		Button backToMenuButton = new Button("Back to Menu");
		backToMenuButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		backToMenuButton.setMinWidth(150);
		backToMenuButton.setMinHeight(40);
		backToMenuButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;");

		Button exitGameButton = new Button("Exit Game");
		exitGameButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		exitGameButton.setMinWidth(150);
		exitGameButton.setMinHeight(40);
		exitGameButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;");

		backToMenuButton.setOnMouseEntered(e -> backToMenuButton
				.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: black; -fx-border-width: 3px;"));
		backToMenuButton.setOnMouseExited(e -> backToMenuButton
				.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;"));

		exitGameButton.setOnMouseEntered(e -> exitGameButton
				.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: black; -fx-border-width: 3px;"));
		exitGameButton.setOnMouseExited(e -> exitGameButton
				.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 3px;"));

		buttonBox.getChildren().addAll(backToMenuButton, exitGameButton);
		dialogLayout.getChildren().addAll(title, message, buttonBox);

		dialog.getDialogPane().setContent(dialogLayout);

		backToMenuButton.setOnAction(e -> {
			dialog.setResult(ButtonType.CLOSE);
			showMenuScreen(stage);
		});

		exitGameButton.setOnAction(e -> {
			dialog.setResult(ButtonType.CLOSE);
			stage.close();
		});

		dialog.showAndWait();
	}
}