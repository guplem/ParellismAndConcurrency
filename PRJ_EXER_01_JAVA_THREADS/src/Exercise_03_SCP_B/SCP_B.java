package Exercise_03_SCP_B;

public class SCP_B {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 3 SYNCHRONIZED STORAGE-COUNTER-PRINTER WITH ACTIVE WAITING AND SHARED OBJECT");
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("");
    	
        Storage storage = new Storage();
        SynchroMechanism synchro = new SynchroMechanism();
        
        Counter counter = new Counter(storage, synchro);
        Printer printer = new Printer(storage, synchro);
        
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        counter.stop();
        printer.stop();
    }
}

class SynchroMechanism {
    public volatile boolean canStore = true;
    public volatile boolean canPrint = false;
}

class Storage {
    private volatile int value = -1000;
    
    public int getValue() {return this.value;}
    public void setValue(int value) {this.value = value;}
}

class Counter extends Thread {
    private Storage storage;
    private SynchroMechanism synchro;
    
    public Counter (Storage storage, SynchroMechanism synchro) {
        this.storage = storage;
        this.synchro = synchro;
        // once created, instances of Counter start themselves
        this.start();
    }
    
    public void run () {
        int i;
        while (true) {
            for (i=0; i<=9; i++) {
                while(!synchro.canStore) {
                    Thread.yield();
                }
                storage.setValue(i);
                synchro.canStore = false;
                synchro.canPrint = true;
            }
        }
    }
}

class Printer extends Thread {
    private Storage storage;
    private SynchroMechanism synchro;
    
    public Printer (Storage storage, SynchroMechanism synchro) {
        this.storage = storage;
        this.synchro = synchro;
        // once created, instances of Printer start themselves
        this.start();
    }
    
    public void run () {
        int i;
        while (true) {
            while(!synchro.canPrint) {
                Thread.yield();
            }
            printStoredValue();
            synchro.canPrint = false;
            synchro.canStore = true;
        }
    }

	private void printStoredValue() {
    	int value = storage.getValue();
    	String printedText = "";
        for (int i=0; i<=value; i++) {
        	printedText += " ";
        }
        System.out.println(printedText + value);
	}
}