import java.net.InetAddress;
import java.awt.Color;
import java.util.Random;

public class TankPlayer{
	private InetAddress address;
	private int port;
	private String name;
	private int x,y;
	private Color tileColor;
	private float r, g, b;
	
	public TankPlayer(String name,InetAddress address, int port){
		this.address = address;
		this.port = port;
		this.name = name;
		this.randomizeColor();
		//this.tileColor = this.randomizeColor();
	}

	public InetAddress getAddress(){
		return address;
	}

	public int getPort(){
		return port;
	}

	public String getName(){
		return name;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y=y;		
	}

	public Color getColor(){
		return this.tileColor;
	}

	public void setColor(Color c){
		this.tileColor = c;
	}

	public void randomizeColor(){
		Random rand = new Random();
		this.r = rand.nextFloat();
		this.g = rand.nextFloat();
		this.b = rand.nextFloat();
	}

	//The representation of the player and their color
	public String toString(){
		String retval="";
		retval+="PLAYER ";
		retval+=name+" ";
		retval+=x+" ";
		retval+=y+" ";
		retval+=r+" ";
		retval+=g+" ";
		retval+=b;
		return retval;
	}
}
