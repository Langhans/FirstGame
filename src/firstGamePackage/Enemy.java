package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Enemy implements ActionListener{

	
	public int x;
	public int y;
	
	
	InputStream enemyStream;
	Image enemyImg;
	
	int x_start;
	int y_start;
		
	//Enemy dimensions 
	public static int enemy_height = 50;
	public static int enemy_width = 50;
	
	//step / unit for one move
	static int step = 8;
			
	//Ships state		
	boolean exploding = false;
	
	// Vapen  
	 
	
	public static Timer exploTimer;
	public static int timerI=1;
	
	
	
	
	
	public Enemy (){
	
		try{
			enemyStream = getClass().getClassLoader().getResourceAsStream(GameElements.pics[5]);
		}
		catch (Exception e){
			System.out.println("Cannot find enemyPic");
		}
	
		try{
			enemyImg = ImageIO.read( enemyStream );
		}
		catch (Exception e){
			System.out.println("Cannot make enemyPic from stream");
		}
	
		x = GameElements.x_max + (int) (GameElements.x_max * Math.random() );
		y = (int)(GameElements.y_max * Math.random() );
		
		
		
		exploTimer = new Timer (200 , this);
	
	}//End Konstruktor
		
	
	
	public Graphics2D drawEnemy (Graphics2D g2){
		g2.setColor(Color.RED);
		g2.drawString(this.x + "-" + this.y, x, y);
		g2.drawImage(enemyImg, x, y,  enemy_width ,  enemy_height , null);
		return g2;		
		
	}

	
	
	public void drawNextFrame(){
		
		
		if (x>= 0 - enemy_width ){
			x = x-9;
			//y = (int)( 450 * Math.sin( (Math.PI)/ (x/2) ) );
		}
		else{
			
			try {
				//this.finalize();
				this.x = GameElements.x_max + 25;
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	public void actionPerformed ( ActionEvent e ){
		// TODO Auto-generated method stub                                                
        
	timerI++;                                                                             
                                                                                          
	if (timerI <= 4){                                                                     
		try{                                                                              
			enemyStream = getClass().getClassLoader().getResourceAsStream(GameElements.pics[ timerI ]);
		}                                                                                 
		catch (Exception e1){                                                             
			System.out.println( " CAnnot read STream " + e1.getLocalizedMessage());       
		}                                                                                 
		                                                                                  
		// load IMage from stream                                                         
		try{                                                                              
		enemyImg =  ImageIO.read( enemyStream);                                             
		}                                                                                 
		catch(Exception e2){                                                              
			System.out.println("CAnnot build ship! Errormesg.: "+e2.getMessage());        
			System.exit(0);                                                               
		}                                                                                 
	}                                                                                     
	else {  
		//Reset timer, counter och pic/Image!
		exploTimer.stop();                                                                
		exploding = false;                                                                
		timerI = 1;                                                                 
	                                                                                      
		try{                                                                              
			enemyStream = getClass().getClassLoader().getResourceAsStream(GameElements.pics[5]);
		}                                                                                 
		catch (Exception e1){                                                             
			System.out.println( " CAnnot read STream " + e1.getLocalizedMessage());       
		}                                                                                 
		                                                                                  
		// load IMage from stream                                                         
		try{                                                                              
			enemyImg =  ImageIO.read( enemyStream);                                             
		}                                                                                 
		catch(Exception e2){                                                              
			System.out.println("CAnnot build ship! Errormesg.: "+e2.getMessage());        
			System.exit(0);                                                               
		}                                                                                 
	                                                                                      
		                                                                                  
	}                                                                                     
	                                                                                      
}                                                                                         
	                                                                                      
	                                                                                      
public void setExploding()	{                                                             
	this.exploding=true;         
	exploTimer.start();
}                                                                                         
	
	
	
	
	
	
	
	
}// End of class Enemy
