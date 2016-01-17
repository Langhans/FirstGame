package firstGamePackage;

import static firstGamePackage.GraphicsTools.makeImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Ship extends AbstrGameObject {

  Timer exploTimer;
  static int timerCounter;
  static int timerI = 0;
  InputStream shipStream;
  private Image image;

  protected boolean exploding = false;

 

  static int x_start = 30;
  static int y_start;

  private Image ship_image = null;

  // step / unit for one move via tangenterna!
  static int step = 8;

  // Vapen
  protected Rocket rocket;
  protected int rocket_count;
  
  public Ship() {
    rocket_count = 10;
    rocket = new Rocket();

    // Startposition for spaceship
    super.x = x_start;
    y_start = GamePanel.y_max / 2;
    super.y = y_start;
    
    super.width = 75;
    super.height = 75;
    
    super.image = makeImage("/Pics/alienblaster.png");
    ship_image = super.image;
  }

  // returns a Graphics2D Object to draw the Ship
  @Override
  public Graphics2D draw(Graphics2D g2) {
    g2.drawImage(ship_image, x, y, width, height, null);
    return g2;
  }

  @Override
  public void prepareNextFrame() {

    if (exploding) {
     ship_image = null;
    } else {
      ship_image = super.image;
    }
  }

  // return ships aktuella position
  public int getX() {
    return this.x;
  }

  // return ships aktuella position
  public int getY() {
    return this.y;
  }

  // return rocket- count
  public int getRocketCount() {
    return this.rocket_count;
  }

  // MOVE SHIP
  public void moveUp() {
    if (y <= step)
      y = 0;
    else
      this.y = y - step;
  }

  public void moveDown() {
    if (y <= GamePanel.y_max - step - height)
      y = y + step;
  }

  public void moveFwd() {
    if (x <= GamePanel.x_max - width - step)
      x = x + step;
  }

  public void moveBwd() {
    if (x >= step)
      x = x - step;
  }

  public void fireRocket() {
    // TODO Auto-generated method stub
    if (this.rocket_count >= 1) {
      
      this.rocket.initRocket(
          getRocketStartX() ,
          getRocketStartY() );
      this.rocket_count -= 1;
      this.rocket.setFired();
    }
  }
  
  private int getRocketStartX(){
    return x + width;
  }
  
  private int getRocketStartY(){
    return y + height / 2;
  }

  public void exploding(){
    // TODO SHIP exploding()
    System.out.println("SHIP EXPLODES!");
  }
  
  
  public void setExploding() {
    this.exploding = true;
  }

}// End Class Ship
