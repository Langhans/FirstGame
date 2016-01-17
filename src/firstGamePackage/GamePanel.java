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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

public class GamePanel extends JPanel implements ActionListener{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Paneldimension, default 800x600
	public static Dimension panelSize = new Dimension(800,600);
	public static int x_max = panelSize.width;
	public static int y_max = panelSize.height;
	
	//Frames, speed, 
	public static int frameIntervall = 40;
	public static boolean running = true;
	public static int speed = 4;
	
	static int t=10;
	public static double rotation = 0;
	BufferedImage frame = new BufferedImage(x_max , y_max, BufferedImage.TYPE_INT_RGB); 
	Graphics frame_graph = frame.getGraphics();
	protected Timer timer;
	
	// Stars
	public final static int star_amount = 350;
	public final static int enemy_amount = 15;
	public final static Star[] stars_array = new Star[ star_amount ];
	public final static List<Enemy> enemy_array = new ArrayList<>();
	private Ship ship = null;

	private static Graphics g;
	
	//KONSTRUKTOR
	public GamePanel(){
		
		this.setBackground(Color.BLACK);
		
		timer=new Timer( frameIntervall , this );
		
		g = this.getGraphics();
		
		// timer.start(); //testing only 
		
		makeStars();
		
		//skapa ship-Obj
		ship = new Ship();
		ShipStyrning styrning = new ShipStyrning(ship);
		addMouseListener(styrning);
		addKeyListener( styrning );
		addMouseMotionListener ( styrning );

		makeEnemies();
		
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		timer.start(); //testing only 
	}
	
	private void makeEnemies() {
	  for(int i = 0 ; i < enemy_amount ; i++){
      enemy_array.add(new Enemy());
	  }
  }

  private void makeStars() {
	//Skapa stars
    for (int i = 0; i < star_amount ; i++)
      stars_array[i] = new Star();
  }


  public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 =(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.rotate( rotation );
		
		g2.setColor(Color.RED);
		String rocketC = "Rockets: " + ship.getRocketCount();
		g2.drawString(rocketC, 25, 25);
		
		for (Star star : stars_array) {
			star.draw(g2);
		} 
			
		ship.draw(g2);

		for (Enemy enemy : enemy_array) {
			enemy.draw(g2);
		} 
		
		if (ship.rocket.isFired())
			ship.rocket.draw(g2);
		
	}
	
	
	
	public void prepareAllForNextFrame(){
		
		for (Star star : stars_array){
				star.prepareNextFrame();
		}
		
		
		for(Enemy enemy : enemy_array){
		  if(GraphicsTools.isColliding(ship, enemy)){
		    ship.setExploding();
		    
		  }
		  if (ship.rocket.isFired() && GraphicsTools.isColliding(ship.rocket , enemy )){
		    ship.rocket.explode();
		  }
		}
		
		
		// Flyttar Skepp
		ship.prepareNextFrame();
		
		for(Enemy enemy : enemy_array){
		  enemy.prepareNextFrame();
		}
		
		// draw Rocket 
		if (ship.rocket.isFired()){
				ship.rocket.prepareNextFrame();
			}
		
		
		// COLLISION FIRST !!!!
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		prepareAllForNextFrame();
		repaint();		
	}


// windowlistener -> resize panelsize, maxX och Y för att anpassa vid ändrat window!!!
	
// Functions som test om enemy,is hit by rocket or ship hit by enemy -> inled explosioner, reset, etc.

}

