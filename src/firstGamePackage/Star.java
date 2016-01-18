package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Star{
	
	int x;
	int y;
	
	int size_width = 1; //between 1 and 3 pixel
	int size_height = 1; 
	
	public Star(){
	  randomStar();
	}
	
	private void randomStar(){
	  x = (int)(Math.random() * 2 * GamePanel.x_max) - GamePanel.x_max;
	  y=(int)(Math.random() * 2 * GamePanel.y_max) - GamePanel.y_max;
	  
	  size_width=(int)(Math.random()*4);
	  size_height=(int)(Math.random()*4);
	}
	
	public Graphics2D draw( Graphics2D g2){
		g2.setColor(Color.WHITE);
		g2.fillOval( x , y, size_width, size_height);
		return g2;
	}

  
  public void prepareNextFrame() {
    //Move star to the left with main-speed, when arrived, restart on the right side, random new form!
    if (x>=0){
      x = x-GamePanel.SPEED;
    } else {
      randomStar();
    }
  }
}
