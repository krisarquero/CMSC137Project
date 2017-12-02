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
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;

public class BattleSplix extends JPanel implements Runnable, BattleSplixConstants{
	Board board = new Board(32, 30);
	JFrame frame= new JFrame();
	JPanel stats = new JPanel();
	JLabel status = new JLabel("");
	JPanel container = new JPanel();
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
		container.setLayout(new BorderLayout());
		frame.setTitle(APP_NAME+":"+name);
		socket.setSoTimeout(100);

		this.randomizePlace();
		//GUI
		stats.setPreferredSize(new Dimension(300,600));
		container.add(this, BorderLayout.CENTER);
		container.add(stats, BorderLayout.WEST);
		//stats.setBackground(Color.BLACK);
		//frame.getContentPane().add(stats);
		//frame.getContentPane().add(this);
		frame.getContentPane().add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(940, 600);
		frame.setVisible(true);
		

		offscreen=(BufferedImage)this.createImage(600, 600);
	
		this.addKeyListener(new KeyHandler());		
		this.addMouseMotionListener(new MouseMotionHandler());
		this.setFocusable(true);
		this.requestFocus();
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
		this.updateStats();
		String[][] brd = board.getBoard();
		for(int i = 0; i<32; i++){
			for(int j=0; j<30; j++){
				if(brd[i][j].length()==1){
					g.setColor(Color.GRAY);
					g.fillRect(i*20, j*20, 20, 20);
					switch(Integer.parseInt(brd[i][j])){
						case BRICKLESS:
							g.setColor(Color.BLACK);
							g.fillRect(i*20, j*20, 19, 19);
							break;
						 case BRICK:
							g.drawImage(new ImageIcon("Block/brick.png").getImage(),i*20,j*20,19,19, this);
							break;
						case METAL:
							g.drawImage(new ImageIcon("Block/metal.png").getImage(),i*20,j*20,19,19, this);
							break;
						case VINE:
							g.drawImage(new ImageIcon("Block/vine.png").getImage(),i*20,j*20,19,19, this);
							break;
					}
				}else{
					String[] tileInfo = brd[i][j].split(" ");
					g.setColor(new Color(Float.valueOf(tileInfo[1]), Float.valueOf(tileInfo[2]), Float.valueOf(tileInfo[3])));
					g.fillRect(i*20, j*20, 18, 18);
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
				if(y>600) y = 600;
				//draw on the offscreen image
				//g.drawImage(new ImageIcon("Tank/Tank.png").getImage(),x,y,20,20, this);
				board.updateBoard(pname+" "+playerInfo[4]+" "+playerInfo[5]+" "+playerInfo[6], x/20, y/20);
				g.setColor(new Color(Float.valueOf(playerInfo[4]), Float.valueOf(playerInfo[5]), Float.valueOf(playerInfo[6])));
				g.fillOval(x, y, 20, 20);
				g.drawString(pname,(x-10)>0?x-10:x,(y+30)<600?y+30:y-30);					
			}
		}
		//g.drawImage(offscreen, 0, 0, null);
	}

	class MouseMotionHandler extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent me){}
	}
	
	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			prevX=x;prevY=y;
			switch (ke.getKeyCode()){
				case KeyEvent.VK_DOWN:
					if(isMove(x/20, (y+yspeed)/20) && y+yspeed<600) y+=yspeed;
					break;
				case KeyEvent.VK_UP:
					if(isMove(x/20, (y-yspeed)/20) && y-yspeed>0) y-=yspeed;
					break;
				case KeyEvent.VK_LEFT:
					if(isMove((x-xspeed)/20, (y)/20) && x-xspeed>0) x-=xspeed;
					break;
				case KeyEvent.VK_RIGHT:
					if(isMove((x+xspeed)/20, (y)/20) && x+xspeed<640) x+=xspeed;
					break;
			}
			if (prevX != x || prevY != y){
				//board.updateBoard(name, x/20, y/20);
				send("PLAYER "+name+" "+x+" "+y);
			}
		}
	}

	public boolean isMove(int x, int y){
		if((x<32 && y<30) && (board.getBoard()[x][y].equals("0") || board.getBoard()[x][y].length() > 1)) return true;
		return false;
	}

	public void randomizePlace(){
		// boolean b = true;
		// while(b){
			int x = rand.nextInt(640);
			int y = rand.nextInt(600);
		// 	if(board.getBoard()[x/20][y/20].equals("0")) b = false;
		// }

		this.x = x;
		this.y = y;
	}

	public void updateStats(){
		Map players = new HashMap();
		String[][] brd = board.getBoard();
		for(int i = 0; i<32; i++){
			for(int j=0; j<30; j++){
				if(brd[i][j].length()!=1){
					String[] tileInfo = brd[i][j].split(" ");
					if(!players.containsKey(tileInfo[0])){ players.put(tileInfo[0], 1); }
					else{
						int temp = (int)players.get(tileInfo[0]);
						players.replace(tileInfo[0], temp+1);
					}
				}
			}
		}
		String retval="";
		for(Iterator ite=players.keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			retval+= "<html> "+name+": "+ Integer.toString((int)players.get(name))+"<br> <html>";
			status.setFont(status.getFont().deriveFont(32f)); 
			status.setText(retval);
			stats.add(status);
		}

		// int x = rand.nextInt(640);
		// System.out.println(x);

	}
	
	public static void main(String args[]) throws Exception{
		if (args.length != 2){
			//System.out.println("Usage: java -jar circlewars-client <server> <player name>");
			System.exit(1);
		}

		new BattleSplix(args[0],args[1]);
	}
}
