package firstGamePackage;

public class BigEnemy extends Enemy {

  private static AbstrGameObject target;
  private int last_fired;
  private static int FIRE_INTERVALL = 2; // 2sec

  public BigEnemy() {
    super();
    y = 50;
    x = 50;
    width = 150;
    height = 150;
    image = PictureFactory.bigEnemy1_img;
    obj_image = image;
    target = GamePanel.ship;
    speed = GamePanel.getSpeedFactor(1);
    theta = 0;
    ROT_SPEED = 0d;
  }

  protected void adjustDirectionToTarget() {
    Direction dir = GraphicsTools.adjustDirectionToTarget(this, target);
    theta = 0;

    if (dir != null) {
      direction = dir;
    }
  }

  @Override
  protected void objectSpecificMove(AbstrGameObject obj) {
    adjustDirectionToTarget();
    // stays NORTH!
    if (y > GamePanel.y_max / 4) {
      y = GamePanel.y_max / 4;
    }
    GraphicsTools.checkBounds(this);
    fireBigGun();
  }

  private void fireBigGun() {

    if (GamePanel.Animation_tick > last_fired
        + GamePanel.getSpeedFactor(FIRE_INTERVALL)) {
      last_fired = GamePanel.Animation_tick;

      for (int i = 0; i < 10; i++) {
        GamePanel.enemy_laser_array.add(new BigGunLaser(x, y, theta + i));
      }
    }
  }
}
