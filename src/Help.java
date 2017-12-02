import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Help extends JPanel implements MouseListener{
	
	private JPanel back = new JPanel();
	private ImageIcon image;
	private Image img;
	
	public Help(){
	
		this.setLayout(null);
		
		image = new ImageIcon("graphics/return.png");
		img = Menu.resizeImage(image, 50, 50);
		back.add(new JLabel(new ImageIcon(img)));
		back.setBounds(10, 10, 60, 60);
		back.addMouseListener(this);
		back.setOpaque(false);
		add(back);
		
		image = new ImageIcon("graphics/background.png");
		img = image.getImage();
		img = Menu.resizeImage(image, 1080, 625);
		JLabel background = new JLabel();
		ImageIcon icon = new ImageIcon(img);
		background.setIcon(icon);
		background.setBounds(0, 0, 1080,625);
		background.setOpaque(false);
		
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