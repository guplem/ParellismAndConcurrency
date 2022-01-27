package Exercise_02_PingPong_BB;

public class PingPong_Sync_B {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 2 SYNCHRONIZED PING PONG WITH ACTIVE WAITING AND SHARED OBJECT");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("");
    	
        SharedObject shared = new SharedObject();
        
        Ping ping = new Ping(shared);
        Pong pong = new Pong(shared);
		
        pong.start();
        ping.start();
        
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        ping.stop();
        pong.stop();
    }
    
}

class SharedObject {
    private volatile int whoCanPrint = 1;
    
    public int getWhoCanPrint () {return this.whoCanPrint;}
    public void setWhoCanPrint (int wcp) {this.whoCanPrint=wcp;}
}

class Ping extends Thread {
    
    private SharedObject shared;
    
	public Ping(SharedObject shared) {
		this.shared = shared;
	}
    
    public void run () {
        while (true) {
            while (shared.getWhoCanPrint()!=1) {Thread.yield();}
            System.out.println("PING");
            shared.setWhoCanPrint(2);
        }
    }
}

class Pong extends Thread {
    
    private SharedObject shared;
    
	public Pong(SharedObject shared) {
		this.shared = shared;
	}
    
    public void run () {
        while (true) {
            while (shared.getWhoCanPrint()!=2) {Thread.yield();}
            System.out.println("   PONG");
            shared.setWhoCanPrint(1);
        }
    }
}