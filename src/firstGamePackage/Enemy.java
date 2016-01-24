package firstGamePackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Enemy extends AbstrGameObject {

  protected Image enemy_image;

  protected boolean targetLocked = false;
  
  protected Direction direction = new Direction(1, 0);
  protected int speed = 4;

  public Enemy() {
    image = GraphicsTools.makeImage("/Pics/enemy1.png");
    enemy_image = super.image;
    super.x = GamePanel.x_max + (int) (GamePanel.x_max * Math.random());
    super.y = (int) (GamePanel.y_max * Math.random());
    super.width = 50;
    super.height = 50;
    super.explo_pics = GraphicsTools.makeExploPics("/Pics/explosionPics");
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
    g2.drawImage(enemy_image, x, y, width, height, null);
    return g2;
  }

  @Override
  public void prepareNextFrame() {
    if (exploding) {
       if (tick < 0){
         enemy_image = super.image;
       } else{
       enemy_image = explo_pics[tick];
       }
    } else {
      GraphicsTools.flipOverGameObjPosition(this);
        x = x + (int) (direction.getX_dir() * speed);
        y = y + (int) (direction.getY_dir() * speed);
    }
  }

  public void setTargetLocked() {
    targetLocked = true;
  }
  
  public void setTargetUnlocked(){
    targetLocked = false;
  }

}// End of class Enemy
