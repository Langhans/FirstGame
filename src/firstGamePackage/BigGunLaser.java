package firstGamePackage;

public class BigGunLaser extends EnemyLaser {

  public BigGunLaser(int x_start, int y_start, Direction dir_start,
      double theta_start) {
    
    super(x_start, y_start,
        GraphicsTools.getDirectionFromAngle(theta_start), theta_start );
    width *= 2;
    height *=2;
    
    image = PictureFactory.bigGunLaser1_img;
    obj_image = image;
    
  }

}
