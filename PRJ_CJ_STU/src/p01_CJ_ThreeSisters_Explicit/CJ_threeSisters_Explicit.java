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
	
	ReentrantLock lock = new ReentrantLock(true);
	Condition barbCanTake = lock.newCondition();
	Condition cordCanTake = lock.newCondition();
	
	int adaCountForBarb = 0;
	int adaCountForCord = 0;
	int barbCountForCord = 0;
	
	
	public void adaTakes() {	
		lock.lock();
		
		System.out.println("ADA takes a cookie");
		adaCountForBarb++;
		adaCountForCord++;		
		
		if (adaCountForBarb >= 2)
			barbCanTake.signal();
		
		if (adaCountForCord >= 3 && barbCountForCord >= 1)
			cordCanTake.signal();
		
		lock.unlock();
	}
	
	public void barbTakes() {
		lock.lock();
		
		while(adaCountForBarb < 2)
			barbCanTake.awaitUninterruptibly();
		
		adaCountForBarb = 0;
		System.out.println("\t\t\tBARB takes a cookie");
		barbCountForCord ++;

		if (adaCountForCord >= 3 && barbCountForCord >= 1)
			cordCanTake.signal();
		
		lock.unlock();
	}
	
	public void cordTakes() {
		lock.lock();
		
		if (adaCountForCord >= 3 && barbCountForCord >= 1)
			cordCanTake.awaitUninterruptibly();
		
		adaCountForCord = 0;
		barbCountForCord = 0;
		
		System.out.println("\t\t\t\t\t\tCORD takes a cookie");
		lock.unlock();
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
