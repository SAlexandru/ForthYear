package pamanager;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import ciagent.*;


/**
 * The <code>UserNotificationAgentCustomizer</code> class the customizer for
 * the user notification agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class UserNotificationAgentCustomizer extends JDialog implements Customizer {
  JPanel panel1 = new JPanel();
  JButton initializeButton = new JButton();
  JButton cancelButton = new JButton();
  JLabel jLabel1 = new JLabel();
  ButtonGroup buttonGroup = new ButtonGroup();
  JTextField nameTextField = new JTextField();
  boolean cancelled = true;
  protected UserNotificationAgent agent;
  ButtonGroup actionButtonGroup = new ButtonGroup();


  /**
   * Creates a <code>UserNotificationAgentCustomizer</code> object.
   *
   * @param frame the Frame object for this customizer
   * @param title the String object that contains the title for this customizer
   * @param modal the boolean flag that indicates modality
   *
   */
  public UserNotificationAgentCustomizer(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates a <code>UserNotificationAgentCustomizer</code> object.
   *
   */
  public UserNotificationAgentCustomizer() {
    this(null, "UserNotificationAgent Customizer", false);
  }


  /**
   * Retrieves the agent for this customizer.
   *
   * @return the UserNotificationAgent object
   *
   */
  UserNotificationAgent getAgent() {
    return agent;
  }


  /**
   * Sets the object to be customized.
   *
   * @param obj the Object to be customized
   */
  public void setObject(Object obj) {
    agent = (UserNotificationAgent) obj;
    getDataFromBean();

    // only allow user to change the name on the first try
    if (!(agent.getState().getState() == CIAgentState.UNINITIATED)) {
      nameTextField.setEnabled(false);  // show user they can't edit the name
    }
  }


  /**
   * Initializes the GUI controls.
   *
   * @throws Exception if any error occurs during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setPreferredSize(new Dimension(400, 150));
    panel1.setLayout(null);
    initializeButton.setText("Initialize");
    initializeButton.setBounds(new Rectangle(79, 100, 79, 27));
    initializeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        initializeButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.setBounds(new Rectangle(213, 100, 79, 27));
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    jLabel1.setText("Name");
    jLabel1.setBounds(new Rectangle(35, 26, 95, 17));
    nameTextField.setText("Alarm");
    nameTextField.setBounds(new Rectangle(162, 22, 168, 21));
    getContentPane().add(panel1);
    panel1.add(jLabel1, null);
    panel1.add(nameTextField, null);
    panel1.add(initializeButton, null);
    panel1.add(cancelButton, null);
  }


  /**
   * Initializes the agent.
   *
   * @param e the ActionEvent object generated when the initialize button
   *          is pressed
   *
   */
  void initializeButton_actionPerformed(ActionEvent e) {
    setDataOnBean();
    agent.initialize();  // (re)-initialize the agent
    cancelled = false;
    dispose();
  }


  /**
   * Cancels the customizer.
   *
   * @param e the ActionEvent object generated when the cancel button
   *          is pressed
   *
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    cancelled = true;
    dispose();
  }


  /**
   * Inidicates whether the customizer has been cancelled.
   *
   * @return the boolean true if the customizer was cancelled
   *
   */
  public boolean isCancelled() {
    return cancelled;
  }


  /**
   * Gets data from the bean and sets the GUI controls.
   */
  public void getDataFromBean() {
    nameTextField.setText(agent.getName());
  }


  /**
   * Set properties on the agent bean, taking data from GUI.
   */
  public void setDataOnBean() {
    if (nameTextField.isEnabled()) {
      String name = nameTextField.getText();

      agent.setName(name);
    }
  }
}
