package firstGamePackage;

public class EnemyLaser extends Laser {

  public EnemyLaser(int x_start, int y_start,
      double theta_start) {
    
    super(x_start, y_start, theta_start);
    image = PictureFactory.laser2_img;
    obj_image = image;
    width = image.getWidth(null);
    height = image.getHeight(null);
    width /= 3;
    height /= 3;
    speed = GamePanel.getSpeedFactor(20) ;
  }
}
