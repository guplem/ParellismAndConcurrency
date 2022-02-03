package e1_mpp_AtomicInteger;

import java.util.concurrent.atomic.AtomicInteger;


public class MPP_AI {

	public static final int CAN_PING = 1; // use this value to indicate that "one thread can ping" 
	public static final int CAN_PONG = 2; // use this value to indicate that "one thread can pong"
	public static final int WRITING = 3; // use this value to indicate that "one thread is writing something"
	
	public static void main (String [] args) throws InterruptedException {
		
		AtomicInteger shared = new AtomicInteger(CAN_PING);
		
		Ping [] thePings = new Ping[10];
		Pong [] thePongs = new Pong[10];
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i] = new Ping(i, shared);
			thePongs[i] = new Pong(i, shared);
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

class Ping extends Thread {
	
	private int id;
	private AtomicInteger sharedAtomicInt;
	
	public Ping (int id, AtomicInteger shared) {
		this.id = id;
		this.sharedAtomicInt = shared;
	}
	
	public void run ()  {
		while (true) {
			
			/* COMPLETE */ 
			
			System.out.print("PING("+id+")");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}
			
			// once ping's written, let's give a pong the chance to write
			sharedAtomicInt.set(MPP_AI.CAN_PONG);
		}
	}
}

class Pong extends Thread {
	
	private int id;
	private AtomicInteger sharedAtomicInt;
	
	public Pong (int id, AtomicInteger shared) {
		this.id = id;
		this.sharedAtomicInt = shared;
	}
	
	
	public void run ()  {
		while (true) {
			
			/* COMPLETE */ 
			
			System.out.println("PONG("+id+")");
			
			/* COMPLETE */
		}
	}
}