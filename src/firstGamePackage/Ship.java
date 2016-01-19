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
  
  /**
   * Ship is the center of coordinate system in this game.
   * Ships direction is virtual motion of the ship in regards to 
   * the stars which move in the opposite direction. This creates an 
   * illusion of motion. The Direction is based on a rotation around the 
   * center of the Ship, by an angle tetha. Theta is used to calculate changes
   * in the Direction using standard angle functions (sine and cosine) on a 
   * unit-circle.    
   * 
   * @author Tim Langhans, YRGO.se
   */
  
  
  private static final double ROT_UNIT = 0.3d;

  private Image image;
  
  static int x_start = 30;
  static int y_start;
  
  private int x_real;
  private int y_real;
  private Direction direction = new Direction(1,0);
  private double theta = 0;
  
  
  private Image ship_image = null;
  // step =  unit for one move via key press!
  static int step = 50;
  // Vapen
  protected Rocket rocket = null;
  protected int rocket_count;
  
  public Ship() {
    rocket_count = 10;
    rocket = new Rocket();

//  Startposition for spaceship
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
    g2.translate(x_real + width / 2 , y_real + height / 2 );
    g2.rotate(theta);
    g2.drawImage(ship_image, 0 - width/2, 0 - height/2, width, height, null);
    g2.rotate(-theta);
    g2.translate(-(x_real + width / 2) , -(y_real + height / 2) );
    return g2;
  }
  
  private void adjustRealXandY(){
    
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
  
  public Direction getDirection(){
    return direction;
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
  
  
  public void rotateRight(){
    crankRight();
    calcNewDirectionFromTetha();    
  }
  
public void rotateLeft(){
    crankLeft();
    calcNewDirectionFromTetha();
  }
  
  // calculate new direction from changed angle of ship
  private void calcNewDirectionFromTetha() {
    direction.setX_dir(Math.cos(theta) );
    direction.setY_dir(Math.sin(theta) );
  }

  // change angle of ship (less angle)
  private void crankLeft() {
    if (theta - ROT_UNIT < 0 ){
      theta = 2 * Math.PI + theta - ROT_UNIT;
    } else {
      theta = theta - ROT_UNIT;
    }
  }
  // change angle of ship (higher angle)
  private void crankRight(){
    if (theta + ROT_UNIT > 2 * Math.PI){
      theta = (theta + ROT_UNIT) - 2 * Math.PI;
    } else {
      theta = theta + ROT_UNIT;
    }
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
    return x_real + width/2  + (int)((width/2) * Math.cos(theta));
  }
  
  private int getWeaponStartY(){
    return y_real + height/2 + (int)((height / 2) * Math.sin(theta));
  }

  public void exploding(){
    // TODO SHIP exploding()
    System.out.println("SHIP EXPLODES!");
  }
  
  @Override
  public void explode() {
    super.exploding = true;
  }

  public void fireLaser() {
    GamePanel.laser_array.add(new Laser(
        getWeaponStartX(), 
        getWeaponStartY(), 
        new Direction(
            direction.getX_dir(),
            direction.getY_dir()),
            theta ));
  }
}// End Class Ship
