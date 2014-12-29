package infofilter;

import javax.swing.UIManager;
import java.awt.*;


/**
 * The <code>InfoFilterApp</code> class implements the InfoFilter application
 * which uses agents to filter information from web pages and news groups.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class InfoFilterApp {
  boolean packFrame = false;

  /**
   * Creates a <code>InfoFilterApp</code> object.
   *
   */
  public InfoFilterApp() {
    InfoFilterFrame frame = new InfoFilterFrame();

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    } else {
      frame.validate();
    }

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();

    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

   /**
   * Runs the InfoFilter application.
   *
   * @param args the String[] object (not used)
   *
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {}
    new InfoFilterApp();
  }
}
