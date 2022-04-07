package ex1_c_Semaphores;

import java.util.concurrent.*;

public class Ex1_C_Semaphores {

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
	Semaphore canPing = new Semaphore(1);
	Semaphore canPong = new Semaphore(0);
	int line = 0;
	
	public void letMePing(int id) {
		/* COMPLETE */
		try {
			canPing.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pingDone() {
		/* COMPLETE */
		line++;
		canPong.release(line%2==0 ? 1 : 2);
	}
	
	public void letMePong(int id) {
		/* COMPLETE */
		try {
			canPong.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pongDone() {
		/* COMPLETE */
		if (canPong.availablePermits() <= 0)
			canPing.release();
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
			sync.letMePing(id);
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
			try {Thread.sleep(5);} catch (Exception e) {}
			sync.letMePong(id);
			try {Thread.sleep(50);} catch (Exception e) {}
			System.out.print("Pong("+id+") ");
			sync.pongDone();
		}
	}
}
