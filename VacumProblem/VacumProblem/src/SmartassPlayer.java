
public class SmartassPlayer extends Player {

	int[][] map;
	
	public SmartassPlayer() {
		map = new int[World.N][World.N];
		for (int i = 0; i < World.N; i++){
			for (int j = 0 ; j < World.N; j++ ){
				map[i][j] = 0;
			}
		}
		literal = 's';
	}
	
	@Override
	public boolean move(World w) {
		if (w.hasPrizes() == false) {
			return false;
		}
		
		if(w.isPrize(y, x)) {
			w.prizeTaken(this);
			return true;
		}
		
		Pair p = getNearestPrize(w);
		x += Math.signum(p.x - x);
		y += Math.signum(p.y - y);
		score += World.MOVE_PENALTY;
		
		return true;
	}
	
	public Pair getNearestPrize(World w) {
		int min = Integer.MAX_VALUE;
		int px = 0, py = 0;
		
		for (int i = 0; i < World.N; i++) {
			for (int j = 0; j < World.N; j++) {
				if (w.isPrize(i, j)) {
					int d = dist(y, x, i, j);
					if (d < min) {
						py = i;
						px = j;
						min = d;
					}
				}
			}
		}
		
		return new Pair(px, py);
	}
	
	private int dist(int y1, int x1, int y2, int x2) {
		int d = (int) (Math.pow(y1-y2, 2) + Math.pow(x1 - x2, 2));
		return d;
	}
}
