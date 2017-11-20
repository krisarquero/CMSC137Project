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

	
	// public void move(){
	// 	if (this.getXPos() + Tank.WIDTH + this.getDX() > 600){
	// 		this.setDirection(0,this.getDY());
	// 	}
	// 	if (this.getYPos() + Tank.HEIGHT + this.getDY() > 600){
	// 		this.setDirection(this.getDX(),0);
	// 	}
	// 	if (this.getYPos() + this.getDY() < 0){
	// 		this.setDirection(this.getDX(),0);
	// 	}
	// 	if (this.getXPos() + this.getDX() < 0){
	// 		this.setDirection(0,this.getDY());
	// 	}
	// 	super.move();
	// }

	// public void collide(Sprite object){
	// 	this.setDirection(0,0);
	// }

	// public void notCollide(Sprite object){
	// 	this.move();

	// }
}
