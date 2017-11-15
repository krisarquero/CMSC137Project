import java.net.*;
import java.util.*;
import java.io.*;

public class Server implements Runnable {

	public static final int GAME_START=0;
	public static final int IN_PROGRESS=1;
	public final int GAME_END=2;
	public final int WAITING_FOR_PLAYERS=3;
	public static final int PORT = 12345;
	String playerData;
	int playerCount=0;
    DatagramSocket serverSocket = null;
	BattleState game;
	int gameStage=WAITING_FOR_PLAYERS;
	int numPlayers;
	Thread t = new Thread(this);

	public Server(int numPlayers){
		this.numPlayers = numPlayers;
		try {
            serverSocket = new DatagramSocket(PORT);
			serverSocket.setSoTimeout(0);
		} catch (IOException e) {
            System.err.println("Could not listen on port: "+ PORT);
            System.exit(-1);
		}catch(Exception e){}
		//Create the game state
		game = new BattleState();
		
		System.out.println("Game created...");
		
		//Start the game thread
		t.start();
	}

	public void broadcast(String msg){
		for(Iterator ite=game.getPlayers().keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			Tank player=(Tank)game.getPlayers().get(name);			
			send(player,msg);	
		}
	}

	public void send(Tank player, String message){
		DatagramPacket packet;	
		byte buff[] = message.getBytes();		
		packet = new DatagramPacket(buff, buff.length, player.getInetAddress(),player.getPort());
		try{
			serverSocket.send(packet);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void run(){
		while(true){
						
			// Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			serverSocket.receive(packet);
			}catch(Exception ioe){}
			
			/**
			 * Convert the array of bytes to string
			 */
			playerData=new String(buf);
			
			//remove excess bytes
			playerData = playerData.trim();
			//if (!playerData.equals("")){
			//	System.out.println("Player Data:"+playerData);
			//}
		
			// process
			switch(gameStage){
				  case WAITING_FOR_PLAYERS:
						//System.out.println("Game State: Waiting for players...");
						if (playerData.startsWith("CONNECT")){
							String tokens[] = playerData.split(" ");
							Tank player=new Tank(packet.getAddress(),packet.getPort(), tokens[1]);
							System.out.println("Player connected: "+tokens[1]);
							game.update(tokens[1].trim(),player);
							broadcast("CONNECTED "+tokens[1]);
							playerCount++;
							if (playerCount==numPlayers){
								gameStage=GAME_START;
							}
						}
					  break;	
				  case GAME_START:
					  System.out.println("Game State: START");
					  broadcast("START");
					  gameStage=IN_PROGRESS;
					  break;
				  case IN_PROGRESS:
					  //System.out.println("Game State: IN_PROGRESS");
					  
					  //Player data was received!
					  if (playerData.startsWith("PLAYER")){
						  //Tokenize:
						  //The format: PLAYER <player name> <x> <y>
						  String[] playerInfo = playerData.split(" ");					  
						  String pname =playerInfo[1];
						  int x = Integer.parseInt(playerInfo[2].trim());
						  int y = Integer.parseInt(playerInfo[3].trim());
						  //Get the player from the game state
						  Tank player=(Tank)game.getPlayers().get(pname);					  
						  player.setXPos(x);
						  player.setYPos(y);
						  //Update the game state
						  game.update(pname, player);
						  //Send to all the updated game state
						  broadcast(game.toString());
					  }
					  break;
			}				  
		}
	}	
	
	
	public static void main(String args[]){
		if (args.length > 0){
			new Server(Integer.parseInt(args[0]));
		}
		
	}

}