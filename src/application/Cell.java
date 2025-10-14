package application;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import java.util.HashMap;

public class Cell extends Button {
	private int row;
	private int col;
	private CellType type;
	private int hardness;
	private boolean destroyable;
	private Miner miner;
	private Map map;
	private GameModel model;
	
	private String enemy_img = "bat.png";

	private boolean revealed = false;
	private boolean mineable = false;
	private boolean hasMiner = false;
	private boolean hasEnemy = false;
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
		this.addEventFilter(MouseDragEvent.MOUSE_DRAG_ENTERED, e -> {
			if (!walkable) {
				model.moveAlongPath();
				e.consume();
			}
		});
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

		this.setOnDragDetected(null);
		this.setOnMouseDragEntered(null);
		this.setOnMouseDragReleased(null);

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
		setPosition(this.row, this.col);
		updateVisual();
	}
	
	public boolean hasEnemy() {
		return hasEnemy;
	}
	
	public void setHasEnemy(boolean hasEnemy) {
		this.hasEnemy = hasEnemy;
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

	// --- Visual representation ---
	public void updateVisual() {
	    getStyleClass().clear();
	    getStyleClass().add("cell");
	    
	    // Enemy jest widoczny tylko gdy komórka jest revealed
	    boolean enemyVisible = hasEnemy && revealed;
	    
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
	    } else if (hasMiner) {
	        String bgColor = getBackgroundColorForType();
	        setStyle("-fx-background-color: " + bgColor + "; "
	                + "-fx-background-image: url(\"file:img/" + miner.getCharacterImage() + "\"); "
	                + "-fx-background-size: contain; "
	                + "-fx-background-repeat: no-repeat; "
	                + "-fx-background-position: center;");
	        this.setOnMouseEntered(null);
	        this.setOnMouseExited(null);
	    } else if (enemyVisible) {
	        String bgColor = getBackgroundColorForType();
	        setStyle("-fx-background-color: " + bgColor + "; "
	                + "-fx-background-image: url(\"file:img/" + enemy_img + "\"); "
	                + "-fx-background-size: contain; "
	                + "-fx-background-repeat: no-repeat; "
	                + "-fx-background-position: center;");
	        this.setOnMouseEntered(null);
	        this.setOnMouseExited(null);
	    } else if (!revealed) {
	        setStyle("");
	        getStyleClass().add("unrevealed");
	        this.setOnMouseEntered(null);
	        this.setOnMouseExited(null);
	    } else {
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
	
	private static final java.util.Map<String, ImageCursor> CURSOR_CACHE = new HashMap<>();
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