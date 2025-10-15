package application;

import javafx.scene.layout.GridPane;

/**
 * Handles the visual representation and rendering of the game map.
 * Manages the GridPane display and cell visibility around the player.
 */
public class MapView {
	private final GridPane gridPane;
	private final GameModel gameModel;

	/**
	 * Constructs a new MapView with the specified grid pane and game model.
	 *
	 * @param gridPane the JavaFX GridPane for displaying cells
	 * @param gameModel the game model containing map data
	 */
	public MapView(GridPane gridPane, GameModel gameModel) {
		this.gridPane = gridPane;
		this.gameModel = gameModel;
		initializeGrid();
	}

	/**
	 * Initializes the grid by populating it with cells from the game model.
	 */
	private void initializeGrid() {
		gridPane.getChildren().clear();
		Cell[][] cells = gameModel.getMap().getCells();

		for (int i = 0; i < gameModel.getMap().getRows(); i++) {
			for (int j = 0; j < gameModel.getMap().getCols(); j++) {
				gridPane.add(cells[i][j], j, i);
			}
		}

		revealAroundMiner();
	}

	/**
	 * Updates the visual appearance of all cells in the map.
	 */
	public void updateView() {
		for (Cell[] row : gameModel.getMap().getCells()) {
			for (Cell cell : row) {
				cell.updateVisual();
			}
		}
	}

	/**
	 * Reveals cells around the miner's position and sets mineable status.
	 * Sky, walkable sky, and shop cells are always revealed.
	 */
	public void revealAroundMiner() {
		int minerRow = gameModel.getMiner().getRow();
		int minerCol = gameModel.getMiner().getCol();
		int maxDistance = 2;

		for (Cell[] row : gameModel.getMap().getCells()) {
			for (Cell cell : row) {
				if (cell.getType() == CellType.SKY || cell.getType() == CellType.SKY_WALKABLE ||
						cell.getType() == CellType.SHOP) {
					cell.setRevealed(true);
				} else {
					int distance = Math.abs(minerRow - cell.getRow()) + Math.abs(minerCol - cell.getCol());
					// Reveal if within distance, otherwise unreveal
					cell.setRevealed(distance <= maxDistance);
					cell.setMineable(distance <= 1);
				}
			}
		}
	}
}