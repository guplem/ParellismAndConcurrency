package joke_machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
	
	private static Socket connection;
	private static BufferedReader inputChannel;
	private static PrintWriter outputChannel;
	
	public static void main (String [] args) throws IOException {
		String reply;
		int numLines;
		
		connect();
		
		// send a HELLO request and show the reply
		System.out.println("Sending a HELLO request...");
		sendRequest("HELLO");
		reply = receiveReply();
		System.out.println("\tServer reply: "+reply);
		
		System.out.println("Sending a JOKE request...");
		sendRequest("JOKE");
		reply = receiveReply();
		System.out.println("\tServer reply: "+reply);
		
		numLines = Integer.parseInt(reply);
		System.out.println("Server is about to send a joke of "
		                    +numLines+" lines");
		System.out.println();
		for (int i=1; i<=numLines; i++) {
			reply=receiveReply();
			System.out.println("\t"+reply);
		}
		System.out.println("\n---- end of joke -----\n");
		
		System.out.println("Sending another JOKE request...");
		sendRequest("JOKE");
		reply = receiveReply();
		System.out.println("\tServer reply: "+reply);
		
		numLines = Integer.parseInt(reply);
		System.out.println("Server is about to send a joke of "
		                    +numLines+" lines");
		System.out.println();
		for (int i=1; i<=numLines; i++) {
			reply=receiveReply();
			System.out.println("\t"+reply);
		}
		System.out.println("\n---- end of joke -----\n");
		
		System.out.println("Sending a STOP request...");
		sendRequest("STOP");
		System.out.println("...and disconnecting");
		disconnect();
		System.out.println();
	}
	
	private static void connect () throws IOException {
        connection = new Socket("localhost", 6768);
        inputChannel = new BufferedReader(
                           new InputStreamReader(
                               connection.getInputStream()));
        outputChannel = new PrintWriter(connection.getOutputStream(), true);
    }
    
    private static void disconnect () throws IOException {
        inputChannel.close();
        outputChannel.close();       
        connection.close();
    }
    
    private static String receiveReply () throws IOException {
        return inputChannel.readLine();    
    }
    
    private static void sendRequest (String request) throws IOException {
        outputChannel.println(request);
    }

}
