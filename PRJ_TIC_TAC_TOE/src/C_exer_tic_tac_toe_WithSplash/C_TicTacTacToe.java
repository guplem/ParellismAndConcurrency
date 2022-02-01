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
	
	private volatile boolean canTic = true;
	private volatile boolean canTac = false;
	private volatile boolean canToe = false;
	
	private volatile boolean wantsSplash = false;
	private volatile boolean canSplash = false;
	
	public volatile boolean majusTac = true;
	
	public void letMeTic () {
		while(!canTic)
			Thread.yield();
		return;
	}
	
	public void ticWritten () {
		canTic = false;
		canTac = true;
		canSplash = false;
	}
	
	
	public void letMeTac () {
		while(!canTac)
			Thread.yield();
		return;
	}
	
	public void tacWritten () {
		canTac = false;
		canSplash = wantsSplash && !majusTac;
		wantsSplash = false;
		canToe = !canSplash;
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
		canSplash = false;
	}

	public void letMeSplash() {
		while(!canSplash)
		{
			wantsSplash = true;
			Thread.yield();
		}
		return;
	}

	public void splashDone() {
		canTic = true;
		canTac = false;
		canToe = false;
		canSplash = false;
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
			
			// Sleep to ensure the visualization of the "SPLASH!" message
			try { Thread.sleep(75); } catch (InterruptedException e) { e.printStackTrace(); }
			
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

