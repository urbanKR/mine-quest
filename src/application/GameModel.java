package application;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameModel {

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	private final Map map;
	private final Miner miner;

	private Runnable callback;
	private Runnable winCallback;

	private boolean gameWon;
	private final int startRowMiner = 4;
	private final int startColMiner = 3;
	private int colsNum;
	private int rowsNum;

	private Queue<Point> pathQueue;

	private int keysCollected = 0;
	private final int totalKeys = 3;

	public GameModel() {
		this.miner = new Miner(startRowMiner, startColMiner);
		this.map = new Map(miner, this);
		this.gameWon = false;

		this.rowsNum = map.getRows();
		this.colsNum = map.getCols();

		pathQueue = new LinkedList<>();

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
		System.out.print("hi");

		return true;
	}

	public void resetGame(int startRow, int startCol) {
		Cell[][] cells = map.getCells();
		cells[miner.getRow()][miner.getCol()].setHasMiner(false);

		miner.moveTo(startRow, startCol);
		cells[startRow][startCol].setHasMiner(true);

		gameWon = false;
		keysCollected = 0;
	}

	private Direction getDirection(int fromRow, int fromCol, int toRow, int toCol) {
		if (toRow < fromRow)
			return Direction.UP;
		if (toRow > fromRow)
			return Direction.DOWN;
		if (toCol < fromCol)
			return Direction.LEFT;
		if (toCol > fromCol)
			return Direction.RIGHT;
		return null; // Same cell
	}

	// Queue handling
	public void clearPath() {
		pathQueue.clear();
	}

	public void addToPath(int row, int col) {
		pathQueue.add(new Point(row, col));
	}

	public void moveAlongPath() {
		Point prevLocation = pathQueue.poll();

		if (prevLocation != null) {
			moveNextStep(prevLocation);
		}
	}

	private void moveNextStep(Point prevLocation) {
		if (pathQueue.isEmpty()) {
			return;
		}

		Point coord = pathQueue.poll();
		Direction moveDirection = getDirection(prevLocation.x, prevLocation.y, coord.x, coord.y);
		if (moveDirection != null) {
			moveMiner(moveDirection);
		}
		prevLocation = coord;
		callback.run();

		PauseTransition pause = new PauseTransition(Duration.millis(200));
		pause.setOnFinished(e -> moveNextStep(coord)); // recursively move to next step
		pause.play();
	}

	// Key collection
	public void collectKey() {
		keysCollected++;
		System.out.println("Key collected! Total: " + keysCollected + "/" + totalKeys);
	}

	public boolean hasAllKeys() {
		return keysCollected >= totalKeys;
	}

	// Win condition check
	public void checkWinCondition() {
		if (hasAllKeys() && !gameWon) {
			gameWon = true;
			System.out.println("YOU WIN!");
			if (winCallback != null) {
				winCallback.run();
			}
		}
	}

	// Callbacks
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

	public void setWinCallback(Runnable winCallback) {
		this.winCallback = winCallback;
	}

	// getters
	public Map getMap() {
		return map;
	}

	public Miner getMiner() {
		return miner;
	}

	public boolean isGameWon() {
		return gameWon;
	}

	public int getKeysCollected() {
		return keysCollected;
	}

	public int getTotalKeys() {
		return totalKeys;
	}
}