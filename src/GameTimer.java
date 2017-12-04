import java.lang.Thread;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameTimer extends Thread implements BattleSplixConstants{
	private Thread time;
	private String server;
	DatagramSocket socket = new DatagramSocket();

	public GameTimer(String server) throws Exception{
		this.time = new Thread(this);
		this.server = server;
	}	

	//Timer is based on the sleep time of the thread and is sent to the server
	public void run(){
		try{
			int i=0;
			for(i=GAME_EXTENT; i>=0; i--){
				send("TIMER "+i);
				Thread.sleep(1000);
				if(i==0) send("END "+i);
			}
		}catch(Exception e){}
	}

	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}		
	}
}