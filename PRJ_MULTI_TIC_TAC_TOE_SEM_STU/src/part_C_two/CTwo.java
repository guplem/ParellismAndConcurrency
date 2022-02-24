package part_C_two;

import java.util.concurrent.Semaphore;

public class CTwo {
	
	public static void main (String [] args) throws InterruptedException {
		final int INSTANCES = 10;
		
		Frog kermit;
		
		TIC theTICs [] = new TIC[INSTANCES];
		TAC theTACs [] = new TAC[INSTANCES];
		TOE theTOEs [] = new TOE[INSTANCES];
		
		Synchronizer shared = new Synchronizer();
		
		for (int i = 0; i<INSTANCES; i++) {
			theTICs[i] = new TIC(i, shared);
			theTICs[i].start();
			theTACs[i] = new TAC(i, shared);
			theTACs[i].start();
			theTOEs[i] = new TOE(i, shared);
			theTOEs[i].start();
		}
		
		kermit = new Frog(shared);
		kermit.start();
		
		Thread.sleep(10000);
		
		kermit.stop();
		for (Thread th : theTICs) {th.stop();}
		for (Thread th : theTACs) {th.stop();}
		for (Thread th : theTOEs) {th.stop();}
	}
}

class Synchronizer {
	private volatile boolean uppercase = true;
	private volatile int lastTicId = -1;
	private volatile boolean frogReady = false;
	
	private Semaphore canTic = new Semaphore(/* permits */);
	private Semaphore canTac = new Semaphore(/* permits */);
	private Semaphore canToe = new Semaphore(/* permits */);
	private Semaphore canLeap = new Semaphore(/* permits */);
	
	
	public void letMeTic (int id) {
		/* COMPLETE */
		// same as in part C one
	}
	public void ticDone () {
		/* COMPLETE */
		// same as in part C one
	}
	
	public void letMeTac () {
		/* COMPLETE */
		// same as in part C one
	}
	public void tacDone ()  {
		/* COMPLETE */
		// same as in part C one
	}
	
	public void letMeToe (int id) {
		/* COMPLETE */
		// same as in part C one
	}
	public void toeDone () {
		/* COMPLETE */
	}
	
	public void letMeLeap () {
		/* COMPLETE */
	}
	public void leapDone () {
		/* COMPLETE */
	}
	
	public boolean nowUppercase () {return this.uppercase;}
}

class Frog extends Thread {
	
	private Synchronizer synchronizer;
	
	public Frog (Synchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}
	
	public void run () {
		while (true) {
			try {sleep(1000);} catch (InterruptedException ie) {}
			synchronizer.letMeLeap();
			System.out.println();
			System.out.println("    CROAK!");
			System.out.println();
			synchronizer.leapDone();
		}
	}
}

class TIC extends Thread {
	
	private int id;
	private Synchronizer synchronizer;
	
	public TIC (int id, Synchronizer synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}
	
	public void run () {
		while (true) {
			synchronizer.letMeTic(id);
			System.out.print("TIC("+id+")-");
			synchronizer.ticDone();
		}
	}
}

class TAC extends Thread {
	
	private int id;
	private Synchronizer synchronizer;
	
	public TAC (int id, Synchronizer synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}
	
	public void run (){
		while (true) {
			synchronizer.letMeTac();
			if (synchronizer.nowUppercase())
				System.out.print("TAC["+id+"]");
			else 
				System.out.print("tac["+id+"]");
			synchronizer.tacDone();
		}
	}
}

class TOE extends Thread {
	
	private int id;
	private Synchronizer synchronizer;
	
	public TOE (int id, Synchronizer synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}
	
	public void run () {
		while (true) {
			synchronizer.letMeToe(id);
			System.out.println("-TOE("+id+")");
			try {sleep(50);} catch (InterruptedException ie) {}
			synchronizer.toeDone();
		}
	}
}


