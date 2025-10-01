package application;  

public class GameModel {

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private final Map map;  
    private final Miner miner;
    private boolean gameWon;
    private final int startRowMiner = 4;
    private final int startColMiner = 3;
    private int colsNum;
    private int rowsNum;

    public GameModel() {
        this.map = new Map();
        this.miner = new Miner(startRowMiner, startColMiner);
        this.gameWon = false;

        this.rowsNum = map.getRows();
        this.colsNum = map.getCols();
        
        // Place miner on the map at start
        map.getCells()[startRowMiner][startColMiner].setHasMiner(true);  
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

        Cell[][] cells = map.getCells();

        // bounds check
        if (newRow < 0 || newCol < 0 || newRow >= map.getRows() || newCol >= map.getCols()) {
            return false;
        }

        // check if target cell is walkable
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