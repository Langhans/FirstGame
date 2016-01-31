package firstGamePackage;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

  /**
   * 
   */
  private static final GameWindow window = new GameWindow();
  private final GamePanel gamePanel;

  private GameWindow() {
    gamePanel = new GamePanel();

    // Set ToolBar for Buttons and Label
    JPanel toolPanel = new JPanel();
    JButton start_But = new JButton("PAUSE");
    start_But.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (!gamePanel.timer.isRunning()) {
          gamePanel.timer.start();
        } else
          gamePanel.timer.stop();
      }
    });

    toolPanel.add(start_But);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(toolPanel, BorderLayout.NORTH);
    mainPanel.add(gamePanel, BorderLayout.CENTER);
    this.add(mainPanel);
    gamePanel.setMinimumSize(new Dimension(500, 400));
    gamePanel.setPreferredSize(new Dimension(800, 600));
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.pack();
    this.setLocationRelativeTo(null);
    
    // Handle Focus!
    toolPanel.setFocusable(false);
    this.setFocusable(false);
    gamePanel.requestFocus();
    gamePanel.requestFocusInWindow();
    gamePanel.addFocusListener(new FocusListener() {

      @Override
      public void focusLost(FocusEvent arg0) {
        gamePanel.requestFocus();
      }

      @Override
      public void focusGained(FocusEvent arg0) {
      }
    });

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  public static GameWindow getInstance() {
    return window;
  }

  public static void setToCrosshairCursor() {
    window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
  }

  public static void setToGameCursor() {
    window.setCursor(new Cursor(Cursor.HAND_CURSOR));
  }
}
