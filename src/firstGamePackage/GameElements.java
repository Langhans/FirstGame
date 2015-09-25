package firstGamePackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;





import java.awt.event.ActionListener;

public class GameElements extends JPanel implements ActionListener{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String [] pics = {"alienblaster.png","explo1.png","explo2.png", "explo3.png" , "explo4.png", "enemy1.png"};
	
	//Paneldimension, default 800x600
	public static Dimension panelSize = new Dimension(800,600);
	public static int x_max = panelSize.width;
	public static int y_max = panelSize.height;
	
	//Frames, speed, 
	public static int frameIntervall = 40;
	public static boolean running = true;
	public static int speed = 4;
	
	static int t=10;
	
	Graphics g; 
//	
	BufferedImage frame = new BufferedImage(x_max , y_max, BufferedImage.TYPE_INT_RGB); 
	Graphics frame_graph = frame.getGraphics();
	public Timer timer;
	
	// Stars
	public int star_amount = 450;
	public static int enemy_amount = 15;
	public Star [] stars_array = new Star[ star_amount ];
	public static Enemy [] enemy_array = new Enemy [enemy_amount];
	public static Ship ship = null;
	
	
	
	//KONSTRUKTOR
	public GameElements(){
		
		this.setBackground(Color.BLACK);
		
		timer=new Timer( frameIntervall , this );
		
		g = this.getGraphics();
		
		
		
		//timer.start(); //testing only 
		
		
		
		
		//Skapa stars
		for (int i = 0; i<= star_amount-1 ; i++)
			stars_array[i] = new Star();
		
		//skapa ship-Obj
		ship = new Ship();
		
		for(int i = 0 ; i <= enemy_amount-1 ; i++)
			enemy_array[i] = new Enemy ();
		
		
		
		addMouseListener(new ShipStyrning());
		this.addKeyListener( new ShipStyrning() );
		addMouseMotionListener ( new ShipStyrning() );
//		ship.fireRocket();
		
		System.out.println(this.getKeyListeners().toString());
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		
		timer.start(); //testing only 
		
		
		
	}
	
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		Graphics2D g2 =(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		g2.setColor(Color.RED);
		String rocketC = "Rockets: " + ship.getRocketCount();
		g2.drawString(rocketC, 25, 25);
		
		
		for (Star star : stars_array) {
			star.draw(g2);
		} 
			
		ship.drawShip(g2);
		

		for (Enemy enemy : enemy_array) {
			enemy.drawEnemy(g2);
			
		} 
		
		
		if (ship.rocket.fired)
			ship.rocket.drawRocket(g2);
		//else
			//System.out.println("Rocket not visible!");
	}
	
	
	
	public void prepareNextFrame(){
		
		// Flyttar all Stars
		for (Star star : stars_array){
			try {
				star.drawNextFrame();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
		// Flyttar Skepp
		ship.drawNextFrame();
		
		// draw Rocket 
		if (ship.rocket.fired){
		//System.out.println("True");
			try {
				ship.rocket.drawNextFrame();
			} catch (Throwable e1) {
			System.out.println("Cannot draw next frame for ROcket! ");
			e1.printStackTrace();
			}
		
		}
		
		for (Enemy enemy : enemy_array){
			if (ship.rocket.x >= enemy.x && ship.rocket.x <= enemy.x+enemy.enemy_width
					&& ship.rocket.y >= enemy.y && ship.rocket.y <= enemy.y + enemy.enemy_height)
				enemy.setExploding();
			
			if (enemy.x >=  ship.x && enemy.y >= ship.y)
				ship.setExploding();
			
			
			
			
			try {
				enemy.drawNextFrame();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
	}
	
	
	
	
	
	
	

	public static void main (String [] args){
		
		System.out.println("PRintings från Main Loop!");
	
			GameElements gamePanel = new GameElements();

		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screensize = toolkit.getScreenSize();
		
		// Set ToolBar för knappar och Label
		 JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		JButton start_But = new JButton ("START");
		start_But.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!gamePanel.timer.isRunning()){
				gamePanel.timer.start();
				System.out.println("Timer startet!");
			}
				else
					gamePanel.timer.stop();
			}
		});
		
		
		JLabel score_Lab = new JLabel ();
		score_Lab.setBorder(BorderFactory.createTitledBorder("Your Score:"));
		toolBar.add(start_But);
		toolBar.add(score_Lab);
		toolBar.setVisible(true);
		
		JFrame frame = new JFrame("SpaceShooter");
		
		frame.add(toolBar, BorderLayout.NORTH);
	
		frame.add( gamePanel , BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setSize( GameElements.panelSize );
		frame.setLocation( screensize.width/2 - x_max/2, screensize.height/2 - y_max/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			
		prepareNextFrame();
	
		repaint();		
	}




// windowlistener -> resize panelsize, maxX och Y för att anpassa vid ändrat window!!!
	
// Functions som test om enemy,is hit by rocket or ship hit by enemy -> inled explosioner, reset, etc.



	
}

