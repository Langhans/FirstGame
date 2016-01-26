package firstGamePackage;

import java.awt.Graphics2D;
import java.awt.Image;

public class Enemy2 extends Enemy{

  private static AbstrGameObject target;
  private double ROT_SPEED = 0.01;
  
  public Enemy2(){
    super();
    image = PictureFactory.enemy2_img;
    super.obj_image = image;
    target = GamePanel.ship;
    speed = 2;
    theta = 0;
  }
  
//  @Override
//  public void prepareNextFrame() {
//    if (exploding) {
//       if (tick < 0){
//         enemy_image = image;
//       } else{
//       enemy_image = explo_pics[tick];
//       }
//    } else {
//      GraphicsTools.flipOverGameObjPosition(this);
//      adjustDirectionToTarget();
//        x = x + (int) (direction.getX_dir() * speed);
//        y = y + (int) (direction.getY_dir() * speed);
//    }
//  }

  
  private void adjustDirectionToTarget() {
    double dx = target.x - x;
    double dy = (target.y - y);
    double hyp = Math.sqrt(dx * dx + dy * dy);

    // when hyp is small, rocket has hit target!
    if (hyp <= 0.01) {
      this.explode();
      target.explode();
    } else {
      double sin = dy / hyp;
      double cos = dx / hyp;
      double newDir = Math.atan(sin/cos);
      if (theta - newDir > ROT_SPEED){
        newDir = theta - ROT_SPEED;
      }else if (newDir - theta > ROT_SPEED){
        newDir = theta + ROT_SPEED;
      }
      //TODO rocket rotation, flying backwards!?
      if (dx < 0) {
        theta =  newDir ; 
      } else {
        theta = newDir;
      }
      direction.setX_dir(Math.cos(theta)); // cos(t)
      direction.setY_dir(Math.sin(theta)); // sin(t)
    }
  }
  
  @Override 
  public void objectSpecificMove(AbstrGameObject obj){
    GraphicsTools.flipOverGameObjPosition(obj);
    adjustDirectionToTarget();
  }
  
}
