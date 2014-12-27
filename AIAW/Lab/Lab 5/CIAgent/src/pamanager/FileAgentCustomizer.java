package pamanager;

import java.util.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;
import java.awt.event.*;
import ciagent.*;


/**
 * The <code>FileAgentCustomizer</code> class implements the
 * customizer for the file agent class.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class FileAgentCustomizer extends JDialog implements Customizer {
  JPanel panel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField fileTextField = new JTextField();
  JLabel jLabel3 = new JLabel();
  JComboBox conditionComboBox = new JComboBox();
  JButton browseButton = new JButton();
  JLabel jLabel4 = new JLabel();
  JTextField thresholdTextField = new JTextField();
  JLabel jLabel5 = new JLabel();
  JComboBox agentsComboBox = new JComboBox();
  JLabel jLabel6 = new JLabel();
  JTextField messageTextField = new JTextField();
  JButton initializeButton = new JButton();
  JButton cancelButton = new JButton();
  JTextField nameTextField = new JTextField();
  ButtonGroup buttonGroup = new ButtonGroup();
  boolean cancelled = true;
  protected FileAgent agent;
  JRadioButton alertRadioButton = new JRadioButton();
  JRadioButton executeRadioButton = new JRadioButton();
  JRadioButton notifyRadioButton = new JRadioButton();
  JTextField commandTextField = new JTextField();
  JTextField actionTextField = new JTextField();
  JLabel jLabel7 = new JLabel();


  /**
   * Retrieves the file agent.
   *
   * @return the FileAgent object
   *
   */
  FileAgent getAgent() {
    return agent;
  }


  /**
   * Creates a <code>FileAgentCustomizer</code> object using the given
   * frame, title, and modal setting.
   *
   * @param frame the Frame object for this customizer
   * @param title the String object that contains the title for this
   *              customizer
   * @param modal the boolean flag that indicates modality
   *
   */
  public FileAgentCustomizer(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates a <code>FileAgentCustomizer</code> object.
   *
   */
  public FileAgentCustomizer() {
    this(null, "FileAgent Customizer", false);
  }


  /**
   * Sets the object to be customized.
   *
   * @param obj the Object (FileAgent) object to be customized
   */
  public void setObject(Object obj) {
    agent = (FileAgent) obj;
    getDataFromBean();

    // only allow user to change the name on the first try
    if (!(agent.getState().getState() == CIAgentState.UNINITIATED)) {
      nameTextField.setEnabled(false);  // show user they can't edit the name
    }
  }


  /**
   * Initializes the customizer.
   *
   * @throws Exception if any errors occur during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setPreferredSize(new Dimension(400, 425));
    panel1.setLayout(null);
    jLabel1.setText("Name");
    jLabel1.setBounds(new Rectangle(31, 25, 84, 17));
    jLabel2.setText("File or Directory");
    jLabel2.setBounds(new Rectangle(31, 62, 104, 17));
    fileTextField.setBounds(new Rectangle(30, 89, 350, 21));
    jLabel3.setText("Condition");
    jLabel3.setBounds(new Rectangle(32, 122, 103, 17));
    conditionComboBox.setBounds(new Rectangle(32, 145, 161, 24));
    conditionComboBox.addItem("Modified");
    conditionComboBox.addItem("Deleted");
    conditionComboBox.addItem("Over threshold");
    browseButton.setText("Browse...");
    browseButton.setBounds(new Rectangle(185, 57, 103, 27));
    browseButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseButton_actionPerformed(e);
      }
    });
    jLabel4.setText("Threshold");
    jLabel4.setBounds(new Rectangle(237, 125, 109, 17));
    thresholdTextField.setBounds(new Rectangle(237, 148, 141, 21));
    jLabel5.setText("Action");
    jLabel5.setBounds(new Rectangle(26, 187, 138, 17));
    agentsComboBox.setBounds(new Rectangle(187, 296, 195, 24));
    jLabel6.setText("Parameters");
    jLabel6.setBounds(new Rectangle(193, 187, 104, 17));
    messageTextField.setBounds(new Rectangle(186, 218, 194, 21));
    initializeButton.setText("Initialize");
    initializeButton.setBounds(new Rectangle(84, 376, 79, 27));
    initializeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        initializeButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.setBounds(new Rectangle(211, 376, 79, 27));
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    nameTextField.setText("Watch");
    nameTextField.setBounds(new Rectangle(184, 24, 182, 21));
    alertRadioButton.setText("Signal an Alert");
    alertRadioButton.setBounds(new Rectangle(27, 220, 145, 25));
    executeRadioButton.setText("Execute a command");
    executeRadioButton.setBounds(new Rectangle(24, 259, 163, 25));
    notifyRadioButton.setText("Notify an agent");
    notifyRadioButton.setBounds(new Rectangle(24, 291, 139, 25));
    commandTextField.setBounds(new Rectangle(186, 259, 195, 26));
    actionTextField.setText("notify");
    actionTextField.setBounds(new Rectangle(188, 329, 194, 25));
    jLabel7.setText("Action string");
    jLabel7.setBounds(new Rectangle(69, 334, 84, 17));
    buttonGroup.add(alertRadioButton);
    buttonGroup.add(executeRadioButton);
    buttonGroup.add(notifyRadioButton);
    alertRadioButton.setSelected(true);
    getContentPane().add(panel1);
    panel1.add(fileTextField, null);
    panel1.add(jLabel4, null);
    panel1.add(thresholdTextField, null);
    panel1.add(jLabel6, null);
    panel1.add(alertRadioButton, null);
    panel1.add(executeRadioButton, null);
    panel1.add(notifyRadioButton, null);
    panel1.add(messageTextField, null);
    panel1.add(commandTextField, null);
    panel1.add(agentsComboBox, null);
    panel1.add(jLabel5, null);
    panel1.add(actionTextField, null);
    panel1.add(jLabel7, null);
    panel1.add(initializeButton, null);
    panel1.add(cancelButton, null);
    panel1.add(conditionComboBox, null);
    panel1.add(jLabel3, null);
    panel1.add(jLabel1, null);
    panel1.add(nameTextField, null);
    panel1.add(browseButton, null);
    panel1.add(jLabel2, null);
  }


  /**
   * Performs the action when the OK button is pressed.
   *
   * @param e the ActionEvent object generated when the OK button is pressed
   *
   */
  void initializeButton_actionPerformed(ActionEvent e) {
    setDataOnBean();     // write data to underlying bean
    agent.initialize();  // (re)-initialize the agent
    cancelled = false;
    dispose();
  }


  /**
   * Performs the action when the cancel button is pressed.
   *
   * @param e the ActionEvent object generated when the cancel button is pressed
   *
   */
  void cancelButton_actionPerformed(ActionEvent e) {
    cancelled = true;
    dispose();
  }


  /**
   * Indicates whether the customizer has been cancelled
   *
   * @return the boolean <code>true</code> if the customizer has been
   *         cancelled; otherwise returns <code>false</code>
   *
   */
  public boolean isCancelled() {
    return cancelled;
  }


  /**
   * Puts up a file selection dialog when the browse button is pressed.
   *
   * @param e the ActionEvent object generated when the browse button is pressed
   *
   */
  void browseButton_actionPerformed(ActionEvent e) {
    FileDialog dlg = new FileDialog((Frame) this.getParent(), "Select File or Directory", FileDialog.LOAD);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();

    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
    String dir = dlg.getDirectory();
    String fName = dlg.getFile();
    if ((dir != null) && (fName != null)) {
      fileTextField.setText(dir + fName);
    }
  }


  /**
   *  Gets the properties data from bean and sets the GUI controls.
   */
  public void getDataFromBean() {
    nameTextField.setText(agent.getName());
    fileTextField.setText(agent.getFileName());
    conditionComboBox.setSelectedIndex(agent.getCondition());
    thresholdTextField.setText(String.valueOf(agent.getThreshold()));
    int action = agent.getAction();

    switch (action) {
      case FileAgent.ALERT :
        alertRadioButton.setSelected(true);
        break;
      case FileAgent.EXECUTE :
        executeRadioButton.setSelected(true);
        break;
      case FileAgent.EVENT :
        notifyRadioButton.setSelected(true);
        break;
    }
    messageTextField.setText(agent.getParms());
    Vector agents = agent.getAgents();

    if (agents != null) {  // should always be at least one
      for (int i = 0; i < agents.size(); i++) {
        CIAgent lclAgent = (CIAgent) agents.elementAt(i);
        agentsComboBox.addItem(lclAgent.getName());
      }
    }
  }


  /**
   *  Sets properties on the bean using data from the GUI controls.
   */
  public void setDataOnBean() {
    if (nameTextField.isEnabled()) {
      String name = nameTextField.getText();

      agent.setName(name);
    }
    String fileOrDirName = fileTextField.getText();

    agent.setFileName(fileOrDirName);
    int cond = conditionComboBox.getSelectedIndex();

    agent.setCondition(cond);
    String threshold = thresholdTextField.getText();

    if (threshold.length() == 0) {
      threshold = "0";
    }
    agent.setThreshold(new Integer(threshold).intValue());
    int action = -1;
    String parms = "";

    if (alertRadioButton.isSelected()) {
      action = FileAgent.ALERT;
      parms = messageTextField.getText().trim();
    } else if (executeRadioButton.isSelected()) {
      action = FileAgent.EXECUTE;
      parms = commandTextField.getText().trim();
    } else if (notifyRadioButton.isSelected()) {
      action = FileAgent.EVENT;
      agent.setActionString(actionTextField.getText().trim());
      // need to get selected agent and pass to agent listener
      String agentName = (String)agentsComboBox.getSelectedItem() ;
      if (agentName != null) {
        CIAgent listenerAgent = agent.getAgent(agentName) ;
        agent.addCIAgentEventListener(listenerAgent);
      }
    }
    agent.setAction(action);
    agent.setParms(parms);
  }

}
