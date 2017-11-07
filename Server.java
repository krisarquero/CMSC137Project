import java.io.*;
import java.net.*;

public class Server {


	// Initialization of variables
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static final int maxNoOfUsers = 10;
	private static final ClientThread[] threads = new ClientThread[maxNoOfUsers];

	public static void main(String args[]) {

		// Default port number
		int portNumber = 2222;

		// Prints the usage of the program and the current port number used by the program
		if (args.length < 1) {
			System.out.println("Usage: java Server <portNumber>\n" + "[ Port Number: " + portNumber + " ]");
		} else {
			portNumber = Integer.valueOf(args[0]).intValue();
		}

		// Opens the server socket
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		// 
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxNoOfUsers; i++) {
					if (threads[i] == null) {
						(threads[i] = new ClientThread(clientSocket, threads)).start();
						break;
					}
				}
				if (i == maxNoOfUsers) {
					PrintStream outputStream = new PrintStream(clientSocket.getOutputStream());
					outputStream.println("Client service overload. Try again later.");
					outputStream.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}