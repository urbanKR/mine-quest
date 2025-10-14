package application;

public class Enemy {
	private int row;
	private int col;
	private int direction = 1;
	
	Miner player;	
	Map map;
	
	private int damage = 10;
	
	public Enemy(Miner miner, Map map, int row, int col) {
		this.player = miner;
		this.map = map;
		this.row = row;
		this.col = col;
	}
	
	public void move(int row, int newCol) {
		this.row = row;
		this.col = newCol;
	}
	
	public void revertDirection() {
		direction *= -1;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getDirection() {
		return direction;
	}
}
