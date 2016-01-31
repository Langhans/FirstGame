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
import java.awt.event.ComponentEvent;
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

public class GamePanel extends JPanel implements ActionListener , ComponentListener{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Paneldimension, default 800x600
  public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  public static Dimension panelSize = new Dimension(800, 600);
  public static int x_max = screenSize.width;
  public static int y_max = screenSize.height;

  // Frames, speed,
  public static int FRAME_INTERVALL = 30;
  public static boolean running = true;
  public static int SPEED = 8;

  static int t = 10;
  protected Timer timer;

  protected Timer anim_timer;
  public final static int ANIMATION_INTERVALL = 300;
  // Stars
  public final static int star_amount = 70;
  public final static int enemy_amount = 5;
  public final static Star[] stars_array = new Star[star_amount];
  public final static List<Enemy> enemy_array = new ArrayList<>();
  public final static List<Laser> laser_array = new ArrayList<>();
  public final static List<Laser> enemy_laser_array = new ArrayList<>();
  public final static List<AbstrGameObject> explo_array = new ArrayList<>();

  public static final boolean TESTMODE = false;

  private static AnimationListener animationListener;

  public static Ship ship = null;

  private static Graphics g;
  private static Graphics2D g2;

  // KONSTRUKTOR
  public GamePanel() {
    addComponentListener(this);
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
    anim_timer = new Timer(ANIMATION_INTERVALL, animationListener);
    timer.start();
    anim_timer.start();

    new EnemyMaker().start();
  }

  public void prepareAllForNextFrame() {

    checkEnemyForCollisions();
    checkShipForCollision();
    checkRocketForCollisionAndPrepareNextFrame();
        
    ship.prepareNextFrame();
    
    for (Laser l : laser_array) {
      l.prepareNextFrame();
    }
    
    for (Enemy e: enemy_array){
      e.prepareNextFrame();
    }
    
    for( Laser el : enemy_laser_array){
      el.prepareNextFrame();
    }

    // change Star direction!
    Star.setStarDirection(ship.getDirection());

    for (Star star : stars_array) {
      star.prepareNextFrame();
    }

    // refresh explosion array
    prepareExplosions();
  }

  private void prepareExplosions() {
    AbstrGameObject obj;

    for (int i = 0; i < explo_array.size(); i++) {
      obj = explo_array.get(i);

      if (obj.tick > -1) {
        obj.prepareNextFrame();
      } else {
        explo_array.remove(i);
      }
    }
  }

  private void checkEnemyForCollisions() {
    Enemy enemy;

    for (int i = 0; i < enemy_array.size(); i++) {
      enemy = enemy_array.get(i);

      if (GraphicsTools.isColliding(ship, enemy)) {
        explo_array.add(ship);
        explo_array.add(enemy);
        ship.explode();
        enemy.explode();
        enemy_array.remove(enemy);
      }

      Laser l;

      for (int j = 0; j < laser_array.size(); j++) {
        l = laser_array.get(j);

        if (GraphicsTools.outOfPanel(l)) {
          laser_array.remove(j);
          continue;
        } else {

          if (GraphicsTools.isColliding(l, enemy)) {
            explo_array.add(enemy);
            enemy.explode();
            enemy_array.remove(enemy);
          }
        }
      }
    }
  }

  private void checkShipForCollision() {
    Laser eLaser;

    for (int i = 0; i < enemy_laser_array.size(); i++) {

      eLaser = enemy_laser_array.get(i);

      if (GraphicsTools.outOfPanel(eLaser)) {
        enemy_laser_array.remove(i);
      } else {
        if (GraphicsTools.isColliding(eLaser, ship)) {
          enemy_laser_array.remove(i);
          explo_array.add(ship);
          ship.explode();
        }
      }
    }

  }

  private void checkRocketForCollisionAndPrepareNextFrame() {
    if (ship.rocket != null && ship.rocket.isFired()) {

      final AbstrGameObject target = ship.rocket.getTarget();

      if (GraphicsTools.isColliding(ship.rocket, target)) {
        explo_array.add(target);
        explo_array.add(ship.rocket);
        ship.rocket.explode();
        target.explode();
        enemy_array.remove(target);
        ship.resetRocket();
      } else {
        ship.rocket.prepareNextFrame();
      }
    }
  }

  private void drawImages(Graphics2D g2) {

    // TODO move to info-bar in different GUI panel
    g2.setColor(Color.RED);
    String rocketC = "Rockets: " + ship.getRocketCount();
    g2.drawString(rocketC, 25, 25);

    for (Star star : stars_array) {
      star.draw(g2);
    }

    ship.draw(g2);

    for (Laser l : laser_array) {
      l.draw(g2);
    }

    if (ship.rocket != null) {
      ship.rocket.draw(g2);
    }
    
    for (Enemy enemy : enemy_array) {
      enemy.draw(g2);
    }
    
    for (Laser l : enemy_laser_array){
      l.draw(g2);
    }

    for (AbstrGameObject obj : explo_array) {
      obj.draw(g2);
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    // draw till buffered Images!?
    drawImages(g2);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    prepareAllForNextFrame();
    repaint();
  }

  private void makeEnemies() {
   
    if (TESTMODE) {
      
      for (int i = 0; i < enemy_amount; i++) {
        enemy_array.add(new Enemy());
      }
//      enemy_array.add(new BigEnemy());
      enemy_array.add(new Enemy2());
      enemy_array.add(new Enemy());
      enemy_array.add(new Enemy3());
    }
  }

  private void makeStars() {
    for (int i = 0; i < star_amount; i++)
      stars_array[i] = new Star();
  }

  private class AnimationListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      for (int i = 0; i < explo_array.size(); i++) {
        explo_array.get(i).animationTick();
      }
    }
  }

  private class EnemyMaker extends Thread implements ActionListener {

    private Timer timer;

    public EnemyMaker() {
      timer = new Timer(4500, this);
      timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if (enemy_array.size() < enemy_amount){
      enemy_array.add(new Enemy2());
      enemy_array.add(new Enemy());
      enemy_array.add(new Enemy3());
//      enemy_array.add(new BigEnemy());
      }
    }
  }

  @Override
  public void componentResized(ComponentEvent e) {
    x_max = e.getComponent().getWidth();
    y_max = e.getComponent().getHeight();
  }

  @Override
  public void componentMoved(ComponentEvent e) {
  }

  @Override
  public void componentShown(ComponentEvent e) {
  }

  @Override
  public void componentHidden(ComponentEvent e) {
  }

  public static int getSpeedFactor(int factor) {
    return factor * 1000 / GamePanel.ANIMATION_INTERVALL;
  }
}
