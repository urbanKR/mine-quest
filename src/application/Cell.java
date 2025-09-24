package application;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;

public class Cell extends Button {

	// -- PROPERTIES --
    private final int row;
    private final int col;

    // -- CELL CONSTRUCTOR --
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        
        setMinSize(40, 40);
        setMaxSize(40, 40);
    }
    
    // -- GETTER AND SETTERS --
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public void setCellUnrevealed() {
        setStyle("-fx-background-color: #A0A0A0; -fx-border-color: #808080; -fx-border-width: 1px; -fx-alignment: center;");
    }
    
    public void setCellAsMiner() {
        setStyle("-fx-background-color: #FFD700; -fx-border-color: #000; -fx-border-width: 1px; -fx-alignment: center;");
    }
    
    public void setCellAsSky() {
        setStyle("-fx-background-color: #C2E7FF; -fx-border-width: 1px; -fx-alignment: center;");
    }

    public void setCellAsGrass() {
        setStyle("-fx-background-color: #A3E055; -fx-border-color: #469B11; -fx-border-width: 1px; -fx-alignment: center;");
    }

   
    // -- OTHER FUNCTIONS --


    
    
}
