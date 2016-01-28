package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Rocket extends AbstrGameObject {

  private boolean fired = false;
  private AbstrGameObject target;

  public Rocket(AbstrGameObject target) {
    height = 25;
    width = 50;
    image = PictureFactory.rocket_img;
    explo_pics = PictureFactory.explo1_imgs;
    obj_image = image;
    this.target = target;
    speed = GamePanel.getSpeedFactor(7) ;
    ROT_SPEED = 0.2;
  }

//

  public void initRocket(double theta, int x_start, int y_start) {
    this.theta = theta;
    x = x_start;
    y = y_start;
    direction = GraphicsTools.getDirectionFromAngle(theta);
  }

  public void prepareNextFrame() {

    if (fired) {
     super.prepareNextFrame();
    }
  }

  private void adjustDirectionToTarget() {

    direction = GraphicsTools.adjustDirectionToTarget( this , target);
    
    if( direction == null ){
      this.explode();
    }
  }

  public void setFired() {
    this.fired = true;
  }

  public boolean isFired() {
    return fired;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public AbstrGameObject getTarget() {
    return target;
  }

  @Override
  protected void objectSpecificMove(AbstrGameObject obj) {
    GraphicsTools.flipOverGameObjPosition(obj);
    adjustDirectionToTarget();
  }
}// End Class Rocket