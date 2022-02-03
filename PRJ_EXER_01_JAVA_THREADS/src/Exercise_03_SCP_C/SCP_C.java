package Exercise_03_SCP_C;


public class SCP_C {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 3.C SYNCHRONIZED STORAGE-COUNTER-PRINTER WITH ACTIVE WAITING AND STORAGE AS SYNCHRONIZATION");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("");
    	
        Storage storage = new Storage();
        
        Counter counter = new Counter(storage);
        Printer printer = new Printer(storage);
        
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        counter.stop();
        printer.stop();
    }
}

class Storage {
    private volatile int value = -1000;
    
    private volatile boolean canBeSet = true;
    
    public int getValue() {        
    	while (canBeSet) {
            try {Thread.sleep(0,10);} catch(InterruptedException ie) {}
        }
        
    	int retValue = value;
    	canBeSet = true;
        return retValue;
    }
    
    public void setValue(int value) {
    	
    	while ( !canBeSet) {
            try {Thread.sleep(0,10);} catch(InterruptedException ie) {}
        }
    	
        this.value = value;
        // once a value has been set it can be got but a new one can't be set
        // until the old one has been got
        canBeSet = false;

    }
}



class Counter extends Thread {
    private Storage storage;
    
    public Counter (Storage storage) {
        this.storage = storage;
        // once created, instances of Counter start themselves
        this.start();
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
    private Storage storage;
    
    public Printer (Storage storage) {
        this.storage = storage;
        // once created, instances of Printer start themselves
        this.start();
    }
    
    public void run () {
        int i;
        while (true) {
            i = storage.getValue();
            for (int n=1; n<=i; n++) {
                System.out.print(" ");
            }
            System.out.println(i);
        }
    }
}