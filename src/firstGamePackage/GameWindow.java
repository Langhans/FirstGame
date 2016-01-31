package firstGamePackage;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class GameWindow extends JFrame {
  
  private static final GameWindow window = new GameWindow();
  private final GamePanel gamePanel;
  
  private GameWindow(){
  gamePanel = new GamePanel();
  Toolkit toolkit = Toolkit.getDefaultToolkit();
  Dimension screensize = toolkit.getScreenSize();
  
  // Set ToolBar för knappar och Label
  JPanel toolPanel = new JPanel();
  JButton start_But = new JButton ("START");
  start_But.addActionListener(new ActionListener() {
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (!gamePanel.timer.isRunning()){
      gamePanel.timer.start();
    }
      else
        gamePanel.timer.stop();
    }
  });
  
  JLabel score_Lab = new JLabel ();
  score_Lab.setBorder(BorderFactory.createTitledBorder("Your Score:"));
  toolPanel.add(start_But);
  toolPanel.add(score_Lab);
  toolPanel.setVisible(true);
  
  JPanel frame = new JPanel();
  frame.setLayout(new BorderLayout());
  
  frame.add(toolPanel, BorderLayout.NORTH);
  frame.add( gamePanel , BorderLayout.CENTER);
  this.add(frame);
  gamePanel.setMinimumSize(new Dimension(800,600));
  gamePanel.setPreferredSize(new Dimension(800,600));
  this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
  this.pack();
  this.setLocation( screensize.width/2 - GamePanel.x_max/2, screensize.height/2 - GamePanel.y_max/2);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  gamePanel.requestFocus();
  gamePanel.requestFocusInWindow();
  this.setVisible(true);
}
  
  public static GameWindow getInstance(){
    return window;
  }
  
  public  static void setToCrosshairCursor(){
    window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
  }
  
  public static void setToGameCursor(){
    window.setCursor(new Cursor(Cursor.MOVE_CURSOR));
  }
  
}
