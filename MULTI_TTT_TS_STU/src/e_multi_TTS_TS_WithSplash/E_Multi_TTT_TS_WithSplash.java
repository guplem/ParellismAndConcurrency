package e_multi_TTS_TS_WithSplash;

import java.util.concurrent.atomic.AtomicInteger;

/* Copy, from your solution of the previous exercise:
   - The launcher class containing main
   - Tic, Tac and Toe classes
   - TsBasedSynchronizer class
   Then modifies what needs to be modified in order to accomodate
   the splash
 */


//------------------------------------------------------------

class Splash extends Thread {
	private TsBasedSynchronizer syncTool;
	
	public Splash (TsBasedSynchronizer st) {
		this.syncTool = st;
	}
	
	public void run () {
		while(true) {
			try {Thread.sleep(500);} catch(InterruptedException ie) {}
			syncTool.letMeSplash();
			System.out.println("\n\tSPLASH!");
			syncTool.splashDone();
		}
	}
}

