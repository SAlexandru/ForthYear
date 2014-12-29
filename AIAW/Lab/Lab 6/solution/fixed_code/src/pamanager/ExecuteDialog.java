package pamanager;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * The <code>ExecuteDialog</code> class implements the dialog used when
 * executing a command.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class ExecuteDialog extends JDialog {
  /**
	 * 
	 */
	private static final long serialVersionUID = -3223728596863136217L;
JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea traceTextArea = new JTextArea();
  JButton oKButton = new JButton();
  JButton cancelButton = new JButton();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  boolean cancel = false;


  /**
   * Creates a <code>ExecuteDialog</code> object with the given frame,
   * title, and modal setting.
   *
   * @param frame the Frame object for this dialog
   * @param title the String object that contains the title for this dialog
   * @param modal the boolean flag that indicates modality
   *
   */
  public ExecuteDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates a <code>ExecuteDialog</code> object.
   *
   */
  public ExecuteDialog() {
    this(null, "", false);
  }


  /**
   * Initializes this execute dialog.
   *
   * @throws Exception if any errors occur during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    oKButton.setText("OK");
    oKButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        oKButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    jPanel2.setLayout(borderLayout2);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setHgap(25);
    jPanel2.setMinimumSize(new Dimension(300, 300));
    traceTextArea.setPreferredSize(new Dimension(200, 100));
    traceTextArea.setMinimumSize(new Dimension(200, 100));
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(oKButton, null);
    jPanel1.add(cancelButton, null);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(traceTextArea, null);
  }


  /**
   * Performs the action when the cancel button is pressed.
   *
   * @param e the ActionEvent object generated when the cancel button is
   *          pressed
   *
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    cancel = true;  // user wants to cancel the alarm or watch
    dispose();
  }


  /**
   * Performs the action when the OK button is pressed.
   *
   * @param e the ActionEvent object generated when the OK button is
   *          pressed
   *
   */
  void oKButton_actionPerformed(ActionEvent e) {
    dispose();
  }


  /**
   * Retrieve the cancel status.
   *
   * @return the boolean <code>true</code> if the user wants to cancel or
   *         <code>false</code> if the user does not want to cancel
   *
   */
  boolean getCancel() {
    return cancel;
  }


  /**
   * Retrieves the text area for this dialog.
   *
   * @return the JTextArea object
   *
   */
  JTextArea getTextArea() {
    return traceTextArea;
  }
}
