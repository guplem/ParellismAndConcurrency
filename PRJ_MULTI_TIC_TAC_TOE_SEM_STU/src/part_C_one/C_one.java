package part_C_one;

import java.util.concurrent.Semaphore;

public class C_one {
	
	public static void main (String [] args) throws InterruptedException {
		final int INSTANCES = 10;
		
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
		
		Thread.sleep(10000);
		
		for (Thread th : theTICs) {th.stop();}
		for (Thread th : theTACs) {th.stop();}
		for (Thread th : theTOEs) {th.stop();}
		
	}
}

class Synchronizer {
	private volatile boolean uppercase = true;
	private volatile int lastTicId = -1;
	private Semaphore canTic = new Semaphore(1);
	private Semaphore canTac = new Semaphore(0);
	private Semaphore canToe = new Semaphore(0);
	
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
		canTic.release();
	}
	
	public boolean nowUppercase () {return this.uppercase;}
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
			synchronizer.toeDone();
		}
	}
}


