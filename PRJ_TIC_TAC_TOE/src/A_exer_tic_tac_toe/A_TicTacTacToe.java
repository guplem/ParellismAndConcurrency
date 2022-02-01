package A_exer_tic_tac_toe;

public class A_TicTacTacToe {
	
	public static void main (String [] args)  {	
		
		Shared sharedObject = new Shared();
		
		Thread tic = new Tic(sharedObject);
		Thread tac = new Tac(sharedObject);
		Thread toe = new Toe(sharedObject);
		
		tic.start();
		tac.start();
		toe.start();
		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tic.stop();
		tac.stop();
		toe.stop();
	}
}

class Shared {
	public boolean canTic = true;
	public boolean canTac = false;
	public boolean canToe = false;
}

class Tic extends Thread {
	// endlessly prints TIC-
	
	public Tic(Shared sharedObject) {
		this.sharedObject = sharedObject;
	}
	
	private Shared sharedObject;
	
	@Override
	public void run() {
		while(true) {
			if (sharedObject.canTic)
				print();
			else
				Thread.yield();
		}
	}

	private void print() {
		System.out.print("TIC-");
		sharedObject.canTic = false;
		sharedObject.canTac = true;
	}
}

class Tac extends Thread {
	// endlessly prints TAC (or tac)
	
	public Tac(Shared sharedObject) {
		this.sharedObject = sharedObject;
	}

	private Shared sharedObject;
	private boolean majus = true;
	
	@Override
	public void run() {
		while(true) {
			if (sharedObject.canTac)
				print();
			else
				Thread.yield();
		}
	}

	private void print() {
		if (majus)
			System.out.print("TAC");
		else
			System.out.print("tac");
		majus = !majus;
		sharedObject.canTac = false;
		sharedObject.canToe = true;
	}
	
}

class Toe extends Thread {
	// endlessly prints -TOE
	
	public Toe(Shared sharedObject) {
		this.sharedObject = sharedObject;
	}
	
	private Shared sharedObject;
	
	@Override
	public void run() {
		while(true) {
			if (sharedObject.canToe)
				print();
			else
				Thread.yield();
		}
	}
	
	
	private void print() {
		System.out.println("-TOE");
		sharedObject.canToe = false;
		sharedObject.canTic = true;
	}
}

