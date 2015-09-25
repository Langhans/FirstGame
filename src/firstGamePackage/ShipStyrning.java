package firstGamePackage;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ShipStyrning implements KeyListener, MouseListener, MouseMotionListener {

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(" KEY TYPED IN SHIPSTYRNING! YEESSSSS");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("KeyPressed Event mottagen av Shipstyrningsobjektet!");
		
		int key = e.getKeyCode();
		
		switch (key){
		
		case KeyEvent.VK_UP:
			GameElements.ship.moveUp();
			System.out.println("UP KEY!");
		
		case KeyEvent.VK_DOWN:
			GameElements.ship.moveDown();
		
		case KeyEvent.VK_RIGHT:
			GameElements.ship.moveFwd();
			
		case KeyEvent.VK_LEFT:
			GameElements.ship.moveBwd();
			
		case KeyEvent.VK_R:
			GameElements.ship.fireRocket();
			
	
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
		GameElements.ship.fireRocket();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		System.out.printf(" -- %d -- %d -- \n", x , y);
		
		for (Enemy enemy : GameElements.enemy_array){
			if (x >= enemy.x && x < (enemy.x + enemy.enemy_width)
					&& y >= enemy.y && (y <= enemy.y + enemy.enemy_height)){
				
				System.out.printf("Enemy %d at %d -- %d !\n", enemy.hashCode(), enemy.x , enemy.y );
				
				enemy.setExploding();
				
				
			}
		}
		
		
		
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
		// TODO Auto-generated method stub
		GameElements.ship.x = e.getX() - Ship.ship_width/2;
		GameElements.ship.y = e.getY() - Ship.ship_height/2;
	}

	

}
