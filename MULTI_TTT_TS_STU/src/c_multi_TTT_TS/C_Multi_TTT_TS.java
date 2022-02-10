package c_multi_TTT_TS;

import java.util.concurrent.atomic.AtomicInteger;


public class C_Multi_TTT_TS {
	
	static int INSTANCES = 10;
	
	public static void main (String [] args) throws InterruptedException {	
		TsBasedSynchronizer syncTool = new TsBasedSynchronizer();
		Tic[] theTics = new Tic[INSTANCES];
		Tac[] theTacs = new Tac[INSTANCES];
		Toe[] theToes = new Toe[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			theTics[i] = new Tic(i, syncTool);
			theTacs[i] = new Tac(i, syncTool);
			theToes[i] = new Toe(i, syncTool);
			theToes[i].start();
			theTacs[i].start();
			theTics[i].start();
		}
		
		Thread.sleep(5000);
		
		System.exit(0);
	}
}

class TsBasedSynchronizer  {
	private static final  int TIC_POSSIBLE = 1;
	private static final int TAC_POSSIBLE = 2;
	private static final int TOE_POSSIBLE = 3;
	private static final int WRITING_IN_COURSE = 4;
	
	AtomicInteger state = new AtomicInteger(TIC_POSSIBLE);
	
	public void letMeTic() {
		
	}
	public void letMeTac() {
		
	}
	public void letMeToe() {
		
	}
	
	public void ticDone() {
		
	}
	
	public void tacDone() {
		
	}
	
	public void toeDone() {
		
	}
	
}

class Tic extends Thread {
	
	private TsBasedSynchronizer syncTool;
	private int id;
	
	public Tic (int id, TsBasedSynchronizer st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
		}
	}
}

class Tac extends Thread {
	
	private TsBasedSynchronizer syncTool;
	private int id;
	
	private static volatile boolean upperCase = true;  // static. All instance share
	                                                   // the same variable
	
	public Tac (int id, TsBasedSynchronizer st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			/* COMPLETE */
		}
	}
}

class Toe extends Thread {
	
	private TsBasedSynchronizer syncTool;
	private int id;
	
	public Toe (int id, TsBasedSynchronizer st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
		}
	}
}

