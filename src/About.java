import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class About extends JPanel implements MouseListener{
	
	private JPanel back = new JPanel();
	private JPanel devPanel = new JPanel();
	private JPanel iconPanel = new JPanel();
	private ImageIcon image;
	private Image img;
	private ArrayList<JPanel> devIcon = new ArrayList<JPanel>();
	private ArrayList<JPanel> devs = new ArrayList<JPanel>();
	
	public About(){
	
		String[] lodi = {"Ronald John Leis","Kristine Arquero","Julie Anne Jerusalem"};
		this.setLayout(null);
		
		image = new ImageIcon("graphics/return.png");
		img = Menu.resizeImage(image, 50, 50);
		back.add(new JLabel(new ImageIcon(img)));
		back.setBounds(10, 10, 60, 60);
		back.addMouseListener(this);
		back.setOpaque(false);
		add(back);
		
		image = new ImageIcon("graphics/background.jpeg");
		img = image.getImage();
		img = Menu.resizeImage(image, 1080, 625);
		JLabel background = new JLabel();
		ImageIcon icon = new ImageIcon(img);
		background.setIcon(icon);
		background.setBounds(0, 0, 1080, 625);
		background.setOpaque(false);
		
		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(null);
		aboutPanel.setBounds(250, 0, 800, 200);
		aboutPanel.setOpaque(false);

		JLabel about = new JLabel("ABOUT", JLabel.CENTER);
		about.setFont(new Font("Ubuntu", Font.BOLD, 30));
    	about.setFont(new Font("Agency FB", Font.BOLD, 30));
    	about.setForeground(Color.WHITE);
    	about.setBounds(350, -60, 400, 200);
		
		JLabel aboutContent = new JLabel();
		aboutContent.setText("<html>&nbsp;&nbsp;&nbsp;&nbsp;This BattleSplix game is the final project of three Computer Science students for the course CMSC 137.</html>");
		aboutContent.setForeground(Color.WHITE);
		aboutContent.setFont(new Font("Ubuntu", Font.PLAIN, 18));
		aboutContent.setFont(new Font("Arial", Font.PLAIN, 18));
		aboutContent.setOpaque(false);
		aboutContent.setBounds(0, 0, 700, 200);
		
		JLabel devteam = new JLabel("DEVELOPERS", JLabel.CENTER);
		devteam.setFont(new Font("Ubuntu", Font.BOLD, 30));
    	devteam.setFont(new Font("Agency FB", Font.BOLD, 30));
    	devteam.setForeground(Color.WHITE);
    	devteam.setBounds(100, 60, 400, 200);

		aboutPanel.add(devteam);
    	aboutPanel.add(aboutContent);
		add(about);
		add(aboutPanel);

		devPanel.setLayout(null);
		iconPanel.setLayout(null);

		
		for(int i = 0; i < 3; i++){
			String str = "graphics/icondev" + (i+1);
			str = str.concat(".png");
			image = new ImageIcon(str);
			img = image.getImage();
			img = Menu.resizeImage(image, 100, 100);
			
			devIcon.add(new JPanel());
			devIcon.get(i).add(new JLabel(new ImageIcon(img)));
			devIcon.get(i).setBounds(0, i*120, 100, 110);
			devIcon.get(i).setOpaque(false);
			iconPanel.add(devIcon.get(i));
			
			image = new ImageIcon("graphics/textbg.png");
			img = image.getImage();
			img = Menu.resizeImage(image, 500, 80);
			JLabel textbg = new JLabel();
			icon = new ImageIcon(img);
			textbg.setIcon(icon);
			textbg.setBounds(-25, 0, 500, 80);
			textbg.setOpaque(false);
			
			JLabel dev = new JLabel(lodi[i]);
			dev.setBounds(20, 0, 500, 80);
			dev.setFont(new Font("Ubuntu", Font.PLAIN, 25));
			devs.add(new JPanel());
			devs.get(i).setLayout(null);
			devs.get(i).add(dev);
			devs.get(i).add(textbg);
			devs.get(i).setBounds(0, i*120, 300, 80);
			devs.get(i).setOpaque(false);
			devs.get(i).getComponent(0).setForeground(Color.BLACK);
			devs.get(i).addMouseListener(this);
			devPanel.add(devs.get(i));
		}

		iconPanel.setBounds(300, 190, 100, 500);
		devPanel.setBounds(440, 205, 500, 500);
		iconPanel.setOpaque(false);
		devPanel.setOpaque(false);
		this.setOpaque(false);
		this.add(iconPanel);
		this.add(devPanel);
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