package ex2_CountDown;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceLauncher {
	
	public static void main (String [] args) throws IOException {
		ServerSocket ss = new ServerSocket (5566);
		System.out.println("CountDown service started");
		
		/* Complete */

		Socket connection;
        while (true) {
            connection = ss.accept();
            new Server(connection).start();
        }
	}
}
