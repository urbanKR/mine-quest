package application;

/**
 * Represents the difficulty levels available in the game, each with predefined map layouts.
 *
 * <p>Each difficulty level provides a different map configuration that affects:
 * <ul>
 *   <li>Resource distribution and density</li>
 *   <li>Enemy placement and frequency</li>
 *   <li>Map complexity and challenge level</li>
 *   <li>Overall game progression pace</li>
 * </ul>
 * </p>
 *
 * <p>The enum also contains static methods that return pre-defined 2D array map layouts
 * where each integer corresponds to a specific cell type.</p>
 */
public enum Difficulty {
    /**
     * Easy difficulty - simpler layout with abundant resources and fewer challenges.
     * Suitable for beginners learning game mechanics.
     */
    EASY("Easy"),

    /**
     * Medium difficulty - balanced layout with moderate resource distribution and challenges.
     * Provides a well-rounded gaming experience.
     */
    MEDIUM("Medium"),

    /**
     * Hard difficulty - complex layout with scarce resources and increased challenges.
     * Designed for experienced players seeking greater difficulty.
     */
    HARD("Hard");

    /** The user-friendly display name for the difficulty level */
    private final String displayName;

    /**
     * Constructs a Difficulty level with the specified display name.
     *
     * @param displayName the user-visible name for this difficulty level
     */
    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of this difficulty level.
     *
     * @return the user-friendly name (e.g., "Easy", "Medium", "Hard")
     */
    public String getDisplayName() {
        return displayName;
    }

    // ========== CELL TYPE MAPPING CONSTANTS ==========
    // The following integers represent different cell types in the map layouts:
    // 0  = SKY (non-walkable background)
    // 1  = SKY_WALKABLE (walkable ground surface)
    // 2  = GRASS (surface block, destroyable)
    // 3  = DIRT (common underground block, destroyable)
    // 4  = SECRET_KEY (special block containing keys, destroyable)
    // 5  = FINAL_AREA (end game walkable area)
    // 6  = FINAL_CHEST (victory chest, requires all keys)
    // 7  = SHOP (merchant interaction point)
    // 8  = GRAVEL (loose material, low hardness, destroyable)
    // 9  = STONE (common rock, moderate hardness, destroyable)
    // 10 = COAL (valuable resource, moderate hardness, destroyable)
    // 11 = IRON (valuable resource, high hardness, destroyable)
    // 12 = GOLD (premium resource, high hardness, destroyable)
    // 100 = Special marker (typically used for enemy spawn positions)

    /**
     * Returns the pre-defined map layout for Easy difficulty.
     * @return a 2D integer array representing the easy difficulty map layout
     */
    public static int[][] getEasyLayout() {
        return new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 100, -1, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 8, 8, 9, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 8, 9, 9, 8, 3, 3, 3, 10, 10, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 10, 10, 10, 10, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 10, 10, 10, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 10, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 3, 4, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 11, 11, 11, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 11, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 100, -1, 3, 3, 3, 12, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 12, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 }
        };


    }

    /**
     * Returns the pre-defined map layout for Medium difficulty.
     * @return a 2D integer array representing the medium difficulty map layout
     */
    public static int[][] getMediumLayout() {
        return new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 100, -1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 100, -1, 8, 8, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 8, 8, 9, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 8, 9, 9, 8, 3, 3, 3, 11, 10, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 11, 11, 10, 10, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 11, 11, 3, 3, 3, 3, 3, 11, 11, 10, 10, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 11, 11, 3, 3, 3, 3, 3, 3, 10, 10, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 3, 3, 100, -1, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 11, 11, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 11, 11, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 8, 8, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 12, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 12, 12, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 100, -1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 100, -1, 3, 3, 3, 3, 3 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 }
        };


    }

    /**
     * Returns the pre-defined map layout for Hard difficulty.
     * @return a 2D integer array representing the hard difficulty map layout
     */
    public static int[][] getHardLayout() {
        return new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 100, -1, 9, 9, 9, 10, 10, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 4, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 9, 100, -1, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9, 9, 100, -1, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 11, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 11, 11, 11, 9, 100, -1, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 11, 11, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 100, -1, 9, 9, 9, 9, 4 },
                { 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 4, 12, 12, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 12, 12, 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 100, -1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 }
        };
    }
}
