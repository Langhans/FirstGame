package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Rocket extends AbstrGameObject {

  // Rockets state

  private boolean exploding = false;
  private boolean fired = false;
  private final int speed = 10;

  private Image rocket_image;
  private AbstrGameObject target;

  private Direction direction = new Direction(1, 0);
  private double theta = 0;

  public Rocket(AbstrGameObject target) {
    super.height = 25;
    super.width = 50;
    super.image = GraphicsTools.makeImage("/Pics/rocket.png");
    rocket_image = super.image;
    this.target = target;
  }

  public Graphics2D draw(Graphics2D g2) {

    g2.translate(x + width / 2, y + height / 2);
    g2.rotate(theta);
    g2.drawImage(rocket_image, 0 - width / 2, 0 - height / 2, width, height,
        null);
    g2.rotate(-theta);
    g2.translate(-(x + width / 2), -(y + height / 2));
    return g2;
  }

  public void initRocket(int x, int y) {
    super.x = x;
    super.y = y;
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
      direction.setX_dir(cos); // cos(t)
      direction.setY_dir(sin); // sin(t)
      theta = Math.atan(sin / cos);
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

  public void explode() {
    // TODO Rocket exploding()
    System.out.println("ROCKET EXPLODES!");
  }

  public AbstrGameObject getTarget() {
    return target;
  }

}// End Class Rocket