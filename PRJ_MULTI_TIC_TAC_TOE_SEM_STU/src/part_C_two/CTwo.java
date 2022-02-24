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
	
	private Semaphore canTic = new Semaphore(1);
	private Semaphore canTac = new Semaphore(0);
	private Semaphore canToe = new Semaphore(0);
	private Semaphore canLeap = new Semaphore(0);
	
	
	public void letMeTic (int id) {
		while(true) {
			try { canTic.acquire(); } catch (InterruptedException e) { }
			if (id != lastTicId ) {
				lastTicId = id;
				return;
			} else {
				canTic.release();
			}
		}
	}
	public void ticDone () {
		canTac.release();
	}
	
	public void letMeTac () {
		try { canTac.acquire(); } catch (InterruptedException e) { }
	}
	public void tacDone ()  {
		canToe.release();
	}
	
	public void letMeToe (int id) {
		while (true) {			
			try { canToe.acquire(); } catch (InterruptedException e) {}
			if (id == lastTicId ) {
				return;
			} else {
				canToe.release();
			}
		}
	}
	public void toeDone () {
		if (!frogReady)
			canTic.release();
		else
			canLeap.release();
	}
	
	public void letMeLeap () {
		frogReady = true;
		try { canLeap.acquire(); } catch (InterruptedException e) { }
	}
	public void leapDone () {
		frogReady = false;
		canTic.release();
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


