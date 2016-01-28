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

//  static int x_start = 30;
//  static int y_start;
//
  private int x_target;
  private int y_target;
  static int step = 75;
  // Vapen
  protected Rocket rocket = null;
  protected int rocket_count;
  protected boolean aimMode = false;
  protected RocketTargetLock targetLock = null;

  private int MOVE_SPEED = GamePanel.getSpeedFactor(3);

  public Ship() {
    rocket_count = 1000;
    // Startposition for spaceship
    x = GamePanel.x_max / 2;
    y = GamePanel.y_max / 2;
    x_target = x;
    y_target = y;
    theta = 0;
    width = 75;
    height = 75;
    speed = 0 ;
    image = PictureFactory.ship_img;
    obj_image = image;
    explo_pics = PictureFactory.explo1_imgs;
  }

  public void setX_target(int x_t) {
    x_target = x_t;
  }

  public void setY_target(int y_t) {
    y_target = y_t;
  }

  @Override
  public void prepareNextFrame() {
    adjustTargetXandY();
    super.prepareNextFrame();
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
    direction = GraphicsTools.getDirectionFromAngle(theta);
  }

  public void rotateLeft() {
    crankLeft();
    direction = GraphicsTools.getDirectionFromAngle(theta);
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
      x -= MOVE_SPEED ;
    } else if (x <= x_target) {
      x += MOVE_SPEED;
    }

    if (y > y_target) {
      y -= MOVE_SPEED;
    } else if (y <= y_target) {
      y += MOVE_SPEED;
    }
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
  }
}// End Class Ship
