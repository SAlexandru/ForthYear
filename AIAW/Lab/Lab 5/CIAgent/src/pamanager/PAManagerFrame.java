package pamanager;

import java.io.*;
import java.awt.*;
import java.beans.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;
import ciagent.*;


/**
 * The <code>PAManagerFrame</code> class implements the GUI and the logic
 * for the PAManager application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class PAManagerFrame extends JFrame implements CIAgentEventListener, PropertyChangeListener, AgentPlatform {
  JMenuBar menuBar1 = new JMenuBar();
  JMenu fileMenu = new JMenu();
  JMenu editMenu = new JMenu();
  JMenuItem cutMenuItem = new JMenuItem();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea traceTextArea = new JTextArea();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel jLabel2 = new JLabel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JMenuItem clearMenuItem = new JMenuItem();
  JMenuItem exitMenuItem = new JMenuItem();
  JMenu createMenu = new JMenu();
  JMenu helpMenu = new JMenu();
  JMenuItem AboutMenuItem = new JMenuItem();
  Vector agents = new Vector();
  JTable agentTable = new JTable();      // list of alarms and watches
  Hashtable agentClasses = new Hashtable();
  private TableModel agentTableModel = null;
  protected String[] columnNameList = {  COL_NAME, COL_TYPE, COL_STATE, COL_TASK };
  protected Object[][] data = null;
  final static int NUM_COLS = 4;
  final static int COL_NAMEID = 0;
  private final static String COL_NAME = "Name";
  final static int COL_TYPEID = 1;
  private final static String COL_TYPE = "Type";
  final static int COL_STATEID = 2;
  private final static String COL_STATE = "State";
  final static int COL_TASKID = 3;
  private final static String COL_TASK = "Task";
  JMenuItem propertiesMenuItem = new JMenuItem();
  JMenuItem startProcessingMenuItem = new JMenuItem();
  JMenuItem suspendProcessingMenuItem = new JMenuItem();
  JMenuItem resumeProcessingMenuItem = new JMenuItem();


  /**
   * Creates a <code>PAManagerFrame</code> object.
   *
   */
  public PAManagerFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
      readPropertiesFile();  // load the agent classes and display names
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initializes the components of this frame.
   *
   * @throws Exception if any errors occur during initialization
   *
   */
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(400, 400));
    this.setTitle("CIAgent Personal Agent Manager Application");
    fileMenu.setText("File");
    editMenu.setText("Edit");
    cutMenuItem.setText("Cut");
    cutMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Cut_actionPerformed(e);
      }
    });
    jPanel2.setLayout(borderLayout2);
    jLabel1.setText("Activity Log");
    jPanel1.setMinimumSize(new Dimension(550, 200));
    jPanel1.setPreferredSize(new Dimension(550, 200));
    jPanel1.setLayout(borderLayout3);
    jLabel2.setText("CIAgent List");
    clearMenuItem.setText("Clear");
    clearMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearMenuItem_actionPerformed(e);
      }
    });
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exitMenuItem_actionPerformed(e);
      }
    });
    createMenu.setText("Create");
    helpMenu.setText("Help");
    AboutMenuItem.setText("About");
    AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutMenuItem_actionPerformed(e);
      }
    });
    agentTable.setPreferredSize(new Dimension(550, 400));
    jScrollPane2.setPreferredSize(new Dimension(500, 400));
    propertiesMenuItem.setText("Properties...");
    propertiesMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        propertiesMenuItem_actionPerformed(e);
      }
    });
    startProcessingMenuItem.setText("Start processing");
    startProcessingMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        startProcessingMenuItem_actionPerformed(e);
      }
    });
    suspendProcessingMenuItem.setText("Suspend processing");
    suspendProcessingMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        suspendProcessingMenuItem_actionPerformed(e);
      }
    });
    resumeProcessingMenuItem.setText("Resume processing");
    resumeProcessingMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resumeProcessingMenuItem_actionPerformed(e);
      }
    });
    fileMenu.add(clearMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(exitMenuItem);
    editMenu.add(cutMenuItem);
    editMenu.addSeparator();
    editMenu.add(propertiesMenuItem);
    editMenu.addSeparator();
    editMenu.add(startProcessingMenuItem);
    editMenu.add(suspendProcessingMenuItem);
    editMenu.add(resumeProcessingMenuItem);
    menuBar1.add(fileMenu);
    menuBar1.add(editMenu);
    menuBar1.add(createMenu);
    menuBar1.add(helpMenu);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1, BorderLayout.NORTH);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel2, BorderLayout.NORTH);
    jPanel1.add(jScrollPane2, BorderLayout.CENTER);
    setUpTheTable();
    jScrollPane2.getViewport().add(agentTable, null);
    jScrollPane1.getViewport().add(traceTextArea, null);
    helpMenu.add(AboutMenuItem);
    this.setJMenuBar(menuBar1);
  }


  /**
   * Processes a window event to add exiting on close.
   *
   * @param e the WindowEvent object that was generated for this frame
   *
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      exitMenuItem_actionPerformed(null);
    } else if (e.getID() == WindowEvent.WINDOW_ACTIVATED) {
      e.getWindow().repaint();
    }
  }


  /**
   * Stops the selected agent and removes it from the PAManager list.
   *
   * @param e the ActionEvent object that was generated when cut was
   *          selected
   */
  void Cut_actionPerformed(ActionEvent e) {
    int selectedRow = agentTable.getSelectedRow();

    if ((selectedRow < 0) || (selectedRow >= agents.size())) {
      return;  // nothing selected
    }
    CIAgent agent = (CIAgent) agents.elementAt(selectedRow);

    agent.stopAgentProcessing();          //  first, stop the agent from runnning
    agents.removeElementAt(selectedRow);  //  remove from agent list
    refreshTable();                       // refresh table with this agent out of there
    setEditMenuItemStates();              // turn edit menu items on/off
  }


  /**
   * Adds a message to the trace text area.
   *
   * @param msg the String object that contains the message to be appended
   *
   */
  public synchronized void trace(String msg) {
    traceTextArea.append(msg);
  }


  /**
   * Processes the CIAgentEvent received by this application by displaying
   * information in the trace text area.
   *
   * @param event the CIAgentEvent object
   *
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    Object source = event.getSource();
    String agentName = "";

    if (source instanceof CIAgent) {
      agentName = ((CIAgent) source).getName();
    }
    Object arg = event.getArgObject();
    Object action = event.getAction();

    if (action != null) {
      if (action.equals("trace")) {
        if (((arg != null) && (arg instanceof String))) {
          trace("\n"+ (String) arg);  // display the msg
        }
      } else {
        trace("\nPAManager received action event: " + action + " from agent " + agentName);
      }
    }
  }


  /**
   * Processes an event (does not actually post it to the event queue).
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void postCIAgentEvent(CIAgentEvent event) {
    processCIAgentEvent(event);  // don't queue, just process
  }


  /**
   * Sets up the agent table.
   *
   */
  public void setUpTheTable() {
    agentTable = new JTable();

    // Get the data from the Data Set
    data = getTableData();

    // Create a model of the data.
    agentTableModel = new AbstractTableModel() {

      // These methods always need to be implemented.
      public int getColumnCount() {
        return columnNameList.length;
      }

      public int getRowCount() {
        return data.length;
      }

      public Object getValueAt(int theRow, int theCol) {
        return data[theRow][theCol];
      }

       public String getColumnName(int theCol) {
        return columnNameList[theCol];
      }

      public Class getColumnClass(int theCol) {
        return getValueAt(0, theCol).getClass();
      }

      // don't allow the user to change any values in the JTable
      public boolean isCellEditable(int theRow, int theCol) {
        boolean canEdit = false;
        return canEdit;
      }

      // don't allow the user to change any values in the JTable
      public void setValueAt(Object theValue, int theRow, int theCol) {
       Boolean lclBoolean = null;
        String lclString = ((String) theValue).trim();

        switch (theCol) {
          case COL_NAMEID :
            break;
          case COL_TYPEID :
            break;
          case COL_STATEID :
            break;
          case COL_TASKID :
            break;
        }  // end switch
      }
    };
    agentTable = new JTable(agentTableModel);
    agentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

     // code to detect table selection events
     ListSelectionModel rowSM = agentTable.getSelectionModel();

    rowSM.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {

        // ignore extra messages
        if (e.getValueIsAdjusting()) {
          return;
        }
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (lsm.isSelectionEmpty()) {
          // no rows selected
          setEditMenuItemStates();  // turn off edit menu items
        } else {
            setEditMenuItemStates();
           // updateTable();            //refresh status column
        }
      }
    });
    (agentTable.getColumn(COL_NAME)).setPreferredWidth(50);
    (agentTable.getColumn(COL_TYPE)).setPreferredWidth(50);
    (agentTable.getColumn(COL_STATE)).setPreferredWidth(25);
    (agentTable.getColumn(COL_TASK)).setPreferredWidth(100);
    setEditMenuItemStates();
    agentTable.setRowSelectionAllowed(true) ;
  }


  /**
   * Retrieves the data from each agent and fills the table model.
   *
   * @return the Object[][] that contains the table data
   */
  private Object[][] getTableData() {
    Object[][] lclArray = null;

    if (agents.size() == 0) {
      lclArray = new Object[1][NUM_COLS];
      lclArray[0][0] = "";
      lclArray[0][1] = "";
      lclArray[0][2] = "";
      lclArray[0][3] = "";
      return lclArray;
    }  // no agents yet !!!!
    if (agents != null) {
      lclArray = new Object[agents.size()][NUM_COLS];
    }
    for (int i = 0; i < agents.size(); i++) {
      CIAgent agent = (CIAgent) agents.elementAt(i);

      lclArray[i][0] = agent.getName();
      lclArray[i][1] = agent.getDisplayName();
      lclArray[i][2] = agent.getState().toString();
      lclArray[i][3] = agent.getTaskDescription();
    }
    return lclArray;
  }


  /**
   * Changes the contents of the STATE column in the table only.
   */
  private void updateTableData() {
    if (agents.size() == 0) {
      return;  // no agent data yet !!!!
    }
    for (int i = 0; i < agents.size(); i++) {
      CIAgent agent = (CIAgent) agents.elementAt(i);

      data[i][2] = agent.getState().toString();
    }
    return;
  }


  /**
   *  Updates the table data then sends an event to refresh the screen.
   */
  private void updateTable() {
    updateTableData();
    TableModelEvent e = new TableModelEvent(agentTableModel);

    agentTable.tableChanged(e);
  }


  /**
   * Refreshes the table data.
   */
  private void refreshTable() {
    data = getTableData();
    updateTable();
  }


  /**
   *  Enables and disables the Edit menu items based on whether an agent
   *  has been selected and if so, updates the state of the selected agent.
   */
  private void setEditMenuItemStates() {
    CIAgent selectedAgent = null;
    int selectedRow = agentTable.getSelectedRow();

    if ((selectedRow < 0) || (selectedRow >= agents.size())) {
      selectedAgent = null;
    } else {
      selectedAgent = (CIAgent) agents.elementAt(selectedRow);
    }
    if (selectedAgent == null) {
      cutMenuItem.setEnabled(false);
      propertiesMenuItem.setEnabled(false);
      startProcessingMenuItem.setEnabled(false);
      suspendProcessingMenuItem.setEnabled(false);
      resumeProcessingMenuItem.setEnabled(false);
    } else {
      cutMenuItem.setEnabled(true);
      propertiesMenuItem.setEnabled(true);
      int state = selectedAgent.getState().getState();

      startProcessingMenuItem.setEnabled(false);
      suspendProcessingMenuItem.setEnabled(false);
      resumeProcessingMenuItem.setEnabled(false);

      // enable the options which make sense based on current state
      switch (state) {
        case CIAgentState.INITIATED :
          startProcessingMenuItem.setEnabled(true);
          break;
        case CIAgentState.ACTIVE :
          suspendProcessingMenuItem.setEnabled(true);
          break;
        case CIAgentState.SUSPENDED :
          resumeProcessingMenuItem.setEnabled(true);
          break;
        case CIAgentState.UNINITIATED :

          // if not initialized -- no processing actions allowed
          break;
      }
    }  // endif
  }


  /**
   * Opens the customizer dialog when the user double clicks on an agent.
   *
   * @param agent the CIAgent object that was selected
   * @param modal the boolean flag indicating modality
   */
  private void openCustomizer(CIAgent agent, boolean modal) {
    Class customizerClass = agent.getCustomizerClass();

    if (customizerClass == null) {
      trace("ERROR - No customizer defined for this agent");
      // tell user, no customizer defined
      return;
    }

    // found a customizer, now open it
    Customizer customizer = null;

    try {
      customizer = (Customizer) customizerClass.newInstance();
    } catch (Exception exc) {
      trace("\nError opening customizer - " + exc.toString());
      return;  // bail out
    }

   // customizer must be JFrame (window) or JDialog
   if (customizer instanceof JFrame) {
      Point pos = this.getLocation();
      JFrame frame = (JFrame) customizer;

      frame.setLocation(pos.x + 20, pos.y + 20);
      customizer.setObject(agent);
      frame.show();
    } else if (customizer instanceof JDialog) {
      Point pos = this.getLocation();
      JDialog dlg = (JDialog) customizer;

      dlg.setModal(modal);
      dlg.setLocation(pos.x + 20, pos.y + 20);
      customizer.setObject(agent);
      dlg.show();
    } else {
      trace("\nError - CIAgent customizer must be JFrame or JDialog");
      // Note: could provide support for JPanel here
    }
    setEditMenuItemStates();  // turn edit menu items on/off
    refreshTable();
    this.invalidate();
    this.repaint();
  }


  /**
   * Reads the PAManager.properties file that contains a list of supported
   * agents.
   */
  private void readPropertiesFile() {

    // create a properties object to hold the preferences
    Properties properties = new Properties();

    try {


      // Note: assume that properties file is in the pamanager directory
      FileInputStream in = new FileInputStream("pamanager.properties");

      properties.load(new BufferedInputStream(in));
      String property;

      property = properties.getProperty("AgentClassNames");
      StringTokenizer tok = new StringTokenizer(property, ";");
      String displayName;

      while (tok.hasMoreTokens()) {
        String agentClassName = tok.nextToken();

        // try to instantiate the agent bean
        CIAgent agent = null;

        try {
          Class klas = Class.forName(agentClassName);

          agent = (CIAgent) klas.newInstance();
          displayName = agent.getDisplayName();
          System.out.println("Adding agent class ... " + displayName);
          addAgentMenuItem(displayName);  // create menu item and add to Create menu
          agentClasses.put(displayName, agentClassName);  // save in hashtable for later
        } catch (Exception exc) {
          System.out.println("Error can't instantiate agent: " + agentClassName + " " + exc.toString());
        }
      }
    } catch (Exception e) {
      System.out.println("Error: cannot find or load PAManager properties file");
    }
  }


  /**
   * Adds a menu item for an agent to the Create Menu so the user can
   * select the agent for use.
   *
   *
   * @param item the String object that contains the agent type
   */
  private void addAgentMenuItem(String item) {
    JMenuItem menuItem = new JMenuItem(item);

    menuItem.setActionCommand(item);
    menuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CreateMenuItem_actionPerformed(e);
      }
    });
    createMenu.add(menuItem);  // create menu
  }


  /**
   * Creates a new agent and adds it to the table.
   *
   * @param theEvent the ActionEvent object
   */
  void CreateMenuItem_actionPerformed(ActionEvent theEvent) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    // try to instantiate the agent bean
    CIAgent agentBean = null;
    Object bean = null;
    String beanName = theEvent.getActionCommand();
    String className = (String) agentClasses.get(beanName);  // retrieve the class name

    try {
      Class klas = Class.forName(className);      // load or reload the class

      bean = klas.newInstance();                  // create an instance
      agentBean = (CIAgent) bean;                 // try to cast it to CIAgent
      agentBean.setAgentPlatform(this);  // pass reference to agent for later use
      agentBean.addCIAgentEventListener(this);  // register PAManager as listener
      agentBean.addPropertyChangeListener(this);
      openCustomizer(agentBean, true);            // automatically open customizer
      addAgent(agentBean);                        // add the agent to the platform
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this,         // Parent
        e.toString(),                             // Msg
        "Error: Can't create agent " + beanName,  // Title
        JOptionPane.ERROR_MESSAGE                 // Severity
          );
    }
    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    refreshTable();
    this.invalidate();
    this.repaint();
  }


  /**
   * Opens the bean customizer on the selected agent.
   *
   * @param e the ActionEvent object generated when the agent was selected
   */
  void propertiesMenuItem_actionPerformed(ActionEvent e) {
    int selectedRow = agentTable.getSelectedRow();

    if ((selectedRow < 0) || (selectedRow >= agents.size())) {
      return;  // nothing selected
    }
    CIAgent agent = (CIAgent) agents.elementAt(selectedRow);

    openCustomizer(agent, false);
  }


  /**
   * Closes the main window on exit.
   *
   * @param e the ActionEvent object generated when exit was selected
   */
  void exitMenuItem_actionPerformed(ActionEvent e) {
    System.exit(0);
  }


  /**
   * Clears the PAManager by stopping and removing all agents.
   *
   * @param e the ActionEvent object generated when clear was selected
   */
  void clearMenuItem_actionPerformed(ActionEvent e) {
    int size = agents.size();

    for (int i = 0; i < size; i++) {
      CIAgent agent = (CIAgent) agents.elementAt(0);  // get first agent

      agent.stopAgentProcessing();                    //  first, stop the agent from runnning
      agents.removeElementAt(0);                      //  remove from agent list
    }
    refreshTable();  // refresh table with agents removed
    traceTextArea.setText("") ; // clear the bottom text area
  }


  /**
   * Refreshes the table when an agent waschanged.
   *
   * @param event the PropertyChangeEvent object generated when the agent
   *              was changed
   *
   */
  public void propertyChange(PropertyChangeEvent event) {
    refreshTable();  // an agent property changed, refresh the table
  }


  /**
   * Starts an agent.
   *
   * @param e the ActionEvent object generated when start was selected
   */
  void startProcessingMenuItem_actionPerformed(ActionEvent e) {
    int selectedRow = agentTable.getSelectedRow();

    if ((selectedRow < 0) || (selectedRow >= agents.size())) {
      return;  // nothing selected
    }

    // only start if agent is in the initialized state
    CIAgent agent = (CIAgent) agents.elementAt(selectedRow);

    if (agent.getState().getState() == CIAgentState.INITIATED) {
      agent.startAgentProcessing();
      setEditMenuItemStates();  // turn edit menu items on/off
      updateTable();
    }
  }


  /**
   * Suspends agent processing.
   *
   * @param e the ActionEvent object generated when suspend was selected
   */
  void suspendProcessingMenuItem_actionPerformed(ActionEvent e) {
    int selectedRow = agentTable.getSelectedRow();

    if ((selectedRow < 0) || (selectedRow >= agents.size())) {
      return;  // nothing selected
    }
    CIAgent agent = (CIAgent) agents.elementAt(selectedRow);

    agent.suspendAgentProcessing();
    setEditMenuItemStates();  // turn edit menu items on/off
    updateTable();
  }


  /**
   * Resumes agent processing.
   *
   * @param e the ActionEvent object generated when resume was selected
   */
  void resumeProcessingMenuItem_actionPerformed(ActionEvent e) {
    int selectedRow = agentTable.getSelectedRow();

    if ((selectedRow < 0) || (selectedRow >= agents.size())) {
      return;  // nothing selected
    }
    CIAgent agent = (CIAgent) agents.elementAt(selectedRow);

    agent.resumeAgentProcessing();
    setEditMenuItemStates();  // turn edit menu items on/off
    updateTable();
  }


  /**
   * Displays the About dialog.
   *
   * @param e the ActionEvent object generated when About was selected
   *
   */
  void AboutMenuItem_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About Personal Agent Manager", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }


  /**
   * Adds an agent bean to this platform.
   *
   * @param agent the CIAgent object to be added to this container, giving
   *              it a unique name
   */
  public void addAgent(CIAgent agent) {
    if (agents.contains(agent)) {
      return;  // don't add a second instance
    }
    String name = agent.getName();

    for (int i = 0; i < agents.size(); i++) {

      // see if we have a name collision
      if (name.equals(((CIAgent) agents.elementAt(i)).getName())) {
        String newName = generateUniqueName(agent);
        agent.setName(newName);  // change the name to be unique
      }
    }
    agents.addElement(agent);
  }


  /**
   * Generate a unique agent bean name by appending a colon and an integer
   *
   * @param agent the CIAgent object for which a new name is generated
   *
   * @return the String object that contains the unique name
   */
  private String generateUniqueName(CIAgent agent) {

    // keep a local hash list of used agent names
    Hashtable names = new Hashtable();

    for (int i = 0; i < agents.size(); i++) {
      CIAgent bean = (CIAgent) agents.elementAt(i);

      names.put(bean.getName(), bean);
    }
    String name = agent.getName();
    String tmpName;
    String tmpInx;

    while (names.containsKey(name)) {
      int inx = 0;

      if ((inx = name.indexOf(':')) != -1) {
        tmpName = name.substring(0, inx);
        tmpInx = name.substring(inx + 1);
        int index = Integer.parseInt(tmpInx);

        name = tmpName + ":" + (index + 1);  // bump index
      } else {
        name = name + ":1";                  // add numeric suffic
      }
    }
    return name;  // returns a unique name
  }

  //
  //  AgentPlatform interface methods
  //


  /**
   * Retrieves a list of the registered agents running on this platform.
   *
   * @return the Vector object that contains the agents
   */
  public Vector getAgents() {
    return (Vector) agents.clone();
  }

   /**
   * Retrieves the agent that has specified name.
   *
   * @param agentName the String object that contains the name of the agent to be
   *        retrieved
   *
   * @return the CIAgent object or null if not found
   */
  public CIAgent getAgent(String agentName) {
    Enumeration enumX = agents.elements() ;
    while (enumX.hasMoreElements()) {
      CIAgent lclAgent = (CIAgent)enumX.nextElement() ;
      if (lclAgent.getName().equals(agentName)) {
        return lclAgent ;
      }
    }
    return null;   // agent not found
  }
}
