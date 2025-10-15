package application;

import static application.Difficulty.getEasyLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game map containing cells, enemies, and game layout.
 * Constructs the game world based on the selected difficulty level.
 */
public class Map {
	private int rows = 30;
	private int cols = 20;
	private final Cell[][] cells;

	private List<Enemy> enemies;
	private int keyCounter = 0;

	/**
	 * Constructs a new map with the specified difficulty.
	 *
	 * @param miner the player character
	 * @param model the game model for cell interactions
	 * @param difficulty the difficulty level determining map layout
	 */
	public Map(Miner miner, GameModel model, Difficulty difficulty) {
		int[][] layout = switch (difficulty) {
			case MEDIUM -> Difficulty.getMediumLayout();
			case HARD -> Difficulty.getHardLayout();
			default -> Difficulty.getEasyLayout();
		};

		this.rows = layout.length;
		this.cols = layout[0].length;

		this.enemies = new ArrayList<>();

		cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				boolean enemy = false;
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
					case -1 -> {type = CellType.DESTROYED; enemy = true; enemies.add(new Enemy(miner, this,i,j));}
					case 100 -> type = CellType.DESTROYED;
					default -> type = CellType.SKY;
				}

				cells[i][j] = new Cell(type, miner, this, model);

				if (type == CellType.SECRET_KEY) {
					cells[i][j].setKeyIndex(keyCounter);
					keyCounter++;
				}
				cells[i][j].setHasEnemy(enemy);
				cells[i][j].setPosition(i, j);
			}
		}
	}

	/**
	 * Gets the 2D array of cells representing the map.
	 *
	 * @return the grid of cells
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * Gets the number of rows in the map.
	 *
	 * @return the row count
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Gets the number of columns in the map.
	 *
	 * @return the column count
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Gets the list of enemies on the map.
	 *
	 * @return the list of enemy objects
	 */
	public List<Enemy> getEnemies() {
		return enemies;
	}
}