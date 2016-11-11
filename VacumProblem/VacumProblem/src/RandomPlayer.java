import java.util.Random;

public class RandomPlayer extends Player {
	
	public RandomPlayer() {
		literal = 'r';
	}
	
	public boolean move(World w) {
		Random r = new Random();
		
		if (w.hasPrizes() == false) {
			return false;
		}
		
		if(w.isPrize(y, x)) {
			w.prizeTaken(this);
			return true;
		}
		
		int x1, y1;
		do {
			x1 = r.nextInt(3) - 1;
			y1 = r.nextInt(3) - 1;
		} while (w.canMove(y1, x1, this) == false || (x1 == 0 && y1 == 0));
		
		x += x1;
		y += y1;
		score += World.MOVE_PENALTY;
		
		return true;
	}
}
