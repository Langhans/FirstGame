package firstGamePackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;

import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Paneldimension, default 800x600
  public static Dimension panelSize = new Dimension(800, 600);
  public static int x_max = panelSize.width;
  public static int y_max = panelSize.height;

  // Frames, speed,
  public static int FRAME_INTERVALL = 10;
  public static boolean running = true;
  public static int SPEED = 2;

  static int t = 10;
  public static double rotation = 0;
  protected Timer timer;
  
  protected Timer anim_timer;
  public final static int ANIMATION_INTERVALL = 200;
  // Stars
  public final static int star_amount = 350;
  public final static int enemy_amount = 15;
  public final static Star[] stars_array = new Star[star_amount];
  public final static List<Enemy> enemy_array = new ArrayList<>();
  public final static List<Laser> laser_array = new ArrayList<>();
  public final static List<AbstrGameObject> explo_array = new ArrayList<>();
  
  private static AnimationListener animationListener;
  
  private Ship ship = null;

  private static Graphics g;
  private static Graphics2D g2;

  // KONSTRUKTOR
  public GamePanel() {

    this.setBackground(Color.BLACK);
    timer = new Timer(FRAME_INTERVALL, this);
    g = this.getGraphics();
    g2 = (Graphics2D) g;
    
    makeStars();

    // skapa ship-Obj
    ship = new Ship();
    ShipStyrning styrning = new ShipStyrning(ship);
    addMouseListener(styrning);
    addKeyListener(styrning);
    addMouseMotionListener(styrning);

    makeEnemies();

    this.setFocusable(true);
    this.requestFocusInWindow(true);
    
    
    animationListener = new AnimationListener();
    anim_timer = new Timer(ANIMATION_INTERVALL ,animationListener);
    
    timer.start(); 
    anim_timer.start();
  }

  private void makeEnemies() {
    for (int i = 0; i < enemy_amount; i++) {
      enemy_array.add(new Enemy());
    }
  }

  private void makeStars() {
    for (int i = 0; i < star_amount; i++)
      stars_array[i] = new Star();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    // draw till buffered Images!
  prepareBackImage(g2);
  prepareEnemyImage(g2);
  }

  private void prepareEnemyImage(Graphics2D g2) {
    for (Enemy enemy : enemy_array) {
      enemy.draw(g2);
    }
  }

  private void prepareBackImage(Graphics2D g2) {

   g2.rotate(rotation);
        
    g2.setColor(Color.RED);
    String rocketC = "Rockets: " + ship.getRocketCount();
    g2.drawString(rocketC, 25, 25);

    for (Star star : stars_array) {
      star.draw(g2);
    }
    
    ship.draw(g2);

    for(Laser l : laser_array){
      l.draw(g2);
    }
    
    if (ship.rocket.isFired()) {
      ship.rocket.draw(g2);
    }
  }

  public void prepareAllForNextFrame() {
    for (Star star : stars_array) {
      star.prepareNextFrame();
    }
    
    for( Laser l : laser_array){
      l.prepareNextFrame();
    }

    for (Enemy enemy : enemy_array) {
      if (GraphicsTools.isColliding(ship, enemy)) {
        ship.explode();
      }
      if (ship.rocket.isFired()
          && GraphicsTools.isColliding(ship.rocket, enemy)) {
        explo_array.add(enemy);
        explo_array.add(ship.rocket);
        ship.rocket.explode();
        enemy.explode();
      }
    }
    ship.prepareNextFrame();

    for (Enemy enemy : enemy_array) {
      enemy.prepareNextFrame();
    }

    if (ship.rocket.isFired()) {
      ship.rocket.prepareNextFrame();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    prepareAllForNextFrame();
    repaint();
  }
  
  private class AnimationListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      for (int i = 0 ; i < explo_array.size() ; i++){
        explo_array.get(i).animationTick();
      }
    }
  }
  
  // windowlistener -> resize panelsize, maxX och Y för att anpassa vid ändrat
  // window!!!

  // Functions som test om enemy,is hit by rocket or ship hit by enemy -> inled
  // explosioner, reset, etc.

}
