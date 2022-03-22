package joke_machine;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server extends Thread {
	
	private static ServerSocket serverSocket;
	private static List<String[]> theJokes; 
	
	// launcher...
	public static void main (String [] args) throws IOException {
		serverSocket = new ServerSocket(6768);
		(new Server()).start();
		System.out.println("Joke machine server created and listening to port 6768");
	} // launcher ends here
	
	
	private Connection[] connections = new Connection[3];
	private class Connection {
		public Socket socket;
		public BufferedReader inputChannel;
		public PrintWriter outputChannel;
	}
	private class Request {
		public Request(Connection connection, String command) {
			this.connection = connection;
			this.command = command;
		}
		public Connection connection;
		public String command;
	}
	
	private Random alea = new Random();
	
	public void run () {
		try {
			innerRun();
		}
		catch (IOException ioex) {}
	}
	
	private void innerRun () throws IOException {
		Request request;
		while (true) {
			// accept a new connection
			acceptConnection();
			
			// get a HELLO request and reply with JOKES numberOfJokes
			// if request is not HELLO, close connection and skip the rest
			// of the iteration (continue;)
			
			request = this.receiveRequest();

			if (request.command.equals("HELLO")) {
				sendReply("JOKES " + theJokes.size(), request.connection);
			} else {
				disconnect(request.connection);
				continue;
			}
			
			// get a request and iterate until the request is STOP
			// JOKE requests generate several consecutive replies... 
			// ... an integer (the number of lines of the joke) and
			// all the lines of the joke
			request = this.receiveRequest();
			while (!request.command.equals("STOP")) {
				
				if (request.command.equals("JOKE")) {
					String[] randomJoke = theJokes.get(alea.nextInt(theJokes.size()));
					for (int i = -1; i < randomJoke.length; i++) {
						if (i == -1)
							sendReply(randomJoke.length+"", request.connection);
						else
							sendReply(randomJoke[i], request.connection);
					}
				}
				
				request = this.receiveRequest();
			}
			
			disconnect(request.connection);
		}
	}
	// UTILITY METHODS. Do not modify
	
	private void acceptConnection () throws IOException {
		
		int freeSpace = -1;
		for (int c = 0; c < this.connections.length; c ++ ) {
			if (this.connections[c] == null)
				freeSpace = c;
		}
		if (freeSpace == -1)
			return;
		
		Connection connexion = new Connection();
		connexion.socket = this.serverSocket.accept();
		connexion.inputChannel = new BufferedReader(
                                new InputStreamReader(
                                		connexion.socket.getInputStream()));
		connexion.outputChannel = new PrintWriter(
				connexion.socket.getOutputStream(), true);
		
		connections [freeSpace] = connexion;
		
    }
	
	private Request receiveRequest () throws IOException {
		for (int c = 0; c < this.connections.length; c ++ ) {
			String request = this.connections[c].inputChannel.readLine();
			if (request != null)
				return new Request(connections[c], request);
		}
		return null;
    }
	
	private void sendReply(String reply, Connection conection) throws IOException {
		conection.outputChannel.println(reply);
	}

	private void disconnect(Connection conection) throws IOException {
		conection.socket.close();
		conection.inputChannel.close();
		conection.outputChannel.close();
	}
	
	//-----
	static {
		theJokes = new ArrayList<String[]>();
		String line;
		List<String>joke;
		try {
			BufferedReader bur = new BufferedReader(new FileReader("Jokes.txt"));
			line = bur.readLine();
			while (line!=null) {
				joke = new ArrayList<String>();
				while(!line.equals("/*EOJ*/")) {
					joke.add(line);
					line=bur.readLine();
				}
				theJokes.add(joke.toArray(new String[0]));
				line = bur.readLine();
			}
			// when here end of file has been reached...
			bur.close();
		}
		catch (Exception ex) {
			System.out.println("unable to load jokes. Terminating...");
			System.exit(0);
		}
	}
}

