package marketplace;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ciagent.*;


/**
 * The <code>MarketplaceFrame</code> class implements the marketplace
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
public class MarketplaceFrame extends JFrame implements CIAgentEventListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 52182980311330305L;
JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea topTextArea = new JTextArea();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel jLabel2 = new JLabel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea traceTextArea = new JTextArea();
  JMenuItem clearMenuItem = new JMenuItem();
  JMenuItem exitMenuItem = new JMenuItem();
  JMenuItem startMenuItem = new JMenuItem();
  JMenuItem stopMenuItem = new JMenuItem();
  JMenu jMenu2 = new JMenu();
  JMenu jMenu3 = new JMenu();
  JMenu jMenu4 = new JMenu();
  JMenu HelpMenu = new JMenu();
  JMenuItem aboutMenuItem = new JMenuItem();
  JCheckBoxMenuItem basicBuyerCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem intermediateBuyerCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem advancedBuyerCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem basicSellerCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem intermediateSellerCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem advancedSellerCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem detailsCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem summaryCheckBoxMenuItem = new JCheckBoxMenuItem();
  FacilitatorAgent facilitator;
  BuyerAgent basicBuyerAgent;
  BetterBuyerAgent intermedBuyerAgent;
  BestBuyerAgent advancedBuyerAgent;
  SellerAgent basicSellerAgent;
  BetterSellerAgent intermedSellerAgent;
  BestSellerAgent advancedSellerAgent;
  public static final int SUMMARY = 0;  // trace level
  public static final int DETAILS = 1;  // trace level
  /**
   * Creates a <code>MarketplaceFrame</code> object.
   *
   */
  public MarketplaceFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initializes the GUI controls.
   *
   * @throws Exception if any errors occurred during initialization
   *
   */
  private void jbInit() throws Exception {
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exitMenuItem_actionPerformed(e);
      }
    });
    clearMenuItem.setText("Clear");
    clearMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearMenuItem_actionPerformed(e);
      }
    });
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(500, 428));
    this.setTitle("CIAgent Marketplace Application ");
    menuFile.setText("File");
    jPanel2.setMinimumSize(new Dimension(400, 200));
    jPanel2.setPreferredSize(new Dimension(400, 200));
    jPanel2.setLayout(borderLayout2);
    jLabel1.setText("Facilitator");
    jPanel1.setLayout(borderLayout3);
    jLabel2.setText("Marketplace");
    startMenuItem.setText("Start");
    startMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        startMenuItem_actionPerformed(e);
      }
    });
    stopMenuItem.setText("Stop");
    stopMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        stopMenuItem_actionPerformed(e);
      }
    });
    stopMenuItem.setEnabled(false) ;
    jMenu2.setText("View");
    jMenu3.setText("Buyers");
    jMenu4.setText("Sellers");
    HelpMenu.setText("Help");
    aboutMenuItem.setText("About");
    aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aboutMenuItem_actionPerformed(e);
      }
    });
    basicBuyerCheckBoxMenuItem.setText("Basic");
    intermediateBuyerCheckBoxMenuItem.setText("Intermediate");
    advancedBuyerCheckBoxMenuItem.setText("Advanced");
    basicBuyerCheckBoxMenuItem.setSelected(true);
    basicSellerCheckBoxMenuItem.setText("Basic");
    intermediateSellerCheckBoxMenuItem.setText("Intermediate");
    advancedSellerCheckBoxMenuItem.setText("Advanced");
    basicSellerCheckBoxMenuItem.setSelected(true);
    detailsCheckBoxMenuItem.setText("Details");
    detailsCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        detailsCheckBoxMenuItem_actionPerformed(e);
      }
    });
    summaryCheckBoxMenuItem.setText("Summary");
    summaryCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        summaryCheckBoxMenuItem_actionPerformed(e);
      }
    });
    summaryCheckBoxMenuItem.setSelected(true);
    menuBar1.add(menuFile);
    menuBar1.add(jMenu2);
    menuBar1.add(jMenu3);
    menuBar1.add(jMenu4);
    menuBar1.add(HelpMenu);
    this.getContentPane().add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1, BorderLayout.NORTH);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabel2, BorderLayout.NORTH);
    jPanel1.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(traceTextArea, null);
    jScrollPane1.getViewport().add(topTextArea, null);
    menuFile.add(startMenuItem);
    menuFile.add(stopMenuItem);
    menuFile.addSeparator();
    menuFile.add(clearMenuItem);
    menuFile.addSeparator();
    menuFile.add(exitMenuItem);
    jMenu2.add(detailsCheckBoxMenuItem);
    jMenu2.add(summaryCheckBoxMenuItem);
    HelpMenu.add(aboutMenuItem);
    jMenu4.add(basicSellerCheckBoxMenuItem);
    jMenu4.add(intermediateSellerCheckBoxMenuItem);
    jMenu4.add(advancedSellerCheckBoxMenuItem);
    jMenu3.add(basicBuyerCheckBoxMenuItem);
    jMenu3.add(intermediateBuyerCheckBoxMenuItem);
    jMenu3.add(advancedBuyerCheckBoxMenuItem);
    this.setJMenuBar(menuBar1);
  }


  /**
   * Exits the application.
   *
   * @param e the ActionEvent object generated when exit was selected
   *
   */
  public void fileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }


  /**
   * Used to close or repaint the window.
   *
   * @param e the WindowEvent object generated when the GUI event occurred
   *
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    } else if (e.getID() == WindowEvent.WINDOW_ACTIVATED) {
      e.getWindow().repaint();
    }
  }


  /**
   * Clears the text areas only, does not affect the agent processing
   *
   * @param e the ActionEvent object generated when Clear was selected
   *
   */
  void clearMenuItem_actionPerformed(ActionEvent e) {
    topTextArea.setText("");
    traceTextArea.setText("");
  }


  /**
   * Exits the application.
   *
   * @param e the ActionEvent object generated when Exit was selected
   *
   */
  void exitMenuItem_actionPerformed(ActionEvent e) {
       System.exit(0) ;
  }


  /**
   * Starts the application and creates the facilitator, buyer, and seller
   * agents.
   *
   * @param e the ActionEvent object generated when Start was selected
   *
   */
  void startMenuItem_actionPerformed(ActionEvent e) {
    topTextArea.setText("");
    traceTextArea.setText("");
    this.setTitle("CIAgent Marketplace Application - Running");

    int traceLevel = SUMMARY;  // default is summary

    if (detailsCheckBoxMenuItem.isSelected()) {
      traceLevel = DETAILS;
    }
    facilitator = FacilitatorAgent.getInstance();  // singleton
    facilitator.reset();  // clear it out
    facilitator.setTraceLevel(traceLevel);
    facilitator.addCIAgentEventListener(this);
    facilitator.initialize();
    facilitator.startAgentProcessing();

    // need to check Buyer and Seller menu items
    // to see if we need to instantiate any agents
    if (basicSellerCheckBoxMenuItem.isSelected()) {
      basicSellerAgent = new SellerAgent();
      basicSellerAgent.setTraceLevel(traceLevel);
      basicSellerAgent.addCIAgentEventListener(this);  // for tracing
      basicSellerAgent.initialize();
      basicSellerAgent.startAgentProcessing();
    }
    if (intermediateSellerCheckBoxMenuItem.isSelected()) {
      intermedSellerAgent = new BetterSellerAgent();
      intermedSellerAgent.setTraceLevel(traceLevel);
      intermedSellerAgent.addCIAgentEventListener(this);  // for tracing
      intermedSellerAgent.initialize();
      intermedSellerAgent.startAgentProcessing();
    }
    if (advancedSellerCheckBoxMenuItem.isSelected()) {
      advancedSellerAgent = new BestSellerAgent();
      advancedSellerAgent.setTraceLevel(traceLevel);
      advancedSellerAgent.addCIAgentEventListener(this);  // for tracing
      advancedSellerAgent.initialize();
      advancedSellerAgent.startAgentProcessing();
    }
    if (basicBuyerCheckBoxMenuItem.isSelected()) {
      basicBuyerAgent = new BuyerAgent();
      basicBuyerAgent.setTraceLevel(traceLevel);
      basicBuyerAgent.addCIAgentEventListener(this);  // for tracing
      basicBuyerAgent.initialize();
      basicBuyerAgent.startAgentProcessing();
    }
    if (intermediateBuyerCheckBoxMenuItem.isSelected()) {
      intermedBuyerAgent = new BetterBuyerAgent();
      intermedBuyerAgent.setTraceLevel(traceLevel);
      intermedBuyerAgent.addCIAgentEventListener(this);  // for tracing
      intermedBuyerAgent.initialize();
      intermedBuyerAgent.startAgentProcessing();
    }
    if (advancedBuyerCheckBoxMenuItem.isSelected()) {
      advancedBuyerAgent = new BestBuyerAgent();
      advancedBuyerAgent.setTraceLevel(traceLevel);
      advancedBuyerAgent.addCIAgentEventListener(this);  // for tracing
      advancedBuyerAgent.initialize();
      advancedBuyerAgent.startAgentProcessing();
    }
    topTextArea.append("Starting Marketplace \n");
    startMenuItem.setEnabled(false) ;
    stopMenuItem.setEnabled(true) ;
  }


  /**
   * Stops the facilator, buyer, and seller agents.
   *
   * @param e the ActionEvent object generated when Stop was selected
   *
   */
  void stopMenuItem_actionPerformed(ActionEvent e) {
    if (facilitator != null) {
      facilitator.removeCIAgentEventListener(this) ;
    }
    if (basicSellerAgent != null) {
      basicSellerAgent.stopAgentProcessing();
    }
    if (intermedSellerAgent != null) {
      intermedSellerAgent.stopAgentProcessing();
    }
    if (advancedSellerAgent != null) {
      advancedSellerAgent.stopAgentProcessing();
    }
    if (basicBuyerAgent != null) {
      basicBuyerAgent.stopAgentProcessing();
    }
    if (intermedBuyerAgent != null) {
      intermedBuyerAgent.stopAgentProcessing();
    }
    if (advancedBuyerAgent != null) {
      advancedBuyerAgent.stopAgentProcessing();
    }
    topTextArea.append("Ending Marketplace \n");
    traceTextArea.append("Ending Marketplace \n");
    this.setTitle("CIAgent Marketplace Application");
    startMenuItem.setEnabled(true) ;
    stopMenuItem.setEnabled(false) ;
  }


  /**
   * Sets the trace level to DETAILS.
   *
   * @param e the ActionEvent object generated when details was checked
   *
   */
  void detailsCheckBoxMenuItem_actionPerformed(ActionEvent e) {
  }


  /**
   * Sets the trace level to SUMMARY.
   *
   * @param e the ActionEvent object generated when summary was checked
   *
   */
  void summaryCheckBoxMenuItem_actionPerformed(ActionEvent e) {
  }



  /**
   * Used by agents to display messages in marketplace pane.
   *
   * @param msg the String object that contains the message to be displayed
   *
   */
  synchronized void trace(String msg) {
    traceTextArea.append(msg);
    traceTextArea.setCaretPosition(traceTextArea.getText().length());
  }



  /**
   * Used by the Facilitator agent to display msgs in top pane.
   *
   * @param msg the String object that contains the message to be displayed
   *
   */
  synchronized void traceFacilitator(String msg) {
    topTextArea.append(msg);
    topTextArea.setCaretPosition(topTextArea.getText().length());

  }


  /**
   * Processes a CIAgentEvent if it contains a trace message.
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    Object source = event.getSource();
    Object arg = event.getArgObject();
    Object action = event.getAction();

    if ((action != null) && (action.equals("trace"))) {
      if (((arg != null) && (arg instanceof String))) {
        if (source instanceof FacilitatorAgent) {
          traceFacilitator((String) arg);
        } else {
          trace((String) arg);  // display the msg
        }
      }
    }
  }


  /**
   * Method postCIAgentEvent
   *
   * @param event the CIAgentEvent object
   *
   */
  public void postCIAgentEvent(CIAgentEvent event) {
    processCIAgentEvent(event);  // don't queue, just process 
  }


  /**
   * Method aboutMenuItem_actionPerformed
   *
   * @param e the ActionEvent object
   *
   */
  void aboutMenuItem_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About Marketplace Application", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }
}
