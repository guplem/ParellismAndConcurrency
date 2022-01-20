package Exercise_04_B;

public class EX_04_B {
    
    public static void main (String [] args) {
        LineThread a, b;
        
        a = new LineThread("A");
        b = new LineThread("  B");
        
        /* COMPLETE */
        
        a.start();
        b.start();
        
        // sleep five seconds then kill a and b
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        a.stop();
        b.stop();
        
        System.out.println(); System.out.println();
        System.out.println("A wrote "+a.getCounter()+" times");
        System.out.println("B wrote "+b.getCounter()+" times");
    }
}

class LineThread extends Thread{
    
    private String line; // the line to write
    private int counter; // counter
    
    public LineThread (String line) {
        this.line = line;
        counter = 0;
    }
    
    public int getCounter() {
        return this.counter;
    }
    
    public void run () {
        while (true) {
            System.out.println(line+":"+this.counter);
            counter++;
        }
    }
    
}
