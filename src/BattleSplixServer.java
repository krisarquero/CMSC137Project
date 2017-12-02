import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BattleSplixServer implements Runnable, BattleSplixConstants{
	String playerData;
	int playerCount=0;
    DatagramSocket serverSocket = null;
	BattleSplixState game;
	int gameStage=WAITING_FOR_PLAYERS;
	int numPlayers;
	Thread t = new Thread(this);

	public BattleSplixServer(int numPlayers){
		this.numPlayers = numPlayers;
		try {
            serverSocket = new DatagramSocket(PORT);
			serverSocket.setSoTimeout(100);
		} catch (IOException e) {
            System.err.println("Problem listening to port: "+PORT);
            System.exit(-1);
		}catch(Exception e){}
		//Create the game state
		game = new BattleSplixState();
		
		System.out.println("Game successfully created...");
		
		//Start the game thread
		t.start();
	}
	
	public void broadcast(String msg){
		for(Iterator ite=game.getPlayers().keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			TankPlayer player=(TankPlayer)game.getPlayers().get(name);			
			send(player,msg);	
		}
	}

	public void send(TankPlayer player, String msg){
		DatagramPacket packet;	
		byte buf[] = msg.getBytes();		
		packet = new DatagramPacket(buf, buf.length, player.getAddress(),player.getPort());
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
			
			playerData=new String(buf);
			
			//remove excess bytes
			playerData = playerData.trim();
		
			// process
			switch(gameStage){
				  case WAITING_FOR_PLAYERS:
						if (playerData.startsWith("CONNECT")){
							String tokens[] = playerData.split(" ");
							TankPlayer player=new TankPlayer(tokens[1],packet.getAddress(),packet.getPort());
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
					  
					  //Player data was received!
					  if (playerData.startsWith("PLAYER")){
						  //Tokenize:
						  //The format: PLAYER <player name> <x> <y>
						  String[] playerInfo = playerData.split(" ");
						  String pname =playerInfo[1];
						  int x = Integer.parseInt(playerInfo[2].trim());
						  int y = Integer.parseInt(playerInfo[3].trim());
						  //Get the player from the game state
						  TankPlayer player=(TankPlayer)game.getPlayers().get(pname);					  
						  player.setX(x);
						  player.setY(y);
						  //Update the game state
						  game.update(pname, player);
						  //Send to all the updated game state
						  broadcast(game.toString());
					  }else if(playerData.startsWith("MISSILE")){
					  	//Formate MISSILE <dir> <x> <y>
					  	broadcast(playerData);
					  }
					  break;
			}				  
		}
	}	
	
	
	public static void main(String args[]){
		if (args.length != 1){
			//System.out.println("Usage: java -jar circlewars-server <number of players>");
			System.exit(1);
		}
		
		new BattleSplixServer(Integer.parseInt(args[0]));
	}
}

