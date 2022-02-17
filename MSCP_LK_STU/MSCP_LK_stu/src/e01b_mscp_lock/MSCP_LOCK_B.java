package e01b_mscp_lock;

import java.util.concurrent.locks.*;

public class MSCP_LOCK_B {

	public static void main(String[] args) {
		final int NUM_PRINTERS = 10;
		
		Printer[] thePrinters = new Printer[NUM_PRINTERS]; 
		SyncStorage storage = new SyncStorage();
		Counter counter = new Counter(storage);
		
		for (int i=0; i<thePrinters.length; i++) {
			thePrinters[i] = new Printer(i, storage);
			thePrinters[i].start();
		}
		counter.start();
		
		try {
			Thread.sleep(5000);
		}
		catch(InterruptedException ie) {}

		counter.stop();
		for (int i=0; i<thePrinters.length; i++) {
			thePrinters[i].stop();
		}
		
	}

}


//Classes Counter and Printer are OK. Do not change them

class SyncStorage {
	
	/* Copy from previous part and add/modify whatever necessary */
    
}

class Counter extends Thread {
	private SyncStorage storage;
	
	public Counter (SyncStorage storage) {
		this.storage = storage;
	}
	
	public void run () {
		int i;
		while (true) {
			for (i=0; i<=9; i++) {
				storage.setValue(i);
			}
		}
	}
}

class Printer extends Thread {
	private int id;
	private SyncStorage storage;
	
	public Printer (int id, SyncStorage storage) {
		this.id = id;
		this.storage = storage;
	}
	
	public void run () {
		int value;
		while (true) {
			value = storage.getValue(id);
			for (int i=0; i<=value; i++) {System.out.print(" ");};
			System.out.println(value+"["+id+"]");
			storage.valuePrinted();
			try {Thread.sleep(20);} catch(InterruptedException ie) {}
		}
	}
}
