package pamanager;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;
import java.beans.*;

import ciagent.*;


/**
 * The <code>AirfareAgentCustomizer</code> class implements the
 * customizer for the AirfareAgent.
 * intelligent agent classes.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class AirfareAgentCustomizer extends JDialog implements Customizer, CIAgentEventListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 6552871627669114499L;
JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton initializeButton = new JButton();
  JButton CancelButton = new JButton();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JComboBox<String> destCityComboBox = new JComboBox<String>();
  JLabel jLabel2 = new JLabel();
  JComboBox<String> departCityComboBox = new JComboBox<String>();
  JLabel jLabel3 = new JLabel();
  AirfareAgent agent;  // the agent bean we are customizing
  JLabel jLabel4 = new JLabel();
  JTextField nameTextField = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JComboBox<String> departMonthComboBox = new JComboBox<String>();
  JComboBox<String> returnMonthComboBox = new JComboBox<String>();
  JComboBox<String> departDayComboBox = new JComboBox<String>();
  JComboBox<String> returnDayComboBox = new JComboBox<String>();
  Vector<String> months = new Vector<String>();
  Vector<?> cities = new Vector<Object>();
  boolean cancelled = true;

  Hashtable<String, String> citiesToAirports = new Hashtable<String, String>() ;
  Hashtable<String, String> airportsToCities = new Hashtable<String, String>();
  Hashtable<String, String> abbrevToMonths   = new Hashtable<String, String>() ;
  JRadioButton notifyRadioButton = new JRadioButton();
  JComboBox<String> agentsComboBox = new JComboBox<String>();
  JLabel jLabel7 = new JLabel();
  JTextField actionTextField = new JTextField();

  /**
   * Creates an <code>AirfareAgentCustomizer</code> object.
   *
   * @param frame the Frame object for this customizer
   * @param title the String object that contains the title of this
   *        customizer
   * @param modal the boolean flag indicating the modality
   *
   */
  public AirfareAgentCustomizer(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      init();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates an <code>AirfareAgentCustomizer</code> object.
   *
   */
  public AirfareAgentCustomizer() {
    this(null, "AirfareAgent Customizer", false);
  }


  /**
   *  Sets the object to customize.
   *
   * @param obj the Object to be customized
   */
  public void setObject(Object obj) {
    agent = (AirfareAgent) obj;
    getDataFromBean();
    agent.addCIAgentEventListener(this);

    // only allow user to change the name on the first try
    if (!(agent.getState().getState() == CIAgentState.UNINITIATED)) {
      nameTextField.setEnabled(false);  // show user they can't edit the name
    }
  }


  /**
   * Initializes the values in this customizer.
   *
   */
  protected void init() {
    months.addElement("January");
    abbrevToMonths.put("JAN", "January") ;
    months.addElement("February");
    abbrevToMonths.put("FEB", "February");
    months.addElement("March");
    abbrevToMonths.put("MAR", "March") ;
    months.addElement("April");
    abbrevToMonths.put("APR", "April");
    months.addElement("May");
    abbrevToMonths.put("MAY", "May") ;
    months.addElement("June");
    abbrevToMonths.put("JUN", "June") ;
    months.addElement("July");
    abbrevToMonths.put("JUL", "July") ;
    months.addElement("August");
    abbrevToMonths.put("AUG", "August") ;
    months.addElement("September");
    abbrevToMonths.put("SEP", "September") ;
    months.addElement("October");
    abbrevToMonths.put("OCT", "October") ;
    months.addElement("November");
    abbrevToMonths.put("NOV", "November") ;
    months.addElement("December");
    abbrevToMonths.put("DEC", "December") ;
    for (int i = 0; i < 12; i++) {
      departMonthComboBox.addItem((String) months.elementAt(i));
      returnMonthComboBox.addItem((String) months.elementAt(i));
    }
    for (int i = 1; i < 32; i++) {
      departDayComboBox.addItem(String.valueOf(i));
      returnDayComboBox.addItem(String.valueOf(i));
    }
    citiesToAirports.put("Chicago, IL", "ORD");
    citiesToAirports.put("Minneapolis, MN", "MSP");
    citiesToAirports.put("Orlando, FL", "MCO" );
    citiesToAirports.put("Philadelphia, PA", "PHL");
    citiesToAirports.put("Rochester, MN", "RST");
    airportsToCities.put("ORD","Chicago, IL");
    airportsToCities.put("MSP","Minneapolis, MN");
    airportsToCities.put("MCO","Orlando, FL");
    airportsToCities.put("PHL","Philadelphia, PA");
    airportsToCities.put("RST","Rochester, MN");
    Enumeration<String> enumeration = citiesToAirports.keys() ;
    while (enumeration.hasMoreElements()) {
      String city = (String)enumeration.nextElement();
      departCityComboBox.addItem(city);
      destCityComboBox.addItem(city);
    }
  }


  /**
   * Initializes the customizer GUI components.
   *
   * @throws Exception if any error occurs during initialization
   *
   */
  void jbInit() throws Exception {
    returnDayComboBox.setBounds(new Rectangle(310, 147, 75, 24));
    departDayComboBox.setBounds(new Rectangle(310, 66, 75, 24));
    returnMonthComboBox.setBounds(new Rectangle(150, 147, 128, 24));
    departMonthComboBox.setBounds(new Rectangle(150, 66, 128, 24));
    jLabel6.setText("Return date:");
    jLabel6.setBounds(new Rectangle(12, 144, 88, 17));
    jLabel5.setText("Departure date:");
    jLabel5.setBounds(new Rectangle(12, 65, 106, 17));
    panel1.setLayout(borderLayout1);
    initializeButton.setText("Initialize");
    initializeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        initializeButton_actionPerformed(e);
      }
    });
    CancelButton.setText("Cancel");
    CancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CancelButton_actionPerformed(e);
      }
    });
    jPanel2.setLayout(null);
    jLabel1.setText("Departure city:");
    jLabel1.setBounds(new Rectangle(12, 103, 143, 17));
    destCityComboBox.setBounds(new Rectangle(150, 187, 230, 25));
    destCityComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        destCityComboBox_actionPerformed(e);
      }
    });
    destCityComboBox.setEditable(true);
    jLabel2.setText("Destination city:");
    jLabel2.setBounds(new Rectangle(12, 188, 107, 17));
    departCityComboBox.setBounds(new Rectangle(150, 102, 230, 25));
    departCityComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        departCityComboBox_actionPerformed(e);
      }
    });
    departCityComboBox.setEditable(true);
    jLabel3.setBounds(new Rectangle(214, 128, 41, 17));
    panel1.setMinimumSize(new Dimension(400, 350));
    panel1.setPreferredSize(new Dimension(400, 350));
    jLabel4.setText("Name");
    jLabel4.setBounds(new Rectangle(12, 19, 41, 17));
    nameTextField.setBounds(new Rectangle(157, 16, 139, 21));
    notifyRadioButton.setText("Notify an agent");
    notifyRadioButton.setBounds(new Rectangle(10, 224, 103, 25));
    agentsComboBox.setBounds(new Rectangle(151, 223, 229, 25));
    jLabel7.setText("Action string");
    jLabel7.setBounds(new Rectangle(45, 258, 98, 14));
    actionTextField.setText("notify");
    actionTextField.setBounds(new Rectangle(153, 254, 226, 22));
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(initializeButton, null);
    jPanel1.add(CancelButton, null);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel3, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(destCityComboBox, null);
    jPanel2.add(departCityComboBox, null);
    jPanel2.add(jLabel5, null);
    jPanel2.add(jLabel6, null);
    jPanel2.add(departMonthComboBox, null);
    jPanel2.add(returnMonthComboBox, null);
    jPanel2.add(departDayComboBox, null);
    jPanel2.add(returnDayComboBox, null);
    jPanel2.add(nameTextField, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(jLabel1, null);
    jPanel2.add(notifyRadioButton, null);
    jPanel2.add(agentsComboBox, null);
    jPanel2.add(actionTextField, null);
    jPanel2.add(jLabel7, null);
  }


  /**
   * Does nothing.
   *
   * @param e the ActionEvent object
   *
   */
  void departCityComboBox_actionPerformed(ActionEvent e) {}


  /**
   * Does nothing.
   *
   * @param e the ActionEvent object
   *
   */
  void destCityComboBox_actionPerformed(ActionEvent e) {}


  /**
   * Initializes the agent with the values set in the customizer.
   *
   * @param e the ActionEvent object generated when the initialize
   *          button was pressed
   *
   */
  void initializeButton_actionPerformed(ActionEvent e) {

    // first get user data from the customizer panel and set values on agent
    setDataOnBean();
    agent.initialize();  // (re)-initialize the agent
    cancelled = false;
    dispose();
  }


  /**
   * Cancels the customizer.
   *
   * @param e the ActionEvent object generated when the cancel button
   *           was pressed
   *
   */
  void CancelButton_actionPerformed(ActionEvent e) {
    cancelled = true;
    dispose();
  }


  /**
   *  Gets data from bean and sets GUI controls.
   */
  public void getDataFromBean() {

    nameTextField.setText(agent.getName()) ;
    String airport = agent.getOrigCity() ;
    String city = (String)airportsToCities.get(airport) ;
    departCityComboBox.setSelectedItem(city);
    airport = agent.getDestCity() ;
    city = (String)airportsToCities.get(airport) ;
    destCityComboBox.setSelectedItem(city);

    String abbrevMonth = agent.getDepartMonth();
    String month = (String)abbrevToMonths.get(abbrevMonth) ;
    departMonthComboBox.setSelectedItem(month);
    departDayComboBox.setSelectedItem(agent.getDepartDay());
    abbrevMonth = agent.getReturnMonth() ;
    month = (String) abbrevToMonths.get(abbrevMonth) ;
    returnMonthComboBox.setSelectedItem(month);
    returnDayComboBox.setSelectedItem(agent.getReturnDay());

     Vector<?> agents = agent.getAgents();

    if (agents != null) {  // should always be at least one
      for (int i = 0; i < agents.size(); i++) {
        CIAgent lclAgent = (CIAgent) agents.elementAt(i);
        agentsComboBox.addItem(lclAgent.getName());
      }
    }

    notifyRadioButton.setSelected(true);
    actionTextField.setText(agent.getActionString());

  }


  /**
   *  Sets properties on the agent bean using data from the GUI.
   */
  public void setDataOnBean() {

    if (nameTextField.isEnabled()) {
        String name = nameTextField.getText() ;
        agent.setName(name) ;
    }
    String city = (String) departCityComboBox.getSelectedItem() ;
    String airport = (String)citiesToAirports.get(city); // return airport
    agent.setOrigCity(airport);
    city = (String) destCityComboBox.getSelectedItem() ;
    airport = (String)citiesToAirports.get(city); // return airport
    agent.setDestCity(airport);

    String month = (String) departMonthComboBox.getSelectedItem();
    String abbrevMonth = month.substring(0,3).toUpperCase() ;
    agent.setDepartMonth(abbrevMonth) ;
    agent.setDepartDay((String) departDayComboBox.getSelectedItem());
    month = (String) returnMonthComboBox.getSelectedItem();
    abbrevMonth = month.substring(0,3).toUpperCase() ;
    agent.setReturnMonth(abbrevMonth);
    agent.setReturnDay((String) returnDayComboBox.getSelectedItem());

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


  /**
   * Processes a CIAgentEvent.
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    event.getSource();
    event.getArgObject();
    Object action = event.getAction();

    if (action != null) {
      if (action.equals("trace")) {

        // ignore trace message from agent ???
      } else if (action.equals("addArticle")) {
        System.out.println("news reader customizer -- article read ");
      }
    }  // if action
  }


  /**
   * Processes a CIAgentEvent (does not queue it).
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void postCIAgentEvent(CIAgentEvent event) {
    processCIAgentEvent(event);  // don't queue, just process
  }

}
