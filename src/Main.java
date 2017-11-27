import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

public class Main extends JFrame{
	private static JPanel cards = new JPanel();
	private static JLabel quit;
	String name ="anne";
	String ipadd ="10.0.52.132";

	public Main() throws Exception{

		cards.setLayout(new CardLayout());
		cards.add(new Menu(), "Play");
		//cards.add(new BattleSplix(ipadd,name), "Start");
		cards.add(new About(), "About");
		cards.add(new Help(), "Help");
		cards.setOpaque(false);
		
		getContentPane().add(cards);
		setSize(800,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}
	public static JPanel getCards(){
		return cards;
	}

	public static void main(String args[]) throws Exception{
/*		if (args.length != 2){
		
			System.out.println("Usage: java -jar circlewars-client <server> <player name>");
			System.exit(1);
		}*/

     		//new BattleSplix(args[0],args[1]);
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

		public Menu(){
			setLayout(null);
			ImageIcon image = new ImageIcon("graphics/bg.png");
			Image img = resizeImage(image, 800, 600);
			JLabel background = new JLabel();
			ImageIcon icon = new ImageIcon(img);
			background.setIcon(icon);
			background.setBounds(0, 0, 800, 570);
			background.setOpaque(false);
			
			image = new ImageIcon("graphics/buttonPlay.png");
		 	img = resizeImage(image, 150, 70);
			play.add(new JLabel(new ImageIcon(img)));
				
			image = new ImageIcon("graphics/buttonHelp.png");
			img = resizeImage(image, 110, 50);
			help.add(new JLabel(new ImageIcon(img)));
				
			image = new ImageIcon("graphics/buttonAbout.png");
			img = resizeImage(image, 110, 50);
			about.add(new JLabel(new ImageIcon(img)));

				
			image = new ImageIcon("graphics/buttonQuit.png");
			img = resizeImage(image, 110, 50);
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
	
	     	play.setBounds(150,110,200,100);   // x,y,width,height
			about.setBounds(260,200,200,100);
			help.setBounds(50,200,200,100);
			quit.setBounds(150,280,200,100);

			menu.setLayout(null);
			menu.add(play);
			menu.add(about);
			menu.add(help);
			menu.add(quit);
			menu.setOpaque(false);
			menu.setBounds(150, 180, 500, 400);

			add(menu);
			add(background);
		}
		public static Image resizeImage(ImageIcon img, int width, int height){
			return (img.getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH));
		}
		
		public void mouseExited(MouseEvent me){
			if(me.getSource() == play){
				ImageIcon image = new ImageIcon("graphics/buttonPlay.png");
				Image img = resizeImage(image, 150, 70);
				((JLabel)(play.getComponent(0))).setIcon(new ImageIcon(img));
			}
			if(me.getSource() == about){
				ImageIcon image = new ImageIcon("graphics/buttonAbout.png");
				Image img = resizeImage(image, 110, 50);
				((JLabel)(about.getComponent(0))).setIcon(new ImageIcon(img));
			} 
			if(me.getSource() == help){
				ImageIcon image = new ImageIcon("graphics/buttonHelp.png");
				Image img = resizeImage(image, 110, 50);
				((JLabel)(help.getComponent(0))).setIcon(new ImageIcon(img));
			}
			if(me.getSource() == quit){
				ImageIcon image = new ImageIcon("graphics/buttonQuit.png");
				Image img = resizeImage(image, 110, 50);
				((JLabel)(quit.getComponent(0))).setIcon(new ImageIcon(img));
			}
		}
	
		public void mouseClicked(MouseEvent me){
			CardLayout cardLayout = (CardLayout)Main.getCards().getLayout();
		    
		    if(me.getSource() == play){
		    	cardLayout.show(Main.getCards(),"Play");
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
	
		public void mousePressed(MouseEvent me){ }
	
		public void mouseReleased(MouseEvent me){ }
	
		public void mouseEntered(MouseEvent me){
			if(me.getSource() == play){
				ImageIcon image = new ImageIcon("graphics/buttonPlayHover.png");
				Image img = resizeImage(image, 150, 70);
				((JLabel)(play.getComponent(0))).setIcon(new ImageIcon(img));
			}
			if(me.getSource() == about){
				ImageIcon image = new ImageIcon("graphics/buttonAboutHover.png");
				Image img = resizeImage(image, 110, 50);
				((JLabel)(about.getComponent(0))).setIcon(new ImageIcon(img));
			} 
			if(me.getSource() == help){
				ImageIcon image = new ImageIcon("graphics/buttonHelpHover.png");
				Image img =resizeImage(image, 110, 50);
				((JLabel)(help.getComponent(0))).setIcon(new ImageIcon(img));
			}
			if(me.getSource() == quit){
				ImageIcon image = new ImageIcon("graphics/buttonQuitHover.png");
				Image img = resizeImage(image, 110, 50);
				((JLabel)(quit.getComponent(0))).setIcon(new ImageIcon(img));
			}
		}
}