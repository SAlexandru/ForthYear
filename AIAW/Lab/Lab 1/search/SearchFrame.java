package search;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


/**
 * This is the frame for the application which demonstrates the five basic
 * search algorithms: depth-first, breadth-first, iterated, best-first,
 * and genetic.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class SearchFrame extends JFrame implements Runnable {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea traceTextArea = new JTextArea();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JComboBox startComboBox = new JComboBox();
  JComboBox goalComboBox = new JComboBox();
  ButtonGroup radioButtonGroup = new ButtonGroup();
  BorderLayout borderLayout2 = new BorderLayout();
  SearchGraph graph;
  GeneticSearch geneticSearch;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu FileMenu = new JMenu();
  JMenuItem StartMenuItem = new JMenuItem();
  JMenuItem ClearMenuItem = new JMenuItem();
  JMenuItem ExitMenuItem = new JMenuItem();
  JMenu AlgorithmMenu = new JMenu();
  JMenu HelpMenu = new JMenu();
  JMenuItem AboutMenuItem = new JMenuItem();
  JRadioButtonMenuItem DFSRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem BFSRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem IteratedRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem BestFirstRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem GeneticSearchRadioButtonMenuItem = new JRadioButtonMenuItem();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JComboBox GeneticObjClassComboBox = new JComboBox();
  JComboBox NumGenerationsComboBox = new JComboBox();
  JLabel jLabel5 = new JLabel();
  JComboBox PopulationSizeComboBox = new JComboBox();
  JLabel jLabel6 = new JLabel();
  JTextField FitnessThresholdTextField = new JTextField();
  private volatile Thread runnit;
  private int searchAlgorithm;
  private boolean debugOn = false;

  // search algorithm identifiers
  public static final int DEPTH_FIRST = 0;
  public static final int BREADTH_FIRST = 1;
  public static final int ITERATED = 2;
  public static final int BEST_FIRST = 3;
  public static final int GENETIC_SEARCH = 4;


  /**
   * Constructs the frame for the search application.
   */
  public SearchFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();

      // Set up for search application
      graph = buildTestGraph();                      // build the test graph
      Enumeration enum = graph.getGraph().keys();    // get city names

      while (enum.hasMoreElements()) {
        String name = (String) enum.nextElement();

        startComboBox.addItem(name);                 // fill start cities
        goalComboBox.addItem(name);                  // fill goal cities
      }                                              // endwhile
      DFSRadioButtonMenuItem.setSelected(true);      // default to depth-first
      DFSRadioButtonMenuItem_actionPerformed(null);  // set default
      SearchNode.setDisplay(traceTextArea);          // used for trace display
      startComboBox.setSelectedItem("Rochester");    // defaults to Rochester
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
    this.getContentPane().setLayout(borderLayout1);
    this.getContentPane().setBackground(Color.white);
    this.setSize(new Dimension(454, 536));
    this.setTitle("Search Application");
    jPanel2.setLayout(null);
    jLabel1.setText("Start node");
    jLabel1.setBounds(new Rectangle(29, 10, 119, 17));
    jLabel2.setText("Goal state");
    jLabel2.setBounds(new Rectangle(255, 12, 114, 17));
    startComboBox.setBounds(new Rectangle(29, 32, 128, 24));
    goalComboBox.setBounds(new Rectangle(254, 32, 128, 24));
    FileMenu.setText("File");
    StartMenuItem.setText("Start");
    StartMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        StartMenuItem_actionPerformed(e);
      }
    });
    ClearMenuItem.setText("Clear");
    ClearMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClearMenuItem_actionPerformed(e);
      }
    });
    ExitMenuItem.setText("Exit");
    ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ExitMenuItem_actionPerformed(e);
      }
    });
    AlgorithmMenu.setText("Algorithm");
    HelpMenu.setText("Help");
    AboutMenuItem.setText("About");
    AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutMenuItem_actionPerformed(e);
      }
    });
    DFSRadioButtonMenuItem.setText("Depth-first");
    DFSRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DFSRadioButtonMenuItem_actionPerformed(e);
      }
    });
    BFSRadioButtonMenuItem.setText("Breadth-first");
    BFSRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BFSRadioButtonMenuItem_actionPerformed(e);
      }
    });
    IteratedRadioButtonMenuItem.setText("Iterated deepening");
    IteratedRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        IteratedRadioButtonMenuItem_actionPerformed(e);
      }
    });
    BestFirstRadioButtonMenuItem.setText("Best-first");
    BestFirstRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BestFirstRadioButtonMenuItem_actionPerformed(e);
      }
    });
    GeneticSearchRadioButtonMenuItem.setText("Genetic search");
    GeneticSearchRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GeneticSearchRadioButtonMenuItem_actionPerformed(e);
      }
    });
    jLabel3.setText("Number of generations");
    jLabel3.setBounds(new Rectangle(33, 140, 135, 17));
    jLabel4.setText("Genetic object class name");
    jLabel4.setBounds(new Rectangle(31, 73, 175, 17));
    GeneticObjClassComboBox.setBounds(new Rectangle(29, 95, 194, 24));
    GeneticObjClassComboBox.addItem("search.GeneticSearchObj1");
    GeneticObjClassComboBox.addItem("search.GeneticSearchObj2");

    // Add user-defined genetic search object classe names here!
    NumGenerationsComboBox.setBounds(new Rectangle(29, 157, 128, 24));
    NumGenerationsComboBox.addItem(new Integer(1));
    NumGenerationsComboBox.addItem(new Integer(25));
    NumGenerationsComboBox.addItem(new Integer(50));
    NumGenerationsComboBox.addItem(new Integer(75));
    NumGenerationsComboBox.addItem(new Integer(100));
    NumGenerationsComboBox.addItem(new Integer(200));
    NumGenerationsComboBox.addItem(new Integer(500));
    NumGenerationsComboBox.setSelectedItem(new Integer(100));
    jLabel5.setText("Populaton size");
    jLabel5.setBounds(new Rectangle(289, 74, 109, 17));
    PopulationSizeComboBox.setBounds(new Rectangle(288, 95, 96, 24));
    PopulationSizeComboBox.addItem(new Integer(10));
    PopulationSizeComboBox.addItem(new Integer(25));
    PopulationSizeComboBox.addItem(new Integer(50));
    PopulationSizeComboBox.addItem(new Integer(75));
    PopulationSizeComboBox.addItem(new Integer(100));
    PopulationSizeComboBox.addItem(new Integer(200));
    PopulationSizeComboBox.addItem(new Integer(500));
    PopulationSizeComboBox.setSelectedItem(new Integer(100));
    jLabel6.setText("Fitness threshold");
    jLabel6.setBounds(new Rectangle(288, 135, 109, 17));
    FitnessThresholdTextField.setText("20.0");
    FitnessThresholdTextField.setBounds(new Rectangle(289, 157, 95, 24));
    radioButtonGroup.add(DFSRadioButtonMenuItem);
    radioButtonGroup.add(BFSRadioButtonMenuItem);
    radioButtonGroup.add(IteratedRadioButtonMenuItem);
    radioButtonGroup.add(BestFirstRadioButtonMenuItem);
    radioButtonGroup.add(GeneticSearchRadioButtonMenuItem);
    jPanel1.setLayout(borderLayout2);
    jPanel1.setPreferredSize(new Dimension(400, 280));
    this.getContentPane().add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(traceTextArea, null);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1, null);
    jPanel2.add(startComboBox, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(goalComboBox, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(PopulationSizeComboBox, null);
    jPanel2.add(jLabel5, null);
    jPanel2.add(FitnessThresholdTextField, null);
    jPanel2.add(jLabel6, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(GeneticObjClassComboBox, null);
    jPanel2.add(NumGenerationsComboBox, null);
    jMenuBar1.add(FileMenu);
    jMenuBar1.add(AlgorithmMenu);
    jMenuBar1.add(HelpMenu);
    FileMenu.add(StartMenuItem);
    FileMenu.addSeparator();
    FileMenu.add(ClearMenuItem);
    FileMenu.addSeparator();
    FileMenu.add(ExitMenuItem);
    AlgorithmMenu.add(DFSRadioButtonMenuItem);
    AlgorithmMenu.add(BFSRadioButtonMenuItem);
    AlgorithmMenu.add(IteratedRadioButtonMenuItem);
    AlgorithmMenu.add(BestFirstRadioButtonMenuItem);
    AlgorithmMenu.add(GeneticSearchRadioButtonMenuItem);
    HelpMenu.add(AboutMenuItem);
    this.setJMenuBar(jMenuBar1);
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
   * Build a test graph of the cities in the midwestern United States. The
   * graph is created using <code>SearchNodes</code> and links.
   *
   * @return a graph of the cities in the midwest
   */
  public static SearchGraph buildTestGraph() {
    SearchGraph graph = new SearchGraph("test");

    // Build the example tree
    SearchNode roch = new SearchNode("Rochester", "Rochester");

    graph.put(roch);
    SearchNode sfalls = new SearchNode("Sioux Falls", "Sioux Falls");

    graph.put(sfalls);
    SearchNode mpls = new SearchNode("Minneapolis", "Minneapolis");

    graph.put(mpls);
    SearchNode lacrosse = new SearchNode("LaCrosse", "LaCrosse");

    graph.put(lacrosse);
    SearchNode fargo = new SearchNode("Fargo", "Fargo");

    graph.put(fargo);
    SearchNode stcloud = new SearchNode("St.Cloud", "St.Cloud");

    graph.put(stcloud);
    SearchNode duluth = new SearchNode("Duluth", "Duluth");

    graph.put(duluth);
    SearchNode wausau = new SearchNode("Wausau", "Wausau");

    graph.put(wausau);
    SearchNode gforks = new SearchNode("Grand Forks", "Grand Forks");

    graph.put(gforks);
    SearchNode bemidji = new SearchNode("Bemidji", "Bemidji");

    graph.put(bemidji);
    SearchNode ifalls = new SearchNode("International Falls", "International Falls");

    graph.put(ifalls);
    SearchNode gbay = new SearchNode("Green Bay", "Green Bay");

    graph.put(gbay);
    SearchNode madison = new SearchNode("Madison", "Madison");

    graph.put(madison);
    SearchNode dubuque = new SearchNode("Dubuque", "Dubuque");

    graph.put(dubuque);
    SearchNode rockford = new SearchNode("Rockford", "Rockford");

    graph.put(rockford);
    SearchNode chicago = new SearchNode("Chicago", "Chicago");

    graph.put(chicago);
    SearchNode milwaukee = new SearchNode("Milwaukee", "Milwaukee");

    graph.put(milwaukee);
    roch.addLinks(new SearchNode[]{ mpls, lacrosse, sfalls, dubuque });
    mpls.addLinks(new SearchNode[]{ duluth, stcloud, wausau });
    mpls.addLinks(new SearchNode[]{ lacrosse, roch });
    lacrosse.addLinks(new SearchNode[]{ madison, dubuque, roch });
    lacrosse.addLinks(new SearchNode[]{ mpls, gbay });
    sfalls.addLinks(new SearchNode[]{ fargo, roch });
    fargo.addLinks(new SearchNode[]{ sfalls, gforks, stcloud });
    gforks.addLinks(new SearchNode[]{ bemidji, fargo, ifalls });
    bemidji.addLinks(new SearchNode[]{ gforks, ifalls, stcloud, duluth });
    ifalls.addLinks(new SearchNode[]{ bemidji, duluth, gforks });
    duluth.addLinks(new SearchNode[]{ ifalls, mpls, bemidji });
    stcloud.addLinks(new SearchNode[]{ bemidji, mpls, fargo });
    dubuque.addLinks(new SearchNode[]{ lacrosse, rockford });
    rockford.addLinks(new SearchNode[]{ dubuque, madison, chicago });
    chicago.addLinks(new SearchNode[]{ rockford, milwaukee });
    milwaukee.addLinks(new SearchNode[]{ gbay, chicago });
    gbay.addLinks(new SearchNode[]{ wausau, milwaukee, lacrosse });
    wausau.addLinks(new SearchNode[]{ mpls, gbay });

    // Use straight line distance from Rochester as
    // cost for best-first search example.
    roch.cost = 0;  // goal
    sfalls.cost = 232;
    mpls.cost = 90;
    lacrosse.cost = 70;
    dubuque.cost = 140;
    madison.cost = 170;
    milwaukee.cost = 230;
    rockford.cost = 210;
    chicago.cost = 280;
    stcloud.cost = 140;
    duluth.cost = 180;
    bemidji.cost = 260;
    wausau.cost = 200;
    gbay.cost = 220;
    fargo.cost = 280;
    gforks.cost = 340;
    return graph;
  }


  /**
   * Sets the search to DEPTH_FIRST when the depth-first search radio button
   * is selected.
   *
   * @param e the ActionEvent for the radio button
   */
  void DFSRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    searchAlgorithm = DEPTH_FIRST;
    setTitle("Search Application - Depth-First Search");
    setActiveControls();  // turn parameter controls on/off
  }


  /**
   * Sets the search to BREADTH_FIRST when the breadth-first search radio button
   * is selected.
   *
   * @param e the ActionEvent for the radio button
   */
  void BFSRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    searchAlgorithm = BREADTH_FIRST;
    setTitle("Search Application - Breadth-First Search");
    setActiveControls();  // turn parameter controls on/off
  }


  /**
   * Sets the search to ITERATED when the iterated search radio button
   * is selected.
   *
   * @param e the ActionEvent for the radio button
   */
  void IteratedRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    searchAlgorithm = ITERATED;
    setTitle("Search Application - Iterated Search");
    setActiveControls();  // turn parameter controls on/off
  }


  /**
   * Sets the search to BEST_FIRST when the best-first search radio button
   * is selected.
   *
   * @param e the ActionEvent for the radio button
   */
  void BestFirstRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    searchAlgorithm = BEST_FIRST;
    setTitle("Search Application - Best-First Search");
    goalComboBox.setSelectedItem("Rochester");  // goal must be Rochester
    setActiveControls();                        // turn parameter controls on/off
  }


  /**
   * Sets the search to GENETIC_SEARCH when the genetic search radio button
   * is selected.
   *
   * @param e the ActionEvent for the radio button
   */
  void GeneticSearchRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    searchAlgorithm = GENETIC_SEARCH;
    setTitle("Search Application - Genetic Search");
    setActiveControls();  // turn parameter controls on/off
    if (geneticSearch == null) {
      geneticSearch = new GeneticSearch();
      geneticSearch.setTextArea(traceTextArea);
    }
  }


  /**
   * Sets up the active controls depending on what search algorithm was
   * selected.
   */
  private void setActiveControls() {
    if (searchAlgorithm == GENETIC_SEARCH) {
      GeneticObjClassComboBox.setEnabled(true);
      NumGenerationsComboBox.setEnabled(true);
      PopulationSizeComboBox.setEnabled(true);
      FitnessThresholdTextField.setEnabled(true);
      startComboBox.setEnabled(false);
      goalComboBox.setEnabled(false);
    } else {
      GeneticObjClassComboBox.setEnabled(false);
      NumGenerationsComboBox.setEnabled(false);
      PopulationSizeComboBox.setEnabled(false);
      FitnessThresholdTextField.setEnabled(false);
      startComboBox.setEnabled(true);
      if (searchAlgorithm == BEST_FIRST) {
        goalComboBox.setEnabled(false);  //best first goal is Rochester
      } else {
        goalComboBox.setEnabled(true);
      }
    }
    traceTextArea.setText("");  // clear the text area
  }


  /**
   * Starts a thread when START is selected.
   *
   * @param e the ActionEvent for the selection
   */
  void StartMenuItem_actionPerformed(ActionEvent e) {
    runnit = new Thread(this);
    runnit.start();  // start the thread running
  }


  /**
   * Clears the text box when CLEAR is selected.
   *
   * @param e the ActionEvent for the selection
   */
  void ClearMenuItem_actionPerformed(ActionEvent e) {
    traceTextArea.setText("");
  }


  /**
   * Ends the application when EXIT is selected.
   *
   * @param e the ActionEvent for the selection
   */
  void ExitMenuItem_actionPerformed(ActionEvent e) {
    System.exit(0);
  }


  /**
   * Displays the "About" dialog when the option is selected.
   *
   * @param e the ActionEvent for the selection
   */
  void AboutMenuItem_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About Search Application", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }


  /**
   *   Runs the selected algorithm in a separate thread, writing some trace
   *   information into the the text area of the application window. If debug
   *   is set on, additional trace information will be displayed.
   */
  public void run() {
    int method = 0;
    SearchNode answer = null;
    SearchNode startNode;
    String start = (String) startComboBox.getSelectedItem();

    startNode = graph.getNode(start);
    String goal = (String) goalComboBox.getSelectedItem();

    graph.reset();  // reset all nodes for another search
    switch (searchAlgorithm) {
      case DEPTH_FIRST : {
        traceTextArea.append("\n\nDepth-First Search for " + goal + ":\n\n");
        answer = graph.depthFirstSearch(startNode, goal);
        break;
      }
      case BREADTH_FIRST : {
        traceTextArea.append("\n\nBreadth-First Search for " + goal + ":\n\n");
        answer = graph.breadthFirstSearch(startNode, goal);
        break;
      }
      case ITERATED : {
        traceTextArea.append("\n\nIterated-Deepening Search for " + goal + ":\n\n");
        answer = graph.iterDeepSearch(startNode, goal);  //
        break;
      }
      case BEST_FIRST : {
        traceTextArea.append("\n\nBest-First Search for " + goal + ":\n\n");
        goalComboBox.setSelectedItem("Rochester");  // goal must be Rochester
        answer = graph.bestFirstSearch(startNode, "Rochester");
        break;
      }
      case GENETIC_SEARCH : {
        traceTextArea.append("\n\nGenetic Search using... \n\n");
        geneticSearch.setGeneticObjectClassName((String) GeneticObjClassComboBox.getSelectedItem());
        geneticSearch.setMaxNumPasses(((Integer) NumGenerationsComboBox.getSelectedItem()).intValue());
        geneticSearch.setDebugOn(debugOn);
        geneticSearch.setPopulationSize(((Integer) PopulationSizeComboBox.getSelectedItem()).intValue());
        try {
          geneticSearch.setFitnessThreshold(Double.valueOf(FitnessThresholdTextField.getText().trim()).doubleValue());
        } catch (Exception e) {
          geneticSearch.setFitnessThreshold(20.0);       // use default
          FitnessThresholdTextField.setText("20.0");
        }
        geneticSearch.init();  // re-initialize the genetic object population
        geneticSearch.search();
        break;
      }
    }                                                    // end switch
    if (searchAlgorithm != GENETIC_SEARCH) {
      if (answer == null) {
        traceTextArea.append("Could not find answer!\n");
      } else {
        traceTextArea.append("Answer found in node " + answer.label);
      }
    }
  }
}
