package B_exer_tic_tac_toe;

public class B_TicTacTacToe {
	
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
	
	private boolean canTic = true;
	private boolean canTac = false;
	private boolean canToe = false;
	
	public void letMeTic () {
		while(!canTic)
			Thread.yield();
		return;
	}
	
	public void ticWritten () {
		canTic = false;
		canTac = true;
	}
	
	
	public void letMeTac () {
		while(!canTac)
			Thread.yield();
		return;
	}
	
	public void tacWritten () {
		canTac = false;
		canToe = true;
	}
	
	public void letMeToe () {
		while(!canToe)
			Thread.yield();
		return;
	}
	
	public void toeWritten () {
		canToe = false;
		canTic = true;
	}
	
	
}


class Tic extends Thread {
	
	private Shared shared;
	
	public Tic (Shared sh) {
		this.shared = sh;
	}
	
	public  void run () {
		while (true) {
			shared.letMeTic();
			print();
			shared.ticWritten();
		}
	}
	
	private void print() {
		System.out.print("TIC-");
	}
}


class Tac extends Thread {
	
	private Shared shared;
	private boolean majus = true;
	
	public Tac (Shared sh) {
		this.shared = sh;
	}
	
	public void run () {
		while (true) {
			shared.letMeTac();
			print();
			shared.tacWritten();
		}
	}
	
	private void print() {
		if (majus)
			System.out.print("TAC");
		else
			System.out.print("tac");
		majus = !majus;
	}
}


class Toe extends Thread {
	
	private Shared shared;
	
	public Toe (Shared sh) {
		this.shared = sh;
	}
	
	public  void run () {
		while (true) {
			shared.letMeToe();
			print();
			shared.toeWritten();
		}
	}
	
	private void print() {
		System.out.println("-TOE");
	}
}

