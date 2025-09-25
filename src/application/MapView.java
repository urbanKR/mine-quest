package application;

import javafx.scene.layout.GridPane;

public class MapView {
    private final GridPane gridPane;
    private final GameModel gameModel;

    public MapView(GridPane gridPane, GameModel gameModel) {
        this.gridPane = gridPane;
        this.gameModel = gameModel;
        initializeGrid();
    }

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

    public void updateView() {
        for (Cell[] row : gameModel.getMap().getCells()) {
            for (Cell cell : row) {
                cell.updateVisual();
            }
        }
    }

    public void revealAroundMiner() {
        int minerRow = gameModel.getMiner().getRow();
        int minerCol = gameModel.getMiner().getCol();
        int maxDistance = 2;

        for (Cell[] row : gameModel.getMap().getCells()) {
            for (Cell cell : row) {
                // Always reveal SKY and GROUND
                if (cell.getType() == CellType.SKY || cell.getType() == CellType.SKY_WALKABLE) {
                    cell.setRevealed(true);
                } else {
                    int distance = Math.abs(minerRow - cell.getRow()) + Math.abs(minerCol - cell.getCol());
                    // Reveal if within distance, otherwise unreveal
                    cell.setRevealed(distance <= maxDistance);
                }
            }
        }
    }
}
