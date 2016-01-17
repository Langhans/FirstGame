package firstGamePackage;

import java.awt.Graphics;
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
		System.out.println(" KEY TYPED IN SHIPSTYRNING! YEESSSSS");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("KeyPressed Event mottagen av Shipstyrningsobjektet!");
		int key = e.getKeyCode();
		
		switch (key){
		
		case KeyEvent.VK_UP:
			ship.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			ship.moveDown();
			break;
		case KeyEvent.VK_RIGHT:
			ship.moveFwd();
			break;
		case KeyEvent.VK_LEFT:
			ship.moveBwd();
			break;
		case KeyEvent.VK_R:
			ship.fireRocket();
			break;
		default:
			System.out.println(" no move: KeyCode: " + key);
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	

}
