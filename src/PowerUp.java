import java.lang.Thread;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PowerUp extends Thread implements BattleSplixConstants{
	private String server;
	private String name;
	private int speedup = 10;
	private int normalize = 5;
	DatagramSocket socket = new DatagramSocket();


	public PowerUp(String name, String server) throws Exception{
		Thread p = new Thread(this);
		this.server = server;
		this.name = name;
		p.start();
	}

	//This powerup will last for 8 seconds
	public void run(){
		try{
			send("INCWERPAPU "+name+" "+Integer.toString(speedup));
			Thread.sleep(8000);
			send("DECWERPAPU "+name+" "+Integer.toString(normalize));
			this.stop();
		}catch(Exception ioe){}
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