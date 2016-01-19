package firstGamePackage;

public class Direction {

  private double x_dir;
  private double y_dir;
  
  public Direction(double x , double y){
    this.x_dir = x;
    this.y_dir = y;
  }
  
  
  
  public double getX_dir() {
    return x_dir;
  }

  public double getY_dir() {
    return y_dir;
  }

  public void setX_dir(double x_dir) {
    this.x_dir = x_dir;
  }

  public void setY_dir(double y_dir) {
    this.y_dir = y_dir;
  }
}
