package application;

public class Shop {
    private static final int[][] PICKAXE_UPGRADES = {
            {1, 10},      // Level 0: 1 damage, 10 gold cost
            {2, 20},      // Level 1: 2 damage, 20 gold cost
            {5, 50},      // Level 2: 5 damage, 50 gold cost
            {10, 70},     // Level 3: 10 damage, 70 gold cost
            {20, 100}     // Level 4: 20 damage, 100 gold cost (MAX)
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

    public static int getNextPickaxeLevel(Miner miner) {
        return miner.getPickaxeLevel() + 1;
    }

    public static int getNextPickaxeDamage(Miner miner) {
        int nextLevel = getNextPickaxeLevel(miner);
        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return -1;
        }
        return PICKAXE_UPGRADES[nextLevel][0];
    }

    public static int getNextPickaxeCost(Miner miner) {
        int nextLevel = getNextPickaxeLevel(miner);
        if (nextLevel >= PICKAXE_UPGRADES.length) {
            return -1;
        }
        return PICKAXE_UPGRADES[nextLevel][1];
    }

    public static boolean isMaxPickaxeLevel(Miner miner) {
        return miner.getPickaxeLevel() >= PICKAXE_UPGRADES.length - 1;
    }
}