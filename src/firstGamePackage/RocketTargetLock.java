package firstGamePackage;

import java.awt.Point;
import java.awt.Rectangle;

public class RocketTargetLock extends Thread {

  private Ship ship;
  private int mouse_x;
  private int mouse_y;
  private AbstrGameObject target;

  public RocketTargetLock(Ship ship, int x, int y) {
    this.ship = ship;
    mouse_x = x;
    mouse_y = y;
  }

  public AbstrGameObject getTarget() {
    return target;
  }

  @Override
  public void run() {

    Rectangle r;

    for (Enemy enemy : GamePanel.enemy_array) {
      // if intersects with an enemy
      r = new Rectangle(enemy.x, enemy.y, enemy.width, enemy.height);

      if (r.contains(new Point(mouse_x, mouse_y))) {
        this.target = enemy;
        enemy.setTargetLocked();

      }

    }
  }
}
