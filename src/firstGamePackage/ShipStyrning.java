package firstGamePackage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ShipStyrning implements KeyListener, MouseListener, MouseMotionListener {

  private final Ship ship;
  
  
  public ShipStyrning( Ship ship) {
    this.ship = ship;
  }
	
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(" KEY TYPED IN SHIPSTYRNING!");
	}

	@Override
	public void keyPressed(KeyEvent e) {

	  int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP){
		  ship.moveUp();
		} else if (key == KeyEvent.VK_RIGHT){
		  ship.moveRight();
		} else if (key == KeyEvent.VK_LEFT){
		  ship.moveLeft();
		} else if (key == KeyEvent.VK_DOWN){
		  ship.moveDown();
		} else if (key == KeyEvent.VK_R){
		  ship.fireRocket();
		} else if (key == KeyEvent.VK_Q){
		  ship.rotateLeft();
		} else if (key == KeyEvent.VK_E){
		  ship.rotateRight();
		} else if (key == KeyEvent.VK_W){
		  ship.fireLaser();
		} else if (key == KeyEvent.VK_SPACE){
		  toggleAimMode();
		}
	}

	// leave aimmode by mouse click on ship
	private void toggleAimMode() {
    if (!ship.aimMode){
      ship.aimMode = true;
    }
  }

  @Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	  Rectangle r = new Rectangle(ship.x,ship.y,ship.width,ship.height);
	  
	  if (r.contains(new Point(e.getX(),e.getY()))){
	    ship.aimMode = false;
	  }
	}

	@Override
	public void mousePressed(MouseEvent e) {
	  
	  Rectangle shipRect = new Rectangle(ship.x,ship.y,ship.width,ship.height);
	  
	  if (shipRect.contains(new Point(e.getX() , e.getY()))){
	    ship.aimMode = false;
	    ship.targetLock = null;
	  } else {
	    RocketTargetLock targetLock = new RocketTargetLock(ship , e.getX(), e.getY() );
	    ship.setTargetLock( targetLock);
	  }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	  
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	  if (ship.aimMode){
	    ;
	  }else {
	    ship.x = e.getX(); 
	    ship.y = e.getY(); 
	  }
	}

	

}
