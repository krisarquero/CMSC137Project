import java.io.*;
import java.net.*;
import java.util.*;
        
class ChatServer implements Runnable{
    public static int portNumber = 12345;
    public static Socket clientSocket;
    public static ServerSocket serverSocket;
    public static Vector clients = new Vector();

    public ChatServer(Socket socket){
        try {
            System.out.println("A new client connection is established...");
            clientSocket = socket;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) throws Exception{

        System.out.println("Chat server running on port: " + portNumber + "...");

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        while (true){
            try {
                clientSocket = serverSocket.accept();
                ChatServer server = new ChatServer(clientSocket);
                Thread serverThread = new Thread(server);
                serverThread.start();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void run(){
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter outputStream= new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            clients.add(outputStream);

            while(true){
                String clientMessage = inputStream.readLine().trim();

                for (int i = 0; i < clients.size(); i++){
                    try {
                        BufferedWriter writer= (BufferedWriter)clients.get(i);
                        writer.write(clientMessage);
                        writer.write("\r\n");
                        writer.flush();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
