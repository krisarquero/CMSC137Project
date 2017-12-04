import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

public class Main extends JFrame implements BattleSplixConstants{
	private static JPanel cards = new JPanel();
	private static JLabel quit;
	private static String initAdd = "localhost";
	private static String initName = "Werpahouse";
	

	public Main() throws Exception{
		// UI Layer Indicator
		System.out.println("Main/Menu Panel Level");

		cards.setLayout(new CardLayout());
		cards.add(new Menu(), "Play");
		//cards.add(new GamePanel(), "Start");
		//cards.add(new MainFrame(), "Start");
		cards.add(new About(), "About");
		cards.add(new Help(), "Help");
		cards.setOpaque(false);

		getContentPane().add(cards);
		setBackground(Color.BLACK);
		setTitle(APP_NAME);
		setSize(1071,620);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static JPanel getCards(){
		return cards;
	}

	public static void updateCards() throws Exception{
		cards.removeAll();
		cards.setLayout(new CardLayout());
		cards.add(new Menu(), "Play");
		cards.add(new GamePanel(Menu.ipadd, Menu.name), "Start");
		cards.add(new About(), "About");
		cards.add(new Help(), "Help");
		cards.setOpaque(false);
	}

	public static void main(String args[]) throws Exception{
		new Main();
	}
}


class Menu extends JPanel implements MouseListener{
	private JPanel play = new JPanel();
	private JPanel start = new JPanel();
	private JPanel about = new JPanel();
	private JPanel help = new JPanel();
	private JPanel quit = new JPanel();
	private JPanel menu = new JPanel();
	static String name="";
	static String ipadd="";


	public Menu(){
		setLayout(null);
		ImageIcon image = new ImageIcon("graphics/bg.png");
		Image img = resizeImage(image, 1080, 625);
		JLabel background = new JLabel();
		ImageIcon icon = new ImageIcon(img);
		background.setIcon(icon);
		background.setBounds(0, 0, 1080, 625);
		background.setOpaque(false);
		image = new ImageIcon("graphics/buttonPlay.png");
	 	img = resizeImage(image, 120, 80);
		play.add(new JLabel(new ImageIcon(img)));

		image = new ImageIcon("graphics/buttonHelp.png");
		img = resizeImage(image, 100, 60);
		help.add(new JLabel(new ImageIcon(img)));

		image = new ImageIcon("graphics/buttonAbout.png");
		img = resizeImage(image, 110, 60);
		about.add(new JLabel(new ImageIcon(img)));
			
		image = new ImageIcon("graphics/buttonQuit.png");
		img = resizeImage(image, 100, 60);
		quit.add(new JLabel(new ImageIcon(img)));
		
		play.setOpaque(false);
		start.setOpaque(false);
		about.setOpaque(false);
		help.setOpaque(false);
		quit.setOpaque(false);
		//start.addMouseListener(this);
		play.addMouseListener(this);
		about.addMouseListener(this);
		help.addMouseListener(this);
		quit.addMouseListener(this);
	    play.setBounds(250,110,200,100);   // x,y,width,height
		about.setBounds(360,200,200,100);
		help.setBounds(150,200,200,100);
		quit.setBounds(250,280,200,100);

		menu.setLayout(null);
		menu.add(play);
		menu.add(about);
		menu.add(help);
		menu.add(quit);
		menu.setOpaque(false);
		menu.setBounds(180, 180, 800, 600);

		add(menu);
		add(background);
	}
	public static Image resizeImage(ImageIcon img, int width, int height){
		return (img.getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH));
	}

	public static String getPlayerName(){
		return name;
	}

	public static String getIpadd(){
		return ipadd;
	}

	public void mouseExited(MouseEvent me){
		if(me.getSource() == play){
			ImageIcon image = new ImageIcon("graphics/buttonPlay.png");
			Image img = resizeImage(image, 120, 80);
			((JLabel)(play.getComponent(0))).setIcon(new ImageIcon(img));
		}
		if(me.getSource() == about){
			ImageIcon image = new ImageIcon("graphics/buttonAbout.png");
			Image img = resizeImage(image, 110, 60);
			((JLabel)(about.getComponent(0))).setIcon(new ImageIcon(img));
		}
		if(me.getSource() == help){
			ImageIcon image = new ImageIcon("graphics/buttonHelp.png");
			Image img = resizeImage(image, 100, 60);
			((JLabel)(help.getComponent(0))).setIcon(new ImageIcon(img));
		}
		if(me.getSource() == quit){
			ImageIcon image = new ImageIcon("graphics/buttonQuit.png");
			Image img = resizeImage(image, 100, 60);
			((JLabel)(quit.getComponent(0))).setIcon(new ImageIcon(img));
		}
	}

	public void mouseClicked(MouseEvent me){
		CardLayout cardLayout = (CardLayout)Main.getCards().getLayout();

	   if(me.getSource() == play){
	    	//cardLayout.show(Main.getCards(),"Play");
	   		UIManager um = new UIManager();
	   		um.put("Panel.background", Color.WHITE);
			um.put("Button.background", Color.BLACK);
			um.put("Button.foreground", Color.WHITE);
	    	name = JOptionPane.showInputDialog(null, "Please enter your name:", "Lodi");
			ipadd= JOptionPane.showInputDialog(null, "Please enter IP Address:", "localhost");

			if (name != null && ipadd != null){
				try {
					Main.updateCards();
				} catch(Exception e){}
					cardLayout = (CardLayout)Main.getCards().getLayout();
					cardLayout.show(Main.getCards(), "Start");
			}
		}

		if(me.getSource() == about){
			cardLayout.show(Main.getCards(), "About");
		}

		if(me.getSource() == help){
			cardLayout.show(Main.getCards(), "Help");
		}

		if(me.getSource() == quit){
			System.exit(0);
		}
	}

	public void mouseEntered(MouseEvent me){
		if(me.getSource() == play){
			ImageIcon image = new ImageIcon("graphics/buttonPlayHover.png");
			Image img = resizeImage(image, 120, 80);
			((JLabel)(play.getComponent(0))).setIcon(new ImageIcon(img));
		}
		if(me.getSource() == about){
			ImageIcon image = new ImageIcon("graphics/buttonAboutHover.png");
			Image img = resizeImage(image, 110, 60);
			((JLabel)(about.getComponent(0))).setIcon(new ImageIcon(img));
		}
		if(me.getSource() == help){
			ImageIcon image = new ImageIcon("graphics/buttonHelpHover.png");
			Image img =resizeImage(image, 100, 60);
			((JLabel)(help.getComponent(0))).setIcon(new ImageIcon(img));
		}
		if(me.getSource() == quit){
			ImageIcon image = new ImageIcon("graphics/buttonQuitHover.png");
			Image img = resizeImage(image, 100, 60);
			((JLabel)(quit.getComponent(0))).setIcon(new ImageIcon(img));
		}
	}

	public void mousePressed(MouseEvent me){ }
	public void mouseReleased(MouseEvent me){ }
}
