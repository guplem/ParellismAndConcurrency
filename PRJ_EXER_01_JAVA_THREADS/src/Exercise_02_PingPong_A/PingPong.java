package Exercise_02_PingPong_A;

public class PingPong {
    
    public static void main (String [] args) {
    	
		System.out.println("EXERCISE 2 UNSYNCHRONIZED PING PONG");
		System.out.println("-----------------------------------");
		System.out.println("");
    	
        Ping ping = new Ping();
        Pong pong = new Pong();
        
        ping.start();
        pong.start();
        
        try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) { }
        
        ping.stop();
        pong.stop();
        
    }
    
}

class Ping extends Thread {
    public void run () {
        while (true) {
            System.out.println("PING");
            try {
				Thread.sleep(10);
    		} catch (InterruptedException e) { }
        }
    }
}

class Pong extends Thread {
    public void run () {
        while (true) {
            System.out.println(" PONG");
            try {
				Thread.sleep(10);
    		} catch (InterruptedException e) { }
        }
    }
}