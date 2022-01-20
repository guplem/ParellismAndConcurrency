package Exercise_03_SCP_A;

public class SCP_A {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 3 UNSYNCHRONIZED STORAGE-COUNTER-PRINTER");
		System.out.println("-------------------------------------------------");
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
    
    public int getValue() {return this.value;}
    public void setValue(int value) {this.value = value;}
}

class Counter extends Thread {
    private Storage storage;
    
    public Counter (Storage storage) {
        this.storage = storage;
        start();
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
        start();
    }
    
    public void run () {
		while(true) {
	    	int value = storage.getValue();
	    	String printedText = "";
	        for (int i=0; i<=value; i++) {
	        	printedText += " ";
	        }
	        System.out.println(printedText + value);
		}
    }
}