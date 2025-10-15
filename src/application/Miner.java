package application;

/**
 * Represents the player character with position, inventory, and status attributes.
 * Manages mining tools, oxygen levels, gold collection, and movement.
 */
public class Miner {
    private int row, col;
    private int toolsDamage = 100;
    private int pickaxeLevel = 0;
    private int oxygenLevel = 0;
    private String characterImage;
    private String pickaxeImage = "pickaxe-wood.png";
    private int goldAmount = 0;
    private int maxOxygen = 300;
    private int currentOxygen = maxOxygen;
    private int layerSize = 10;
    private int groundLevel = 4;

    private Runnable loseCallback;

    /**
     * Constructs a new Miner at the specified position.
     *
     * @param row the starting row position
     * @param col the starting column position
     * @param characterImage the image file for the character
     */
    public Miner(int row, int col, String characterImage) {
        this.row = row;
        this.col = col;
        this.characterImage = characterImage;
    }

    /**
     * Gets the current row position.
     *
     * @return the row coordinate
     */
    public int getRow() { return row; }

    /**
     * Gets the current column position.
     *
     * @return the column coordinate
     */
    public int getCol() { return col; }

    /**
     * Gets the maximum oxygen capacity.
     *
     * @return the maximum oxygen value
     */
    public int getMaxOxygen() {
        return maxOxygen;
    }

    /**
     * Sets the maximum oxygen capacity and resets current oxygen.
     *
     * @param maxOxygen the new maximum oxygen value
     */
    public void setMaxOxygen(int maxOxygen) {
        this.maxOxygen = maxOxygen;
        this.currentOxygen = maxOxygen;
    }

    /**
     * Gets the oxygen level attribute.
     *
     * @return the oxygen level
     */
    public int getOxygenLevel() {
        return oxygenLevel;
    }

    /**
     * Sets the oxygen level attribute.
     *
     * @param oxygenLevel the new oxygen level
     */
    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    /**
     * Sets the miner's position.
     *
     * @param row the new row position
     * @param col the new column position
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Moves the miner to a new position.
     *
     * @param newRow the target row position
     * @param newCol the target column position
     */
    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    /**
     * Gets the mining damage of current tools.
     *
     * @return the tools damage value
     */
    public int getToolsDamage() {
        return toolsDamage;
    }

    /**
     * Depletes oxygen based on depth and triggers game over if oxygen is too low.
     * Oxygen refills when at ground level.
     */
    public void depleteOxygen() {
        currentOxygen -= row / layerSize + 1;

        if(currentOxygen < - 8) {
            if(loseCallback != null) {
                loseCallback.run();
            }
        }

        if(row == groundLevel) {
            currentOxygen = maxOxygen;
        }

        System.out.println(currentOxygen);
    }

    /**
     * Applies damage to the miner by reducing oxygen.
     *
     * @param damage the amount of damage to take
     */
    public void hurt(int damage) {
        currentOxygen -= damage;

        if(currentOxygen < - 8) {
            if(loseCallback != null) {
                loseCallback.run();
            }
        }
    }

    /**
     * Sets the mining damage of tools.
     *
     * @param damage the new tools damage value
     */
    public void setToolsDamage(int damage) {
        this.toolsDamage = damage;
    }

    /**
     * Gets the current pickaxe level.
     *
     * @return the pickaxe level
     */
    public int getPickaxeLevel() {
        return pickaxeLevel;
    }

    /**
     * Sets the pickaxe level.
     *
     * @param level the new pickaxe level
     */
    public void setPickaxeLevel(int level) {
        this.pickaxeLevel = level;
    }

    /**
     * Gets the character image file name.
     *
     * @return the character image file name
     */
    public String getCharacterImage() {
        return characterImage;
    }

    /**
     * Gets the current gold amount.
     *
     * @return the gold amount
     */
    public int getGoldAmount() {
        return goldAmount;
    }

    /**
     * Gets the current oxygen value.
     *
     * @return the current oxygen
     */
    public int getOxygen() {
        return currentOxygen;
    }

    /**
     * Sets the gold amount.
     *
     * @param goldAmount the new gold amount
     */
    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    /**
     * Gets the pickaxe image file name.
     *
     * @return the pickaxe image file name
     */
    public String getPickaxeImage() {
        return pickaxeImage;
    }

    /**
     * Sets the pickaxe image file name.
     *
     * @param pickaxeImage the new pickaxe image file name
     */
    public void setPickaxeImage(String pickaxeImage) {
        this.pickaxeImage = pickaxeImage;
    }

    /**
     * Adds gold to the miner's inventory.
     *
     * @param amount the amount of gold to add
     */
    public void addGold(int amount) {
        this.goldAmount += amount;
        System.out.println("GOLD ADDED");
    }

    /**
     * Subtracts gold from the miner's inventory if sufficient funds available.
     *
     * @param amount the amount of gold to subtract
     * @return true if subtraction was successful, false if insufficient funds
     */
    public boolean subtractGold(int amount) {
        if(this.goldAmount - amount < 0) return false;
        this.goldAmount -= amount;
        return true;
    }

    /**
     * Sets the callback to execute when the miner loses (oxygen ends).
     *
     * @param loseCallback the runnable to execute on game over
     */
    public void setLoseCallback(Runnable loseCallback) {
        this.loseCallback = loseCallback;
    }
}