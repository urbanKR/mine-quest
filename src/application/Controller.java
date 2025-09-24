package application;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	
	// -- INIT VARIABLES --

    @FXML private GridPane gridPane;
    
    private int rows = 32;
    private int cols = 32;
    private Cell[][] cells;

    // -- INIT FUNCTIONS --
    
    @FXML
    public void initialize() {
    	newGame(); 
    }
   
    // -- MAIN FEATURES --

    // Create a simple board game
    private void newGame() {
    	gridPane.getChildren().clear();
             
        cells = new Cell[rows][cols];

        // CREATE A NEW GRID OF CELLS
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(i, j);
                gridPane.add(cell, j, i);
                cells[i][j] = cell;
            }
        }

    }

}
