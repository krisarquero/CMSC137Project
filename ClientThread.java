import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

	//Declaration of variables
	private String clientName = null;
	private DataInputStream inputStream = null;
	private PrintStream outputStream = null;
	private Socket clientSocket = null;
	private final ClientThread[] threads;
	private int maxNoOfUsers;

	//Constructor declaration
	public ClientThread(Socket clientSocket, ClientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxNoOfUsers = threads.length;
 	}

	public void run() {
		int maxNoOfUsers = this.maxNoOfUsers;
		ClientThread[] threads = this.threads;

		//Creating the input and output streams for the client
		try {
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new PrintStream(clientSocket.getOutputStream());
			String name;
			while (true) {
				outputStream.println("Enter your name: ");
				name = inputStream.readLine().trim();
				if (name.indexOf('@') == -1) {
					break;
				} else {
 					outputStream.println("The name should not contain '@' character.");
				}
			}

			outputStream.println("----- Welcome " + name + "!\nTo leave, enter \"/quit\". -----");

			//marker to easily identify a user
			synchronized (this) {
				for (int i = 0; i < maxNoOfUsers; i++) {
				if (threads[i] != null && threads[i] == this) {
					clientName = "@" + name;
					break;
				}
			}

			for (int i = 0; i < maxNoOfUsers; i++) {
				if (threads[i] != null && threads[i] != this) {
					threads[i].outputStream.println("----- " + name + " joined the group chat. -----");
				}
			}
		}

		//Getting the message from the user
		while (true) {
			String line = inputStream.readLine();
			if (line.startsWith("/quit")) {	//Checks if the line read is /quit then breaks the loop
				break;
			}

			if (line.startsWith("@")) {
				String[] words = line.split("\\s", 2);
				
				if (words.length > 1 && words[1] != null) {
					words[1] = words[1].trim();
					
					if (!words[1].isEmpty()) {
						synchronized (this) {
							for (int i = 0; i < maxNoOfUsers; i++) {
								if (threads[i] != null && threads[i] != this && threads[i].clientName.equals(words[0])) {
   									threads[i].outputStream.println(name + ": " + words[1]);
									this.outputStream.println(name + ": " + words[1]);
									break;
								}
							}
						}
					}
				}
			
			} else {

	  			synchronized (this) {
	  				for (int i = 0; i < maxNoOfUsers; i++) {
	  					if (threads[i] != null && threads[i].clientName != null) {
	  						threads[i].outputStream.println(name + ": " + line);
	  					}
	  				}
				}
			}
		}

		//Iterates through all the users and if the user has already left the chat
		synchronized (this) {
			for (int i = 0; i < maxNoOfUsers; i++) {
				if (threads[i] != null && threads[i] != this && threads[i].clientName != null) {
					threads[i].outputStream.println("----- " + name + " left the group chat. -----");
				}
			}
		}
		
		outputStream.println("----- Bye " + name + " -----");

		synchronized (this) {
			for (int i = 0; i < maxNoOfUsers; i++) {
				if (threads[i] == this) {
					threads[i] = null;
				}
			}
		}

			inputStream.close();
			outputStream.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}