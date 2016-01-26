package firstGamePackage;

import static firstGamePackage.GraphicsTools.makeImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Ship extends AbstrGameObject {

  /**
   * Ship is the center of coordinate system in this game. Ships direction is
   * virtual motion of the ship in regards to the stars which move in the
   * opposite direction. This creates an illusion of motion. The Direction is
   * based on a rotation around the center of the Ship, by an angle tetha. Theta
   * is used to calculate changes in the Direction using standard angle
   * functions (sine and cosine) on a unit-circle.
   * 
   * @author Tim Langhans, YRGO.se
   */

  private static final double ROT_UNIT = 0.35d;

  static int x_start = 30;
  static int y_start;

  private int x_target;

  private int y_target;
  private Direction direction = new Direction(1, 0);
  private double theta = 0;

//  private Image obj_image = null;
  // step = unit for one move via key press!
  static int step = 50;
  // Vapen
  protected Rocket rocket = null;
  protected int rocket_count;
  protected boolean aimMode = false;
  protected RocketTargetLock targetLock = null;

  public Ship() {
    rocket_count = 1000;
    // Startposition for spaceship
    super.x = GamePanel.x_max / 2;
    super.y = GamePanel.y_max / 2;
    x_target = super.x;
    y_target = super.y;

    super.width = 75;
    super.height = 75;

    super.image = makeImage("/Pics/alienblaster.png");
    obj_image = super.image;
    super.explo_pics = GraphicsTools.makeExploPics("/Pics/explosionPics");
  }

  public void setX_target(int x_target) {
    this.x_target = x_target;
  }

  public void setY_target(int y_target) {
    this.y_target = y_target;
  }

  // returns a Graphics2D Object to draw the Ship
  @Override
  public Graphics2D draw(Graphics2D g2) {

    g2.translate(x + width / 2, y + height / 2);
    g2.rotate(theta);
    g2.drawImage(obj_image, 0 - width / 2, 0 - height / 2, width, height,
        null);
    g2.rotate(-theta);
    g2.translate(-(x + width / 2), -(y + height / 2));
    return g2;
  }

  @Override
  public void prepareNextFrame() {

    adjustTargetXandY();

    if (exploding) {

      if (tick < 0) {
        exploding = false;
      } else {
        obj_image = explo_pics[tick];
      }
    } else {
      obj_image = super.image;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  // return rocket- count
  public int getRocketCount() {
    return rocket_count;
  }

  // MOVE SHIP
  public void moveUp() {

    if (y_target <= step)
      y_target = 0;
    else
      y_target -= step;
  }

  public Direction getDirection() {
    return direction;
  }

  public void moveDown() {
    if (y_target <= GamePanel.y_max - step - height)
      y_target += step;
  }

  public void moveRight() {
    if (x_target <= GamePanel.x_max - width - step)
      x_target += step;
  }

  public void moveLeft() {
    if (x_target >= step)
      x_target -= step;
  }

  public void rotateRight() {
    crankRight();
    calcNewDirectionFromTetha();
  }

  public void rotateLeft() {
    crankLeft();
    calcNewDirectionFromTetha();
  }

  public void fireLaser() {
    GamePanel.laser_array.add(new Laser(getWeaponStartX(), getWeaponStartY(),
        new Direction(direction.getX_dir(), direction.getY_dir()), theta));
  }

  public void fireRocket() {

    if (targetLock == null || targetLock.getTarget() == null) {
      return;
    }

    if (rocket_count >= 1 && rocket == null) {
      rocket = new Rocket(targetLock.getTarget());
      rocket.initRocket(theta, getWeaponStartX(), getWeaponStartY());
      rocket_count -= 1;
      rocket.setFired();
    }
  }

  // adjusting per frame for smooth motion
  private void adjustTargetXandY() {

    if (x > x_target) {
      x -= 2;
    } else if (x < x_target) {
      x += 2;
    }

    if (y > y_target) {
      y -= 2;
    } else if (y < y_target) {
      y += 2;
    }
  }

  // calculate new direction from changed angle of ship
  private void calcNewDirectionFromTetha() {
    direction.setX_dir(Math.cos(theta));
    direction.setY_dir(Math.sin(theta));
  }

  // change angle of ship (less angle)
  private void crankLeft() {
    if (theta - ROT_UNIT < 0) {
      theta = 2 * Math.PI + theta - ROT_UNIT;
    } else {
      theta = theta - ROT_UNIT;
    }
  }

  // change angle of ship (higher angle)
  private void crankRight() {
    if (theta + ROT_UNIT > 2 * Math.PI) {
      theta = (theta + ROT_UNIT) - 2 * Math.PI;
    } else {
      theta = theta + ROT_UNIT;
    }
  }

  private int getWeaponStartX() {
    return x + width / 2 + (int) ((width / 2) * Math.cos(theta));
  }

  private int getWeaponStartY() {
    return y + height / 2 + (int) ((height / 2) * Math.sin(theta));
  }

  public boolean isInAimMode() {
    return aimMode;
  }

  public void setTargetLock(RocketTargetLock rocketTargetLock) {
    if (this.targetLock == null) {
      this.targetLock = rocketTargetLock;
      targetLock.start();
    }
  }

  public void resetRocket() {
    rocket = null;
    targetLock = null;
  }

  public void resetAimMode() {
    aimMode = false;
    GameWindow.setToGameCursor();

    if (targetLock != null) {
      Enemy enemy = (Enemy) targetLock.getTarget();

      if (enemy != null) {
        enemy.setTargetUnlocked();
      }
      targetLock = null;
    }
  }

  @Override
  protected void objectSpecificMove(AbstrGameObject obj) {
    // TODO Auto-generated method stub
    
  }
}// End Class Ship
