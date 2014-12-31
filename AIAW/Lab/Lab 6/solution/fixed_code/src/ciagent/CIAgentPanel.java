package ciagent;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * The <code>CIAgentPanel</code> is part of the GUI for the <code>CIAgent</code>
 * application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 2001
 *
 */
public class CIAgentPanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 2476719357301334234L;
JLabel jLabel1 = new JLabel();
  JTextField nameTextField = new JTextField();
  JLabel jLabel2 = new JLabel();
  JLabel stateLabel = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField sleepTimeTextField = new JTextField();
  JTextField asyncTimeTextField = new JTextField();
  JToggleButton startStopToggleButton = new JToggleButton();
  JToggleButton suspendResumeToggleButton = new JToggleButton();


  /**
   * Creates a <code>CIAgentPanel</code>.
   */
  public CIAgentPanel() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Initializes this panel.
   *
   * @throws Exception if an error occurs while initializing the panel
   */
  private void jbInit() throws Exception {
    jLabel1.setText("Name");
    jLabel1.setBounds(new Rectangle(27, 18, 106, 17));
    this.setLayout(null);
    nameTextField.setBounds(new Rectangle(183, 17, 146, 21));
    jLabel2.setText("State");
    jLabel2.setBounds(new Rectangle(26, 111, 41, 17));
    stateLabel.setText("Uninitiated");
    stateLabel.setBounds(new Rectangle(184, 115, 140, 17));
    jLabel4.setText("Sleep time");
    jLabel4.setBounds(new Rectangle(25, 47, 101, 17));
    jLabel5.setText("Asynch time");
    jLabel5.setBounds(new Rectangle(26, 83, 80, 17));
    sleepTimeTextField.setBounds(new Rectangle(184, 50, 146, 21));
    asyncTimeTextField.setBounds(new Rectangle(185, 81, 145, 21));
    startStopToggleButton.setText("Start");
    startStopToggleButton.setBounds(new Rectangle(25, 150, 115, 25));
    startStopToggleButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        startStopToggleButton_actionPerformed(e);
      }
    });
    suspendResumeToggleButton.setText("Suspend");
    suspendResumeToggleButton.setBounds(new Rectangle(186, 149, 115, 25));
    suspendResumeToggleButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        suspendResumeToggleButton_actionPerformed(e);
      }
    });
    this.add(jLabel1, null);
    this.add(sleepTimeTextField, null);
    this.add(jLabel5, null);
    this.add(jLabel4, null);
    this.add(startStopToggleButton, null);
    this.add(jLabel2, null);
    this.add(stateLabel, null);
    this.add(suspendResumeToggleButton, null);
    this.add(asyncTimeTextField, null);
    this.add(nameTextField, null);
  }


  /**
   * Performs an action when the start/stop toggle button triggers an event.
   *
   * @param e the ActionEvent that triggers the action
   */

  void startStopToggleButton_actionPerformed(ActionEvent e) {}


  /**
   * Performs an action when the suspend/resume button triggers an event.
   *
   * @param e the ActionEvent that triggers the action
   */

  void suspendResumeToggleButton_actionPerformed(ActionEvent e) {}


  /**
   * Uses input fields to set data in the specified <code>CIAgent</code> object.
   *
   * @param agent the CIAgent object in which data is set
   */
  public void setDataOnBean(CIAgent agent) {
    agent.setName(nameTextField.getText().trim());
    agent.setSleepTime(Integer.parseInt(sleepTimeTextField.getText().trim()));
    agent.setAsyncTime(Integer.parseInt(asyncTimeTextField.getText().trim()));
  }


  /**
   * Sets text fields based on data in the specified <code>CIAgent</code> object.
   *
   * @param agent the CIAgent object from which data is retrieved
   */
  public void getDataFromBean(CIAgent agent) {
    nameTextField.setText(agent.getName());
    sleepTimeTextField.setText(String.valueOf(agent.getSleepTime()));
    asyncTimeTextField.setText(String.valueOf(agent.getAsyncTime()));
    stateLabel.setText(agent.getState().toString());
  }
}
