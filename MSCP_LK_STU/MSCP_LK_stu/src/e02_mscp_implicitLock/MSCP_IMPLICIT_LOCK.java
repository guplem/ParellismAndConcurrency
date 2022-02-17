package e02_mscp_implicitLock;

public class MSCP_IMPLICIT_LOCK {

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
	private static final int MUST_PRINT = 3;

	// sync related stuff. STATE
	private volatile int state = CAN_SET;
	
	// sync related stuff. id of last printer
	private volatile int lastId = -1;
	
	// storage related stuff. VALUE and methods
    private volatile int value = -1000;
    
    public void setValue(int value) {
    	// lock not needed here since only one counter exists
    	while (state!=CAN_SET) {Thread.yield();}
    	this.value = value;
    	state = CAN_GET;
    }
    
    public int getValue(int id) {
    	boolean goAhead = false;
    	while (!goAhead) {
    		synchronized (this) {
    			if (state==CAN_GET && lastId!=id) {
    				goAhead = true;
    				state = MUST_PRINT;
    				lastId = id;
    			}
    		}
    	}
    	return value;
    }
    
    public void valuePrinted() {
    	state=CAN_SET;
    }
    
}

//Classes Counter and Printer are OK. Do not change them

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
