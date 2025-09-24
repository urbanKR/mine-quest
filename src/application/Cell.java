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
        setStyle("-fx-background-color: #A0A0A0; -fx-border-color: #808080; -fx-border-width: 2px; -fx-alignment: center;");
    }
    
    // -- GETTER AND SETTERS --
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    // -- OTHER FUNCTIONS --
    
}
