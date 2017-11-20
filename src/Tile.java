import java.awt.event.*;
import java.awt.*;

public class Tile implements Runnable{
	private int xPos, dx;
	private int yPos, dy;
	private int height;
	private int width;
	private String owner;
	private boolean isCollision;
	private int type;

	public Tile(int xPos,int yPos,int height,int width, int type){
		this.xPos = xPos;
		this.yPos = yPos;
		this.height = height;
		this. width = width;
		this.type = type;
	}

	public int getXPos(){
		return this.xPos;
	}

	public int getDX(){
		return this.dx;
	}

	public int getDY(){
		return this.dy;
	}
	public int getYPos(){
		return this.yPos;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}
	
	public boolean collisionCheck(Tile object){
		Rectangle thisBounds = new Rectangle(this.getXPos() + this.dx,this.getYPos() + this.dy,this.height,this.width);
    	Rectangle objectBounds = new Rectangle(object.getXPos(),object.getYPos(),object.getHeight(),object.getWidth());
   
    	return (thisBounds.intersects(objectBounds));
	}

	public void run(){
		
	}
}