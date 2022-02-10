package b_multi_TTT_TS;

import java.util.concurrent.atomic.AtomicBoolean;


public class B_Multi_TTT_TS {
	
	static int INSTANCES = 10;
	
	public static void main (String [] args) throws InterruptedException {	
		AtomicBooleanEncapsulation syncTool = new AtomicBooleanEncapsulation();
		Tic[] theTics = new Tic[INSTANCES];
		Tac[] theTacs = new Tac[INSTANCES];
		Toe[] theToes = new Toe[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			theTics[i] = new Tic(i, syncTool);
			theTacs[i] = new Tac(i, syncTool);
			theToes[i] = new Toe(i, syncTool);
			theToes[i].start();
			theTacs[i].start();
			theTics[i].start();
		}
		
		Thread.sleep(5000);
		
		System.exit(0);
	}
}

class AtomicBooleanEncapsulation {
	public AtomicBoolean ticPossible = new AtomicBoolean(/* COMPLETE */);
	public AtomicBoolean tacPossible = new AtomicBoolean(/* COMPLETE */);
	public AtomicBoolean toePossible = new AtomicBoolean(/* COMPLETE */);
}

class Tic extends Thread {
	
	private AtomicBooleanEncapsulation syncTool;
	private int id;
	
	public Tic (int id, AtomicBooleanEncapsulation st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
			System.out.print("TIC("+id+")-");
			try {Thread.sleep(50);} catch (InterruptedException ie) {}
			/* COMPLETE */
		}
	}
}

class Tac extends Thread {
	
	private AtomicBooleanEncapsulation syncTool;
	private int id;
	
	private static volatile boolean upperCase = true;  // static. All instance share
	                                                   // the same variable
	
	public Tac (int id, AtomicBooleanEncapsulation st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			/* COMPLETE */
		}
	}
}

class Toe extends Thread {
	
	private AtomicBooleanEncapsulation syncTool;
	private int id;
	
	public Toe (int id, AtomicBooleanEncapsulation st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
		}
	}
}

