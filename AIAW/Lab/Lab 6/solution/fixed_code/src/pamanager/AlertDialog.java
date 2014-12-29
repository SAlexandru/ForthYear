package pamanager;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * The <code>AlertDialog</code> class implements the Alert dialog used to
 * display alert information.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class AlertDialog extends JDialog {
  /**
	 * 
	 */
	private static final long serialVersionUID = 8690433258903695744L;
JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton closeButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea msgTextArea = new JTextArea();
  FlowLayout flowLayout1 = new FlowLayout();

  /**
   * Creates a <code>AlertDialog</code> object.
   *
   * @param frame the Frame object for this dialog
   * @param title the String object that contains the title of this dialog
   * @param modal the boolean flag that indicates modality
   *
   */
  public AlertDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates a <code>AlertDialog</code> object.
   *
   */
  public AlertDialog() {
    this(null, "", false);
  }


  /**
   * Initializes this Alert dialog.
   *
   * @throws Exception if any errors occur during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    closeButton.setText("Close");
    closeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeButton_actionPerformed(e);
      }
    });
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setHgap(30);
    jScrollPane1.setAutoscrolls(true);
    jScrollPane1.setPreferredSize(new Dimension(400,250)) ;
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(closeButton, null);
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(msgTextArea, null);
  }


  /**
   * Performs the action for the OK button.
   *
   * @param e the ActionEvent object that contains the event generated
   *          when the OK button is pressed
   *
   */
  void closeButton_actionPerformed(ActionEvent e) {
    dispose();
  }


  /**
   * Sets the message text in the text area.
   *
   * @param text the String object that contains the message text
   *
   */
  public void setMsgText(String text) {
    msgTextArea.setText(text);
    msgTextArea.setCaretPosition(0);
  }


  /**
   * Appends the message text to the text area.
   *
   * @param text the String object that contains the text message
   *
   */
  public void appendMsgText(String text) {
    msgTextArea.append("\n" + text);
    msgTextArea.setCaretPosition(msgTextArea.getText().length());
  }


  /**
   * Retrieves the message text in the text area.
   *
   * @return the String object that contains the message text
   *
   */
  public String getMsgText() {
    return msgTextArea.getText();
  }
}
