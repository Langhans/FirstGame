package firstGamePackage;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Laser extends AbstrGameObject{

  private static Image laser; 
  
  private static int width; 
  private static int height;
  
  private final Direction direction;
  private final int speed = 45;
  private final double tetha;
  
  static {
    try {
    laser = ImageIO.read(Laser.class.getResourceAsStream("/Pics/laser.png"));
     width = laser.getWidth(null)  ;
    height = laser.getHeight(null) ;
    
    } catch (Exception e){
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
  
  public Laser(int x , int y , Direction direction , double tetha){
      super.x = x;
      super.y = y;
      this.direction = direction;
      this.tetha = tetha;
      super.width = width/2;
      super.height = height/2;
  }
  
  
  @Override
  public Graphics2D draw(Graphics2D g2) {
    g2.translate( x  , y + height/2);
    g2.rotate(tetha);
    g2.drawImage(laser,  0 , -height/2 , width , height, null);
    g2.rotate(-tetha);
    g2.translate(-x , -(y+ height/2));
    return g2;
  }

  @Override
  public void prepareNextFrame() {
    x = x + (int)(direction.getX_dir() * speed);
    y = y + (int)(direction.getY_dir() * speed);
  }

  @Override
  public void explode() {
    // TODO Auto-generated method stub
  }
}
