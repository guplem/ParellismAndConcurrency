package e2_mpp_AtomicBoolean;

import java.util.concurrent.atomic.AtomicBoolean;

import e1_mpp_AtomicInteger.MPP_AI;

public class MPP_AB {
	public static void main (String [] args) throws InterruptedException {
		
		Synchronizer sync = new Synchronizer();
		
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

class Synchronizer {
	
	// you'll need to instances of AtomicBoolean properly initialized
	AtomicBoolean canPing = new AtomicBoolean(true);
	AtomicBoolean canPong = new AtomicBoolean();
	
	
	public void letMePing() {
		// "spin" until ping can we written
		while(!canPing.compareAndSet(true, false)) {Thread.yield();}
	}
	
	public void letMePong() {
		// "spin" until pong can we written
		while(!canPong.compareAndSet(true, false)) {Thread.yield();}
	}
	
	public void pingDone () {
		canPong.set(true);
	}
	
	public void pongDone () {
		canPing.set(true);
	}
 }

class Ping extends Thread {
	
	private int id;
	private Synchronizer sync;
	
	public Ping (int id, Synchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run ()  {
		while (true) {
			sync.letMePing();
			System.out.print("PING("+id+") ");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}
			sync.pingDone();
		}
	}
}

class Pong extends Thread {
	
	private int id;
	private Synchronizer sync;
	
	public Pong (int id, Synchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run () {
		while (true) {
			sync.letMePong();
			System.out.println("PONG("+id+")");
			sync.pongDone();
		}
	}
}
