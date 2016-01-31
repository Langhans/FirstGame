package firstGamePackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ShipStyrning
    implements KeyListener, MouseListener, MouseMotionListener {

  private final Ship ship;
  private int x_curs;
  private int y_curs;
  
  public ShipStyrning(Ship ship) {
    this.ship = ship;
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {

    int key = e.getKeyCode();

    if (key == KeyEvent.VK_UP) {
      ship.moveUp();
    } else if (key == KeyEvent.VK_RIGHT) {
      ship.moveRight();
    } else if (key == KeyEvent.VK_LEFT) {
      ship.moveLeft();
    } else if (key == KeyEvent.VK_DOWN) {
      ship.moveDown();
    } else if (key == KeyEvent.VK_R) {
      ship.fireRocket();
    } else if (key == KeyEvent.VK_Q) {
      ship.rotateLeft();
    } else if (key == KeyEvent.VK_E) {
      ship.rotateRight();
    } else if (key == KeyEvent.VK_W || key == KeyEvent.VK_SHIFT ) {
      ship.fireLaser();
    } else if (key == KeyEvent.VK_SPACE) {
      toggleAimMode();
    }
  }
  
  private void toggleAimMode() {
    if (ship.isInAimMode()) {
      ship.resetAimMode();
    } else {
      ship.aimMode = true;
      GameWindow.setToCrosshairCursor();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {

    RocketTargetLock targetLock = new RocketTargetLock(ship, e.getX(),
        e.getY());
    ship.setTargetLock(targetLock);
  }

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {  }

  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    x_curs =  e.getX() - ship.width/2;
    y_curs = e.getY() - ship.height/2;
    
    if (ship.aimMode) {
      ;
    } else {
      ship.setX_target(x_curs);
      ship.setY_target(y_curs);
    }
  }
}
