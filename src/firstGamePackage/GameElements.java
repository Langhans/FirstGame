package firstGamePackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
public class GameElements extends JPanel implements ActionListener{

	
	
	//Paneldimension, default 800x600
	public static Dimension panelSize = new Dimension(800,600);
	public static int x_max = panelSize.width;
	public static int y_max = panelSize.height;
	
	//Frames, speed, 
	public static int frameIntervall = 50;
	public static boolean running = true;
	public static int speed = 2;
	
	static int t=10;
	
	Graphics g; 
//	
	BufferedImage frame = new BufferedImage(x_max , y_max, BufferedImage.TYPE_INT_RGB); 
	Graphics frame_graph = frame.getGraphics();
	public Timer timer;
	
	// Stars
	public int star_amount = 150;
	public Star [] stars_array = new Star[ star_amount ];
	
	Ship ship;
	
	//KONSTRUKTOR
	public GameElements(){
		
		this.setBackground(Color.BLACK);
		
		timer=new Timer( frameIntervall , this );
		g = this.getGraphics();
		timer.start();
		
		for (int i = 0; i<= star_amount-1 ; i++)
			stars_array[i] = new Star();
		
		ship = new Ship();
		
		
	}
	
	
	

		
	
		

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.drawOval(t, t, 34, 34);
		g.drawImage(frame , x_max , y_max , this);
		
		
		Graphics2D g2 =(Graphics2D) g;
		for (Star star : stars_array) {
			star.draw(g2);
		} 
			
		ship.drawShip(g2);
		
	}
	
	public static void main (String[] args){
		
		System.out.println("PRintings från Main Loop!");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screensize = toolkit.getScreenSize();
		
		// Set ToolBar för knappar och Label
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		JButton start_But = new JButton ("START");
		JLabel score_Lab = new JLabel ();
		score_Lab.setBorder(BorderFactory.createTitledBorder("Your Score:"));
		toolBar.add(start_But);
		toolBar.add(score_Lab);
		toolBar.setVisible(true);
		
		
		
		JFrame frame = new JFrame("SpaceShooter");
		
		frame.add(toolBar, BorderLayout.NORTH);
		frame.add(new GameElements(), BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setSize( GameElements.panelSize );
		frame.setLocation( screensize.width/2 - x_max/2, screensize.height/2 - y_max/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Create a new ActionObjekt! MÅste skrivas!
		if (t<=350)
			t=t+10;
		else
			t=0;
		
		for(Star star : stars_array){
			try {
				star.drawNextFrame();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		ship.drawNextFrame();
	
		
		
		repaint();
		
		
	}




// windowlistener -> resize panelsize, maxX och Y för att anpassa vid ändrat window!!!
	




	
}

