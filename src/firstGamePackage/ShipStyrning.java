package firstGamePackage;

import java.awt.Graphics;
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
		  ship.moveFwd();
		} else if (key == KeyEvent.VK_RIGHT){
		  ship.moveDown();
		} else if (key == KeyEvent.VK_LEFT){
		  ship.moveUp();
		} else if (key == KeyEvent.VK_DOWN){
		  ship.moveBwd();
		} else if (key == KeyEvent.VK_R){
		  ship.fireRocket();
		} else if (key == KeyEvent.VK_Q){
		  GamePanel.rotation -= 0.05d;
		} else if (key == KeyEvent.VK_E){
		  GamePanel.rotation += 0.05d;
		} else if (key == KeyEvent.VK_W){
		  ship.fireLaser();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		ship.fireRocket();
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
	  ship.x = e.getX(); 
	  ship.y = e.getY(); 
	}

	

}
