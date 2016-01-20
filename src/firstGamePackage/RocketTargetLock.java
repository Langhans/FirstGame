package firstGamePackage;

import java.awt.Point;
import java.awt.Rectangle;

public class RocketTargetLock implements Runnable {

  private Rocket rocket;
  private int coord_x;
  private int coord_y;
  
  public RocketTargetLock(Rocket rocket , int x , int y) {
   this.rocket = rocket;
   coord_x =x;
   coord_y = y;
  }
  
  @Override
  public void run() {
    Rectangle r;
    
    for (Enemy enemy : GamePanel.enemy_array){
      // if intersects with an enemy
      r = new Rectangle(enemy.x,enemy.y,enemy.width,enemy.height);
      
      if (r.contains(new Point(coord_x, coord_y))){
        rocket.setTarget(enemy);
        enemy.height += 40;
      }  
    }
  }
}
