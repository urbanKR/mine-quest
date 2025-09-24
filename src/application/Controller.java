package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ProgressBar;


public class Controller {

    @FXML private GridPane gridPane;
    @FXML private ProgressBar healthBar;
    @FXML private ProgressBar oxygenBar;
    @FXML private Label coinsLabel;
    @FXML private Label bombsLabel;
    @FXML private Label torchesLabel;

    private int rows = 32;
    private int cols = 32;
    private Cell[][] cells;
    private Miner miner;
    private int skyEnds = 10;
    private int spawnMiner = 15;

    @FXML
    public void initialize() {
        newGame();
    }

    private void newGame() {
        gridPane.getChildren().clear();
        cells = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(i, j);

                // Cells above skyEnds are visible sky
                if (i < skyEnds) {
                    cell.setCellAsSky();
                } else {
                    // All cells below skyEnds start unrevealed
                    cell.setCellUnrevealed();
                }

                gridPane.add(cell, j, i);
                cells[i][j] = cell;
            }
        }

        // Place the miner
        placeCharacter(skyEnds - 1, spawnMiner);
    }

    private void placeCharacter(int row, int col) {
        miner = new Miner(row, col);
        cells[row][col].setCellAsMiner();

        // Reveal the two horizontal neighbors in the unrevealed area below
        if (col > 0 && row + 1 < rows) cells[row + 1][col - 1].setCellAsGrass();
        if (row + 1 < rows) cells[row + 1][col].setCellAsGrass(); // directly below miner
        if (col < cols - 1 && row + 1 < rows) cells[row + 1][col + 1].setCellAsGrass();
    }
}
