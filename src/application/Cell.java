package application;

import javafx.scene.control.Button;

public class Cell extends Button {
    private int row;
    private int col;
    private CellType type;

    private boolean revealed = false;
    private boolean hasMiner = false;
    private boolean walkable = true;
    private boolean goal = false;

    public Cell(CellType type) {
        this.type = type;

        switch (type) {
            case SKY: walkable = false; break;
            case SKY_WALKABLE: walkable = true; break;
            case GRASS: walkable = true; break;
            case DIRT: walkable = true; break;
        }

        setMinSize(40, 40);
        setMaxSize(40, 40);
        updateVisual();
    }

    // Set position
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() { return row; }
    public int getCol() { return col; }

    // --- Getters and setters ---
    public CellType getType() { return type; }
    public void setType(CellType type) { 
        this.type = type; 
        updateVisual(); 
    }

    public boolean isRevealed() { return revealed; }
    public void setRevealed(boolean revealed) { 
        this.revealed = revealed; 
        updateVisual(); 
    }

    public boolean hasMiner() { return hasMiner; }
    public void setHasMiner(boolean hasMiner) { 
        this.hasMiner = hasMiner; 
        updateVisual(); 
    }

    public boolean isWalkable() { return walkable; }
    public void setWalkable(boolean walkable) { 
        this.walkable = walkable; 
        updateVisual(); 
    }

    public boolean isGoal() { return goal; }
    public void setGoal(boolean goal) { 
        this.goal = goal; 
        updateVisual(); 
    }

    // --- Visual representation ---
    public void updateVisual() {
        if (hasMiner) {
            setStyle("-fx-background-color: #FFD700; -fx-border-color: #000; -fx-border-width: 1px;");
        } else if (!revealed) {
            setStyle("-fx-background-color: #A0A0A0; -fx-border-color: #808080; -fx-border-width: 1px;");
        } else {
            switch(type) {
                case SKY:
                    setStyle("-fx-background-color: #87CEEB; -fx-border-color: #87CEEB; -fx-border-width: 1px;");
                    break;
                case SKY_WALKABLE:
                    setStyle("-fx-background-color: #87CEEB; -fx-border-color: #87CEEB; -fx-border-width: 1px;");
                    break;
                case GRASS:
                    setStyle("-fx-background-color: #A3E055; -fx-border-color: #469B11; -fx-border-width: 1px;");
                    break;
                case DIRT:
                    setStyle("-fx-background-color: #8B4513; -fx-border-color: #5A2E0F; -fx-border-width: 1px;");
                    break;
            }
        }
    }
}