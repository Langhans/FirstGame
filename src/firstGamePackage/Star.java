package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;

public class Star {

  private int x;
  private int y;
  private int size_width = 1; // between 1 and 3 px
  private int size_height = 1;
  private static Direction direction = new Direction(0, 0);
  private int speed = GamePanel.getSpeedFactor(2);

  public Star() {
    randomStar();
  }

  private void randomStar() {
    x = (int) (Math.random() * GamePanel.screenSize.getWidth()) ;
    y = (int) (Math.random() * GamePanel.screenSize.getHeight());
    size_width = (int) (Math.random() * 4);
    size_height = (int) (Math.random() * 4);
    speed = speed + (int)(Math.random() * speed / 3);
  }

  public Graphics2D draw(Graphics2D g2) {
    g2.setColor(Color.WHITE);
    g2.fillOval(x, y, size_width, size_height);
    return g2;
  }

  public void prepareNextFrame() {
    // Move star to the left with main-speed, when arrived, restart on the right
    // side, random new form!
    if ( x > GamePanel.x_max) x = x - GamePanel.x_max;
    if (x < 0) x = GamePanel.x_max - x; 

    if ( y > GamePanel.y_max) y = y - GamePanel.y_max;
    if (y < 0) y = GamePanel.y_max - y;

    x = x - (int) (direction.getX_dir() * speed);
    y = y - (int) (direction.getY_dir() * speed);
  }

  public static void setStarDirection(Direction dir) {
    direction = dir;
  }

}
