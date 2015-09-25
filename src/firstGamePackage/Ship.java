package firstGamePackage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Ship implements ActionListener {

	Timer exploTimer;
	static int timerCounter;
	static int timerI = 0;
	
	//public static String [] pics = {"alienblaster.png","explo1.png","explo2.png", "explo3.png" , "explo4.png", "enemy1.png"};
	
	
	//File shipImg_File;
	InputStream shipStream; 
	Image shipImg;
	
	int x;
	int y;
	
	static int x_start = 30;
	static int y_start;
	
	
	//Ships dimensions 
	public static int ship_height = 75;
	public static int ship_width = 75;
	
	//step / unit for one move via tangenterna!
	static int step = 8;
			
	//Ships state		
	boolean exploding = false;
	
	// Vapen  
	public Rocket rocket; 
	int rocket_count; 
	
	
	
	
	
		public Ship(){
			
			rocket_count = 10;
			
			rocket = new Rocket (); 
						
			//Startposition for spaceship
			x = x_start;
			y_start = GameElements.y_max/2;
			y = y_start;
			
			makeImage();
			
			
			//Timer som styr explosion
			 exploTimer = new Timer (200 , this);
//			 exploding = true;
			
		}
		
		
		public void makeImage (){
		//Get picture av ship from ressource as stream, used in constructor!
			try{
				shipStream = getClass().getClassLoader().getResourceAsStream(GameElements.pics[0]);
			}
			catch (Exception e){
				System.out.println( " CAnnot read STream " + e.getLocalizedMessage());
			}
			
			// load IMage from stream
			try{
			shipImg =  ImageIO.read( shipStream);
			}
			catch(Exception e){
				System.out.println("CAnnot build ship! Errormesg.: "+e.getMessage());
				System.exit(0);
			}
		}
			
		
		// returns a Graphics2D Object to draw the Ship
			public Graphics2D drawShip (Graphics2D g2){
				
					g2.drawImage(shipImg, x, y, ship_width ,ship_height ,  null);
					return g2;
			}
	
			
			
			public void drawNextFrame(){
				
				if (!exploding){
					;
				}
				else{
					exploTimer.start();
				}
				
			}
			
			
			
//			return ships aktuella position
			public int getX(){
				return this.x;
			}
			
//			return ships aktuella position
			public int getY(){
				return this.y;
			}
			
			//return rocket- count
			public int getRocketCount(){
				return this.rocket_count;
			}
			
			
			
//	MOVE SHIP 
			public void moveUp(){
				if( y <= step)
					y = 0;
				else	
					this.y = y - step;
			}
			
			public void moveDown(){
				if (y <= GameElements.y_max - step - Ship.ship_height )
						y = y + step;
			}
			
			
			public void moveFwd(){
				if (x <= GameElements.x_max - Ship.ship_width - step)
					x = x + step;
			}
			
			public void moveBwd(){
				if (x >= step)
					x= x - step;
			}

			
			public void fireRocket() {
				// TODO Auto-generated method stub
				if (this.rocket_count>=1 && !GameElements.ship.rocket.fired){
					this.rocket.initRocket();
					this.rocket.setFired();
					this.rocket_count -= 1;
				}
				else{
					;
				}
			}



			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			timerI++;
		
			if (timerI <= 4){
				try{
					shipStream = getClass().getClassLoader().getResourceAsStream(GameElements.pics[ timerI ]);
				}
				catch (Exception e1){
					System.out.println( " CAnnot read STream " + e1.getLocalizedMessage());
				}
				
				// load IMage from stream
				try{
				shipImg =  ImageIO.read( shipStream);
				}
				catch(Exception e2){
					System.out.println("CAnnot build ship! Errormesg.: "+e2.getMessage());
					System.exit(0);
				}
			}
			else {
				exploTimer.stop();
				exploding = false;
				timerCounter = 5; 
			
				try{
					shipStream = getClass().getClassLoader().getResourceAsStream(GameElements.pics[0]);
				}
				catch (Exception e1){
					System.out.println( " CAnnot read STream " + e1.getLocalizedMessage());
				}
				
				// load IMage from stream
				try{
				shipImg =  ImageIO.read( shipStream);
				}
				catch(Exception e2){
					System.out.println("CAnnot build ship! Errormesg.: "+e2.getMessage());
					System.exit(0);
				}
			
				
			}
			
		}
			
			
		public void setExploding()	{
			this.exploding=true;
		}
			
			
			
			
	}//End Class Ship
