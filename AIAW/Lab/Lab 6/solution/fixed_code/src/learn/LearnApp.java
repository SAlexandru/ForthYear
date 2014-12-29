package learn;

import javax.swing.UIManager;


/**
 * The <code>LearnApp</code> is an application that demonstrates three learing
 * algorithms: back propagation network, Kohonen map network, and a decision
 * tree.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class LearnApp extends Object {
  boolean packFrame = false;


  /**
   * Creates the learn application.
   */
  public LearnApp() {
    LearnFrame frame = new LearnFrame();

    // Pack frames that have useful preferred size info. from their layout or
    // validate frames that have preset sizes.
    if (packFrame) {
      frame.pack();
    } else {
      frame.validate();
    }
    frame.setVisible(true);
  }


  /**
   * Runs the learn application.
   *
   * @param args  a String array used to pass arguments into the class. Not used
   *              in this application.
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {}
    new LearnApp();
  }
}
