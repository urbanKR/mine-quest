package application;

public class Miner {
    private int row, col;
    private int toolsDamage = 1;

    public Miner(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
    
    public int getToolsDamage() {
    	return toolsDamage;
    }
}
