package ex1_a_ImplicitLock;

public class Ex1_A_ImplicitLock {

	public static void main (String [] args) {
		final int NUM_TH = 10;
		
		Synchronizer sync = new Synchronizer();
		Ping [] pings = new Ping[NUM_TH];
		Pong [] pongs = new Pong[NUM_TH];
		
		for (int i=0; i<NUM_TH; i++) {
			pings[i] = new Ping(i, sync);
			pongs[i] = new Pong(i, sync);
			pongs[i].start();
			pings[i].start();
		}
		
		
		try {Thread.sleep(5000);} catch (Exception e) {}
		
		System.exit(0);
	}
	
}

class Synchronizer {
	
	/* COMPLETE */
	volatile int line = 0;
	volatile int remainingPings = 1;
	volatile int remainingPongs = -1;
	volatile boolean pingAllowed = false;
	volatile boolean pongAllowed = false;
	
	public void letMePing() {
		/* COMPLETE */
    	boolean can = false;
    	while (!can) {
    		synchronized (this) {
    			can = remainingPings > 0 && !pingAllowed;
    			pingAllowed = can || pingAllowed;
    		}
    	}
	}
	
	public void pingDone() {
		/* COMPLETE */
		synchronized (this) {
			remainingPings --;
			line ++;
			remainingPongs = line%2!=0 ? 1 : 2;
			pingAllowed = false;
		}
	}
	
	public void letMePong() {
		/* COMPLETE */
    	boolean can = false;
    	while (!can) {
    		synchronized (this) {
    			can = remainingPongs > 0 && !pongAllowed;
    			pongAllowed = can || pongAllowed;
    		}
    	}
	}
	
	public void pongDone() {
		/* COMPLETE */
		synchronized (this) {
			remainingPongs --;
			if (remainingPongs == 0)
				remainingPings = 1;
			pongAllowed = false;
		}
	}
	
}


class Ping extends Thread {
	
	private Synchronizer sync;
	private int id;
	
	public Ping (int id, Synchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run () {
		while (true) {
			try {Thread.sleep(50+(int)(Math.random()*100));} catch (Exception e) {}
			sync.letMePing();
			try {Thread.sleep(50);} catch (Exception e) {}
			System.out.print("\nPing("+id+") ");
			sync.pingDone();
		}
	}
}

class Pong extends Thread {
	
	private Synchronizer sync;
	private int id;
	
	public Pong (int id, Synchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run () {
		while (true) {
			sync.letMePong();
			try {Thread.sleep(50);} catch (Exception e) {}
			System.out.print("Pong("+id+") ");
			sync.pongDone();
		}
	}
}
