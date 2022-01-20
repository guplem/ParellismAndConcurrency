package Exercise_04_A;

public class EX_04_A {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 4 THREADS THAT WRITE");
		System.out.println("-----------------------------");
		System.out.println("");
    	
        LineThread a, b;
        
        a = new LineThread("A");
        b = new LineThread("  B");
        
        a.start();
        b.start();
        
        try {
            Thread.sleep(5 * 1000);
        } catch(InterruptedException ie) {}
        
        a.stop();
        b.stop();

		System.out.println("");
        System.out.println("A wrote " + a.getCounter() + " times");
        System.out.println("B wrote " + b.getCounter() + " times");
        
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
    
    @Override
    public void run() {
    	while (true) {
    		System.out.println(line+":"+counter);
    		counter ++;
    	}
    }
    
}
