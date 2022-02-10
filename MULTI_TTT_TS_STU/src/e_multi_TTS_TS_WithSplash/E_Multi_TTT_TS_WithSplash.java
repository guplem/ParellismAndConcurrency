package e_multi_TTS_TS_WithSplash;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class E_Multi_TTT_TS_WithSplash {
	
	static int INSTANCES = 10;
	
	public static void main (String [] args) throws InterruptedException {	
		TsBasedSynchronizer syncTool = new TsBasedSynchronizer();
		Tic[] theTics = new Tic[INSTANCES];
		Tac[] theTacs = new Tac[INSTANCES];
		Toe[] theToes = new Toe[INSTANCES];
		Splash splash = new Splash(syncTool);
		
		for (int i=0; i<INSTANCES; i++) {
			theTics[i] = new Tic(i, syncTool);
			theTacs[i] = new Tac(i, syncTool);
			theToes[i] = new Toe(i, syncTool);
			theToes[i].start();
			theTacs[i].start();
			theTics[i].start();
		}
		splash.start();
		
		Thread.sleep(5000);
		
		System.exit(0);
	}
}

class TsBasedSynchronizer  {
	private static final  int TIC_POSSIBLE = 1;
	private static final int TAC_POSSIBLE = 2;
	private static final int TOE_POSSIBLE = 3;
	private static final int SPLASH_POSSIBLE = 4;
	private static final int WRITING_IN_COURSE = 5;
	
	AtomicInteger state = new AtomicInteger(TIC_POSSIBLE);
	AtomicBoolean wannaSplash = new AtomicBoolean(false);
	int nextId = 0;
	
	public void letMeTic() {
		while(!state.compareAndSet(TIC_POSSIBLE, WRITING_IN_COURSE)) {
			Thread.yield();
		}
	}
	public void letMeTac() {
		while(!state.compareAndSet(TAC_POSSIBLE, WRITING_IN_COURSE)) {
			Thread.yield();
		}
	}
	public void letMeToe(int id) {
		while(! (id == nextId && state.compareAndSet(TOE_POSSIBLE, WRITING_IN_COURSE) ) ) {
			Thread.yield();
		}
	}
	
	public void letMeSplash() {
		wannaSplash.set(true);	
		while(!state.compareAndSet(SPLASH_POSSIBLE, WRITING_IN_COURSE)) {
			Thread.yield();
		}
	}
	
	public void ticDone(int id) {
		try {Thread.sleep(150);} catch(InterruptedException ie) {} // FORCED DELAY
		if(id%2 == 0 && wannaSplash.compareAndSet(true, false)) {
			state.set(SPLASH_POSSIBLE);
		} else {
			state.set(TAC_POSSIBLE);
		}
	}
	
	public void tacDone() {
		try {Thread.sleep(150);} catch(InterruptedException ie) {} // FORCED DELAY
		state.set(TOE_POSSIBLE);
	}
	
	public void toeDone(int id) {
		nextId= (id+1) % E_Multi_TTT_TS_WithSplash.INSTANCES;
		try {Thread.sleep(150);} catch(InterruptedException ie) {} // FORCED DELAY
		state.set(TIC_POSSIBLE);
	}
	
	
	public void splashDone() {
		try {Thread.sleep(150);} catch(InterruptedException ie) {} // FORCED DELAY
		state.set(TIC_POSSIBLE);
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
			syncTool.letMeTic();
			System.out.print("TIC("+id+")-");
			syncTool.ticDone(id);
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
			syncTool.letMeTac();
			if (upperCase) System.out.print("TAC("+id+")");
			else System.out.print("tac("+id+")");
			upperCase = !upperCase;
			syncTool.tacDone();
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
			syncTool.letMeToe(id);
			System.out.println("-TOE("+id+")");
			syncTool.toeDone(id);
		}
	}
}

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

