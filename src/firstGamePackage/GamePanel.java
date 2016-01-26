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
  protected Timer timer;

  protected Timer anim_timer;
  public final static int ANIMATION_INTERVALL = 200;
  // Stars
  public final static int star_amount = 150;
  public final static int enemy_amount = 5;
  public final static Star[] stars_array = new Star[star_amount];
  public final static List<Enemy> enemy_array = new ArrayList<>();
  public final static List<Laser> laser_array = new ArrayList<>();
  public final static List<AbstrGameObject> explo_array = new ArrayList<>();

  public static final boolean TESTMODE = true;

  private static AnimationListener animationListener;

  public static Ship ship = null;

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
    anim_timer = new Timer(ANIMATION_INTERVALL, animationListener);

    timer.start();
    anim_timer.start();
    
    new EnemyMaker().start();
  }

  public void prepareAllForNextFrame() {

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

      for (Laser l : laser_array) {
        if (GraphicsTools.isColliding(l, enemy)) {
          
          explo_array.add(enemy);
          enemy.explode();
          enemy_array.remove(enemy);
        }
      }
      enemy.prepareNextFrame();
    }

    ship.prepareNextFrame();

    for (Laser l : laser_array) {
      l.prepareNextFrame();
    }

    // change Star direction!
    Star.setStarDirection(ship.getDirection());

    for (Star star : stars_array) {
      star.prepareNextFrame();
    }

   AbstrGameObject obj;
    
    for (int i = 0 ; i < explo_array.size() ; i++){
     obj = explo_array.get(i);
     if( obj.tick > -1 )
       obj.prepareNextFrame();
     else 
       explo_array.remove(i);
       if (obj instanceof Rocket){
         explo_array.remove(i);
       }
    }
    
    prepareRocketsNextFrame();
  }

  private void prepareRocketsNextFrame() {
    if (ship.rocket != null && ship.rocket.isFired()) {

      final AbstrGameObject target = ship.rocket.getTarget();
      int xR = ship.rocket.getX();
      int yR = ship.rocket.getY();

//      if (GraphicsTools.outOfPanel(ship.rocket)) {
//        ship.rocket = null;
//        return;
//      }
      GraphicsTools.flipOverGameObjPosition(ship.rocket);

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

  private void drawEnemyImage(Graphics2D g2) {
    for (Enemy enemy : enemy_array) {
      enemy.draw(g2);
    }
  }

  private void drawBackImage(Graphics2D g2) {

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
    
    for( AbstrGameObject obj : explo_array){
      obj.draw(g2);
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    // draw till buffered Images!?
    drawBackImage(g2);
    drawEnemyImage(g2);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    prepareAllForNextFrame();
    repaint();
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
  
  
  private class AnimationListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      for (int i = 0; i < explo_array.size(); i++) {
        explo_array.get(i).animationTick();
      }
    }
  }
  
  
  private class EnemyMaker extends Thread implements ActionListener{

    private Timer timer;
    
    public EnemyMaker(){
      timer = new Timer(2500 , this);
      timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
//      enemy_array.add(new Enemy2());
//      enemy_array.add(new Enemy());
      enemy_array.add(new Enemy3());
      
      
    }
  }

  // windowlistener -> resize panelsize, maxX och Y för att anpassa vid ändrat
  // window!!!

  // Functions som test om enemy,is hit by rocket or ship hit by enemy -> inled
  // explosioner, reset, etc.

}
