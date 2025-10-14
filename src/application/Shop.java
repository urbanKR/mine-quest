package application;

public class Shop {
    private static final int[][] PICKAXE_UPGRADES = {
            {1, 10},      // Level 0: 1 damage, 10 gold cost
            {2, 20},      // Level 1: 2 damage, 20 gold cost
            {5, 50},      // Level 2: 5 damage, 50 gold cost
            {10, 70},     // Level 3: 10 damage, 70 gold cost
            {20, 100}     // Level 4: 20 damage, 100 gold cost (MAX)
    };

    private static final int[][] OXYGEN_UPGRADES = {
            {1, 10},      // Level 0: 30 seconds, 10 gold cost
            {50, 30},      // Level 1: 50 seconds, 30 gold cost
            {60, 50},      // Level 2: 60 seconds, 50 gold cost
            {80, 70},     // Level 3: 80 seconds, 70 gold cost
            {20, 100}     // Level 4: 100 seconds, 100 gold cost (MAX)
    };

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
        model.notifyGoldChanged();

        return true;
    }
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

    public static int getNextPickaxeLevel(Miner miner) {
        return miner.getPickaxeLevel() + 1;
    }
    public static int getNextOxygenLevel(Miner miner) {
        return miner.getOxygenLevel() + 1;
    }

    public static int getNextPickaxeDamage(Miner miner) {
        int nextLevel = getNextPickaxeLevel(miner);
        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return -1;
        }
        return PICKAXE_UPGRADES[nextLevel][0];
    }

    public static int getNextOxygenTime(Miner miner) {
        int nextLevel = getNextOxygenLevel(miner);
        if (nextLevel >= OXYGEN_UPGRADES.length) {
            return -1;
        }
        return OXYGEN_UPGRADES[nextLevel][0];
    }

    public static int getNextPickaxeCost(Miner miner) {
        int nextLevel = getNextPickaxeLevel(miner);
        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return -1;
        }
        return PICKAXE_UPGRADES[nextLevel][1];
    }

    public static int getNextOxygenCost(Miner miner) {
        int nextLevel = getNextOxygenLevel(miner);
        if (nextLevel >= OXYGEN_UPGRADES.length) {
            return -1;
        }
        return OXYGEN_UPGRADES[nextLevel][1];
    }

    public static boolean isMaxPickaxeLevel(Miner miner) {
        return miner.getPickaxeLevel() >= PICKAXE_UPGRADES.length - 1;
    }

    public static boolean isMaxOxygenLevel(Miner miner) {
        return miner.getOxygenLevel() >= OXYGEN_UPGRADES.length - 1;
    }
}