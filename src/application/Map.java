package application;

public class Map {
	private final int rows = 30;
	private final int cols = 20;
	private final Cell[][] cells;
	public Map(Miner miner, GameModel model) {
		// 0 = SKY
		// 1 = SKY_WALKABLE(GROUND)
		// 2 = GRASS
		// 3 = DIRT
		// 4 = SECRET_KEY
		// 5 = FINAL_AREA
		// 6 = FINAL_CHEST
		// 7 = SHOP
		// 8 = GRAVEL
		// 9 = STONE
		// 10 = COAL
		// 11 = IRON
		// 12 = GOLD
		int[][] layout = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7 },
				{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 8, 8, 9, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 8, 9, 9, 8, 3, 3, 3, 10, 10, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 10, 10, 10, 10, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 10, 10, 10, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 10, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3, 4, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 11, 11, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 11, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 12, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 12,12 , 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
				{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
				{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5 },
				{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 }
		};

		cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				CellType type;
				switch (layout[i][j]) {
					case 1 -> type = CellType.SKY_WALKABLE;
					case 2 -> type = CellType.GRASS;
					case 3 -> type = CellType.DIRT;
					case 4 -> type = CellType.SECRET_KEY;
					case 5 -> type = CellType.FINAL_AREA;
					case 6 -> type = CellType.FINAL_CHEST;
					case 7 -> type = CellType.SHOP;
					case 8 -> type = CellType.GRAVEL;
					case 9 -> type = CellType.STONE;
					case 10 -> type = CellType.COAL;
					case 11 -> type = CellType.IRON;
					case 12 -> type = CellType.GOLD;
					default -> type = CellType.SKY;
				}
				cells[i][j] = new Cell(type, miner, this, model);

				cells[i][j].setPosition(i, j);
			}
		}
	}

	public Cell[][] getCells() {
		return cells;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
}