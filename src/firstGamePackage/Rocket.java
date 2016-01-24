package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Rocket extends AbstrGameObject {

  private static final double ROCKET_ROT_SPEED = 0.2;
  // Rockets state

  private boolean exploding = false;
  private boolean fired = false;
  private final int speed = 10;

  private Image rocket_image;
  private AbstrGameObject target;

  private Direction direction;
  private double theta = 0;


  public Rocket(AbstrGameObject target) {
    super.height = 25;
    super.width = 50;
    super.image = GraphicsTools.makeImage("/Pics/rocket.png");
    rocket_image = super.image;
    this.target = target;
    explo_pics = GraphicsTools.makeExploPics("/Pics/explosionPics");
  }

  public Graphics2D draw(Graphics2D g2) {
    
    if (!exploding) {
      g2.translate(x + width / 2, y + height / 2);
      g2.rotate(theta);
      g2.drawImage(rocket_image,  - width / 2,  - height / 2, width , height,
          null);
      g2.rotate(-theta);
      g2.translate(-(x + width / 2), -(y + height / 2));
    } else {

      if (tick < 0) {
        rocket_image = super.image;
      } else {
        rocket_image = explo_pics[tick];
      }
    }
    return g2;
  }

  public void initRocket(double theta, int x, int y) {
    this.theta = theta;
    super.x = x;
    super.y = y;
    direction = GraphicsTools.getDirectionFromAngle(theta);
  }

  public void prepareNextFrame() {

    adjustDirectionToTarget();

    if (fired) {
      x = x + (int) (direction.getX_dir() * speed);
      y = y + (int) (direction.getY_dir() * speed);
    }
  }

  private void adjustDirectionToTarget() {
    double dx = target.x - x;
    double dy = (target.y - y);
    double hyp = Math.sqrt(dx * dx + dy * dy);

    // when hyp is small, rocket has hit target!
    if (hyp <= 0.01) {
      this.explode();
      target.explode();
    } else {
      double sin = dy / hyp;
      double cos = dx / hyp;
      double newDir = Math.atan(sin/cos);
      if (theta - newDir > ROCKET_ROT_SPEED){
        newDir = theta - ROCKET_ROT_SPEED;
      }else if (newDir - theta >ROCKET_ROT_SPEED){
        newDir = theta + ROCKET_ROT_SPEED;
      }
      //TODO rocket rotation, flying backwards!?
      if (dx < 0) {
        theta =  newDir ; 
      } else {
        theta = newDir;
      }
      direction.setX_dir(Math.cos(theta)); // cos(t)
      direction.setY_dir(Math.sin(theta)); // sin(t)
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

}// End Class Rocket