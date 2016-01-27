package firstGamePackage;

import java.awt.Graphics2D;
import java.awt.Image;

public class Enemy2 extends Enemy{

  private static AbstrGameObject target;
  
  public Enemy2(){
    super();
    image = PictureFactory.enemy2_img;
    obj_image = image;
    target = GamePanel.ship;
    speed = 3;
    theta = 0;
    ROT_SPEED = 0.01;
  }
  
  protected void adjustDirectionToTarget() {
    direction = GraphicsTools.adjustDirectionToTarget( this , target);
    
    if( direction == null ){
      this.explode();
    }
  }
  
  @Override 
  protected void objectSpecificMove(AbstrGameObject obj){
    adjustDirectionToTarget();
    GraphicsTools.flipOverGameObjPosition(obj);
  }
  
}
