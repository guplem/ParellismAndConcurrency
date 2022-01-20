package Exercise_03_SCP_A;

public class SCP_A {
    
    public static void main (String [] args) {
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
        // once created, instances of Counter start themselves
       /* COMPLETE */
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
        /* COMPLETE */
    }
    
    public void run () {
        /* COMPLETE */
    }
}