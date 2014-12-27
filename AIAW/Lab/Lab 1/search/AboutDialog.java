package search;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * The <code>AboutDialog</code> class implements the About dialog for the search
 * application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class AboutDialog extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JButton OKButton = new JButton();


  /**
   * Creates an <code>AboutDialog</code> object with the given frame,
   * title, and modal setting.
   *
   * @param frame the Frame object for this dialog
   * @param title the String object that contains the title of this dialog
   * @param modal the boolean modal flag setting
   *
   */
  public AboutDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates an <code>AboutDialog</code> object.
   *
   */
  public AboutDialog() {
    this(null, "", false);
  }


  /**
   * Initializes the dialog settings.
   *
   * @throws Exception if any errors occur
   *
   */
  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    panel1.setMinimumSize(new Dimension(420, 250));
    panel1.setPreferredSize(new Dimension(420, 250));
    jPanel2.setLayout(null);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("");
    jLabel1.setBounds(new Rectangle(1, 10, 400, 22));
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setText("Constructing Intelligent Agents Using Java");
    jLabel2.setBounds(new Rectangle(2, 27, 398, 38));
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setText("John Wiley & Sons, Inc. ");
    jLabel3.setBounds(new Rectangle(0, 66, 401, 22));
    jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel4.setText("Technical support: support@bigusbooks.com");
    jLabel4.setBounds(new Rectangle(2, 106, 400, 26));
    jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel5.setText("(c) Copyright Joseph P. Bigus and Jennifer Bigus, 1997, 2000");
    jLabel5.setBounds(new Rectangle(1, 139, 398, 48));
    OKButton.setText("OK");
    OKButton.setBounds(new Rectangle(155, 207, 79, 27));
    OKButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OKButton_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1, null);
    jPanel2.add(OKButton, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(jLabel5, null);
  }


  /**
   * Performs the action for the OK button
   *
   * @param e the ActionEvent object generated for the OK button
   *
   */
  void OKButton_actionPerformed(ActionEvent e) {
    dispose();  // close the dialog
  }
}
