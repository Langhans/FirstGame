package firstGamePackage;

public class Enemy3 extends Enemy {
  

    private static AbstrGameObject target;
    
    public Enemy3(){
      super();
      image = PictureFactory.enemy3_img;
      obj_image = image;
      target = GamePanel.ship;
      speed = 2;
      theta = 0;
      ROT_SPEED = 0.01;
    }
    
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
      if (Math.abs(y - GamePanel.ship.getY()) < 20){
        fireLaser();
      }
    }

    private void fireLaser() {
      GamePanel.laser_array.add(new Laser(x, y,
          new Direction(1, 0), 0));
      
    }
}
