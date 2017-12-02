import java.lang.Thread;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Missile extends Thread implements BattleSplixConstants{
	private boolean isCollide;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private int direction;
	private int speed;
    private String server;
    DatagramSocket socket = new DatagramSocket();


	public Missile(int x, int y, int dir, String server) throws Exception{
		Thread ammo = new Thread(this);
		this.xPos = x;
		this.yPos = y;
		this.direction = dir;
		this.server = server;
		this.isCollide = false;

		if(dir == UP || dir == DOWN){
			this.width = 7;
			this.height = 10;
		}else{
			this.width = 10;
			this.height = 7;
		}

		ammo.start();
	}

	public void setCollision(){
		this.isCollide = true;
	}

	public void run(){
		try{
			while(!isCollide){
				send("MISSILE "+direction+" "+xPos+" "+yPos+" "+width+" "+height);
				Thread.sleep(50);
				switch(direction){
					case UP:
						if((yPos-1) > 0) yPos -= 1;
						break;
					case DOWN:
						if((yPos+1) < 600) yPos += 1;
						break;
					case LEFT:
						if((xPos-1) > 0) xPos -= 1;
						break;
					case RIGHT:
						if((xPos+1) < 640) xPos += 1;
						break;
				}
				if(xPos-1 <= 0 || xPos+1 >= 640) isCollide = true;
				if(yPos-1 <= 0 || yPos+1 >= 600) isCollide = true;
			}
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