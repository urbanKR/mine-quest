package application;

public class Miner {
    private int row, col;
    private int toolsDamage = 1;
    private int pickaxeLevel = 0;
    private int oxygenLevel = 0;
    private String characterImage;
    private String pickaxeImage = "pickaxe-wood.png";
    private int goldAmount = 0;
    private int maxOxygen = 30;
    private int currentOxygen = maxOxygen;
    private int layerSize = 10;
    private int groundLevel = 4;
    
    private Runnable loseCallback;

    public Miner(int row, int col, String characterImage) {
        this.row = row;
        this.col = col;
        this.characterImage = characterImage;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public int getMaxOxygen() {
        return maxOxygen;
    }

    public void setMaxOxygen(int maxOxygen) {
        this.maxOxygen = maxOxygen;
        this.currentOxygen = maxOxygen;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

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

	 public void hurt(int damage) {
	    	currentOxygen -= damage;
	    	
	    	if(currentOxygen < - 8) {
	    		if(loseCallback != null) {
	    			loseCallback.run();
	    		}
	    	}
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
    
    public int getOxygen() {
        return currentOxygen;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getPickaxeImage() {
        return pickaxeImage;
    }

    public void setPickaxeImage(String pickaxeImage) {
        this.pickaxeImage = pickaxeImage;
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
    
    // Callback
    public void setLoseCallback(Runnable loseCallback) {
		this.loseCallback = loseCallback;
	}
}