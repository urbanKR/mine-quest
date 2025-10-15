package application;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a single cell in the game grid, serving as the fundamental building block of the game world.
 *
 * <p>Each Cell is a JavaFX Button that displays different visual representations based on its type,
 * state, and contents. Cells can contain the player, enemies, resources, or interactive elements.
 * The class handles rendering, user interactions, mining mechanics, and visual state management.</p>
 *
 * <p>Key responsibilities include:
 * <ul>
 *   <li>Visual representation based on cell type and state</li>
 *   <li>Mining mechanics and resource collection</li>
 *   <li>Drag-based pathfinding for player movement</li>
 *   <li>Interaction handling (clicks, hover effects)</li>
 *   <li>State management (revealed, walkable, destroyed)</li>
 * </ul>
 * </p>
 */
public class Cell extends Button {

	// Position and identity
	private int row;
	private int col;
	private CellType type;

	// Mining properties
	private int hardness;
	private boolean destroyable;
	private int goldValue = 0;

	// Game object references
	private Miner miner;
	private Map map;
	private GameModel model;

	// Enemy properties
	private String enemy_img = "bat.png";

	// State flags
	private boolean revealed = false;
	private boolean mineable = false;
	private boolean hasMiner = false;
	private boolean hasEnemy = false;
	private boolean walkable = true;
	private boolean destroyed = false;

	// Key management
	private int keyIndex = -1;

	// Cursor cache for performance optimization
	private static final java.util.Map<String, ImageCursor> CURSOR_CACHE = new HashMap<>();

	/**
	 * Constructs a new Cell with the specified type and game references.
	 * Initializes cell properties based on type and sets up event handlers.
	 *
	 * @param type the type of cell (determines appearance and behavior)
	 * @param miner the player character reference
	 * @param map the game map reference for accessing adjacent cells
	 * @param model the game model for triggering game events
	 */
	public Cell(CellType type, Miner miner, Map map, GameModel model) {
		this.type = type;
		this.miner = miner;
		this.map = map;
		this.model = model;

		// Initialize cell properties based on type
		initializeCellProperties(type);

		// Configure button appearance and behavior
		setMinSize(40, 40);
		setMaxSize(40, 40);
		setFocusTraversable(false);

		// Set up drag event filter for pathfinding
		setupDragEventFilter();

		// Update visual representation
		updateVisual();

		// Set up click interaction
		setupClickInteraction();
	}

	/**
	 * Initializes cell properties (walkability, hardness, destroyability, gold value)
	 * based on the cell type.
	 *
	 * @param type the cell type to initialize properties for
	 */
	private void initializeCellProperties(CellType type) {
		switch (type) {
			case SKY, FINAL_CHEST, SHOP:
				walkable = false;
				hardness = 0;
				destroyable = false;
				break;
			case SKY_WALKABLE, FINAL_AREA:
				walkable = true;
				hardness = 0;
				destroyable = false;
				break;
			case GRASS:
				walkable = false;
				hardness = 1;
				destroyable = true;
				break;
			case DIRT:
				walkable = false;
				hardness = 2;
				destroyable = true;
				goldValue = 0;
				break;
			case SECRET_KEY:
				walkable = false;
				hardness = 10;
				destroyable = true;
				break;
			case GRAVEL:
				walkable = false;
				hardness = 5;
				destroyable = true;
				goldValue = 1;
				break;
			case STONE:
				walkable = false;
				hardness = 10;
				destroyable = true;
				goldValue = 2;
				break;
			case COAL:
				walkable = false;
				hardness = 10;
				destroyable = true;
				goldValue = 5;
				break;
			case IRON:
				walkable = false;
				hardness = 20;
				destroyable = true;
				goldValue = 10;
				break;
			case GOLD:
				walkable = false;
				hardness = 20;
				destroyable = true;
				goldValue = 20;
				break;
		}
	}

	/**
	 * Sets up the drag event filter for pathfinding.
	 * Stops pathfinding when dragging over non-walkable cells.
	 */
	private void setupDragEventFilter() {
		this.addEventFilter(MouseDragEvent.MOUSE_DRAG_ENTERED, e -> {
			if (!walkable) {
				model.moveAlongPath();
				e.consume(); // Prevent further processing
			}
		});
	}

	/**
	 * Sets up the click interaction handler.
	 * Handles shop interactions and mining based on adjacent player position.
	 */
	private void setupClickInteraction() {
		this.setOnAction(e -> {
			var adjacentCells = getAdjacentCells();
			var adjacentCellsHasMiner = adjacentCells.stream().anyMatch(Cell::hasMiner);

			if (type == CellType.SHOP && adjacentCellsHasMiner) {
				interactWithShop(model);
			} else {
				mineCell();
			}
		});
	}

	/**
	 * Attempts to mine this cell if conditions are met.
	 *
	 * <p>Mining conditions:
	 * <ul>
	 *   <li>Cell must be destroyable and not already destroyed</li>
	 *   <li>Cell must be mineable (player-accessible)</li>
	 *   <li>Player must have sufficient mining power</li>
	 * </ul>
	 *
	 * <p>When successfully mined:
	 * <ul>
	 *   <li>Reduces hardness by player's tool damage</li>
	 *   <li>If hardness reaches 0, destroys the cell</li>
	 *   <li>Grants gold if the cell has gold value</li>
	 *   <li>Collects keys if cell is SECRET_KEY type</li>
	 *   <li>Updates cell to DESTROYED type and reveals it</li>
	 * </ul>
	 * </p>
	 */
	public void mineCell() {
		System.out.printf("%d", this.hardness);

		// Check if cell can be mined
		if (destroyable && !destroyed && this.mineable) {
			// Apply mining damage
			this.hardness -= this.miner.getToolsDamage();

			// Check if cell is destroyed
			if (this.hardness <= 0) {
				setWalkable(true);
				destroyed = true;

				// Grant gold if applicable
				if(this.goldValue != 0) {
					this.miner.addGold(this.goldValue);
					model.notifyGoldChanged();
				}

				// Collect key if this is a key block
				if (type == CellType.SECRET_KEY) {
					model.collectKey(this.keyIndex);
				}

				// Update cell type and reveal it
				type = CellType.DESTROYED;
				this.setRevealed(true);
			}
		}

		// Check for win condition (final chest with all keys)
		if (type == CellType.FINAL_CHEST && this.mineable && model.hasAllKeys()) {
			model.openChest();
		}
	}

	/**
	 * Sets the grid position of this cell and configures drag-based pathfinding.
	 *
	 * @param row the row position in the grid
	 * @param col the column position in the grid
	 */
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;

		// Clear existing drag handlers
		this.setOnDragDetected(null);
		this.setOnMouseDragEntered(null);
		this.setOnMouseDragReleased(null);

		// Set up drag-based pathfinding for walkable cells
		if (walkable) {
			setupDragPathfinding();
		}
	}

	/**
	 * Configures drag-based pathfinding event handlers for walkable cells.
	 * Allows player to drag from miner position to create movement paths.
	 */
	private void setupDragPathfinding() {
		this.setOnDragDetected(e -> {
			System.out.printf("%d:%d \n", col, row);
			if (hasMiner) {
				((Cell) e.getSource()).startFullDrag();
				model.clearPath();
			}
		});

		this.setOnMouseDragEntered(e -> {
			System.out.printf("%d:%d dragging continues \n", col, row);
			model.addToPath(row, col);
			this.getStyleClass().add("marked");
		});

		this.setOnMouseDragReleased(e -> {
			System.out.printf("%d:%d dragging stopped \n", col, row);
			model.moveAlongPath();
		});
	}

	// ========== POSITION GETTERS ==========

	/**
	 * Gets the row position of this cell.
	 *
	 * @return the row coordinate
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column position of this cell.
	 *
	 * @return the column coordinate
	 */
	public int getCol() {
		return col;
	}

	// ========== KEY MANAGEMENT ==========

	/**
	 * Sets the key index for SECRET_KEY type cells.
	 *
	 * @param index the unique identifier for this key
	 */
	public void setKeyIndex(int index) {
		this.keyIndex = index;
	}

	/**
	 * Gets the key index for SECRET_KEY type cells.
	 *
	 * @return the key index, or -1 if not a key cell
	 */
	public int getKeyIndex() {
		return keyIndex;
	}

	// ========== PROPERTY GETTERS AND SETTERS ==========

	/**
	 * Gets the type of this cell.
	 *
	 * @return the cell type
	 */
	public CellType getType() {
		return type;
	}

	/**
	 * Sets the type of this cell and updates its visual representation.
	 *
	 * @param type the new cell type
	 */
	public void setType(CellType type) {
		this.type = type;
		updateVisual();
	}

	/**
	 * Checks if this cell has been revealed (visible to player).
	 *
	 * @return true if revealed, false otherwise
	 */
	public boolean isRevealed() {
		return revealed;
	}

	/**
	 * Sets the revealed state of this cell and updates visual representation.
	 *
	 * @param revealed true to reveal the cell, false to hide it
	 */
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
		updateVisual();
	}

	/**
	 * Checks if this cell contains the player character.
	 *
	 * @return true if miner is present, false otherwise
	 */
	public boolean hasMiner() {
		return hasMiner;
	}

	/**
	 * Sets whether this cell contains the player character.
	 * Removes pathfinding markers and updates visual representation.
	 *
	 * @param hasMiner true to place miner in this cell, false to remove
	 */
	public void setHasMiner(boolean hasMiner) {
		this.hasMiner = hasMiner;
		this.getStyleClass().remove("marked");
		updateVisual();
	}

	/**
	 * Checks if this cell is walkable (player can move through it).
	 *
	 * @return true if walkable, false otherwise
	 */
	public boolean isWalkable() {
		return walkable;
	}

	/**
	 * Sets the walkable state of this cell and updates position handlers.
	 *
	 * @param walkable true to make cell walkable, false to block movement
	 */
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
		setPosition(this.row, this.col);
		updateVisual();
	}

	/**
	 * Checks if this cell contains an enemy.
	 *
	 * @return true if enemy is present, false otherwise
	 */
	public boolean hasEnemy() {
		return hasEnemy;
	}

	/**
	 * Sets whether this cell contains an enemy.
	 *
	 * @param hasEnemy true to place enemy in this cell, false to remove
	 */
	public void setHasEnemy(boolean hasEnemy) {
		this.hasEnemy = hasEnemy;
	}

	/**
	 * Sets whether this cell can be mined by the player.
	 *
	 * @param mineable true if player can mine this cell, false otherwise
	 */
	public void setMineable(boolean mineable) {
		this.mineable = mineable;
		updateVisual();
	}

	// ========== INTERACTION METHODS ==========

	/**
	 * Interacts with the shop if this cell is a SHOP type.
	 *
	 * @param model the game model to trigger shop opening
	 */
	public void interactWithShop(GameModel model) {
		if (type == CellType.SHOP) {
			model.openShop();
		}
	}

	// ========== VISUAL REPRESENTATION ==========

	/**
	 * Updates the visual representation of this cell based on its state and contents.
	 *
	 * <p>Visual priority (highest to lowest):
	 * <ol>
	 *   <li>Miner + Enemy (both visible)</li>
	 *   <li>Miner only</li>
	 *   <li>Enemy only (if revealed)</li>
	 *   <li>Unrevealed cell (fog of war)</li>
	 *   <li>Revealed cell by type</li>
	 * </ol>
	 * </p>
	 */
	public void updateVisual() {
		getStyleClass().clear();
		getStyleClass().add("cell");

		// Enemy is only visible when cell is revealed
		boolean enemyVisible = hasEnemy && revealed;

		// Handle combined miner and enemy display
		if (hasMiner && enemyVisible) {
			String bgColor = getBackgroundColorForType();
			setStyle("-fx-background-color: " + bgColor + "; "
					+ "-fx-background-image: url(\"file:img/" + miner.getCharacterImage() + "\"), "
					+ "url(\"file:img/" + enemy_img + "\"); "
					+ "-fx-background-size: contain, contain; "
					+ "-fx-background-repeat: no-repeat, no-repeat; "
					+ "-fx-background-position: center, center;");
			this.setOnMouseEntered(null);
			this.setOnMouseExited(null);
		}
		// Handle miner-only display
		else if (hasMiner) {
			String bgColor = getBackgroundColorForType();
			setStyle("-fx-background-color: " + bgColor + "; "
					+ "-fx-background-image: url(\"file:img/" + miner.getCharacterImage() + "\"); "
					+ "-fx-background-size: contain; "
					+ "-fx-background-repeat: no-repeat; "
					+ "-fx-background-position: center;");
			this.setOnMouseEntered(null);
			this.setOnMouseExited(null);
		}
		// Handle enemy-only display
		else if (enemyVisible) {
			String bgColor = getBackgroundColorForType();
			setStyle("-fx-background-color: " + bgColor + "; "
					+ "-fx-background-image: url(\"file:img/" + enemy_img + "\"); "
					+ "-fx-background-size: contain; "
					+ "-fx-background-repeat: no-repeat; "
					+ "-fx-background-position: center;");
			this.setOnMouseEntered(null);
			this.setOnMouseExited(null);
		}
		// Handle unrevealed cell (fog of war)
		else if (!revealed) {
			setStyle("");
			getStyleClass().add("unrevealed");
			this.setOnMouseEntered(null);
			this.setOnMouseExited(null);
		}
		// Handle revealed cell by type
		else {
			setStyle("");
			switch (type) {
				case SKY, SKY_WALKABLE -> {
					getStyleClass().add("sky");
					this.setOnMouseEntered(null);
					this.setOnMouseExited(null);
				}
				case FINAL_CHEST -> {
					getStyleClass().add("final-chest");
					this.setOnMouseEntered(null);
					this.setOnMouseExited(null);
				}
				case GRASS -> {
					getStyleClass().add("grass");
					setMiningCursor();
				}
				case DIRT -> {
					getStyleClass().add("dirt");
					setMiningCursor();
				}
				case GRAVEL -> {
					getStyleClass().add("gravel");
					setMiningCursor();
				}
				case STONE -> {
					getStyleClass().add("stone");
					setMiningCursor();
				}
				case COAL -> {
					getStyleClass().add("coal");
					setMiningCursor();
				}
				case IRON -> {
					getStyleClass().add("iron");
					setMiningCursor();
				}
				case GOLD -> {
					getStyleClass().add("gold");
					setMiningCursor();
				}
				case DESTROYED -> {
					getStyleClass().add("destroyed");
					this.setOnMouseEntered(null);
					this.setOnMouseExited(null);
				}
				case SECRET_KEY -> {
					getStyleClass().add("secret-key");
					this.setOnMouseEntered(null);
					this.setOnMouseExited(null);
				}
				case FINAL_AREA -> {
					getStyleClass().add("final-area");
					this.setOnMouseEntered(null);
					this.setOnMouseExited(null);
				}
				case SHOP -> {
					getStyleClass().add("shop");
					this.setOnMouseEntered(null);
					this.setOnMouseExited(null);
				}
			}
		}
	}

	/**
	 * Sets up mining cursor for destroyable cells.
	 * Uses caching to optimize cursor loading performance.
	 */
	private void setMiningCursor() {
		String pickaxePath = "file:img/" + miner.getPickaxeImage();

		this.setOnMouseEntered(e -> {
			ImageCursor cursor = CURSOR_CACHE.computeIfAbsent(pickaxePath, path -> {
				return new ImageCursor(new Image(path));
			});
			this.setCursor(cursor);
		});

		this.setOnMouseExited(e -> {
			this.setCursor(Cursor.DEFAULT);
		});
	}

	/**
	 * Gets the background color for this cell type.
	 * Used when cell contains miner or enemy to provide contextual background.
	 *
	 * @return the CSS color value for this cell type
	 */
	private String getBackgroundColorForType() {
		if (!revealed) {
			return "#A0A0A0"; // Fog of war color
		}

		return switch (type) {
			case GRASS -> "#A3E055";     // Light green
			case DIRT -> "#8B4513";      // Brown
			case DESTROYED -> "#7a7672"; // Gray
			case SECRET_KEY -> "#FFD700"; // Gold
			case FINAL_AREA -> "#386251"; // Dark green
			case SHOP -> "transparent";   // No background
			default -> "#87CEEB";        // Sky blue (default)
		};
	}

	/**
	 * Gets all adjacent cells including diagonals (8-directional).
	 *
	 * @return a list of adjacent cells, excluding out-of-bounds positions
	 */
	private List<Cell> getAdjacentCells() {
		List<Cell> adjacentCells = new ArrayList<>();
		Cell[][] cells = map.getCells();

		// 8-directional offsets: up, down, left, right, and diagonals
		int[][] directions = {
				{-1, 0}, {1, 0}, {0, -1}, {0, 1},     // Cardinal directions
				{-1, -1}, {-1, 1}, {1, -1}, {1, 1}    // Diagonal directions
		};

		for (int[] d : directions) {
			int newRow = row + d[0];
			int newCol = col + d[1];

			// Check bounds before adding
			if (newRow >= 0 && newRow < map.getRows() && newCol >= 0 && newCol < map.getCols()) {
				adjacentCells.add(cells[newRow][newCol]);
			}
		}

		return adjacentCells;
	}
}