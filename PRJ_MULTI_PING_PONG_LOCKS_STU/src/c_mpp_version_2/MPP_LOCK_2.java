package c_mpp_version_2;

import java.util.concurrent.locks.*;

/* Version 2: In each line, the thread writing PONG 
 * must have the same id that the thread that wrote the previous PONG.
 */

public class MPP_LOCK_2 {
	
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
	private ReentrantLock lock = new ReentrantLock(true);
	private volatile boolean canPing = true; 
	private volatile int lastPingId;
	
	public void letMePing () {
		lock.lock();
		while(!canPing) {
			lock.unlock();
			Thread.yield();
			lock.lock();
		}
	}
	
	public void pingDone (int id) {
		canPing = false;
		lastPingId = id;
	    lock.unlock();
	}
	
	public void letMePong (int id) {
		lock.lock();
		while(canPing || lastPingId != id) {
			lock.unlock();
			Thread.yield();
			lock.lock();
		}
	}
	
	public void pongDone () {
		canPing = true;
	    lock.unlock();
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
			sync.pingDone(id);
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
			sync.letMePong(id);
			System.out.println("PONG("+id+")");
			sync.pongDone();
		}
	}
}