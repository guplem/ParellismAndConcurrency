package Exercise_04_A;

public class EX_04_A {
    
    public static void main (String [] args) {
        LineThread a, b;
        
        a = new LineThread("A");
        b = new LineThread("  B");
        
        /* COMPLETE */
        
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
    
    /* COMPLETE */
    
}
