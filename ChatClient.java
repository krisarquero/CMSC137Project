import java.io.*;
import java.net.*;

public class ChatClient implements Runnable {

	// Initialization of class attributes
	private static BufferedReader inputStream = null;
	private static BufferedReader inputLine = null;
	private static PrintStream outputStream = null;
	private static Socket clientSocket = null;
	private static boolean active = true;
	private static int portNumber = 12345;
	private static String host = "localhost";

	public static void main(String args[]){

		// Checks if the user
		if (args.length > 1) {
			host = args[0];
			// reads the port from the argument
			portNumber = Integer.parseInt(args[1]);
		} else {
			if (portNumber == 12345){
				System.out.println("Please provide the correct host and port number.");
			}
		}

		try {
			// initialization of input and out streams for connection
			clientSocket = new Socket(host, portNumber);
		} catch(IOException e){
			System.out.println("An error occured while creating the socket.");
		}

		// Once the connection is established, the following streams for input and output will be initialized
		try {
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintStream(clientSocket.getOutputStream());
		} catch(IOException e){
			System.out.println(e);
		}

		// ensures that the prerequisites for connection are properly initialized
		if (clientSocket != null && inputStream != null && outputStream != null){
			try {
				new Thread(new ChatClient()).start();
				// Outputs all the inputs that the other connected clients are sending
				while(active){
					outputStream.println(inputLine.readLine().trim());
				}
				outputStream.close();
				inputStream.close();
				clientSocket.close();
			} catch(IOException e){
				System.out.println("An error occured in one of the clients.");
			}
		}
	}

	public void run(){
		//variable to handle the messages 
		String inputLine;
		try {
			//Reads and prints messages until the the word Bye is encountered 
			inputLine = inputStream.readLine();
			while (inputLine != null){
				System.out.println(inputLine);
				inputLine = inputStream.readLine();
				if (inputLine.startsWith("----- Bye") == true){
					break;
				} 
			}
		} catch(IOException e){
			System.out.println("An error occured while getting responses");
		}
	}
}

/* Referred from: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html */