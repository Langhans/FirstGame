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

  private Image image;
  protected boolean exploding = false;
  static int x_start = 30;
  static int y_start;
  
  private int x_real;
  private int y_real;

  private Image ship_image = null;
  // step / unit for one move via tangenterna!
  static int step = 50;
  // Vapen
  protected Rocket rocket = null;
  protected int rocket_count;
  
  public Ship() {
    rocket_count = 10;
    rocket = new Rocket();

//    // Startposition for spaceship
    super.x = GamePanel.x_max / 2;
    y_start = GamePanel.y_max / 2;
    super.y = y_start;
    x_real = super.x;
    y_real = super.y;
    
    super.width = 75;
    super.height = 75;
    
    super.image = makeImage("/Pics/alienblaster.png");
    ship_image = super.image;
  }

  // returns a Graphics2D Object to draw the Ship
  @Override
  public Graphics2D draw(Graphics2D g2) {

    adjustRealXandY();
    g2.drawImage(ship_image, x_real, y_real, width, height, null);
    return g2;
  }
  
  public void adjustRealXandY(){
    
    if (x < x_real){
      x_real -= 2;
    } else if( x > x_real ){
      x_real += 2;
    }
    
    if (y < y_real){
      y_real -= 2 ;
    } else if (y > y_real){
      y_real += 2;
    }
  }

  @Override
  public void prepareNextFrame() {

    if (exploding) {
      // ship_image = null;
      System.out.println("SHIP HIT!");
    } else {
      ship_image = super.image;
    }
  }

  // return ships aktuella position
  public int getX() {
    return x_real;
  }

  // return ships aktuella position
  public int getY() {
    return y_real;
  }

  // return rocket- count
  public int getRocketCount() {
    return rocket_count;
  }

  // MOVE SHIP
  public void moveUp() {
    
    if (super.y <= step)
      super.y = 0;
    else
      super.y -= step;
  }

  public void moveDown() {
    if (super.y <= GamePanel.y_max - step - height)
      super.y += step;
  }

  public void moveFwd() {
    if (super.x <= GamePanel.x_max - width - step)
      super.x += step;
  }

  public void moveBwd() {
    if (super.x >= step)
      super.x -= step;
  }

  public void fireRocket() {
    
    if (this.rocket_count >= 1 && !rocket.isFired()) {
      this.rocket.initRocket(
          getWeaponStartX() ,
          getWeaponStartY() );
      this.rocket_count -= 1;
      this.rocket.setFired();
    } 
  }
  
  private int getWeaponStartX(){
    return x_real + width;
  }
  
  private int getWeaponStartY(){
    return y_real + height / 2;
  }

  public void exploding(){
    // TODO SHIP exploding()
    System.out.println("SHIP EXPLODES!");
  }
  
  
  @Override
  public void explode() {
    this.exploding = true;
  }

  public void fireLaser() {
    GamePanel.laser_array.add(new Laser(
        getWeaponStartX(), 
        getWeaponStartY(), 
        new Direction(45,0)));
    
    
  }
  
  
  
  
}// End Class Ship
