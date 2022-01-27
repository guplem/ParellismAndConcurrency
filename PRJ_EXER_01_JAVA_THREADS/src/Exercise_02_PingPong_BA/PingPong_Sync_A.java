package Exercise_02_PingPong_BA;

public class PingPong_Sync_A {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 2 SYNCHRONIZED PING PONG WITH ACTIVE WAITING");
		System.out.println("-----------------------------------------------------");
		System.out.println("");
    	
        Ping ping = new Ping();
        Pong pong = new Pong();
        
        ping.setCompanion(pong);
        pong.setCompanion(ping);
        
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
class Ping extends Thread {
    
    public volatile boolean canPrint = true;
    
    private Pong companion;
    
    public void setCompanion(Pong pong) {
    	this.companion = pong;
    }
    
    @Override
    public void run() {
        while (true) {
            while (!canPrint) { // Espera activa, el processador segueix ocupat amb el thread, no gaire recomenable
        		Thread.yield();
            }
            System.out.println("PING");
            canPrint = false;
            companion.canPrint = true;
        }
    }
}

class Pong extends Thread {
    
    public volatile boolean canPrint = false;
    
    private Ping companion;
    
    public void setCompanion(Ping ping) {
    	this.companion = ping;
    }
    
    @Override
    public void run() {
        while (true) {
            while (!canPrint) { // Espera activa, el processador segueix ocupat amb el thread, no gaire recomenable
            	Thread.yield();
            }
            System.out.println("   PONG");
            canPrint = false;
            companion.canPrint = true;
        }
    }
}