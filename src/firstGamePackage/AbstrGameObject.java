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
  protected Image obj_image;
  protected boolean exploding = false;
  protected Image[] explo_pics;
  protected int tick = 0;
  protected Direction direction = new Direction(1 , 0);
  protected double theta;
  protected int speed;
  protected double ROT_SPEED;
  
  protected Graphics2D draw(Graphics2D g2){
    g2.translate(x + width / 2, y + height / 2);
    g2.rotate(theta);
    g2.drawImage(obj_image,  - width / 2,  - height / 2, width , height,
        null);
    g2.rotate(-theta);
    g2.translate(-(x + width / 2), -(y + height / 2));
    return g2;
  }

  protected  void prepareNextFrame(){
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
  
  // takes this as argument in sub classes - Template 
  protected abstract void objectSpecificMove(AbstrGameObject obj); 
  
  // has to be overriden with animation code
  protected void animationTick() {
    if (tick < 0) {
      exploding = false;
      tick = -1;
      return;
    } else {
      tick--;
    }
  }
  
  protected void explode(){
    tick = explo_pics.length - 1; // count of explosion frames
    exploding = true;
  }
}
