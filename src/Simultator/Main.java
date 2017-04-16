package Simultator;

public class Main {
	public static void main(String[] args) {
		final int DELAY = 2; // en seconde
		final int REPETITION = 10;
		
		try {
			BeeperControl manager = new BeeperControl(DELAY, REPETITION);
			
			manager.ram = new RAMManager(Utils.memory(1, Utils.GIGA_BYTE)); // initialise le gestionnaire de RAM
			System.out.println(manager.ram + "\n");
			
			manager.start();
		} catch (InvalidSize e) {
			System.out.println("Exception: invalid format for size");
		}
	}
}

class InvalidSize extends Exception {}
