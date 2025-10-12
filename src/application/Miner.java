package application;

public class Miner {
    private int row, col;
    private int toolsDamage = 1;
    private int pickaxeLevel = 0;
    private String characterImage;
    private int goldAmount = 0;

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

    public void setToolsDamage(int damage) {
        this.toolsDamage = damage;
    }
    public int getPickaxeLevel() {
        return pickaxeLevel;
    }
    public void setPickaxeLevel(int level) {
        this.pickaxeLevel = level;
    }
    public String getCharacterImage() {
        return characterImage;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void addGold(int amount) {
        this.goldAmount += amount;
        System.out.println("GOLD ADDED");
    }

    public boolean subtractGold(int amount) {
        if(this.goldAmount - amount < 0) return false;
        this.goldAmount -= amount;
        return true;
    }
}