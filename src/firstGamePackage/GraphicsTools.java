package firstGamePackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class GraphicsTools {

  public static Image makeImage(String path) {

    InputStream stream;
    try {
      stream = GamePanel.class.getResourceAsStream(path);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    Image img;
    // load IMage from stream
    try {
      img = ImageIO.read(stream);
    } catch (Exception e) {
      e.printStackTrace();
      return makeFailImage();
    }
    return img;
  }

  private static Image makeFailImage() {
    BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
    Graphics g = img.getGraphics();
    g.setColor(Color.ORANGE);
    g.fillRect(0, 0, 50, 50);
    g.dispose();
    return img;
  }

  public static boolean isColliding(AbstrGameObject obj1,
      AbstrGameObject obj2) {

    Rectangle r1 = new Rectangle(obj1.x, obj1.y, obj1.width, obj1.height);
    Rectangle r2 = new Rectangle(obj2.x, obj2.y, obj2.width, obj2.height);
    return r1.intersects(r2);
  }

  public static Image[] makeExploPics(String directory) {
    File dir;
    Image[] imgs;
    try {
      dir = new File(GamePanel.class.getResource(directory).toURI());
      imgs = new Image[dir.listFiles().length];

      for (int i = 0; i < dir.listFiles().length; i++) {
        imgs[i] = ImageIO.read(dir.listFiles()[i]);
      }
      return imgs;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  public static void checkBounds(AbstrGameObject obj) {

    if (obj.x < 0) {
      obj.x = 0;
    } else if ((obj.x + obj.width) > GamePanel.x_max) {
      obj.x = GamePanel.x_max - obj.width;
    }

    if (obj.y < 0) {
      obj.y = 0;
    } else if (obj.y + obj.height > GamePanel.y_max) {
      obj.y = GamePanel.y_max - obj.height;
    }
  }

  public static Direction getDirectionFromAngle(double theta) {
    return new Direction(Math.cos(theta), Math.sin(theta));
  }

  public static boolean isInBounds(AbstrGameObject obj) {
    return (obj.x >= 0 && obj.y >= 0 && obj.x <= GamePanel.x_max
        && obj.y <= GamePanel.y_max);
  }

  public static void flipOverGameObjPosition(AbstrGameObject obj) {
    if (obj.x < 0)
      obj.x = GamePanel.x_max;
    if (obj.y < 0)
      obj.y = GamePanel.y_max;
    if (obj.y > GamePanel.y_max)
      obj.y = 0;
    if (obj.x > GamePanel.x_max)
      obj.x = 0;
  }

  public static boolean outOfPanel(AbstrGameObject obj) {
    if (obj.x < 0 || obj.y < 0) {
      return true;
    } else if (obj.x > GamePanel.x_max || obj.y > GamePanel.y_max) {
      return true;
    } else {
      return false;
    }
  }

  public static Direction adjustDirectionToTarget(AbstrGameObject obj,
      AbstrGameObject target) {
    double dx = target.x - obj.x;
    double dy = target.y - obj.y;
    double hyp = Math.sqrt(dx * dx + dy * dy);
    // when hypotenus is small, rocket has hit target!
    if (hyp < 0.001) {
      return null;
    } else {
      double sin = dy / hyp;
      double cos = dx / hyp;
      double newTheta = Math.atan(sin / cos);
      
      if (obj.ROT_SPEED != 0) {
        if (obj.theta - newTheta > obj.ROT_SPEED) {
          newTheta = obj.theta - obj.ROT_SPEED;
        } else if (newTheta - obj.theta > obj.ROT_SPEED) {
          newTheta = obj.theta + obj.ROT_SPEED;
        }
      }
      obj.theta = newTheta;
      return getDirectionFromAngle(newTheta);
    }
  }

}
