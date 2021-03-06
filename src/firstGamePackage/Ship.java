package firstGamePackage;

public class Ship extends AbstrGameObject {

  /**
   * @author Tim Langhans, YRGO.se
   */

  private static final double ROT_UNIT = 0.35d;
  private int x_target;
  private int y_target;
  static int step = 75;
  // weapons
  protected Rocket rocket = null;
  protected int rocket_count;
  protected boolean aimMode = false;
  protected RocketTargetLock targetLock = null;
  private int MOVE_SPEED = GamePanel.getSpeedFactor(2);
  
  

  public Ship() {
    rocket_count = 1000;
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
    GamePanel.laser_array.add(
        new Laser(getWeaponStartX(), getWeaponStartY(), theta));
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
    adjustTargetX();
    adjustTargetY();
  }
  
  private void adjustTargetY() {
    
    if (Math.abs(y - y_target) < MOVE_SPEED){
      return;
    } else if (y > y_target ) {
      y -= MOVE_SPEED;
    } else if (y < y_target) {
      y += MOVE_SPEED;
    } 
  }

  private void adjustTargetX() {
    
    if (Math.abs(x - x_target) < MOVE_SPEED){
      return;
    } else if (x > x_target ) {
      x -= MOVE_SPEED ;
    } else if (x < x_target) {
      x += MOVE_SPEED;
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
