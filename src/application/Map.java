package application;

public class Map {
    private final int rows, cols;
    private final Cell[][] cells;

    public Map(int rows, int cols) {
        this(rows, cols, rows / 10); 
    }

    public Map(int rows, int cols, int skyRows) {
        this.rows = rows;
        this.cols = cols;
        cells = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellType type = (i < skyRows) ? CellType.SKY : CellType.GROUND;
                cells[i][j] = new Cell(i, j, type);

                if (i < skyRows) {
                    cells[i][j].setRevealed(true); 
                }
            }
        }
    }

    public Cell[][] getCells() { return cells; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
