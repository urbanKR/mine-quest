package application;

/**
 * Provides shop functionality for purchasing miner upgrades.
 * Handles pickaxe and oxygen upgrades with predefined costs and benefits.
 */
public class Shop {
    private static final int[][] PICKAXE_UPGRADES = {
            {2, 20},      // Level 1: 2 damage, 20 gold cost
            {5, 50},      // Level 2: 5 damage, 50 gold cost
            {10, 70},     // Level 3: 10 damage, 70 gold cost
            {20, 100},    // Level 4: 20 damage, 100 gold cost
            {40, 150}     // Level 5: 40 damage, 150 gold cost
    };

    private static final String[] PICKAXE_IMAGES = {
            "pickaxe-wood.png",    // Level 1
            "pickaxe-stone.png",   // Level 2
            "pickaxe-iron.png",    // Level 3
            "pickaxe-diamond.png", // Level 4
            "pickaxe-special.png"  // Level 5
    };

    private static final int[][] OXYGEN_UPGRADES = {
            {1, 10},      // Level 0: 30 seconds, 10 gold cost
            {50, 30},     // Level 1: 50 seconds, 30 gold cost
            {60, 50},     // Level 2: 60 seconds, 50 gold cost
            {80, 70},     // Level 3: 80 seconds, 70 gold cost
            {20, 100}     // Level 4: 100 seconds, 100 gold cost
    };

    /**
     * Purchases a pickaxe upgrade for the miner if affordable and not at max level.
     *
     * @param miner the miner to upgrade
     * @param model the game model for notifications
     * @return true if upgrade was successful, false otherwise
     */
    public static boolean buyPickaxeUpgrade(Miner miner, GameModel model) {
        int currentLevel = miner.getPickaxeLevel();

        if (isMaxPickaxeLevel(miner)) {
            return false;
        }

        int nextLevel = currentLevel + 1;

        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return false;
        }

        int cost = PICKAXE_UPGRADES[nextLevel][1];

        if (miner.getGoldAmount() < cost) {
            return false;
        }
        if (!miner.subtractGold(cost)) {
            return false;
        }

        miner.setPickaxeLevel(nextLevel);
        int newDamage = PICKAXE_UPGRADES[nextLevel][0];
        miner.setToolsDamage(newDamage);
        miner.setPickaxeImage(PICKAXE_IMAGES[nextLevel]);
        model.notifyGoldChanged();

        return true;
    }

    /**
     * Purchases an oxygen upgrade for the miner if affordable and not at max level.
     *
     * @param miner the miner to upgrade
     * @param model the game model for notifications
     * @return true if upgrade was successful, false otherwise
     */
    public static boolean buyOxygenUpgrade(Miner miner, GameModel model) {
        int currentLevel = miner.getOxygenLevel();

        if (isMaxOxygenLevel(miner)) {
            return false;
        }

        int nextLevel = currentLevel + 1;

        if (nextLevel >= OXYGEN_UPGRADES.length) {
            return false;
        }

        int cost = OXYGEN_UPGRADES[nextLevel][1];

        if (miner.getGoldAmount() < cost) {
            return false;
        }
        if (!miner.subtractGold(cost)) {
            return false;
        }

        miner.setOxygenLevel(nextLevel);
        int newOxygen = OXYGEN_UPGRADES[nextLevel][0];
        miner.setMaxOxygen(newOxygen);
        model.notifyGoldChanged();

        return true;
    }

    /**
     * Gets the next available pickaxe level.
     *
     * @param miner the miner to check
     * @return the next pickaxe level number
     */
    public static int getNextPickaxeLevel(Miner miner) {
        return miner.getPickaxeLevel() + 1;
    }

    /**
     * Gets the next available oxygen level.
     *
     * @param miner the miner to check
     * @return the next oxygen level number
     */
    public static int getNextOxygenLevel(Miner miner) {
        return miner.getOxygenLevel() + 1;
    }

    /**
     * Gets the damage value of the next pickaxe upgrade.
     *
     * @param miner the miner to check
     * @return the next damage value, or -1 if at max level
     */
    public static int getNextPickaxeDamage(Miner miner) {
        int nextLevel = getNextPickaxeLevel(miner);
        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return -1;
        }
        return PICKAXE_UPGRADES[nextLevel][0];
    }

    /**
     * Gets the oxygen time of the next oxygen upgrade.
     *
     * @param miner the miner to check
     * @return the next oxygen time, or -1 if at max level
     */
    public static int getNextOxygenTime(Miner miner) {
        int nextLevel = getNextOxygenLevel(miner);
        if (nextLevel >= OXYGEN_UPGRADES.length) {
            return -1;
        }
        return OXYGEN_UPGRADES[nextLevel][0];
    }

    /**
     * Gets the cost of the next pickaxe upgrade.
     *
     * @param miner the miner to check
     * @return the upgrade cost, or -1 if at max level
     */
    public static int getNextPickaxeCost(Miner miner) {
        int nextLevel = getNextPickaxeLevel(miner);
        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return -1;
        }
        return PICKAXE_UPGRADES[nextLevel][1];
    }

    /**
     * Gets the cost of the next oxygen upgrade.
     *
     * @param miner the miner to check
     * @return the upgrade cost, or -1 if at max level
     */
    public static int getNextOxygenCost(Miner miner) {
        int nextLevel = getNextOxygenLevel(miner);
        if (nextLevel >= OXYGEN_UPGRADES.length) {
            return -1;
        }
        return OXYGEN_UPGRADES[nextLevel][1];
    }

    /**
     * Checks if the miner has reached the maximum pickaxe level.
     *
     * @param miner the miner to check
     * @return true if at max level, false otherwise
     */
    public static boolean isMaxPickaxeLevel(Miner miner) {
        return miner.getPickaxeLevel() >= PICKAXE_UPGRADES.length - 1;
    }

    /**
     * Checks if the miner has reached the maximum oxygen level.
     *
     * @param miner the miner to check
     * @return true if at max level, false otherwise
     */
    public static boolean isMaxOxygenLevel(Miner miner) {
        return miner.getOxygenLevel() >= OXYGEN_UPGRADES.length - 1;
    }
}