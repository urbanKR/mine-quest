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
                int distance = Math.abs(minerRow - cell.getRow()) + Math.abs(minerCol - cell.getCol());
                if (distance <= maxDistance || cell.getType() == CellType.SKY) {
                    cell.setRevealed(true);
                }
            }
        }
    }
    
    //ublic void render(GameModel model) {
    //	Map map = model.getMap();
    //	for y<maze height
    //		for c x<maze height
    ///			int c ell x = cellsiez * x
    //			int cell 
    //}
    
}
