package p01_CJ_ThreeSisters_Explicit;

import java.util.concurrent.locks.*;

public class CJ_threeSisters_Explicit {
	
	// Launcher. Do not modify
	public static void main (String [] args) throws InterruptedException {
		ThreeSistersMonitor monitor = new ThreeSistersMonitor();
		Sister ada = new Sister(Sister.Identity.ADA, monitor);
		Sister barb = new Sister(Sister.Identity.BARB, monitor);
		Sister cord = new Sister(Sister.Identity.CORD, monitor);
		
		cord.start();
		barb.start();
		ada.start();
		
		Thread.sleep(10000);
		
		cord.stop();
		barb.stop();
		ada.stop();
	}

}

class ThreeSistersMonitor {
	
	// Add here all the attributes of the monitor
	/* COMPLETE */
	
	public void adaTakes() {
		/* COMPLETE */
		System.out.println("ADA takes a cookie");
		lock.unlock();
	}
	
	public void barbTakes() {
		/* COMPLETE */
		System.out.println("\tBARB takes a cookie");
		/* COMPLETE */
	}
	
	public void cordTakes() {
		/* COMPLETE */
		System.out.println("\t\tCORD takes a cookie");
		/* COMPLETE */
	}
}

// -- DO NOT modify class Sister

class Sister extends Thread {
	
	public static enum Identity {ADA, BARB, CORD};
	
	private Identity identity;
	private ThreeSistersMonitor monitor;
	
	public Sister (Identity identity, ThreeSistersMonitor monitor) {
		this.identity = identity;
		this.monitor = monitor;
	}
	
	public void run () {
		while (true) {
			switch (identity) {
				case ADA:
					monitor.adaTakes();
					sleep(10);
					break;
				case BARB: 
					monitor.barbTakes();
					sleep(30);
					break;
				case CORD: 
					monitor.cordTakes();
					sleep(30);
					break;
			}
		}
	} 
	
	private void sleep (int s) {
		try {super.sleep(s);} catch(InterruptedException ie) {}
	}
}
