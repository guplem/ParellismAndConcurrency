package b_mpp_version_1b;

import java.util.concurrent.locks.*;

/* Version  1b 
 * Refactorize 1a with letMe* and *Done methods
 */

public class MPP_LOCK_1b {
	
	public static void main (String [] args) throws InterruptedException {
		
		LockBasedSynchronizer sync = new LockBasedSynchronizer();
		
		Ping [] thePings = new Ping[10];
		Pong [] thePongs = new Pong[10];
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i] = new Ping(i, sync);
			thePongs[i] = new Pong(i, sync);
			thePongs[i].start();
			thePings[i].start();
		}
		
		Thread.sleep(5000);
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i].stop();
			thePongs[i].stop();
		}
		
	}
	
}

class LockBasedSynchronizer {
	private ReentrantLock lock = new ReentrantLock();
	private volatile boolean canPing = true; 
	
	public void letMePing () {
		/* COMPLETE */
		// when this method is exited, ping is possible and the invoker is holding the lock
	}
	
	public void pingDone () {
		/* COMPLETE */
	}
	
	public void letMePong () {
		/* COMPLETE */
		// when this method is exited, pong is possible and the invoker is holding the lock
	}
	
	public void pongDone () {
		/* COMPLETE */
	}
	
}

class Ping extends Thread {
	
	private int id;
	private LockBasedSynchronizer sync;
	
	public Ping (int id, LockBasedSynchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run ()  {
		while (true) {
			sync.letMePing();
			System.out.print("ping("+id+")");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}
			sync.pingDone();
		}
	}
}

class Pong extends Thread {
	
	private int id;
	private LockBasedSynchronizer sync;
	
	public Pong (int id, LockBasedSynchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run ()  {
		while (true) {
			sync.letMePong();
			System.out.println("PONG("+id+")");
			sync.pongDone();
		}
	}
}