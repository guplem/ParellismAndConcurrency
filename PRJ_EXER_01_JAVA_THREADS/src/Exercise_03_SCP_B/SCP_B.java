package Exercise_03_SCP_B;

public class SCP_B {
    
    public static void main (String [] args) {
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
    private int value = -1000;
    
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
                    /* COMPLETE */
                }
                /* COMPLETE */
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
            /* COMPLETE */
        }
    }
}