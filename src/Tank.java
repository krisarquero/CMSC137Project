import java.net.InetAddress;


public class Tank {
	private InetAddress address;
	private int port;
	private String name;
	private int xPos;
	private int yPos;

	public Tank (InetAddress address, int port, String name){
		this.address = address;
		this.port = port;
		this.name = name;
	}

	public InetAddress getInetAddress(){
		return address;
	}

	public int getPort(){
		return port;
	}

	public String getName(){
		return name;
	}

	public void setXPos(int xPos){
		this.xPos = xPos;
	}

	public void setYPos(int yPos){
		this.yPos = yPos;
	}

	public int getXPos(){
		return xPos;
	}

	public int setYPos(){
		return yPos;
	}

 	public String toString(){
 		String string = "";
 		string += "PLAYER ";
		string += name+" ";
		string += xPos+" ";
		string += yPos;
		return string;
 	}
}