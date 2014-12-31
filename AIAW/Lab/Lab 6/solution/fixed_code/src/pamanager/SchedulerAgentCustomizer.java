package pamanager;

import java.awt.*;
import java.beans.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;

import ciagent.*;


/**
 * The <code>SchedulerAgentCustomizer</code> class implements the
 * customizer for the scheduler agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class SchedulerAgentCustomizer extends JDialog implements Customizer {
  /**
	 * 
	 */
	private static final long serialVersionUID = -151175547508611657L;
JPanel panel1 = new JPanel();
  JButton initializeButton = new JButton();
  JButton cancelButton = new JButton();
  JLabel jLabel1 = new JLabel();
  JRadioButton oneTimeRadioButton = new JRadioButton();
  JRadioButton repeatingRadioButton = new JRadioButton();
  ButtonGroup buttonGroup = new ButtonGroup();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField nameTextField = new JTextField();
  JTextField timeTextField = new JTextField();
  JTextField intervalTextField = new JTextField();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JComboBox<String> agentsComboBox = new JComboBox<String>();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  boolean cancelled = true;
  protected SchedulerAgent agent;
  JRadioButton notifyRadioButton = new JRadioButton();
  ButtonGroup actionButtonGroup = new ButtonGroup();
  JLabel jLabel8 = new JLabel();
  JTextField actionTextField = new JTextField();


  /**
   * Creates a <code>SchedulerAgentCustomizer</code> object.
   *
   * @param frame the Frame object for this customizer
   * @param title the String object that contains the title for this
   *              customizer
   * @param modal the boolean flag that indicates the modality
   *
   */
  public SchedulerAgentCustomizer(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates a <code>SchedulerAgentCustomizer</code> object.
   *
   */
  public SchedulerAgentCustomizer() {
    this(null, "SchedulerAgent Customizer", false);
  }


  /**
   * Retrieves the scheduler agent for this customizer.
   *
   * @return the SchedulerAgent object
   *
   */
  SchedulerAgent getAgent() {
    return agent;
  }


  /**
   * Sets the object to customize.
   *
   * @param obj the Object to be customized
   */
  public void setObject(Object obj) {
    agent = (SchedulerAgent) obj;
    getDataFromBean();

    // only allow user to change the name on the first try
    if (!(agent.getState().getState() == CIAgentState.UNINITIATED)) {
      nameTextField.setEnabled(false);  // show user they can't edit the name
    }
  }


  /**
   * Initializes the GUI for the customizer.
   *
   * @throws Exception if any error occurs during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setPreferredSize(new Dimension(400, 400));
    panel1.setLayout(null);
    initializeButton.setText("Initialize");
    initializeButton.setBounds(new Rectangle(79, 365, 79, 27));
    initializeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        initializeButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.setBounds(new Rectangle(213, 366, 79, 27));
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    jLabel1.setText("Name");
    jLabel1.setBounds(new Rectangle(35, 26, 95, 17));
    oneTimeRadioButton.setText("One time");
    oneTimeRadioButton.setBounds(new Rectangle(27, 82, 103, 25));
    repeatingRadioButton.setText("Repeating");
    repeatingRadioButton.setBounds(new Rectangle(26, 132, 103, 25));
    notifyRadioButton.setText("Notify an agent");
    notifyRadioButton.setBounds(new Rectangle(28, 229, 130, 25));
    jLabel8.setText("Action string");
    jLabel8.setBounds(new Rectangle(55, 276, 98, 17));
    actionTextField.setText("notify");
    actionTextField.setBounds(new Rectangle(173, 273, 192, 25));
    buttonGroup.add(oneTimeRadioButton);
    buttonGroup.add(repeatingRadioButton);
    actionButtonGroup.add(notifyRadioButton);
    jLabel2.setText("Time");
    jLabel2.setBounds(new Rectangle(129, 85, 41, 17));
    jLabel3.setText("Interval");
    jLabel3.setBounds(new Rectangle(130, 137, 41, 17));
    nameTextField.setText("Alarm");
    nameTextField.setBounds(new Rectangle(162, 22, 168, 21));
    timeTextField.setText("12:00");
    timeTextField.setBounds(new Rectangle(191, 78, 105, 21));
    intervalTextField.setText("60");
    intervalTextField.setBounds(new Rectangle(191, 135, 108, 21));
    jLabel4.setText("Action");
    jLabel4.setBounds(new Rectangle(48, 200, 41, 17));
    jLabel5.setText("Parameters");
    jLabel5.setBounds(new Rectangle(196, 193, 82, 26));
    agentsComboBox.setBounds(new Rectangle(171, 229, 194, 24));
    jLabel6.setText("hh:mm");
    jLabel6.setBounds(new Rectangle(315, 84, 41, 17));
    jLabel7.setText("seconds");
    jLabel7.setBounds(new Rectangle(312, 138, 67, 17));
    getContentPane().add(panel1);
    panel1.add(jLabel1, null);
    panel1.add(nameTextField, null);
    panel1.add(oneTimeRadioButton, null);
    panel1.add(timeTextField, null);
    panel1.add(jLabel2, null);
    panel1.add(jLabel6, null);
    panel1.add(repeatingRadioButton, null);
    panel1.add(jLabel3, null);
    panel1.add(intervalTextField, null);
    panel1.add(jLabel7, null);
    panel1.add(jLabel4, null);
    panel1.add(jLabel5, null);
    panel1.add(initializeButton, null);
    panel1.add(cancelButton, null);
    panel1.add(agentsComboBox, null);
    panel1.add(actionTextField, null);
    panel1.add(jLabel8, null);
    panel1.add(notifyRadioButton, null);
  }


  /**
   * Initializes the agent when the initialize button is pressed.
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
   * @param e the ActionEvent object generated when the cancel button is
   *          pressed
   *
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    cancelled = true;
    dispose();
  }


  /**
   * Indicates whether the customizer was cancelled.
   *
   * @return the boolean flag that indicates whether the customizer was
   *         cancelled
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
    int interval = agent.getInterval();

    if (interval == 0) {  // must one one-time alarm
      timeTextField.setText(agent.getTime().toString());
      oneTimeRadioButton.setSelected(true);
    } else {

      // interval
      repeatingRadioButton.setSelected(true);
      intervalTextField.setText(String.valueOf(interval));
    }

    notifyRadioButton.setSelected(true);
    actionTextField.setText(agent.getActionString());

    Vector<?> agents = agent.getAgents();

    if (agents != null) {  // should always be at least one
      for (int i = 0; i < agents.size(); i++) {
        CIAgent lclAgent = (CIAgent) agents.elementAt(i);

        agentsComboBox.addItem(lclAgent.getName());
      }
    }
  }


  /**
   *  Sets properties on the agent bean, taking data from GUI.
   */
  public void setDataOnBean() {
    if (nameTextField.isEnabled()) {
      String name = nameTextField.getText();

      agent.setName(name);
    }
    if (oneTimeRadioButton.isSelected()) {

      // one time
      String time = timeTextField.getText();
      StringTokenizer tok = new StringTokenizer(time, ":");
      int hour = new Integer(tok.nextToken()).intValue();
      int min = new Integer(tok.nextToken()).intValue();
      Calendar now = Calendar.getInstance();

      now.set(Calendar.HOUR, hour);
      now.set(Calendar.MINUTE, min);
      agent.setTime(now.getTime());  // pass Date object
    } else {

      // interval
      String interval = intervalTextField.getText();

      agent.setInterval(new Integer(interval).intValue());
    }

     if (notifyRadioButton.isSelected()) {
      agent.setActionString(actionTextField.getText().trim());
      // need to get selected agent and pass to agent listener
      String agentName = (String)agentsComboBox.getSelectedItem() ;
      if (agentName != null) {
        CIAgent listenerAgent = agent.getAgent(agentName) ;
        agent.addCIAgentEventListener(listenerAgent);
      }
    }
  }
}
