import java.util.Random;

public class World {
	public static final int N = 8;
	public static final int PRIZE_VALUE = 100;
	public static final int MOVE_PENALTY = -1;
	
	int[][] B;
	private static World Instance = null;
	
	private World(){
		B = new int[N][N];
		
		Random r = new Random();
		
		for (int i = 0; i<N; i++){
			for (int j = 0 ; j< N; j++ ){
				switch(r.nextInt()%3){
				case 0 : B[i][j] = 0; //blank
				break;
				case 1 : B[i][j] = 1; //prize
				break;
				case 2 : B[i][j] = -1; //wall
				break;
				}
			}
		}
		
		B[0][0] = 0;
		
		for (int i = 0; i < N - 1; i++){
			for (int j = 0 ; j < N - 1; j++ ){
				if(B[i][j] == -1 && (B[i][j+1] == -1 || B[i+1][j] == -1 || B[i+1][j+1] == -1)) {
					B[i][j] = 0;
				}
			}
		}
	}
	
	public static World getInstance() {
		if (Instance == null) {
			Instance = new World();
		}
		return Instance;
	}
	
	public boolean isObstacle(int y, int x) {
		if (B[y][x] == -1) {
			return true;
		}
		return false;
	}
	
	public boolean isPrize(int y, int x) {
		if (B[y][x] == 1) {
			return true;
		}
		return false;
	}
	
	public void prizeTaken(Player p) {
		if (isPrize(p.getY(), p.getX())) {
			B[p.getY()][p.getX()] = 0;
			p.addScore(PRIZE_VALUE);
		}
	}
	
	public void displayWorld(Player[] ps) {
		for (int i = 0; i < N; i++){
			for (int j = 0 ; j < N; j++ ) {
				boolean hasPlayer = false;
				char literal = '0';
				
				for (Player p : ps) {
					if (i == p.getY() &&j == p.getX()) {
						hasPlayer = true;
						literal = p.getLiteral();
						break;
					}
				}
				
				if (hasPlayer) {
					System.out.print(literal + " ");
				}
				else {
					System.out.print((B[i][j] == 1 ? "*" : B[i][j] == -1 ? "|" : "0") + " " );
				}
			}
			System.out.println();
		}
	}
	
	public boolean hasPrizes() {
		for (int i = 0; i < N; i++){
			for (int j = 0 ; j < N; j++ ){
				if (B[i][j] > 0)
					return true;
			}
		}
		return false;
	}
	
	public boolean canMove(int y, int x, Player p) {
		if (p.getY() + y < 0 || p.getY() + y >= N || p.getX() + x < 0 || p.getX() + x >= N) {
			return false;
		}
		if (isObstacle(p.getY() + y, p.getX() + x)) {
			return false;
		}

		return true;
	}
}