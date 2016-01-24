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

  public abstract Graphics2D draw(Graphics2D g2);

  public abstract void prepareNextFrame();

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
