package application;  

public class GameModel {

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private final Map map;  
    private final Miner miner;
    private boolean gameWon;

    public GameModel(int rows, int cols, int startRow, int startCol) {
        this.map = new Map(rows, cols);
        this.miner = new Miner(startRow, startCol);
        this.gameWon = false;

        map.getCells()[startRow][startCol].setHasMiner(true);
    }

    public boolean moveMiner(Direction direction) {
        int newRow = miner.getRow();
        int newCol = miner.getCol();

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        // bounds check
        if (newRow < 0 || newRow >= map.getRows() || newCol < 0 || newCol >= map.getCols()) {
            return false;
        }

        Cell[][] cells = map.getCells();

        // not walkable check
        if (!cells[newRow][newCol].isWalkable()) {
            return false;
        }

        // remove miner from old cell
        cells[miner.getRow()][miner.getCol()].setHasMiner(false);

        // move miner
        miner.moveTo(newRow, newCol);

        // add miner to new cell
        cells[newRow][newCol].setHasMiner(true);


        return true;
    }

    public void resetGame(int startRow, int startCol) {
        Cell[][] cells = map.getCells();
        cells[miner.getRow()][miner.getCol()].setHasMiner(false);

        miner.moveTo(startRow, startCol);
        cells[startRow][startCol].setHasMiner(true);

        gameWon = false;
    }

    // getters
    public Map getMap() { return map; }
    public Miner getMiner() { return miner; }
    public boolean isGameWon() { return gameWon; }
}
