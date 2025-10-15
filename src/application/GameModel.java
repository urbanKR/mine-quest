package application;

import java.awt.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;

/**
 * The main game model that manages the game state, player movement, enemy behavior,
 * and game logic for the mining adventure game.
 *
 * <p>This class serves as the central controller that coordinates between the game map,
 * player (miner), enemies, and the user interface. It handles movement, pathfinding,
 * game timers, win/lose conditions, and callback mechanisms for UI updates.</p>
 */
public class GameModel {

	/**
	 * Represents the four possible movement directions for the player.
	 */
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	// Core game components
	private final Map map;
	private final Miner miner;
	private Stage gameStage;

	// Callback functions for UI updates
	private Runnable callback;
	private Runnable winCallback;
	private Runnable loseCallback;
	private Runnable goldCallback;
	private Runnable oxygenCallback;
	private Runnable shopCallback;
	private Runnable keyCollectedCallback;
	private Runnable chestOpenedCallback;

	// Game state variables
	private boolean gameWon;
	private final int startRowMiner = 4;
	private final int startColMiner = 3;
	private int colsNum;
	private int rowsNum;

	// Pathfinding queue for drag-to-move functionality
	private Queue<Point> pathQueue;

	// Key collection tracking
	private int keysCollected = 0;
	private final int totalKeys = 3;

	// Game timers
	private Timeline timer;
	private Timeline enemyTimer;
	private KeyCodeManager keyCodeManager;

	/**
	 * Constructs a new GameModel with the specified character and difficulty.
	 *
	 * @param characterImage the image file name for the player character
	 * @param difficulty the game difficulty level (EASY, MEDIUM, HARD)
	 */
	public GameModel(String characterImage, Difficulty difficulty) {
		this.miner = new Miner(startRowMiner, startColMiner, characterImage);
		this.map = new Map(miner, this, difficulty);
		this.keyCodeManager = new KeyCodeManager();
		this.gameWon = false;

		// Set up lose condition callback
		miner.setLoseCallback(() -> {
			timer.stop();
			enemyTimer.stop();
			loseCallback.run();
		});

		this.rowsNum = map.getRows();
		this.colsNum = map.getCols();

		pathQueue = new LinkedList<>();

		// Initialize miner position on the map
		map.getCells()[startRowMiner][startColMiner].setHasMiner(true);
		startTimer();

		moveEnemy();
	}

	/**
	 * Starts the enemy movement timer that moves all enemies every second.
	 * Enemies move horizontally and change direction when hitting obstacles.
	 */
	private void moveEnemy() {
		if (enemyTimer != null) {
			return;
		}

		enemyTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			Cell[][] cells = map.getCells();

			for(Enemy enemy : map.getEnemies()) {
				int newCol = enemy.getCol() + enemy.getDirection();
				int row = enemy.getRow();

				// Check if enemy can move to the new position
				if (newCol < 0 || newCol >= map.getCols() || !cells[row][newCol].isWalkable()) {
					enemy.revertDirection(); // Change direction if blocked
					continue;
				}

				// Remove enemy from current cell
				cells[enemy.getRow()][enemy.getCol()].setHasEnemy(false);
				cells[enemy.getRow()][enemy.getCol()].updateVisual();

				// Check if enemy collides with miner
				if (cells[row][newCol].hasMiner()) {
					miner.hurt(enemy.getDamage());
				}

				// Move enemy to new position
				enemy.move(row, newCol);

				// Add enemy to new cell
				cells[row][newCol].setHasEnemy(true);
				cells[row][newCol].updateVisual();
			}
		}));
		enemyTimer.setCycleCount(Timeline.INDEFINITE);
		enemyTimer.play();
	}

	/**
	 * Attempts to move the miner in the specified direction.
	 *
	 * @param direction the direction to move (UP, DOWN, LEFT, RIGHT)
	 * @return true if movement was successful, false if blocked by bounds, non-walkable cell, or enemy
	 */
	public boolean moveMiner(Direction direction) {
		int newRow = miner.getRow();
		int newCol = miner.getCol();

		// Calculate new position based on direction
		switch (direction) {
			case UP -> newRow--;
			case DOWN -> newRow++;
			case LEFT -> newCol--;
			case RIGHT -> newCol++;
		}

		Cell[][] cells = map.getCells();

		// Check if new position is within map bounds
		if (newRow < 0 || newCol < 0 || newRow >= map.getRows() || newCol >= map.getCols()) {
			return false;
		}

		// Check if target cell is walkable
		if (!cells[newRow][newCol].isWalkable()) {
			return false;
		}

		// Check if target cell contains an enemy
		if(cells[newRow][newCol].hasEnemy()) {
			return false;
		}

		// Remove miner from current cell
		cells[miner.getRow()][miner.getCol()].setHasMiner(false);

		// Update miner's position
		miner.moveTo(newRow, newCol);

		// Place miner on new cell
		cells[newRow][newCol].setHasMiner(true);

		return true;
	}

	/**
	 * Resets the game to the specified starting position.
	 *
	 * @param startRow the row to place the miner
	 * @param startCol the column to place the miner
	 */
	public void resetGame(int startRow, int startCol) {
		Cell[][] cells = map.getCells();
		cells[miner.getRow()][miner.getCol()].setHasMiner(false);

		miner.moveTo(startRow, startCol);
		cells[startRow][startCol].setHasMiner(true);

		gameWon = false;
		keysCollected = 0;
	}

	/**
	 * Calculates the direction between two positions.
	 *
	 * @param fromRow starting row
	 * @param fromCol starting column
	 * @param toRow target row
	 * @param toCol target column
	 * @return the direction to move, or null if positions are the same
	 */
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

	// ========== PATHFINDING QUEUE METHODS ==========

	/**
	 * Clears the current path queue.
	 * Used when starting a new drag movement.
	 */
	public void clearPath() {
		pathQueue.clear();
	}

	/**
	 * Adds a cell position to the path queue.
	 *
	 * @param row the row of the cell to add
	 * @param col the column of the cell to add
	 */
	public void addToPath(int row, int col) {
		pathQueue.add(new Point(row, col));
	}

	/**
	 * Initiates movement along the queued path.
	 * Processes the path queue and moves the miner step by step.
	 */
	public void moveAlongPath() {
		Point prevLocation = pathQueue.poll();

		if (prevLocation != null) {
			moveNextStep(prevLocation);
		}
	}

	/**
	 * Recursively moves the miner to the next step in the path.
	 * Uses a pause transition to create smooth sequential movement.
	 *
	 * @param prevLocation the previous location in the path
	 */
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

		// Create smooth movement with delay between steps
		PauseTransition pause = new PauseTransition(Duration.millis(200));
		pause.setOnFinished(e -> moveNextStep(coord));
		pause.play();
	}

	// ========== KEY COLLECTION METHODS ==========

	/**
	 * Collects a key and updates game state.
	 *
	 * @param keyIndex the index of the key being collected
	 */
	public void collectKey(int keyIndex) {
		keysCollected++;
		keyCodeManager.collectKey(keyIndex);
		System.out.println("Key collected! Total: " + keysCollected + "/" + totalKeys);

		if (keyCollectedCallback != null) {
			keyCollectedCallback.run();
		}
	}

	/**
	 * Checks if all required keys have been collected.
	 *
	 * @return true if all keys are collected, false otherwise
	 */
	public boolean hasAllKeys() {
		return keysCollected >= totalKeys;
	}

	// ========== GAME STATE METHODS ==========

	/**
	 * Checks and triggers win condition if all keys are collected.
	 * Stops game timers and executes win callback.
	 */
	public void checkWinCondition() {
		if (hasAllKeys() && !gameWon) {
			gameWon = true;
			System.out.println("YOU WIN!");
			if (winCallback != null) {
				timer.stop();
				enemyTimer.stop();
				winCallback.run();
			}
		}
	}

	/**
	 * Starts the oxygen depletion timer.
	 * Reduces miner's oxygen every second and triggers UI updates.
	 */
	public void startTimer() {
		timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			miner.depleteOxygen();
			oxygenCallback.run();
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}

	/**
	 * Sets the game stage reference.
	 *
	 * @param stage the JavaFX stage for the game window
	 */
	public void setGameStage(Stage stage) {
		this.gameStage = stage;
	}

	/**
	 * Opens the shop interface.
	 * Triggers the shop callback if available.
	 */
	public void openShop() {
		if (shopCallback != null) {
			shopCallback.run();
		}
	}

	// ========== CALLBACK SETTERS ==========

	/**
	 * Sets the callback for general game updates.
	 *
	 * @param callback the runnable to execute on game updates
	 */
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

	/**
	 * Sets the callback for win condition.
	 *
	 * @param winCallback the runnable to execute when player wins
	 */
	public void setWinCallback(Runnable winCallback) {
		this.winCallback = winCallback;
	}

	/**
	 * Sets the callback for lose condition.
	 *
	 * @param loseCallback the runnable to execute when player loses
	 */
	public void setLoseCallback(Runnable loseCallback) {
		this.loseCallback = loseCallback;
	}

	/**
	 * Sets the callback for gold changes.
	 *
	 * @param goldCallback the runnable to execute when gold changes
	 */
	public void setGoldCallback(Runnable goldCallback) {
		this.goldCallback = goldCallback;
	}

	/**
	 * Sets the callback for oxygen updates.
	 *
	 * @param oxygenCallback the runnable to execute when oxygen changes
	 */
	public void setOxygenCallback(Runnable oxygenCallback) {
		this.oxygenCallback = oxygenCallback;
	}

	/**
	 * Sets the callback for shop interactions.
	 *
	 * @param shopCallback the runnable to execute when shop is opened
	 */
	public void setShopCallback(Runnable shopCallback) {
		this.shopCallback = shopCallback;
	}

	/**
	 * Sets the callback for key collection events.
	 *
	 * @param callback the runnable to execute when a key is collected
	 */
	public void setKeyCollectedCallback(Runnable callback) {
		this.keyCollectedCallback = callback;
	}

	/**
	 * Sets the callback for chest opening events.
	 *
	 * @param callback the runnable to execute when chest is opened
	 */
	public void setChestOpenedCallback(Runnable callback) {
		this.chestOpenedCallback = callback;
	}

	// ========== GETTER METHODS ==========

	/**
	 * Gets the game map.
	 *
	 * @return the current map instance
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Gets the player miner.
	 *
	 * @return the miner instance
	 */
	public Miner getMiner() {
		return miner;
	}

	/**
	 * Gets the key code manager.
	 *
	 * @return the key code manager instance
	 */
	public KeyCodeManager getKeyCodeManager() {
		return keyCodeManager;
	}

	/**
	 * Checks if the game has been won.
	 *
	 * @return true if game is won, false otherwise
	 */
	public boolean isGameWon() {
		return gameWon;
	}

	/**
	 * Gets the number of keys collected.
	 *
	 * @return the count of collected keys
	 */
	public int getKeysCollected() {
		return keysCollected;
	}

	/**
	 * Gets the total number of keys required to win.
	 *
	 * @return the total keys needed
	 */
	public int getTotalKeys() {
		return totalKeys;
	}

	/**
	 * Notifies listeners that gold amount has changed.
	 * Triggers the gold callback if available.
	 */
	public void notifyGoldChanged() {
		if (goldCallback != null) {
			goldCallback.run();
		}
	}

	/**
	 * Opens the final chest and triggers completion events.
	 * Executes the chest opened callback if available.
	 */
	public void openChest() {
		if (chestOpenedCallback != null) {
			chestOpenedCallback.run();
		}
	}
}