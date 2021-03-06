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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



public class BattleSplix extends JPanel implements Runnable, BattleSplixConstants{
	Board board = new Board(32, 30);
	JFrame frame= new JFrame();
	JPanel stats = new JPanel();
	JLabel status = new JLabel("");
	JLabel timedt = new JLabel("");
	JPanel container = new JPanel();
	int x,y,xspeed=5,yspeed=5,prevX,prevY;
	int prevDir;
	boolean ongoing = true;
	Thread t=new Thread(this);
	String name="Ronald";
	String pname;
	static String server="localhost";
	boolean connected=false;
    DatagramSocket socket = new DatagramSocket();
	String serverData;
	BufferedImage offscreen;
	Random rand = new Random();
	GridBagConstraints c = new GridBagConstraints();

	public BattleSplix(String server,String name) throws Exception{
		this.server=server;
		this.name=name;
		stats.setLayout(new GridBagLayout());
		container.setLayout(new BorderLayout());
		frame.setTitle(APP_NAME+":"+name);
		socket.setSoTimeout(100);

		this.randomizePlace();
		//GUI
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		timedt.setFont(timedt.getFont().deriveFont(32f));
		timedt.setText("<html> TIME: <br> 3:00 <html>");
		stats.add(timedt, c);
		stats.setPreferredSize(new Dimension(200,600));
		container.add(this, BorderLayout.CENTER);
		container.add(stats, BorderLayout.WEST);
		//stats.setBackground(Color.BLACK);
		//frame.getContentPane().add(stats);
		//frame.getContentPane().add(this);
		// frame.getContentPane().add(container);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		// frame.setResizable(false);
		// frame.setSize(940, 600);
		// frame.setVisible(true);
		
		offscreen=(BufferedImage)this.createImage(600, 600);
	
		this.addKeyListener(new KeyHandler());		
		this.addMouseMotionListener(new MouseMotionHandler());
		this.setFocusable(true);
		this.requestFocus();
		//t.start();
	}

	//Returns the Jpanel containing the board and the stats for the players
	public JPanel getContainer(){
		return this.container;
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
		while(ongoing){
			try{
				Thread.sleep(0);
			}catch(Exception ioe){}
						
			//Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){}
			
			serverData=new String(buf);
			serverData=serverData.trim();
			
			//Connection indicator via terminal 
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}else if (connected){
				repaint();				
			}			
		}
	}
	
	//Paints basically everything found on the board
	public void paintComponent(Graphics g){
		//g.setColor(Color.RED);
		this.updateStats();
		String[][] brd = board.getBoard();
		for(int i = 0; i<32; i++){
			for(int j=0; j<30; j++){
				if(brd[i][j].length()==1){
					//Paints a square rectangle that will serve as a border
					g.setColor(Color.GRAY);
					g.fillRect(i*20, j*20, 20, 20);

					//Paints the respective representation of a tile based on map
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
				}else if(brd[i][j].equals("WERPAPU")){
					g.drawImage(new ImageIcon("Block/invincible.png").getImage(),i*20,j*20,19,19, this);
				}else{
					//Gets the color of the player 
					String[] tileInfo = brd[i][j].split(" ");
					g.setColor(new Color(Float.valueOf(tileInfo[1]), Float.valueOf(tileInfo[2]), Float.valueOf(tileInfo[3])));
					g.fillRect(i*20, j*20, 18, 18);
				}
			}
		}
		//Change from a player was received and shall be repainted
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
				//g.drawImage(new ImageIcon("Block/iconuser.png").getImage(),x,y,25,25, this);
				//g.fillOval(x, y, 18, 18);
				g.drawString(pname,(x-10)>0?x-10:x,(y+30)<600?y+30:y-30);					
			}
		//A missile if currently moving and should be repainted every second
		}else if(serverData != null && serverData.startsWith("MISSILE")){
			String[] playersInfo = serverData.split(" ");
			int x = Integer.parseInt(playersInfo[2]);
			int y = Integer.parseInt(playersInfo[3]);
			int width = Integer.parseInt(playersInfo[4]);
			int height = Integer.parseInt(playersInfo[5]);

			if(x>640) x = 640;
			if(y>600) y = 600;
			g.setColor(Color.WHITE);
			g.fillRect(x*20, y*20, width, height);
		//Update on the time is received every second an should be repainted to every player
		}else if(serverData != null && serverData.startsWith("TIMER")){
			String[] playersInfo = serverData.split(" ");
			int curr = Integer.parseInt(playersInfo[1]);
			if(curr%20 == 0 && curr<180) releasePowerUp();
			int minute = curr/60;
			int sec = curr%60;
			String s = sec<10?"0"+Integer.toString(sec):Integer.toString(sec);
			String retval = "<html> TIME: <br> "+Integer.toString(minute)+":"+s+"<br> <html>";
			timedt.setFont(timedt.getFont().deriveFont(32f)); 
			timedt.setText(retval);
			c.gridy = 1;
			stats.add(timedt, c);
		}else if(serverData != null && serverData.startsWith("NEWERPAPU")){
			String[] playersInfo = serverData.split(" ");
			int x = Integer.parseInt(playersInfo[1]);
			int y = Integer.parseInt(playersInfo[2]);
			if(x>640) x = 640;
			if(y>600) y = 600;

			board.updateBoard("WERPAPU", x/20, y/20);
			g.drawImage(new ImageIcon("Block/invincible.png").getImage(),x,y,19,19, this);
		}else if(serverData != null && serverData.startsWith("INCWERPAPU")){
			String[] playersInfo = serverData.split(" ");
			int speedup = Integer.parseInt(playersInfo[2]);
			this.xspeed = speedup;
			this.yspeed = speedup;
		}else if(serverData != null && serverData.startsWith("DECWERPAPU")){
			String[] playersInfo = serverData.split(" ");
			int speedup = Integer.parseInt(playersInfo[2]);
			this.xspeed = speedup;
			this.yspeed = speedup;
		}else if(serverData != null && serverData.startsWith("END")){
			checkWinners();
		}
		//g.drawImage(offscreen, 0, 0, null);
	}

	public void releasePowerUp(){
		String[][] brd = board.getBoard();
		boolean b = true;
		int x=0, y=0;

		while(b){
			x = rand.nextInt(640);
			y = rand.nextInt(600);
			if(board.getBoard()[x/20][y/20].equals("0") || board.getBoard()[x/20][y/20].length()!=1) b = false;
		}
		send("NEWERPAPU "+x+" "+y);
	}

	public void checkWinners(){
		Map players = new HashMap();
		int max = 0, playerScore = 0;
		String nameID="";
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

		for(Iterator ite=players.keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			playerScore = (int)players.get(name);
			if(playerScore > max){
				max = playerScore;
				nameID = name;
			}
		}
		ongoing = false;
		if(this.name.equals(nameID)){
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, "YOU WIN!");
					System.exit(1);
				}
			});
		}else{
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null, "YOU LOSE!");
					System.exit(1);
				}
			});
		}

	//	System.out.println("WINNER: "+" "+nameID+" SCORE: "+" "+Integer.toString(max));
	}

	public static String getServer(){
		return server;
	}

	// public static Board getGameGoard(){

	// }

	public void correctFocus(){
		this.setFocusable(true);
		this.requestFocus();
	}

	class MouseMotionHandler extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent me){
			correctFocus();
		}
	}
	
	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			try{
				prevX=x;prevY=y;
				switch (ke.getKeyCode()){
					case KeyEvent.VK_DOWN:
						if(isMove(x/20, (y+yspeed)/20) && y+yspeed<600){ y+=yspeed; prevDir = 2; }
						break;
					case KeyEvent.VK_UP:
						if(isMove(x/20, (y-yspeed)/20) && y-yspeed>0){ y-=yspeed; prevDir = 1; }
						break;
					case KeyEvent.VK_LEFT:
						if(isMove((x-xspeed)/20, (y)/20) && x-xspeed>0){ x-=xspeed; prevDir = 3; }
						break;
					case KeyEvent.VK_RIGHT:
						if(isMove((x+xspeed)/20, (y)/20) && x+xspeed<640){ x+=xspeed; prevDir = 4; }
						break;
					case KeyEvent.VK_SPACE:
						new Missile(prevX/20, prevY/20, prevDir, server);
						break;
				}
				if (prevX != x || prevY != y){
					//board.updateBoard(name, x/20, y/20);
					checkPowerUp(x/20, y/20);
					send("PLAYER "+name+" "+x+" "+y);
				}
			}catch(Exception e){}
		}
	}

	public void checkPowerUp(int x, int y){
		if(board.getBoard()[x][y].equals("WERPAPU")){
			try{
				new PowerUp(this.name, server);
				board.updateBoard("0", x,y);
			}catch(Exception e){}
		}
	}

	public boolean isMove(int x, int y){
		if((x<32 && y<30) && (board.getBoard()[x][y].equals("0") || board.getBoard()[x][y].length() > 1)) return true;
		return false;
	}

	public void randomizePlace(){
		boolean b = true;
		int x=0, y=0;

		while(b){
			x = rand.nextInt(640);
			y = rand.nextInt(600);
			if(board.getBoard()[x/20][y/20].equals("0")) b = false;
		}

		this.x = x;
		this.y = y;
	}

	public void updateStats(){
		Map players = new HashMap();
		String[][] brd = board.getBoard();
		for(int i = 0; i<32; i++){
			for(int j=0; j<30; j++){
				if(brd[i][j].length()!= 1 && !brd[i][j].equals("WERPAPU")){
					String[] tileInfo = brd[i][j].split(" ");
					if(!players.containsKey(tileInfo[0])){ players.put(tileInfo[0], 1); }
					else{
						int temp = (int)players.get(tileInfo[0]);
						players.replace(tileInfo[0], temp+1);
					}
				}
			}
		}
		String retval="<html> PLAYER SCORES: <br> <html>";
		for(Iterator ite=players.keySet().iterator();ite.hasNext();){
			String name=(String)ite.next();
			retval+= "<html> "+name+": "+ Integer.toString((int)players.get(name))+"<br> <html>";
			status.setFont(status.getFont().deriveFont(16f)); 
			status.setText(retval);
			c.gridy = 3;
			stats.add(status, c);
		}

		// int x = rand.nextInt(640);
		// System.out.println(x);

	}
	
	// public static void main(String args[]) throws Exception{
	// 	if (args.length != 2){
	// 		//System.out.println("Usage: java -jar circlewars-client <server> <player name>");
	// 		System.exit(1);
	// 	}

	// 	new BattleSplix(args[0],args[1]);
	// }
}
