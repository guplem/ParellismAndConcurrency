package e01a_mscp_lock;

import java.util.concurrent.locks.*;

public class MSCP_LOCK_A {

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


class SyncStorage {
	// sync related stuff. CONSTANTS
	private static final int CAN_SET = 1;
	private static final int CAN_GET = 2;
	// sync related stuff. STATE
	private volatile int state = CAN_SET;
	// sync related stuff. LOCK
	private  Lock lock = new ReentrantLock();
	
	// storage related stuff. VALUE and methods
    private volatile int value = -1000;
    
    public void setValue(int value) {
    	// No fa falta un lock perque només hi ha un "SyncStorage"
    	while (state != CAN_SET) {Thread.yield();}
    	this.value = value;
    	state = CAN_GET;
    }
    
    public int getValue() {
    	// Fa falta lock perque multiples processos poden voler el valor del "SyncStorage"
    	lock.lock();
    	while (state != CAN_GET) {
    		lock.unlock();
    		Thread.yield();
    		lock.lock();
		}
    	return this.value; // Atenció, la propietat del lock es manté després de fer el return
    }
    
    public void valuePrinted() {
    	state = CAN_SET;
		lock.unlock(); // Es treu la propietat del lock aqui perque si no un altre podria agafar el valor abans de que fos imprès
    }
    
}

// Classes Counter and Printer are OK. Do not change them

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
			value = storage.getValue();
			for (int i=0; i<=value; i++) {System.out.print(" ");};
			System.out.println(value+"["+id+"]");
			storage.valuePrinted();
		}
	}
}
