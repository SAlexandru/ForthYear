package pamanager;

import javax.swing.UIManager;
import java.awt.*;


/**
 * The <code>PAManagerApp</code> class implements the personal agent
 * manager application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class PAManagerApp {
  private boolean packFrame = false;

  /**
   * Creates a <code>PAManagerApp</code> object.
   *
   */
  public PAManagerApp() {
    PAManagerFrame frame = new PAManagerFrame();

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
   * Executes the PAManager application.
   *
   * @param args the String[] object that contains the arguments passed to
   *             the application
   *
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {}
    new PAManagerApp();
  }
}
