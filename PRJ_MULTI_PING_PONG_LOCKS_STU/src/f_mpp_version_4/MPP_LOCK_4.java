package f_mpp_version_4;


/* Version 4: 
 * THIS VERSION IS COMPLETE. IT HAS ALREADY BEEN WRITTEN FOR YOU
 */

public class MPP_LOCK_4 {
	
	public static void main (String [] args) throws InterruptedException {
		
		PingPongMonitor sync = new PingPongMonitor();
		
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

class PingPongMonitor {
	
	private volatile boolean canPing = true; 
	private volatile int lastPingId;
	
	public synchronized void doPing(int id) {
		if (canPing) {
			System.out.print("ping("+id+")");
			canPing = !canPing;
			lastPingId = id;
		}
	}
	
	public synchronized void doPong(int id) {
		if (!canPing && id==lastPingId ) {
			System.out.println("PONG("+id+")");
			canPing = !canPing;
		}
	}
	
}

class Ping extends Thread {
	
	private int id;
	private PingPongMonitor ppMonitor;
	
	public Ping (int id, PingPongMonitor mon) {
		this.id = id;
		this.ppMonitor = mon;
	}
	
	public void run ()  {
		while (true) {
			ppMonitor.doPing(id);
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}
		}
	}
}

class Pong extends Thread {
	
	private int id;
	private PingPongMonitor ppMonitor;
	
	public Pong (int id, PingPongMonitor mon) {
		this.id = id;
		this.ppMonitor = mon;
	}
	
	public void run ()  {
		while (true) {
			ppMonitor.doPong(id);
		}
	}
}