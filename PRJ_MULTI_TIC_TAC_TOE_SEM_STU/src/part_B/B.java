package part_B;

import java.util.concurrent.Semaphore;

public class B {
	
	public static void main (String [] args) throws InterruptedException {
		final int INSTANCES = 10;
		
		TIC theTICs [] = new TIC[INSTANCES];
		TAC theTACs [] = new TAC[INSTANCES];
		TOE theTOEs [] = new TOE[INSTANCES];
		
		SharedBundle shared = new SharedBundle();
		
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

// encapsulation of the elements the threads need to share
class SharedBundle {
	public volatile boolean uppercase = true;
	public volatile int lastTicId = -1;
	public Semaphore canTic = new Semaphore(/* permits */);
	public Semaphore canTac = new Semaphore(/* permits */);
	public Semaphore canToe = new Semaphore(/* permits */);
}

class TIC extends Thread {
	
	private int id;
	private SharedBundle shared;
	
	public TIC (int id, SharedBundle shared) {
		this.id = id;
		this.shared = shared;
	}
	
	public void run () {
		while (true) {
			
			/* COMPLETE */
			//System.out.print("TIC("+id+")-");
			
		}
	}
}

class TAC extends Thread {
	
	private int id;
	private SharedBundle shared;
	
	public TAC (int id, SharedBundle shared) {
		this.id = id;
		this.shared = shared;
	}
	
	public void run (){
		
		while (true) {
			/* COMPLETE */
			// System.out.print("TAC("+id+")");
			// System.out.print("tac["+id+"]");
		}
	}
}

class TOE extends Thread {
	
	private int id;
	private SharedBundle shared;
	
	public TOE (int id, SharedBundle shared) {
		this.id = id;
		this.shared = shared;
	}
	
	public void run () {
		while (true) {
			// System.out.println("-TOE("+id+")");
			/* COMPLETE */
		}
	}
}


