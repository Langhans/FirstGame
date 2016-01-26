package firstGamePackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class Enemy extends AbstrGameObject {

  protected boolean targetLocked = false;

  
  public Enemy() {
    image = PictureFactory.enemy1_img;
    obj_image = image;
    x = GamePanel.x_max + (int) (GamePanel.x_max * Math.random());
    y = (int) (GamePanel.y_max * Math.random());
    width = 50;
    height = 50;
    explo_pics = GraphicsTools.makeExploPics("/Pics/explosionPics");
    speed = 4;
    theta = 0;
  }
  
  public Enemy( Direction dir){
    this();
    direction = dir;
  }
 
  @Override
  public Graphics2D draw(Graphics2D g2) {
    if (targetLocked){
      g2.setColor(Color.RED);
      g2.setStroke(new BasicStroke(3));
      g2.drawRect(x, y , width, height);
    }
    g2.drawImage(obj_image, x, y, width, height, null);
    return g2;
  }

  public void setTargetLocked() {
    targetLocked = true;
  }
  
  public void setTargetUnlocked(){
    targetLocked = false;
  }

  @Override
  protected void objectSpecificMove(AbstrGameObject obj) {
    GraphicsTools.flipOverGameObjPosition(obj);
  }

}// End of class Enemy
