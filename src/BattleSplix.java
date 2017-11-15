import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BattleSplix extends JFrame implements Runnable {

	//Default port
	public static final int PORT = 12345;
	public static JPanel panel;
	// Initialization of values
	Thread thread=new Thread(this);
	String playerName;
	String server = "localhost";
	String serverData;
	boolean connected = false;
	String name = "Starter";
	DatagramSocket datagramSocket = new DatagramSocket();
	// Game's constructor

	public BattleSplix(String server, String name) throws Exception {
		super("BATTLE SPLIX: "+name);

		this.server = server;
		this.name = name;

		try{
			// Initializing the panel with grid layout of 10 by 10
			datagramSocket.setSoTimeout(100);
			

			panel = new JPanel(new GridLayout(15,15));
			panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
			Random rand = new Random();
			// Randomizing the blocks for now
			for (int i =0; i<(15*15); i++){
					int obj = rand.nextInt(4);
					JLabel label = new JLabel();
					label.setOpaque(true);
					switch(obj){
						case 0:
							label.setIcon(new ImageIcon("./Block/vine.png"));
							break;
						case 1:
							label.setIcon(new ImageIcon("./Block/brick.png"));
							break;
						case 2:
							label.setText(" ");
							break;
						case 3:
							label.setIcon(new ImageIcon("./Block/water.png"));
							break;
					}
			    label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			    panel.add(label);
			}

			//Setting up the board
			add(panel, BorderLayout.CENTER);
			setSize(500, 500);
			panel.setBackground(Color.BLACK);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}catch(Exception e){
		}

		thread.start();
	}

	
	public static void main(String[] args) throws Exception{
		if (args.length > 1){
			String server = args[0];
			String name = args[1];

			new BattleSplix(server, name);
		} else {
			System.out.println("Please provide the server and player name");
		}
	}

	public void sendPacket(String message){
		try {
			byte[] buff = message.getBytes();
			InetAddress inetAddress = InetAddress.getByName(server);
			DatagramPacket packet = new DatagramPacket(buff, buff.length, inetAddress, PORT);
			datagramSocket.send(packet);
		} catch(Exception e) {
		}
	}

	public void run(){
		while (true){
			try {
				Thread.sleep(1);
			} catch(Exception e){
			}

			byte[] buff = new byte[256];
			DatagramPacket packet = new DatagramPacket(buff, buff.length);

			try {
				datagramSocket.receive(packet);
			} catch(Exception e) {
				System.out.println(e);
			}

			serverData = new String(buff);
			serverData = serverData.trim();


			if (!connected && serverData.startsWith("CONNECTED")){
				connected = true;
				System.out.println("Connected. ");
			} else if (!connected){
				System.out.println("Connecting...");
				sendPacket("CONNECT " + name);
			} else if (connected){
				if (serverData.startsWith("PLAYER")){
					String[] playersData = serverData.split(":");
					for (int i = 0; i < playersData.length; i++){
						String[] player = playersData[i].split(" ");
						String playerName = player[1];
						int xPos = Integer.parseInt(player[2]);
						int yPos = Integer.parseInt(player[3]);

						// update tanks position
					}
				}
			}
		}
	}
}
