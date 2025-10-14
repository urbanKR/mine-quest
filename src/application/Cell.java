package application;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

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
	private boolean destroyed = false;
	private int goldValue = 0;

	public Cell(CellType type, Miner miner, Map map, GameModel model) {
		this.type = type;
		this.miner = miner;
		this.map = map;
		this.model = model;
		
		

		switch (type) {
			case SKY, FINAL_CHEST:
				walkable = false;
				hardness = 0;
				destroyable = false;
				break;
			case SKY_WALKABLE, FINAL_AREA, SHOP:
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

		setMinSize(40, 40);
		setMaxSize(40, 40);
		setFocusTraversable(false);
		updateVisual();

		this.setOnAction(e -> {
			if (type == CellType.SHOP && hasMiner) {
				interactWithShop(model);
			} else {
				mineCell();
			}
		});
	}

	public void mineCell() {
		System.out.printf("%d", this.hardness);
		if (destroyable && !destroyed && this.mineable) {
			this.hardness -= this.miner.getToolsDamage();

			if (this.hardness <= 0) {
				setWalkable(true);
				destroyed = true;

				if(this.goldValue != 0) {
					this.miner.addGold(this.goldValue);
					model.notifyGoldChanged();
				}

				if (type == CellType.SECRET_KEY) {
					model.collectKey();
				}

				type = CellType.DESTROYED;
				this.setRevealed(true);
			}
		}

		if (type == CellType.FINAL_CHEST && this.mineable && model.hasAllKeys()) {
			model.checkWinCondition();
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
				this.getStyleClass().add("marked");
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
		this.getStyleClass().remove("marked");
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
				this.getStyleClass().add("marked");
				model.addToPath(row, col);
			});
			this.setOnMouseDragReleased(e -> {
				System.out.printf("%d:%d dragging stopped \n", col, row);
				model.moveAlongPath();
			});
		}
		updateVisual();
	}

	public void setMineable(boolean mineable) {
		this.mineable = mineable;
		updateVisual();
	}

	public void interactWithShop(GameModel model) {
		if (type == CellType.SHOP) {
			model.openShop();
		}
	}

	/** @noinspection CssUnknownTarget*/ // --- Visual representation ---
	public void updateVisual() {
		if (hasMiner) {
			String backgroundColor = getBackgroundColorForType();

			setStyle("-fx-background-color: " + backgroundColor + "; "
					+ "-fx-background-image: url('file:img/" + miner.getCharacterImage() + "'); "
					+ "-fx-background-size: contain; "
					+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
					+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
			this.setOnMouseEntered(null);
			if (!backgroundColor.isEmpty()) {
				setStyle(getStyle() + " -fx-background-color: " + backgroundColor + ";");
			}
		} else if (!revealed) {
			setStyle("-fx-background-color: #A0A0A0; -fx-border-color: #808080; -fx-border-width: 1px;");
			this.setOnMouseEntered(null);
		} else {
			String s = "file:img/" + miner.getPickaxeImage();
			Image image = new Image(s);

			switch (type) {
				case SKY, SKY_WALKABLE:
					setStyle("-fx-background-color: #87CEEB; -fx-border-color: #87CEEB; -fx-border-width: 1px;");
					this.setOnMouseEntered(null);
					break;
				case FINAL_CHEST:
					setStyle("-fx-background-image: url('file:img/chest.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(null);
					break;
				case GRASS:
					setStyle("-fx-background-image: url('file:img/dirt-grass.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case DIRT:
					setStyle("-fx-background-image: url('file:img/dirt.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case GRAVEL:
					setStyle("-fx-background-image: url('file:img/gravel.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case STONE:
					setStyle("-fx-background-image: url('file:img/stone.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case COAL:
					setStyle("-fx-background-image: url('file:img/coal.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case IRON:
					setStyle("-fx-background-image: url('file:img/iron.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case GOLD:
					setStyle("-fx-background-image: url('file:img/gold.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(e -> {
						this.setCursor(new ImageCursor(image));;
					});
					this.setOnMouseExited(e -> {
						this.setCursor(Cursor.DEFAULT);
					});
					break;
				case DESTROYED:
					setStyle("-fx-background-color: #7a7672; -fx-border-color: #5A2E0F; -fx-border-width: 1px;");
					this.setOnMouseEntered(null);
					break;
				case SECRET_KEY:
					setStyle("-fx-background-image: url('file:img/key-block.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(null);
					break;
				case FINAL_AREA:
					setStyle("-fx-background-image: url('file:img/final-area-block.png'); "
							+ "-fx-background-size: contain; "
							+ "-fx-background-repeat: no-repeat; " + "-fx-background-position: center; "
							+ "-fx-background-insets: 0; " + "-fx-background-radius: 0; " + "-fx-border-radius: 0;");
					this.setOnMouseEntered(null);
					break;
				case SHOP:
					setStyle("-fx-background-image: url('file:img/shop.png'); " +
							"-fx-background-size: 120%; " +
							"-fx-background-repeat: no-repeat; " +
							"-fx-background-position: center; " +
							"-fx-background-color: transparent; " +
							"-fx-border-color: transparent; " +
							"-fx-border-width: 0px;");
					this.setOnMouseEntered(null);
					break;
			}
		}
	}

	private String getBackgroundColorForType() {
		if (!revealed) {
			return "#A0A0A0";
		}

        return switch (type) {
            case GRASS -> "#A3E055";
            case DIRT -> "#8B4513";
            case DESTROYED -> "#7a7672";
            case SECRET_KEY -> "#FFD700";
            case FINAL_AREA -> "#386251";
            case SHOP -> "transparent";
            default -> "#87CEEB";
        };
	}
}