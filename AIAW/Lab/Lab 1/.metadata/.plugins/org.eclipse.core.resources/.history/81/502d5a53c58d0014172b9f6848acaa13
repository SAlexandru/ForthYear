package search;

import javax.swing.UIManager;


/**
 * This is an application that demonstrates the five basic search
 * algorithms: depth-first, breadth-first, iterated, best-first,
 * and genetic.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class SearchApp extends Object {
  boolean packFrame = false;  // indicates whether frame should be packed


  /**
   * Constructs the search application.
   */
  public SearchApp() {
    SearchFrame frame = new SearchFrame();

    // Pack frames that have useful preferred size info. from their
    // layout or validate frames that have preset sizes.
    if (packFrame) {
      frame.pack();
    } else {
      frame.validate();
    }
    frame.setVisible(true);
  }


  /**
   * Runs the search application.
   *
   * @param args  a String array used to pass arguments into the class.
   *              Not used in this application.
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println("Error: unable to set look and feel");
    }

    // Create an instance of the SearchApp
    new SearchApp();
  }
}
