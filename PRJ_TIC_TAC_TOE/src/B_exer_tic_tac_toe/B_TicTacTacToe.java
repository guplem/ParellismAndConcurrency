package B_exer_tic_tac_toe;

public class B_TicTacTacToe {
	
	public static void main (String [] args)  {
		
		/* COMPLETE */
		
		tic.stop();
		tac.stop();
		toe.stop();
	}
}

class Shared {
	
	/* COMPLETE -private attributes- private? yes, private*/
	
	public void letMeTic () {
		// invoked by Tic before writing TIC-.
		// if this method returns then it is safe to write TIC-
		
		/* COMPLETE */
	}
	
	public void ticWritten () {
		// invoked by Tic just after printing TIC-
		
		/* COMPLETE */
	}
	
	
	public void letMeTac () {
		// invoked by Tac before writing TAC (or tac)
		// if this method returns then it is safe to write TAC (or tac)
		
		/* COMPLETE */
	}
	
	public void tacWritten () {
		// invoked by Tac just after printing TAC (or tac)
		
		/* COMPLETE */
	}
	
	public void letMeToe () {
		// invoked by Toe before writing -TOE.
		// if this method returns then it is safe to write -TOE
		
		/* COMPLETE */
	}
	
	public void toeWritten () {
		// invoked by Toe just after printing -TOE
		
		/* COMPLETE */
	}
	
	
}


class Tic extends Thread {
	
	private Shared shared;
	
	public Tic (Shared sh) {
		this.shared = sh;
	}
	
	public  void run () {
		while (true) {
			shared.letMeTic();
			/* COMPLETE */
		}
	}
}


class Tac extends Thread {
	
	private Shared shared;
	// anything else? /* COMPLETE if needed*/
	
	public Tac (Shared sh) {
		this.shared = sh;
	}
	
	public void run () {
		while (true) {
			/* COMPLETE */
		}
	}
}

/* Do not modify this class */
class Toe extends Thread {
	
	private Shared shared;
	
	public Toe (Shared sh) {
		this.shared = sh;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
			shared.toeWritten();
		}
	}
}

