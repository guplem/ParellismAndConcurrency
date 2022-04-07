package ex1_b_AtomicInteger;

import java.util.concurrent.atomic.*;

public class Ex1_B_AtomicInteger {

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
	
	int waiting = -1;
	int pingTime = 0;
	int firstPongTime = 1;
	int secondPongTime = 2;
	int finalPongDone = 3;
	
	/* COMPLETE */
	AtomicInteger remainingPrints = new AtomicInteger(0);
	volatile int line = 1;
	volatile boolean allowedPing = false;
	volatile boolean allowedPong = false;
	
	public void letMePing() {
		/* COMPLETE */
		boolean can = false;
		while (!can) {
			can = remainingPrints.compareAndSet(pingTime, waiting);
		}
		//System.out.println("LET ME PING. Remainings = " + remainingPrints.get());
	}
	
	public void pingDone() {
		/* COMPLETE */
		line ++;
		remainingPrints.compareAndSet(waiting, firstPongTime);
	}
	
	public void letMePong() {
		/* COMPLETE */
		boolean can = false;
		while (!can) {
			can = remainingPrints.compareAndSet(firstPongTime, waiting) || remainingPrints.compareAndSet(secondPongTime, finalPongDone);
		}
		//System.out.println("LET ME PONG. Remainings = " + remainingPrints.get());
	}
	
	public void pongDone() {
		if (!remainingPrints.compareAndSet(waiting, line%2==0 ? pingTime : secondPongTime))
			remainingPrints.compareAndSet(finalPongDone, pingTime);
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
