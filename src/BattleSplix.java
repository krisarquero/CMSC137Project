import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.awt.Color;
import java.util.Random;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BattleSplix extends JPanel implements Runnable, BattleSplixConstants{
	String[][] board = new String[32][30];
	JFrame frame= new JFrame();
	int x,y,xspeed=5,yspeed=5,prevX,prevY;
	Thread t=new Thread(this);
	String name="Ronald";
	String pname;
	String server="localhost";
	boolean connected=false;
    DatagramSocket socket = new DatagramSocket();
	String serverData;
	BufferedImage offscreen;
	Random rand = new Random();


	public BattleSplix(String server,String name) throws Exception{
		this.server=server;
		this.name=name;
		
		frame.setTitle(APP_NAME+":"+name);
		socket.setSoTimeout(100);

		for (String[] row : board) {
		    Arrays.fill(row, "0");
		}

		this.randomizePlace();
		//GUI
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
		
		offscreen=(BufferedImage)this.createImage(600, 600);
	
		frame.addKeyListener(new KeyHandler());		
		frame.addMouseMotionListener(new MouseMotionHandler());

		t.start();		
	}
	
	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}
		
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){}
						
			//Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){}
			
			serverData=new String(buf);
			serverData=serverData.trim();
			
			//Study the following kids. 
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}else if (connected){
				frame.repaint();				
			}			
		}
	}
	
	/**
	 * Repainting method
	 */
	public void paintComponent(Graphics g){
		//g.setColor(Color.RED);
		for(int i = 0; i<30; i++){
			for(int j=0; j<30; j++){
				if(board[i][j].equals("0")){
					continue;
				}else{
					String[] tileInfo = board[i][j].split(" ");
					g.setColor(new Color(Float.valueOf(tileInfo[1]), Float.valueOf(tileInfo[2]), Float.valueOf(tileInfo[3])));
					g.fillRect(i*20, j*20, 20, 20);
				}
			}
		}
		if(serverData != null && serverData.startsWith("PLAYER")){
			String[] playersInfo = serverData.split(":");
			g.setColor(Color.BLACK);
			for (int i=0;i<playersInfo.length;i++){
				String[] playerInfo = playersInfo[i].split(" ");
				String pname =playerInfo[1];
				int x = Integer.parseInt(playerInfo[2]);
				int y = Integer.parseInt(playerInfo[3]);
				if(x>640) x = 640;
				if(y>480) y = 480;
				//draw on the offscreen image
				//g.drawImage(new ImageIcon("Tank/Tank.png").getImage(),x,y,20,20, this);
				board[x/20][y/20] = pname+" "+playerInfo[4]+" "+playerInfo[5]+" "+playerInfo[6];
				g.setColor(new Color(Float.valueOf(playerInfo[4]), Float.valueOf(playerInfo[5]), Float.valueOf(playerInfo[6])));
				g.fillOval(x, y, 20, 20);
				g.drawString(pname,x-10,y+30);					
			}
		}
		//g.drawImage(offscreen, 0, 0, null);
	}
	
	
	
	
	class MouseMotionHandler extends MouseMotionAdapter{
		// public void mouseMoved(MouseEvent me){
		// 	x=me.getX();y=me.getY();
		// 	if (prevX != x || prevY != y){
		// 		send("PLAYER "+name+" "+x+" "+y);
		// 	}				
		// }
	}
	
	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			prevX=x;prevY=y;
			switch (ke.getKeyCode()){
			case KeyEvent.VK_DOWN:y+=yspeed;break;
			case KeyEvent.VK_UP:y-=yspeed;break;
			case KeyEvent.VK_LEFT:x-=xspeed;break;
			case KeyEvent.VK_RIGHT:x+=xspeed;break;
			}
			if (prevX != x || prevY != y){
				//board.updateBoard(name, x/20, y/20);
				send("PLAYER "+name+" "+x+" "+y);
			}	
		}
	}

	public void randomizePlace(){
		int x = rand.nextInt(640);
		int y = rand.nextInt(480);

		this.x = x;
		this.y = y;
	}
	
	public static void main(String args[]) throws Exception{
		if (args.length != 2){
			System.out.println("Usage: java -jar circlewars-client <server> <player name>");
			System.exit(1);
		}

		new BattleSplix(args[0],args[1]);
	}
}
