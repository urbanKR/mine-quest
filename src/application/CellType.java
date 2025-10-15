package application;

/**
 * Enumerates all possible types of cells in the game world.
 *
 * <p>Each cell type defines the visual appearance, behavior, and properties of a grid cell
 * in the game map. Cells can be categorized into several groups:</p>
 *
 * <ul>
 *   <li><strong>Non-interactive:</strong> Background elements that don't affect gameplay</li>
 *   <li><strong>Walkable:</strong> Cells the player can move through</li>
 *   <li><strong>Destroyable:</strong> Blocks that can be mined for resources</li>
 *   <li><strong>Interactive:</strong> Special cells that trigger game events</li>
 *   <li><strong>Goal-oriented:</strong> Cells related to game progression and victory</li>
 * </ul>
 *
 * <p>Cell properties such as walkability, destroyability, hardness, and resource value
 * are typically defined in the {@link Cell} class constructor based on these types.</p>
 */
public enum CellType {
	/**
	 * Non-walkable sky background.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No</li>
	 *   <li><strong>Destroyable:</strong> No</li>
	 *   <li><strong>Hardness:</strong> 0</li>
	 *   <li><strong>Visual:</strong> Light blue background</li>
	 * </ul>
	 */
	SKY,

	/**
	 * Walkable sky platform (clouds or floating islands).
	 * <ul>
	 *   <li><strong>Walkable:</strong> Yes</li>
	 *   <li><strong>Destroyable:</strong> No</li>
	 *   <li><strong>Hardness:</strong> 0</li>
	 *   <li><strong>Visual:</strong> Light blue background</li>
	 * </ul>
	 */
	SKY_WALKABLE,

	/**
	 * Surface grass block - the top layer of terrain.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 1</li>
	 *   <li><strong>Gold Value:</strong> 0</li>
	 *   <li><strong>Visual:</strong> Grass texture with dirt underside</li>
	 * </ul>
	 */
	GRASS,

	/**
	 * Common dirt block found beneath grass layer.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 2</li>
	 *   <li><strong>Gold Value:</strong> 0</li>
	 *   <li><strong>Visual:</strong> Brown dirt texture</li>
	 * </ul>
	 */
	DIRT,

	/**
	 * Destroyed/empty block - result of mining other blocks.
	 * <ul>
	 *   <li><strong>Walkable:</strong> Yes</li>
	 *   <li><strong>Destroyable:</strong> No</li>
	 *   <li><strong>Hardness:</strong> 0</li>
	 *   <li><strong>Visual:</strong> Gray empty cell</li>
	 * </ul>
	 */
	DESTROYED,

	/**
	 * Special block containing a key required for game progression.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 10</li>
	 *   <li><strong>Special:</strong> Collects a key when destroyed</li>
	 *   <li><strong>Visual:</strong> Key block texture</li>
	 * </ul>
	 */
	SECRET_KEY,

	/**
	 * The final area where the victory chest is located.
	 * <ul>
	 *   <li><strong>Walkable:</strong> Yes</li>
	 *   <li><strong>Destroyable:</strong> No</li>
	 *   <li><strong>Hardness:</strong> 0</li>
	 *   <li><strong>Visual:</strong> Final area block texture</li>
	 * </ul>
	 */
	FINAL_AREA,

	/**
	 * The victory chest that can be opened when all keys are collected.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No</li>
	 *   <li><strong>Destroyable:</strong> No</li>
	 *   <li><strong>Hardness:</strong> 0</li>
	 *   <li><strong>Special:</strong> Win condition when accessed with all keys</li>
	 *   <li><strong>Visual:</strong> Chest texture</li>
	 * </ul>
	 */
	FINAL_CHEST,

	/**
	 * Shop location where player can purchase upgrades.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No</li>
	 *   <li><strong>Destroyable:</strong> No</li>
	 *   <li><strong>Hardness:</strong> 0</li>
	 *   <li><strong>Special:</strong> Opens shop interface when adjacent</li>
	 *   <li><strong>Visual:</strong> Shop building texture</li>
	 * </ul>
	 */
	SHOP,

	/**
	 * Loose gravel material - easy to mine but low value.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 5</li>
	 *   <li><strong>Gold Value:</strong> 1</li>
	 *   <li><strong>Visual:</strong> Gravel texture</li>
	 * </ul>
	 */
	GRAVEL,

	/**
	 * Common stone block - moderate difficulty to mine.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 10</li>
	 *   <li><strong>Gold Value:</strong> 2</li>
	 *   <li><strong>Visual:</strong> Stone texture</li>
	 * </ul>
	 */
	STONE,

	/**
	 * Coal ore - valuable resource for mining.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 10</li>
	 *   <li><strong>Gold Value:</strong> 5</li>
	 *   <li><strong>Visual:</strong> Coal ore texture</li>
	 * </ul>
	 */
	COAL,

	/**
	 * Iron ore - valuable metal resource.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 20</li>
	 *   <li><strong>Gold Value:</strong> 10</li>
	 *   <li><strong>Visual:</strong> Iron ore texture</li>
	 * </ul>
	 */
	IRON,

	/**
	 * Gold ore - premium high-value resource.
	 * <ul>
	 *   <li><strong>Walkable:</strong> No (initially)</li>
	 *   <li><strong>Destroyable:</strong> Yes</li>
	 *   <li><strong>Hardness:</strong> 20</li>
	 *   <li><strong>Gold Value:</strong> 20</li>
	 *   <li><strong>Visual:</strong> Gold ore texture</li>
	 * </ul>
	 */
	GOLD
}