package application;

import javafx.scene.control.Button;

public class Cell extends Button {
    private final int row, col;
    private CellType type;

    private boolean revealed = false;
    private boolean hasMiner = false;
    private boolean walkable = true;
    private boolean goal = false;

    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;

        // Default walkable/goal based on type
        switch (type) {
            case SKY -> walkable = true;
            case GROUND -> walkable = true;
            case BLOCK -> walkable = false;
            case GOAL -> {
                walkable = true;
                goal = true;
            }
        }

        setMinSize(40, 40);
        setMaxSize(40, 40);
        updateVisual();
    }

    // --- Getters and setters ---
    public int getRow() { return row; }
    public int getCol() { return col; }

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
        } else if (goal) {
            setStyle("-fx-background-color: #FF4500; -fx-border-color: #000; -fx-border-width: 1px;");
        } else if (!walkable) {
            setStyle("-fx-background-color: #C2E7FF; -fx-border-color: #469B11; -fx-border-width: 1px;");
        } else {
            switch(type) {
                case SKY -> setStyle("-fx-background-color: #87CEEB; -fx-border-color: #496B7A; -fx-border-width: 1px;");
                case GROUND -> setStyle("-fx-background-color: #A3E055; -fx-border-color: #469B11; -fx-border-width: 1px;");
                case BLOCK -> setStyle("-fx-background-color: #8B4513; -fx-border-color: #5A2E0F; -fx-border-width: 1px;");
                case GOAL -> setStyle("-fx-background-color: #FF4500; -fx-border-color: #000; -fx-border-width: 1px;");
            }
        }
    }
}
