package firstGamePackage;

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

  private Image enemy_image;

  private Image[] explo_pics;

  static int step = 8;

  public Enemy() {
    super.image = GraphicsTools.makeImage("/Pics/enemy1.png");
    enemy_image = super.image;
    super.x = GamePanel.x_max + (int) (GamePanel.x_max * Math.random());
    super.y = (int) (GamePanel.y_max * Math.random());
    super.width = 50;
    super.height = 50;

    explo_pics = GraphicsTools.makeExploPics("/Pics/explosionPics");
  }

 

  @Override
  public Graphics2D draw(Graphics2D g2) {
    g2.setColor(Color.RED);
    g2.drawImage(enemy_image, x, y, width, height, null);
    return g2;
  }

  @Override
  public void prepareNextFrame() {
    if (exploding) {
       enemy_image = explo_pics[tick-1];
    } else {
      if (x >= -width) {
        x -= 3;
        // y = (int)( 450 * Math.sin( (Math.PI)/ (x/2) ) );
      } else {
        this.x = GamePanel.x_max + 25;
      }
    }
  }

  @Override
  public void explode() {
    tick = explo_pics.length; // count of explosion frames
    exploding = true;
  }

}// End of class Enemy
