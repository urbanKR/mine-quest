package application;

import javafx.scene.control.Button;

public class Cell extends Button {
	private int row;
	private int col;
	private CellType type;
	private int hardness;
	private boolean destroyable;
	private Miner miner;
	private Map map;
	private GameModel model;

	private boolean revealed = false;
	private boolean mineable = false;
	private boolean hasMiner = false;
	private boolean walkable = true;
	private boolean goal = false;
	private boolean destroyed = false;

	public Cell(CellType type, Miner miner, Map map, GameModel model) {
		this.type = type;
		this.miner = miner;
		this.map = map;
		this.model = model;

		switch (type) {
		case SKY:
			walkable = false;
			hardness = 0;
			destroyable = false;
			break;
		case SKY_WALKABLE, FINAL_AREA, FINAL_CHEST:
			walkable = true;
			hardness = 0;
			destroyable = false;
			break;
		case GRASS:
			walkable = false;
			hardness = 3;
			destroyable = true;
			break;
		case DIRT:
			walkable = false;
			hardness = 6;
			destroyable = true;
			break;
		case SECRET_KEY:
			walkable = false;
			hardness = 10;
			destroyable = true;
			break;
		}

		setMinSize(40, 40);
		setMaxSize(40, 40);
		setFocusTraversable(false);
		updateVisual();

		this.setOnAction(e -> mineCell());
	}

	public void mineCell() {
		System.out.printf("%d", this.hardness);
		if (destroyable && !destroyed && this.mineable) {
			this.hardness -= this.miner.getToolsDamage();

			if (this.hardness <= 0) {
				setWalkable(true);
				destroyed = true;

				if (type == CellType.SECRET_KEY) {
					model.collectKey();
					type = CellType.DESTROYED;
				} else {
					type = CellType.DESTROYED;
				}

				this.setRevealed(true);
			}
		}
	}

	// Set position
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;

		// dragging setup
		if (walkable) {

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
			});
			this.setOnMouseDragReleased(e -> {
				System.out.printf("%d:%d dragging stopped \n", col, row);
				model.moveAlongPath();
			});
		}
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	// --- Getters and setters ---
	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
		updateVisual();
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
		updateVisual();
	}

	public boolean hasMiner() {
		return hasMiner;
	}

	public void setHasMiner(boolean hasMiner) {
		this.hasMiner = hasMiner;

		if (hasMiner && type == CellType.FINAL_CHEST) {
			model.checkWinCondition();
		}

		updateVisual();
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;

		if (walkable) {
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
			});
			this.setOnMouseDragReleased(e -> {
				System.out.printf("%d:%d dragging stopped \n", col, row);
				model.moveAlongPath();
			});
		}
		updateVisual();
	}

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
		updateVisual();
	}

	public void setMineable(boolean mineable) {
		this.mineable = mineable;
		updateVisual();
	}

	/** @noinspection CssUnknownTarget*/ // --- Visual representation ---
	public void updateVisual() {
		if (hasMiner) {
			String backgroundColor = getBackgroundColorForType();

			setStyle("-fx-background-color: " + backgroundColor + "; "
					+ "-fx-background-image: url('file:img/miner-version1.png'); " + "-fx-background-size: contain; "
					+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
					+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");

			if (!backgroundColor.isEmpty()) {
				setStyle(getStyle() + " -fx-background-color: " + backgroundColor + ";");
			}
		} else if (!revealed) {
			setStyle("-fx-background-color: #A0A0A0; -fx-border-color: #808080; -fx-border-width: 1px;");
		} else {
			switch (type) {
			case SKY, SKY_WALKABLE:
				setStyle("-fx-background-color: #87CEEB; -fx-border-color: #87CEEB; -fx-border-width: 1px;");
				break;
			case FINAL_CHEST:
				setStyle("-fx-background-color: #7a6860; -fx-border-color: #413838; -fx-border-width: 1px;");
				break;
			case GRASS:
				setStyle("-fx-background-color: #A3E055; -fx-border-color: #469B11; -fx-border-width: 1px;");
				break;
			case DIRT:
				setStyle("-fx-background-color: #8B4513; -fx-border-color: #5A2E0F; -fx-border-width: 1px;");
				break;
			case DESTROYED:
				setStyle("-fx-background-color: #7a7672; -fx-border-color: #5A2E0F; -fx-border-width: 1px;");
				break;
			case SECRET_KEY:
				setStyle("-fx-background-color: #FFD700; -fx-border-color: #FFA500; -fx-border-width: 2px;");
				break;
			case FINAL_AREA:
				setStyle("-fx-background-color: #90EE90; -fx-border-color: #228B22; -fx-border-width: 2px;");
				break;
			}
		}
	}

	private String getBackgroundColorForType() {
		if (!revealed) {
			return "#A0A0A0";
		}

		switch (type) {
		case SKY:
		case SKY_WALKABLE:
			return "#87CEEB";
		case GRASS:
			return "#A3E055";
		case DIRT:
			return "#8B4513";
		case DESTROYED:
			return "#7a7672";
		case SECRET_KEY:
			return "#FFD700";
		case FINAL_AREA:
			return "#90EE90";
		default:
			return "#87CEEB";
		}
	}
}