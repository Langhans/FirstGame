package firstGamePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BigEnemy extends Enemy implements ActionListener{

  private static AbstrGameObject target;
  private Timer timer;
  
  public BigEnemy(){
    super();
    y = 50;
    x = 50;
    width = 100;
    height = 100;
    image = PictureFactory.bigEnemy1_img;
    obj_image = image;
    target = GamePanel.ship;
    speed = 2;
    theta = 0;
    ROT_SPEED = 0d;
    timer = new Timer(5000 , this);
    timer.start();
  }
  
  protected void adjustDirectionToTarget() {
    Direction dir = GraphicsTools.adjustDirectionToTarget( this , target);
    theta = 0;
    
    if( dir != null ){
      direction = dir;
    }
  }
  
  @Override 
  protected void objectSpecificMove(AbstrGameObject obj){
    adjustDirectionToTarget();
    
    // stays NORTH!
    if (y > GamePanel.y_max / 4){
      y = GamePanel.y_max / 4;
    }
  }

  private void fireBigGun() {
    for (int i = 0 ; i < 5 ; i++){
      GamePanel.laser_array.add(new BigGunLaser(x,y, direction , theta + i) );
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    fireBigGun();
  }
  
  
}
