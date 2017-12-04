import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Help extends JPanel implements MouseListener{
	
	private JPanel back = new JPanel();
	private JPanel usrMan = new JPanel();
	private JPanel arrowleft = new JPanel();
	private JPanel arrowright = new JPanel();
	private JPanel arrowup = new JPanel();
	private JPanel arrowdown = new JPanel();
	private JPanel iPanel = new JPanel(); 
	private ImageIcon image;
	private Image img;
	
	public Help(){
	
		this.setLayout(null);
		
		image = new ImageIcon("graphics/back.png");
		img = Menu.resizeImage(image, 90, 90);
		back.add(new JLabel(new ImageIcon(img)));
		back.setBounds(30, 25, 90, 90);
		back.addMouseListener(this);
		back.setOpaque(false);
		add(back);
		
		image = new ImageIcon("graphics/usrMan.png");
		img = Menu.resizeImage(image, 500, 100);
		usrMan.add(new JLabel(new ImageIcon(img)));
		usrMan.setBounds(250, 30, 600, 120);
		usrMan.addMouseListener(this);
		usrMan.setOpaque(false);
		add(usrMan);
		
		image = new ImageIcon("graphics/background.jpeg");
		img = image.getImage();
		img = Menu.resizeImage(image, 1070, 625);
		JLabel background = new JLabel();
		ImageIcon icon = new ImageIcon(img);
		background.setIcon(icon);
		background.setBounds(0, 0, 1070,600);
		background.setOpaque(false);
		
		
		iPanel.setLayout(null);
		iPanel.setBounds(50, 50, 1000, 900); //X,Y,W,H
		iPanel.setOpaque(false);
		
	/*	
		image = new ImageIcon("graphics/field.png");
		img = image.getImage();
		img = Menu.resizeImage(image, 500,500);
	 arrowKeys.add(new JLabel(new ImageIcon(img)));
  arrowKeys.setBounds(50,50,500,500);
  arrowKeys.setOpaque(false);
		add(arrowKeys);
		*/
		
 	image = new ImageIcon("graphics/penk.png");
		img = image.getImage();
		img = Menu.resizeImage(image,930,400);
  icon = new ImageIcon(img);
  JLabel textbg = new JLabel();
  textbg.setIcon(icon);
		textbg.setBounds(40, 100, 900, 400);
		textbg.setOpaque(false);
		
		JLabel iContent = new JLabel();
		iContent.setText("<html><p>Color as many tiles as you can using the arrowkeys to move right, left, up and down.</p> <br></br><hr></hr> <br></br> <p> Hitting your enemy by pressing the spacebar will reset your enemy's score.</p><br></br><hr></hr> <br></br> <p>Getting a power up will make your speed faster, it will wear off after 8 seconds. </p> <br></br><hr></hr> <br></br><p>The player with the most number of points after 1 minute wins the game.</p> </html>");
		iContent.setForeground(Color.BLACK);
		iContent.setFont(new Font("Ubuntu", Font.PLAIN, 20));
		iContent.setOpaque(false);
		iContent.setBounds(50,100,500,400);
		
	/*	JLabel iContent = new JLabel();
		iContent.setText("<html> <p> Hitting your enemy by pressing the spacebar will reset your enemy's score.</p>></html>");
		iContent.setForeground(Color.BLACK);
		iContent.setFont(new Font("Ubuntu", Font.PLAIN, 20));
		iContent.setOpaque(false);
		iContent.setBounds(50,120,500,100);
		
		JLabel iContent = new JLabel();
		iContent.setText("<html><p>Color as many tiles as you can using the arrowkeys to move right, left, up and down.</p>  </html>");
		iContent.setForeground(Color.BLACK);
		iContent.setFont(new Font("Ubuntu", Font.PLAIN, 20));
		iContent.setOpaque(false);
		iContent.setBounds(50,120,500,100);*/
		
		image = new ImageIcon("graphics/left.png");
		img = Menu.resizeImage(image, 120, 120);
		arrowleft.add(new JLabel(new ImageIcon(img)));
		arrowleft.setBounds(620, 270, 120, 120);
		arrowleft.setOpaque(false);
		add(arrowleft);
		
		image = new ImageIcon("graphics/right.png");
		img = Menu.resizeImage(image, 120, 120);
		arrowright.add(new JLabel(new ImageIcon(img)));
		arrowright.setBounds(800, 265, 120, 120);
		arrowright.setOpaque(false);
		add(arrowright);
		
		image = new ImageIcon("graphics/up.png");
		img = Menu.resizeImage(image, 120, 120);
		arrowup.add(new JLabel(new ImageIcon(img)));
		arrowup.setBounds(700, 190, 120, 120);
		arrowup.setOpaque(false);
		add(arrowup);
		
		image = new ImageIcon("graphics/down.png");
		img = Menu.resizeImage(image, 120, 120);
		arrowdown.add(new JLabel(new ImageIcon(img)));
		arrowdown.setBounds(705, 350, 120,  120);
		arrowdown.setOpaque(false);
		add(arrowdown);
				
		iPanel.add(iContent);
		iPanel.add(textbg);
		add(iPanel);
		
		this.setOpaque(false);
		this.setBounds(0, 0, 800, 600);
		this.add(background);
 

	}
	
	
	
	public void mouseExited(MouseEvent me){ }
	
	public void mouseClicked(MouseEvent me){ 
		CardLayout cardLayout = (CardLayout)Main.getCards().getLayout();
		if(me.getSource() == back){
			cardLayout.first(Main.getCards());
		}
	}
	
	public void mousePressed(MouseEvent me){ }
	
	public void mouseReleased(MouseEvent me){ }
	
	public void mouseEntered(MouseEvent me){ }

}
