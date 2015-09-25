package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Rocket {

	//Rockets state
		public int x=0;
		public int y=0;
	
	
		public boolean explodera = false;
		//public boolean visible = false;
		public boolean fired = false;
	
	public static int rocket_width=  15;
	public static int rocket_height= 15;
	
	//File rocketImg_File;
	InputStream rocketStream; 
	Image rocketImg;
	
	
	//Konstruktor! 
	public Rocket(){
	
		//get Rocket från ressources
	try {
		rocketStream = GameElements.class.getResourceAsStream("/rocket.png");
		rocketStream.toString();
	}
	catch (Exception e){
		System.out.println("RocketSTream cannot be loaded! " + e.getStackTrace());
	}
		
		//load image of ROcket
	try{
	rocketImg =  ImageIO.read( rocketStream);
	}
	catch(Exception e2){
		System.out.println("CAnnot build rocket p! Errormesg.: "+e2.getMessage());
		System.exit(0);
		}
	}// ENd of Konstruktor
	
	
	
	public Graphics2D drawRocket (Graphics2D g2){
			g2.setColor(Color.WHITE);
			g2.drawString(this.x + "-" + this.y, x, y);
			g2.drawImage(rocketImg, this.x , this.y , Rocket.rocket_width, Rocket.rocket_height, null);
			
			return g2;
		}
	
	
	// måste göras innan rocket kan ritas, ger aktuell position
	public void initRocket(){
		//always aktuell position av rocket
			this.x=GameElements.ship.getX() + Ship.ship_width;
			this.y= GameElements.ship.getY() + Ship.ship_height/2 - Rocket.rocket_height/2;
			
	}
	
	
	public void drawNextFrame(){
			//GameElements.ship.rocket.fired=true;
			if ( this.x <= GameElements.x_max ){
				this.x = x + 15;
				
				
				
				
				}
			else{
				try {
					//finalize();
					GameElements.ship.rocket.fired=false;
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}//end next frame Rocket
	
	
	
	public void setFired(){
		if (GameElements.ship.rocket.fired || GameElements.ship.rocket_count<=0)
			;
		else 
			this.fired=true;
			//this.visible = true;
	}
	
	
}//End Class Rocket