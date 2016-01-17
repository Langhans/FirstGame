package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Rocket extends AbstrGameObject {

  // Rockets state
  
  private boolean exploding = false;
  private boolean fired = false;

  private Image rocket_image;

  public Rocket() {
    super.height = 15;
    super.width = 15;
    super.image = GraphicsTools.makeImage("/Pics/rocket.png");
    rocket_image = super.image;
  }

  public Graphics2D draw(Graphics2D g2) {
    g2.setColor(Color.WHITE);
    g2.drawString(x + "-" + y, x, y);
    g2.drawImage(rocket_image, this.x, this.y, width,
        height, null);
    return g2;
  }
  
  public void initRocket( int x , int y) {
    super.x = x ;
    super.y = y - height / 2;
  }

  public void prepareNextFrame() {
    if( fired ){
      this.x = x + 10;
    }
    GraphicsTools.checkBounds(this);
    
  }// end next frame Rocket

  public void setFired() {
    this.fired = true;
  }
  
  public boolean isFired(){
    return fired;
  }
  
  public int getX(){
    return this.x;
  }
  
  public int getY(){
    return this.y;
  }

  public void explode() {
    //TODO Rocket exploding()
    System.out.println("ROCKET EXPLODES!");
  }
  
  

}// End Class Rocket