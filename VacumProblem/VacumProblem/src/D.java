

public class D {

	public static void main(String[] args) throws InterruptedException {
		World w = World.getInstance();
		
		Player[] ps = {new SmartassPlayer(), new CognitivePlayer(), new RandomPlayer()};
		do {
			w.displayWorld(ps);
			for (int i = 0; i < ps.length; i++) {
				Player p = ps[i];
				System.out.println("Player " + (i + 1) + " score is " + p.getScore());
			}
			System.out.println();
			for(Player p : ps) {
				p.move(w);
			}
			Thread.sleep(1000);
		} while (w.hasPrizes());
		
	}
}
