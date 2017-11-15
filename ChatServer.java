import java.io.*;
import java.net.*;

public class ChatServer {

	// Initialization of class attributes
	private static final int maxNoOfUsers = 10;
	private static final ClientThread[] threads = new ClientThread[maxNoOfUsers];
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;

	public static void main(String args[]){
		int portNumber = 12345;

		// Checks if the user provided a port number for the server
		if (args.length > 0){
			portNumber = Integer.parseInt(args[0]);
		} else {
			System.out.println("Please provide a port number. [Port Number Range: 0 to 65535]");
		}

		System.out.println("Port Number in use: " + portNumber);
		// Checks if the provided port number is valid
		if (portNumber >= 0 && portNumber <= 65535){
			// Opens server socket
			try {
				serverSocket = new ServerSocket(portNumber);
			} catch(IOException e){
				System.out.println("An error occured while opening the server socket.");
			}
		} else {
			System.out.println("Invalid port number. [Valid Port Number Range: 0 to 65535]");
		}

		// This where the connection between the server and clients happen
		while (true){
			try {

				// The server tries to accept a client request
				clientSocket = serverSocket.accept();
				int i = 0;

				// Adds the client to the client thread
				for (i = 0; i < maxNoOfUsers; i++){
					if (threads[i] == null){
						(threads[i] = new ClientThread(clientSocket, threads)).start();
						break;
					}
				}

				// If the maximum number of users is reached, the exceeding will be ignored
				if (i == maxNoOfUsers){
					PrintStream outputStream = new PrintStream(clientSocket.getOutputStream());
					outputStream.println("Server overload...");
					outputStream.close();
					clientSocket.close();
				}

			} catch(IOException e) {
				System.out.println("An error occured while waiting for a connection.");
			}
		}
	}
}

/* Referred from: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html */