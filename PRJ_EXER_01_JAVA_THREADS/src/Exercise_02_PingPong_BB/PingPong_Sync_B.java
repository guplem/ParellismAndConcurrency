package Exercise_02_PingPong_BB;

public class PingPong_Sync_B {
    
    public static void main (String [] args) {
        SharedObject shared = new SharedObject();
        
        /* COMPLETE */
        
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
    
    /* COMPLETE */
    
    public void run () {
        while (true) {
            while (shared.getWhoCanPrint()!=1) {/* busy wait*/}
            /* COMPLETE */
        }
    }
}

class Pong extends Thread {
    
    private SharedObject shared;
    
    /* COMPLETE */
    
    public void run () {
        while (true) {
            /* COMPLETE */
        }
    }
}