import java.util.Random;

public class CognitivePlayer extends Player {
	
	int[][] map;
	
	public CognitivePlayer() {
		map = new int[World.N][World.N];
		for (int i = 0; i < World.N; i++){
			for (int j = 0 ; j < World.N; j++ ){
				map[i][j] = 0;
			}
		}
		literal = 'c';
	}
	
	@Override
	public boolean move(World w) {
		Random r = new Random();
		if (w.hasPrizes() == false) {
			return false;
		}
		
		if(w.isPrize(y, x)) {
			w.prizeTaken(this);
			return true;
		}
		
		map[y][x] = 1;
		
		Pair[] availableMoves = {new Pair(0,1), new Pair(1, 0), 
				new Pair(0, -1), new Pair(-1, 0)};
		
		for (Pair p : availableMoves) {
			if (y + p.y < 0 || y + p.y >= World.N)
				continue;
			if (x + p.x < 0 || x + p.x >= World.N)
				continue;
			if(map[y + p.y][x + p.x] != 1 && w.canMove(p.y, p.x, this)) {
				x += p.x;
				y += p.y;
				score += World.MOVE_PENALTY;
				return true;
			}
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
