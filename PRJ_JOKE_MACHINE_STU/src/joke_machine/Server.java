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
		serverSocket = new ServerSocket(6666);
		(new Server()).start();
		System.out.println("Joke machine server created and listening to port 6666");
	} // launcher ends here
	
	
	private Socket connection;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;
	private Random alea = new Random();
	
	public void run () {
		try {
			innerRun();
		}
		catch (IOException ioex) {}
	}
	
	private void innerRun () throws IOException {
		String request;
		while (true) {
			// accept a new connection
			acceptConnection();
			
			// get a HELLO request and reply with JOKES numberOfJokes
			// if request is not HELLO, close connection and skip the rest
			// of the iteration (continue;)
			
			request = this.receiveRequest();

			if (request.equals("HELLO")) {
				sendReply("JOKES " + theJokes.size());
			} else {
				disconnect();
				continue;
			}
			
			// get a request and iterate until the request is STOP
			// JOKE requests generate several consecutive replies... 
			// ... an integer (the number of lines of the joke) and
			// all the lines of the joke
			request = this.receiveRequest();
			while (!request.equals("STOP")) {
				
				if (request.equals("JOKE")) {
					String[] randomJoke = theJokes.get(alea.nextInt(theJokes.size()));
					for (int i = -1; i < randomJoke.length; i++) {
						if (i == -1)
							sendReply(randomJoke.length+"");
						else
							sendReply(randomJoke[i]);
					}
				}
				
				request = this.receiveRequest();
			}
			
			disconnect();
		}
	}
	// UTILITY METHODS. Do not modify
	
	private void acceptConnection () throws IOException {
        this.connection = this.serverSocket.accept();
        this.inputChannel = new BufferedReader(
                                new InputStreamReader(
                                    this.connection.getInputStream()));
        this.outputChannel = new PrintWriter(
                                 this.connection.getOutputStream(), true);
    }
	
	private String receiveRequest () throws IOException {
        return this.inputChannel.readLine();
    }
	
	private void sendReply(String reply) throws IOException {
		this.outputChannel.println(reply);
	}

	private void disconnect() throws IOException {
		this.connection.close();
		this.inputChannel.close();
		this.outputChannel.close();
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

