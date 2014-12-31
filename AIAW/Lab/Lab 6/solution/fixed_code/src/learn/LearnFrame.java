package learn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


/**
 * This is the frame for the application which demonstrates three learing
 * algorithms: back propagation network, Kohonen map network, and a decision
 * tree.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class LearnFrame extends JFrame implements Runnable {
  /**
	 * 
	 */
	private static final long serialVersionUID = -5247222438595654911L;
BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu FileMenu = new JMenu();
  JMenu DataMenu = new JMenu();
  JMenu AlgorithmMenu = new JMenu();
  ButtonGroup buttonGroup = new ButtonGroup();
  JMenu HelpMenu = new JMenu();
  JMenuItem StartMenuItem = new JMenuItem();
  JMenuItem ResetMenuItem = new JMenuItem();
  JMenuItem ExitMenuItem = new JMenuItem();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea dataTextArea = new JTextArea();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea traceTextArea = new JTextArea();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  private volatile Thread runnit;
  private boolean exitThread = false;  // signal to abort training thread
  DataSet dataSet = null;              // current data set
  JRadioButtonMenuItem BackPropRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem KohonenRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem DecisionTreeRadioButtonMenuItem = new JRadioButtonMenuItem();
  JMenuItem LoadDataMenuItem = new JMenuItem();
  JMenuItem AboutMenuItem = new JMenuItem();
  JLabel DataSetFileNameLabel = new JLabel();


  /**
   * Constructs the frame for the learn application.
   */
  public LearnFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initializes the UI components.
   *
   * @throws Exception if any errors occur
   */
  private void jbInit() throws Exception {
    DecisionTreeRadioButtonMenuItem.setText("Decision Tree");
    DecisionTreeRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DecisionTreeRadioButtonMenuItem_actionPerformed(e);
      }
    });
    KohonenRadioButtonMenuItem.setText("Kohonen map");
    KohonenRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        KohonenRadioButtonMenuItem_actionPerformed(e);
      }
    });
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(600, 479));
    this.setTitle("Learn Application - Back Propagation");
    FileMenu.setText("File");
    DataMenu.setText("Data");
    AlgorithmMenu.setText("Algorithm");
    BackPropRadioButtonMenuItem.setText("Back propagation");
    BackPropRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BackPropRadioButtonMenuItem_actionPerformed(e);
      }
    });
    ResetMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ResetMenuItem_actionPerformed(e);
      }
    });
    DataSetFileNameLabel.setText("<none>");
    DataSetFileNameLabel.setBounds(new Rectangle(78, 10, 312, 25));
    buttonGroup.add(DecisionTreeRadioButtonMenuItem);
    buttonGroup.add(BackPropRadioButtonMenuItem);
    buttonGroup.add(KohonenRadioButtonMenuItem);
    BackPropRadioButtonMenuItem.setSelected(true);
    HelpMenu.setText("Help");
    StartMenuItem.setText("Start");
    StartMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        StartMenuItem_actionPerformed(e);
      }
    });
    StartMenuItem.setEnabled(false);  // off until data set loaded
    ResetMenuItem.setText("Reset");
    ExitMenuItem.setText("Exit");
    jPanel2.setLayout(borderLayout2);
    jPanel4.setMinimumSize(new Dimension(600, 200));
    jPanel4.setPreferredSize(new Dimension(600, 200));
    jPanel4.setLayout(borderLayout3);
    jPanel1.setMinimumSize(new Dimension(600, 50));
    jPanel1.setPreferredSize(new Dimension(600, 50));
    jPanel1.setLayout(null);
    jLabel1.setText("Data Set:");
    jLabel1.setBounds(new Rectangle(15, 14, 69, 17));
    jPanel5.setLayout(borderLayout4);
    LoadDataMenuItem.setText("Load...");
    LoadDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LoadDataMenuItem_actionPerformed(e);
      }
    });
    AboutMenuItem.setText("About");
    AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutMenuItem_actionPerformed(e);
      }
    });
    jMenuBar1.add(FileMenu);
    jMenuBar1.add(DataMenu);
    jMenuBar1.add(AlgorithmMenu);
    jMenuBar1.add(HelpMenu);
    AlgorithmMenu.add(BackPropRadioButtonMenuItem);
    AlgorithmMenu.add(KohonenRadioButtonMenuItem);
    AlgorithmMenu.add(DecisionTreeRadioButtonMenuItem);
    FileMenu.add(StartMenuItem);
    FileMenu.addSeparator();
    FileMenu.add(ResetMenuItem);
    FileMenu.addSeparator();
    FileMenu.add(ExitMenuItem);
    this.getContentPane().add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1, null);
    jPanel1.add(DataSetFileNameLabel, null);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(dataTextArea, null);
    jPanel2.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(traceTextArea, null);
    this.getContentPane().add(jPanel3, BorderLayout.SOUTH);
    DataMenu.add(LoadDataMenuItem);
    HelpMenu.add(AboutMenuItem);
    setJMenuBar(jMenuBar1);
  }


  /**
   * Processes window events and is overridden to exit when window closes.
   *
   * @param e the WindowEvent to be processed
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    } else if (e.getID() == WindowEvent.WINDOW_ACTIVATED) {
      e.getWindow().repaint();
    }
  }


  /**
   * Tests a back prop network using the given dataset and text area.
   * Note: this method is run on a separate thread from the GUI
   *
   * @param dataset the DataSet used to test the network
   * @param bottomText  the JTextArea used to display information
   */
  public void testBackProp(DataSet dataset, JTextArea bottomText) {
    BackProp testNet = new BackProp("Test Back Prop Network");

    bottomText.append("Training Back Prop network...");
    testNet.textArea1 = bottomText;
    testNet.setDataSet(dataset);
    testNet.setNumRecs(dataset.numRecords);
    testNet.setFieldsPerRec(dataset.normFieldsPerRec);
    testNet.setData(dataset.normalizedData);  // get vector of data
    int numOutputs = dataset.getClassFieldSize();
    int numInputs = testNet.getFieldsPerRec() - numOutputs;

    testNet.createNetwork(numInputs, numInputs, numOutputs);
    bottomText.append("\nNetwork architecture = " + numInputs + "-" + numInputs + "-" + numOutputs);
    bottomText.append("\nLearn rate = " + testNet.getLearnRate() + ",  Momentum = " + testNet.getMomentum());
    bottomText.append("\n\nEach '*' indicates 100 passes over training data\n");
    int maxNumPasses = 2500;  // default -- could be on applet
    int numRecs = testNet.getNumRecs();
    int numPasses = 0;

    for (numPasses = 0; numPasses < maxNumPasses; numPasses++) {
      for (int j = 0; j < numRecs; j++) {
        testNet.process();  // train
      }
      try {
        Thread.sleep(10);   // give up the processor to GUI
      } catch (InterruptedException e) {}
      if ((numPasses % 100) == 0) {
        bottomText.append("*");
      }
      if (exitThread) {
        testNet.textArea1.append("\n\nUser pressed Reset ... training halted!\n\n");
        break;              // exit the loop
      }
    }
    testNet.textArea1.append("\n Passes Completed: " + numPasses + "  RMS Error = " + testNet.getAveRMSError() + " \n");
    testNet.setMode(1);  // lock the network

    // do a final pass and display the results
    for (int i = 0; i < testNet.getNumRecs(); i++) {
      testNet.process();  // test
      testNet.display_network();
    }
  }


  /**
   * Tests a Kohonen map network using the given dataset and text area.
   * Note: this method is run on a separate thread from the GUI
   *
   * @param dataset the DataSet used to test the network
   * @param bottomText  the JTextArea used to display information
   */
  public void testKMapNet(DataSet dataset, JTextArea bottomText) {
    KMapNet testNet = new KMapNet("Test Kohonen Map Network");

    bottomText.append("Training Kohonen map network...");
    bottomText.append("\nEach '*' indicates 1 pass over training data.\n");
    testNet.textArea1 = bottomText;
    testNet.setDataSet(dataset);
    testNet.setNumRecs(dataset.numRecords);
    testNet.setFieldsPerRec(dataset.fieldsPerRec);
    testNet.setData(dataset.normalizedData);  // get vector of data

    // create network, all fields are inputs
    testNet.createNetwork(testNet.getFieldsPerRec(), 4, 4);  // default net arch
    int maxNumPasses = 20;  // default -- could be on gui panel
    int numRecs = testNet.getNumRecs();
    int numPasses = 0;

    // train the network
    for (numPasses = 0; numPasses < maxNumPasses; numPasses++) {
      for (int j = 0; j < numRecs; j++) {
        testNet.cluster();  // train
      }
      try {
        Thread.sleep(10);   // give up the processor to GUI
      } catch (InterruptedException e) {}
      bottomText.append("*");
      if (exitThread) {
        testNet.textArea1.append("\n\nUser pressed Reset ... training halted!\n\n");
        break;              // exit the loop
      }
    }
    testNet.textArea1.append("\n Passes Completed: " + numPasses + " \n");
    testNet.setMode(1);  // lock the network weights
    for (int i = 0; i < testNet.getNumRecs(); i++) {
      testNet.cluster();  // test
      testNet.display_network();
    }
  }


  /**
   * Tests a decision tree using the given dataset and text area.
   * Note: this method is run on a separate thread from the GUI
   *
   * @param dataset the DataSet used to train the network
   *
   * @param dataSet the DataSet
   * @param bottomText  the JTextArea used to display information
   */
  public void testDecisionTree(DataSet dataSet, JTextArea bottomText) {
    DecisionTree tree = new DecisionTree("\n Test Decision Tree ");

    DecisionTree.textArea1 = bottomText;
    tree.ds = dataSet;
    DecisionTree.textArea1.append("Starting DecisionTree ");
    tree.examples = dataSet.data;  // get vector of data
    tree.variableList = dataSet.variableList;

    // test that data set contains all categorical fields
    boolean allCategorical = true;
    Enumeration<?> enumeration = tree.variableList.elements();

    while (enumeration.hasMoreElements()) {
      Variable var = (Variable) enumeration.nextElement();

      if (!var.isCategorical()) {
        allCategorical = false;
        break;
      }
    }
    if (!allCategorical) {
      DecisionTree.textArea1.append("\nDecision Tree cannot process continuous data\n");
      DecisionTree.textArea1.append("\nPlease select a different data set\n");
      return;
    }
    tree.classVar = (Variable) tree.variableList.get("ClassField");
    tree.variableList.remove("ClassField");
    Node root = tree.buildDecisionTree(tree.examples, tree.variableList, new Node("default"));  // recursively build tree

    // now display the results
    DecisionTree.textArea1.append("\n\nDecisionTree -- classVar = " + tree.classVar.name);
    Node.displayTree(root, "  ");
    DecisionTree.textArea1.append("\nStopping DecisionTree - success!");
  }


  /**
   * Sets the title on the frame when the back prop radio button is clicked.
   *
   * @param e the ActionEvent that was generated
   */
  void BackPropRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    setTitle("Learn Application - Back Propagation");
  }


  /**
   * Sets the title on the frame when the Kohnen map radio button is clicked.
   *
   * @param e the ActionEvent that was generated
   */
  void KohonenRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    setTitle("Learn Application - Kohonen Map");
  }


  /**
   * Sets the title on the frame when the decision tree radio button is clicked.
   *
   * @param e the ActionEvent that was generated
   */
  void DecisionTreeRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    setTitle("Learn Application - Decision Tree");
  }


  /**
   * Gets the dataset filename and loads the dataset.
   *
   * @param e the ActionEvent that was generated
   */
  void LoadDataMenuItem_actionPerformed(ActionEvent e) {
    FileDialog dlg = new FileDialog(this, "Load Data Set", FileDialog.LOAD);

    dlg.setFile("*.dfn");
    dlg.show();
    String dirName = dlg.getDirectory() ;
    String fileName = dlg.getFile();

    if (fileName != null) {
      dataTextArea.setText("");
      dataSet = new DataSet("ds", dirName+fileName);
      dataSet.setDisplay(dataTextArea);
      dataSet.loadDataFile();          // load the data set
      DataSetFileNameLabel.setText(dirName+fileName);
      StartMenuItem.setEnabled(true);  // turn on start menu button
    }
    this.repaint();
  }


  /**
   * Displays the About dialog.
   *
   * @param e the ActionEvent generated when About was selected
   *
   */
  void AboutMenuItem_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About Learn Application", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }


  /**
   * Starts a thread when START is selected.
   *
   * @param e the ActionEvent for the selection
   */
  void StartMenuItem_actionPerformed(ActionEvent e) {
    runnit = new Thread(this);
    exitThread = false;  // reset the reset/halt training flag
    runnit.start();  // start the thread running
  }


  /**
   *   Runs the selected algorithm in a separate thread, writing some trace
   *   information into the the text area of the application window. If debug
   *   is set on, additional trace information will be displayed.
   */
  public void run() {
    traceTextArea.setText("");
    if (BackPropRadioButtonMenuItem.isSelected()) {
      testBackProp(dataSet, traceTextArea);
    }
    if (KohonenRadioButtonMenuItem.isSelected()) {
      testKMapNet(dataSet, traceTextArea);
    }
    if (DecisionTreeRadioButtonMenuItem.isSelected()) {
      testDecisionTree(dataSet, traceTextArea);  // Decision tree
    }
  }


  /**
   * Clears the text area and sets a boolean flag to exit training thread.
   *
   * @param e the ActionEvent that was generated when Reset was selected
   */
  void ResetMenuItem_actionPerformed(ActionEvent e) {
    traceTextArea.setText("");
    exitThread = true;  // signal training thread to halt
  }
}
