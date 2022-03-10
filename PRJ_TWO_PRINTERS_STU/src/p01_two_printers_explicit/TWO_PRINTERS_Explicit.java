package p01_two_printers_explicit;

import java.util.concurrent.locks.*;

public class TWO_PRINTERS_Explicit {
	
	public static void main (String [] args) {
		int NUM_CLIENTS = 6;
		
		PrintingMonitor monitor = new PrintingMonitor();
		PrinterClient [] clients = new PrinterClient[NUM_CLIENTS];
		
		for (int i=0; i<clients.length; i++) {
			clients[i] = new PrinterClient(i+1, monitor);
			clients[i].start();
		}
		
		try {Thread.sleep(20000);} catch(InterruptedException ie) {}
		
		System.out.println("********** KILLING OF THREADS STARTS NOW ****************");
		
		for (int i=0; i<clients.length; i++) {
			clients[i].stop();
		}
	}

}

class PrintingMonitor {
	
	public static enum PrinterCode {CO, BW, COBW};
	private volatile boolean colourFree = true, BWFree = true;
	
	private ReentrantLock lock = new ReentrantLock();
	
	/* COMPLETE */
	
	public PrinterCode requestBWPrinter(int procID) {
		PrinterCode granted;
		
		lock.lock();
		/* COMPLETE */
		System.out.println("- process "+procID+" has been granted "+granted.toString());
		lock.unlock();
		return granted;
	}
	
	public PrinterCode requestColourPrinter(int procID) {
		lock.lock();
		System.out.println("+ process "+procID+" requests COLOUR");
		/* COMPLETE */
		System.out.println("- process "+procID+" has been granted "+PrinterCode.CO);
		lock.unlock();
		return PrinterCode.CO;
	}
	
	public void printDone (PrinterCode printerCode, int procID) {
		lock.lock();
		/* COMPLETE */
		System.out.println("- process "+procID+" has released "+printerCode);
		lock.unlock();
	}
	
}


class PrinterClient extends Thread{
	private PrintingMonitor monitor;
	private int id;
	
	public PrinterClient (int id, PrintingMonitor monitor) {
		this.id = id;
		this.monitor = monitor;
		this.setName("Fil-"+id);
	}
	
	public void run () {
		PrintingMonitor.PrinterCode printerCode;
		while (true) {
			
			if (Math.random()>=0.5) {
				printerCode = monitor.requestBWPrinter(id);
				try{sleep(5);}catch(InterruptedException ie){}
				System.out.println("\tProcess "+id+" BW printing with "+printerCode);
				try{sleep(5);}catch(InterruptedException ie){}
				monitor.printDone(printerCode, id);
			}
			
			else {
				printerCode = monitor.requestColourPrinter(id);
				try{sleep(5);}catch(InterruptedException ie){}
				System.out.println("\tProcess "+id+" COLOUR printing with "+printerCode);
				try{sleep(25);}catch(InterruptedException ie){}
				monitor.printDone(printerCode, id);
			}
			
			//try{sleep(5);}catch(InterruptedException ie){}
		}
	}
}

