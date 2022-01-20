package Exercise_02_PingPong_BA;

public class PingPong_Sync_A {
    
    public static void main (String [] args) {
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
    
    public volatile boolean canPrint = /* true or false? */;
    
    private Pong companion;
    
    /* COMPLETE */
}

class Pong extends Thread {
    
    public volatile boolean canPrint = /* true or false? */;
    
    private Ping companion;
    
    /* COMPLETE */
}