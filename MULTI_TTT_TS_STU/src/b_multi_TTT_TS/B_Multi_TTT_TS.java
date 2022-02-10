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
	public AtomicBoolean ticPossible = new AtomicBoolean(true);
	public AtomicBoolean tacPossible = new AtomicBoolean(false);
	public AtomicBoolean toePossible = new AtomicBoolean(false);
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
			while(!syncTool.ticPossible.compareAndSet(true, false)) {
				Thread.yield();
			}
			System.out.print("TIC("+id+")-");
			try {Thread.sleep(50);} catch (InterruptedException ie) {}
			syncTool.tacPossible.set(true);
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
			while(!syncTool.tacPossible.compareAndSet(true, false)) {
				Thread.yield();
			}
			if (upperCase) System.out.print("TAC("+id+")");
			else System.out.print("tac("+id+")");
			upperCase = !upperCase;
			syncTool.toePossible.set(true);
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
			while(!syncTool.toePossible.compareAndSet(true, false)) {
				Thread.yield();
			}
			System.out.println("-TOE("+id+")");
			syncTool.ticPossible.set(true);
		}
	}
}

