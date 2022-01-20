package Exercise_01;

public class Main {
	public static void main (String [] args) throws InterruptedException{
        
		System.out.println("EXERCISE 1");
		System.out.println("----------");
		System.out.println("");
		
		RandomizerThread [] threadTable = new RandomizerThread[5];
		
		for(int i = 0; i < threadTable.length; i++) {
			threadTable[i] = new RandomizerThread();
			threadTable[i].start();
		}
		
		for(int i = 0; i < threadTable.length; i++) {
			threadTable[i].join();
		}
		
		int totalSum = 0;
		for(int i = 0; i < threadTable.length; i++) {
			totalSum += threadTable[i].GetNumber();
		}
       

		System.out.println("");
		System.out.println("The sum is: " + totalSum);
        
    }
}
