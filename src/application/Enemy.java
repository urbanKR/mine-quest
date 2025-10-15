package application;

/**
 * Represents an enemy entity in the game that moves horizontally and damages the player on contact.
 *
 * <p>Enemies patrol back and forth horizontally across walkable cells. When they encounter
 * obstacles or map boundaries, they reverse direction. If an enemy moves into a cell containing
 * the player, it deals damage to the player.</p>
 *
 * <p>Enemy movement is managed by the {@link GameModel} class through a timer that triggers
 * movement updates at regular intervals.</p>
 */
public class Enemy {

	// Position and movement properties
	private int row;
	private int col;
	private int direction = 1; // 1 for right, -1 for left

	// Game object references
	private Miner player;
	private Map map;

	// Combat properties
	private int damage = 10;

	/**
	 * Constructs a new Enemy at the specified position.
	 *
	 * @param miner the player character that this enemy can damage
	 * @param map the game map for collision detection and movement validation
	 * @param row the initial row position of the enemy
	 * @param col the initial column position of the enemy
	 */
	public Enemy(Miner miner, Map map, int row, int col) {
		this.player = miner;
		this.map = map;
		this.row = row;
		this.col = col;
	}

	/**
	 * Moves the enemy to a new position.
	 *
	 * <p>This method updates the enemy's coordinates but does not perform collision checking.
	 * The calling code is responsible for validating the move before calling this method.</p>
	 *
	 * @param row the new row position
	 * @param newCol the new column position
	 */
	public void move(int row, int newCol) {
		this.row = row;
		this.col = newCol;
	}

	/**
	 * Reverses the enemy's horizontal movement direction.
	 *
	 * <p>Called when the enemy hits an obstacle or map boundary. Changes direction from
	 * right to left or left to right.</p>
	 */
	public void revertDirection() {
		direction *= -1;
	}

	// ========== GETTER METHODS ==========

	/**
	 * Gets the amount of damage this enemy deals to the player on contact.
	 *
	 * @return the damage value (default: 10)
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Gets the current column position of the enemy.
	 *
	 * @return the column coordinate
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Gets the current row position of the enemy.
	 *
	 * @return the row coordinate
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the current horizontal movement direction.
	 *
	 * @return 1 for moving right, -1 for moving left
	 */
	public int getDirection() {
		return direction;
	}
}