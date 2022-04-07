package ex2_CountDown;

import java.net.*;
import java.io.*;

public class Server extends Thread {

	private Socket socket;
	private PrintWriter outChannel;
	private BufferedReader inChannel;
	private String clientName;
	private int number;
	private int delay;

	public Server(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			innerRun();
		} catch (Exception e) {
		}
	}

	private void innerRun() throws Exception {

		/* COMPLETE */
		// Remember that this instance serves just one client and then terminates.

		this.inChannel = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.outChannel = new PrintWriter(this.socket.getOutputStream(), true);

		while (true) {

			String message = this.inChannel.readLine();
			String[] messageSplit = message.split(" ");

			System.out.println("MESSAGE: " + message);

			switch (messageSplit[0]) {
			case "HELLO":
				clientName = messageSplit[1];
				break;
			case "START":
				number = Integer.parseInt(messageSplit[1]);
				break;
			case "DELAY":
				delay = Integer.parseInt(messageSplit[1]);
				break;
			case "GO":

				System.out.println("STARTING COUTNDOWN");
				for (int i = number; i >= 0; i--) {
					Thread.sleep(delay);
					this.outChannel.println(clientName + "(" + i + ")");
					System.out.println("Sending: " + clientName + "(" + i + ")");
				}
				this.outChannel.println("GOODBYE");
				outChannel.close();
				inChannel.close();
				socket.close();
				break;
			}

		}
	}

	/*
	 * Use this method to separate requests into their components (when needed) 0-th
	 * component will contain the keyword (HELLO, START, ...) 1-th component will
	 * contain the "parameter" (the name of the client, the delay, ...)
	 */
	private String[] splitLine(String line) {
		return line.split(" ");
	}

}
