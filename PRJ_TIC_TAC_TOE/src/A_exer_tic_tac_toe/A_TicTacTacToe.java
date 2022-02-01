package A_exer_tic_tac_toe;

public class A_TicTacTacToe {
	
	public static void main (String [] args)  {	
		
		/* COMPLETE */
		
		tic.stop();
		tac.stop();
		toe.stop();
	}
}

class Shared {
	 /*
	 ...an object that encapsulates three boolean attributes (they may be public) 
	 and nothing more
	 */
}

class Tic extends Thread {
	// endlessly prints TIC-
	
	private Shared sharedObject;
	
	/* COMPLETE */
}

class Tac extends Thread {
	// endlessly prints TAC (or tac)
	
	private Shared sharedObject;
	// anything else? 
	
	/* COMPLETE */
	
}

class Toe extends Thread {
	// endlessly prints -TOE
	
	private Shared sharedObject;
	
	/* COMPLETE */
	
}

