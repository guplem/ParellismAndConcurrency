package C_exer_tic_tac_toe_WithSplash;

public class C_TicTacTacToe {
	
	public static void main (String [] args)  {
		
		Shared sharedObject = new Shared();
		
		Thread tic = new Tic(sharedObject);
		Thread tac = new Tac(sharedObject);
		Thread toe = new Toe(sharedObject);
		Thread splash = new Splash(sharedObject);
		
		tic.start();
		tac.start();
		toe.start();
		splash.start();
		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tic.stop();
		tac.stop();
		toe.stop();
		splash.stop();
	}
}

class Shared {
	
	private boolean canTic = true;
	private boolean canTac = false;
	public boolean majusTac = true;
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
		majusTac = !majusTac;
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

	public void letMeSplash() {
		while(!canToe || majusTac)
			Thread.yield();
		return;
	}

	public void splashDone() {
		canTic = true;
		canTac = false;
		canToe = false;
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
		if (shared.majusTac)
			System.out.print("TAC");
		else
			System.out.print("tac");
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
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			shared.toeWritten();
		}
	}
	
	private void print() {
		System.out.println("-TOE");
	}
}

class Splash extends Thread {
	
	private Shared shared;
	
	public Splash (Shared sh) {
		this.shared = sh;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			shared.letMeSplash();
			print();
			shared.splashDone();
		}
	}
	
	private void print() {
		System.out.println("\n\tSPLASH!");
	}
}

