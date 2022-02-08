package d_mpp_version_3;

/* VERSION 3: like version 1a -no restriction- but using the implicit lock in the synchronizer */

public class MPP_LOCK_3 {
	
	public static void main (String [] args) throws InterruptedException {
		
		ImplicitLockSynchronizer sync = new ImplicitLockSynchronizer();
		
		Ping [] thePings = new Ping[10];
		Pong [] thePongs = new Pong[10];
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i] = new Ping(i, sync);
			thePongs[i] = new Pong(i, sync);
			thePings[i].start();
			thePongs[i].start();
		}
		
		Thread.sleep(5000);
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i].stop();
			thePongs[i].stop();
		}
		
	}
	
}

class ImplicitLockSynchronizer {
	public volatile boolean canPing = true; 
	// DO NOT ADD ANYTHING ELSE
}

class Ping extends Thread {
	
	private int id;
	private ImplicitLockSynchronizer sync;
	
	public Ping (int id, ImplicitLockSynchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	public void run ()  {
		
		while (true) {
			
			synchronized(sync) {
				 // If this point is reached, the thread is currently owning the lock in sync
				if (sync.canPing)
				{
					System.out.print("ping("+id+")");
					try {Thread.sleep(10);} catch(InterruptedException ie ) {}
					sync.canPing = false;
				}
			}
			
		}
	}
}

class Pong extends Thread {
	
	private int id;
	private ImplicitLockSynchronizer sync;
	
	public Pong (int id, ImplicitLockSynchronizer sync) {
		this.id = id;
		this.sync = sync;
	}
	
	
	public void run ()  {
		while (true) {
			
			synchronized(sync) {
				 // If this point is reached, the thread is currently owning the lock in sync
				if (!sync.canPing)
				{
					System.out.println(" pong("+id+")");
					try {Thread.sleep(10);} catch(InterruptedException ie ) {}
					sync.canPing = true;
				}
			}
			
		}
	}
}