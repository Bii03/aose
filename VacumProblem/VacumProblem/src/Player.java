
public abstract class Player {
	
	protected int x = 0, y = 0, score = 0;
	protected char literal;
	
	public abstract boolean move(World w);
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getScore() {
		return score;
	}
	public void addScore(int sum) {
		score += sum;
	}
	public char getLiteral() {
		return literal;
	}
}
