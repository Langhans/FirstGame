package firstGamePackage;

public class Laser extends AbstrGameObject{
  
  
  public Laser(int x_start , int y_start , Direction dir_start , double theta_start){
      x = x_start;
      y = y_start;
      direction = dir_start;
      theta = theta_start;
      image = PictureFactory.laser_img;
      obj_image = image; 
      width = image.getWidth(null) ;
      height = image.getHeight(null);
      speed = GamePanel.getSpeedFactor(25) ;
      }
  

  @Override
  protected void prepareNextFrame() {
    x = x + (int)(direction.getX_dir() * speed);
    y = y + (int)(direction.getY_dir() * speed);
  }

  @Override
  protected void explode() {
  }


  @Override
  protected void objectSpecificMove(AbstrGameObject obj) {
  }
}
