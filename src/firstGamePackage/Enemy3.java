package firstGamePackage;

public class Enemy3 extends Enemy {

  private static AbstrGameObject target;
  private boolean laserLoaded = true;

  public Enemy3() {
    super();
    image = PictureFactory.enemy3_img;
    obj_image = image;
    target = GamePanel.ship;
    speed = 2;
    theta = 0;
    ROT_SPEED = 0.01;
  }
  

  private void adjustDirectionToTarget() {
    direction = GraphicsTools.adjustDirectionToTarget(this, target);

    if (direction == null) {
      this.explode();
    }
  }

  @Override
  public void objectSpecificMove(AbstrGameObject obj) {
    GraphicsTools.flipOverGameObjPosition(obj);
    adjustDirectionToTarget();
    // does not move further
    if (x > GamePanel.x_max / 3) {
      direction.setX_dir(0);
    }
    
    if (Math.abs(y - GamePanel.ship.getY()) < 20) {
      fireLaser();
    }
  }

  private void fireLaser() {
    if (laserLoaded) {
      GamePanel.laser_array.add(new EnemyLaser(x, y, new Direction(1, 0), 0));
      laserLoaded = false;
    }
  }
}
