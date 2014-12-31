package rule;

import javax.swing.UIManager;


/**
 * The <code>RuleApp</code> is an application that demonstrates the different
 * rule bases.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class RuleApp {
  boolean packFrame = false;

  //Construct the application


  /**
   * Creates a <code>RuleApp</code> object.
   *
   */
  public RuleApp() {
    RuleFrame frame = new RuleFrame();

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    } else {
      frame.validate();
    }
    frame.setVisible(true);
  }

  //Main method


  /**
   * Runs the rule application.
   *
   * @param args the String[] object (not used)
   *
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {}
    new RuleApp();
  }
}
