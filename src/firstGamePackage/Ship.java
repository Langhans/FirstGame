package firstGamePackage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Ship {

	
	File shipImg_File;
	int x;
	int y;
	
	int x_start = 30;
	int y_start;
	Image shipImg;
	
	
		public Ship(){
			
			boolean exploding = false;
					
			int rocket_count = 10;
			
			//Startposition for spaceship
			x = x_start;
			y_start = GameElements.y_max/2;
			y = y_start;
			
			try{
				shipImg_File = new File("/Users/timlanghans/Downloads/alienblaster.png");
				System.out.println("ShipFile is made" + shipImg_File.getAbsolutePath());
			}
			catch (Exception e){
				System.out.println("CAnnot find pic-file!");
			}
			
			
			try{
			shipImg =  ImageIO.read( shipImg_File);
			}
			catch(Exception e){
				System.out.println("CAnnot build ship! Errormesg.: "+e.getMessage());
				System.exit(0);
			}
			
			
		}
		
			public Graphics2D drawShip (Graphics2D g2){
				
				g2.drawImage(shipImg, x, y, 75 ,75 ,  null);
				return g2;
				
			}
	
			
			public void drawNextFrame(){
				if(x<=GameElements.x_max)
					x+=3;
				else
					x=30;
					
				
				
				
				
			}
	
}
