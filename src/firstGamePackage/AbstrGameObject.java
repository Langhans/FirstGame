package firstGamePackage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.InputStream;

import javax.swing.Timer;

public abstract class AbstrGameObject {

  protected int x;
  protected int y;
  protected int width;
  protected int height;
  protected Image image;
  protected boolean exploding = false;
  protected int tick = 0;
  protected Image[] explo_pics;
  protected Image obj_image;
  protected Direction direction = new Direction(1, 0);
  protected int speed;
  protected double theta;
  
  public abstract Graphics2D draw(Graphics2D g2);

  public  void prepareNextFrame(){
    if (exploding) {
      if (tick < 0){
        obj_image = image;
      } else{
      obj_image = explo_pics[tick];
      }
   } else {
       objectSpecificMove(this);
       x = x + (int) (direction.getX_dir() * speed);
       y = y + (int) (direction.getY_dir() * speed);
   }
  }
  
  // takes this as argument in sub classes
  protected abstract void objectSpecificMove(AbstrGameObject obj); 
  
  // has to be overriden with animation code
  public void animationTick() {
    if (tick < 0) {
      exploding = false;
      tick = -1;
      return;
    } else {
      tick--;
    }
  }
  
  public void explode(){
    tick = explo_pics.length - 1; // count of explosion frames
    exploding = true;
  }

  
}
