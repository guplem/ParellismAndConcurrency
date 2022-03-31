package ex2_CountDown;

import java.net.*;
import java.io.*;

public class Server extends Thread {

	private Socket socket;
	private PrintWriter outChannel;
	private BufferedReader inChannel;
	
	public Server (Socket socket) {
		this.socket = socket;
	}
	
	public void run () {
		try { innerRun(); }
		catch (Exception e) {}
	}
	
	
	private void innerRun () throws Exception{
		
		/* COMPLETE */
		// Remember that this instance serves just one client and then terminates.
		
		outChannel.close();
		inChannel.close();
		socket.close();
	}
	
	
	/*
	 Use this method to separate requests into their components (when needed)
	 0-th component will contain the keyword (HELLO, START, ...)
	 1-th component will contain the "parameter" (the name of the client, the delay, ...)
	 */
	private String [] splitLine (String line) {
		return line.split(" ");
	}
	
}
