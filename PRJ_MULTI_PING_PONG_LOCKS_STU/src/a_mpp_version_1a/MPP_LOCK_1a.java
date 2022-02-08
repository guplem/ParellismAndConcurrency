package a_mpp_version_1a;

import java.util.concurrent.locks.*;

/* Version 1a 
   just use a re-entrant lock (java.util.concurrent.locks.ReentrantLock), 
   that is kept in an ad-hoc synchronizer object
 */

public class MPP_LOCK_1a {
	
	public static void main (String [] args) throws InterruptedException {
		
		LockBasedSynchronizer sync = new LockBasedSynchronizer();
		
		Ping [] thePings = new Ping[10];
		Pong [] thePongs = new Pong[10];
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i] = new Ping(i, sync);
			thePongs[i] = new Pong(i, sync);
			thePings[i].start();
			thePongs[i].start();
		}
		
		Thread.sleep(5000);
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i].stop();
			thePongs[i].stop();
		}
		
	}
	
}

class LockBasedSynchronizer {
	public ReentrantLock lock = new ReentrantLock(true);
	public volatile boolean canPing = true; 
	// when canPing is true the owner of the lock can write ping
	// when canPing is false the owner of the lock can write pong
	
	// DO NOT ADD ANYTHING ELSE
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
			
			sync.lock.lock();
			
			while(!sync.canPing) {
				sync.lock.unlock();
				Thread.yield();
				sync.lock.lock();
			}
			
			System.out.print("ping("+id+")");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}
			
			sync.canPing = false;
			
			// this point is reached when still holding the lock...
			// now release it
		    sync.lock.unlock(); // Important
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
			
			sync.lock.lock();
			
			while(sync.canPing) {
				sync.lock.unlock();
				Thread.yield();
				sync.lock.lock();
			}
			
			System.out.println("  pong("+id+")");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}
			
			sync.canPing = true;
			
			// this point is reached when still holding the lock...
			// now release it
		    sync.lock.unlock(); // Important
		}
	}
}