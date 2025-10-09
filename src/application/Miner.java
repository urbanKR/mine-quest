package application;

public class Miner {
    private int row, col;
    private int toolsDamage = 2;
    private String characterImage;

    public Miner(int row, int col, String characterImage) {
        this.row = row;
        this.col = col;
        this.characterImage = characterImage;
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

    public String getCharacterImage() {
        return characterImage;
    }
}