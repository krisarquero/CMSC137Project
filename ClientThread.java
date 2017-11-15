import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

	private ClientThread[] clientThread;
	private Socket clientSocket = null;
	private String clientName = null;
	private BufferedReader inputStream = null;
	private PrintStream outputStream = null;
	private int maxNoOfUsers;

	// Constructor for ClientThread
	public ClientThread(Socket socket, ClientThread[] thread){
		this.clientSocket = socket;
		this.clientThread = thread;
		this.maxNoOfUsers = thread.length;
	}

	public void run(){
		ClientThread[] clients = this.clientThread;
		int limit = this.maxNoOfUsers;

		//initialization of input and output stream
		try {
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintStream(clientSocket.getOutputStream());

			String name;

			//Getting the name of the user
			while (true){
				outputStream.println("Enter your name: ");
				name = inputStream.readLine().trim();
				break;
			}

			//Prompt that a user has joined
			outputStream.println("----- Welcome " + name + "! Just enter \"Bye\" to leave the groupchat. -----");

			//Getting the name and modifying it in a way that it starts with @
			synchronized(this){
				for (int i = 0; i < limit; i++){
					if (clients[i] != null && clients[i] == this){
						clientName = "@" + name;
						break;
					}
				}
			}

			//Notifying the other clients about the newly joined client
			synchronized(this){
				for (int i = 0; i < limit; i++){
					if (clients[i] != null && clients[i] != this){
						clients[i].outputStream.println("----- " + name + " joined the groupchat. -----");
					}
				}
			}

			//Getting of input message
			while (true){
				String inputLine = inputStream.readLine();

				// This will break the loop or process if the client says bye
				if (inputLine.startsWith("Bye")){
					break;
				}

				if (inputLine.startsWith("@")){
					String[] words = inputLine.split("\\s", 2);

					if (words.length > 1 && words[1] != null){
						words[1] = words[1].trim();
						
					}
				} else {
					//Sending the message to the server to send to other clients
					synchronized(this){
						for (int i = 0; i < limit; i++){
							if (clients[i] != null && clients[i].clientName != null && clients[i] != this){
								clients[i].outputStream.println(name + ": " + inputLine);
							}
						}
					}

				}
			}

			// Reaching this part of the code means that the client has opted to leave the chat
			synchronized(this){
				for (int i = 0; i < limit; i++){
					if (clients[i] != null && clients[i] != this && clients[i].clientName != null){
						clients[i].outputStream.println("----- " + name + " left the groupchat. -----");
					}
				}
			}

			// This will notify the client that he/she is no longer part of the chat
			// It will also serve as a flag for the client to break the process
			outputStream.println("----- Bye " + name + " -----");

			// To remove the client who left from the clients
			synchronized (this){
				for (int i = 0; i < limit; i++){
					if (clients[i] == this){
						clients[i] = null;
					}
				}
			}

			inputStream.close();
			outputStream.close();
			clientSocket.close();

		} catch(IOException e){
			System.out.println(e);
		}
	}
}

/* Referred from: http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html */