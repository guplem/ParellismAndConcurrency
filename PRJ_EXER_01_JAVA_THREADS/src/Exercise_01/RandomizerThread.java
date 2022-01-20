package Exercise_01;

import java.util.Random;

public class RandomizerThread extends Thread {
    
    private int theNumber=-1000;
    
    
    public int GetNumber() {
    	return theNumber;
    }
    
    @Override
    public void run() {
    	theNumber = new Random().nextInt(10);
    	System.out.println("My number is: " + theNumber);
    	super.run();
    }
    
}