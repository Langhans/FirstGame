package firstGamePackage;

public class BigGunLaser extends EnemyLaser {

  public BigGunLaser(int x_start, int y_start, 
      double theta_start) {
    
    super(x_start,
        y_start,
        theta_start );
    speed = GamePanel.getSpeedFactor(25);
    image = PictureFactory.bigGunLaser1_img;
    obj_image = image;
    width  *= 3;
    height *= 3;
  }
}
