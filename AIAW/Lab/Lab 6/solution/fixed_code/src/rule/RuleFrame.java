package rule;

 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


/**
 * The <code>RuleFrame</code> class implements the GUI and application logic
 * that demonstrates boolean and fuzzy rule inferencing.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class RuleFrame extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 6141945881915788417L;
BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu FileMenu = new JMenu();
  JMenuItem StartMenuItem = new JMenuItem();
  JMenuItem ResetMenuItem = new JMenuItem();
  JMenuItem ExitMenuItem = new JMenuItem();
  JMenu DataMenu = new JMenu();
  JMenuItem DefaultsMenuItem = new JMenuItem();
  JMenuItem SetValuesMenuItem = new JMenuItem();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea traceTextArea = new JTextArea();
  JRadioButton forwardChainRadioButton = new JRadioButton();
  JRadioButton backChainRadioButton = new JRadioButton();
  ButtonGroup buttonGroup = new ButtonGroup();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField resultTextField = new JTextField();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JMenu HelpMenu = new JMenu();
  JMenuItem AboutMenuItem = new JMenuItem();
  JMenu RuleBaseMenu = new JMenu();
  JRadioButtonMenuItem VehiclesRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem BugsRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem PlantsRadioButtonMenuItem = new JRadioButtonMenuItem();
  JRadioButtonMenuItem MotorRadioButtonMenuItem = new JRadioButtonMenuItem();
  ButtonGroup ruleBaseButtonGroup = new ButtonGroup();
  JComboBox GoalComboBox = new JComboBox();
  static BooleanRuleBase bugs;
  static BooleanRuleBase plants;
  static BooleanRuleBase vehicles;
  static FuzzyRuleBase motor;
  static RuleBase currentRuleBase;


  /**
   * Creates a <code>RuleFrame</code> object.
   *
   */
  public RuleFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      vehicles = new BooleanRuleBase("Vehicles Rule Base");
      vehicles.setDisplay(traceTextArea);
      initVehiclesRuleBase(vehicles);
      currentRuleBase = vehicles;
      bugs = new BooleanRuleBase("Bugs Rule Base");
      bugs.setDisplay(traceTextArea);
      initBugsRuleBase(bugs);
      plants = new BooleanRuleBase("Plants Rule Base");
      plants.setDisplay(traceTextArea);
      initPlantsRuleBase(plants);
      motor = new FuzzyRuleBase("Motor Fuzzy Rule Base");
      motor.setDisplay(traceTextArea);
      initMotorRuleBase(motor);
      jbInit();

      // initialize textAreas and list controls
      VehiclesRadioButtonMenuItem_actionPerformed(null);
      currentRuleBase.displayRules(traceTextArea);
      currentRuleBase.displayVariables(traceTextArea);
      forwardChainRadioButton.setSelected(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initializes the GUI controls.
   *
   * @throws Exception if an error occurred during initialization
   *
   */
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(507, 522));
    this.setTitle("Rule Application");
    FileMenu.setText("File");
    StartMenuItem.setText("Start");
    StartMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        StartMenuItem_actionPerformed(e);
      }
    });
    ResetMenuItem.setText("Reset");
    ResetMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ResetMenuItem_actionPerformed(e);
      }
    });
    ExitMenuItem.setText("Exit");
    ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ExitMenuItem_actionPerformed(e);
      }
    });
    DataMenu.setText("Data");
    DefaultsMenuItem.setText("Use default values");
    DefaultsMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DefaultsMenuItem_actionPerformed(e);
      }
    });
    SetValuesMenuItem.setText("Set values...");
    SetValuesMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SetValuesMenuItem_actionPerformed(e);
      }
    });
    jPanel1.setPreferredSize(new Dimension(400, 100));
    jPanel1.setLayout(null);
    forwardChainRadioButton.setText("Forward chain");
    forwardChainRadioButton.setBounds(new Rectangle(19, 19, 103, 25));
    backChainRadioButton.setText("Backward chain");
    backChainRadioButton.setBounds(new Rectangle(17, 48, 154, 25));
    jPanel2.setLayout(borderLayout2);
    HelpMenu.setText("Help");
    AboutMenuItem.setText("About");
    AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AboutMenuItem_actionPerformed(e);
      }
    });
    RuleBaseMenu.setText("RuleBase");
    VehiclesRadioButtonMenuItem.setText("Vehicles");
    VehiclesRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        VehiclesRadioButtonMenuItem_actionPerformed(e);
      }
    });
    VehiclesRadioButtonMenuItem.setSelected(true);
    BugsRadioButtonMenuItem.setText("Bugs");
    BugsRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BugsRadioButtonMenuItem_actionPerformed(e);
      }
    });
    PlantsRadioButtonMenuItem.setText("Plants");
    PlantsRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        PlantsRadioButtonMenuItem_actionPerformed(e);
      }
    });
    MotorRadioButtonMenuItem.setText("Motor");
    MotorRadioButtonMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MotorRadioButtonMenuItem_actionPerformed(e);
      }
    });
    ruleBaseButtonGroup.add(VehiclesRadioButtonMenuItem);
    ruleBaseButtonGroup.add(BugsRadioButtonMenuItem);
    ruleBaseButtonGroup.add(PlantsRadioButtonMenuItem);
    ruleBaseButtonGroup.add(MotorRadioButtonMenuItem);
    GoalComboBox.setBounds(new Rectangle(170, 51, 126, 23));
    buttonGroup.add(forwardChainRadioButton);
    buttonGroup.add(backChainRadioButton);
    jLabel1.setText("Goal");
    jLabel1.setBounds(new Rectangle(171, 28, 106, 17));
    jLabel2.setText("Result");
    jLabel2.setBounds(new Rectangle(313, 29, 103, 17));
    resultTextField.setBounds(new Rectangle(312, 51, 176, 21));
    jPanel3.setLayout(flowLayout1);
    flowLayout1.setHgap(20);
    jMenuBar1.add(FileMenu);
    jMenuBar1.add(RuleBaseMenu);
    jMenuBar1.add(DataMenu);
    jMenuBar1.add(HelpMenu);
    FileMenu.add(StartMenuItem);
    FileMenu.addSeparator();
    FileMenu.add(ResetMenuItem);
    FileMenu.addSeparator();
    FileMenu.add(ExitMenuItem);
    DataMenu.add(DefaultsMenuItem);
    DataMenu.add(SetValuesMenuItem);
    this.getContentPane().add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(forwardChainRadioButton, null);
    jPanel1.add(backChainRadioButton, null);
    jPanel1.add(resultTextField, null);
    jPanel1.add(GoalComboBox, null);
    jPanel1.add(jLabel1, null);
    jPanel1.add(jLabel2, null);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(traceTextArea, null);
    this.getContentPane().add(jPanel3, BorderLayout.SOUTH);
    HelpMenu.add(AboutMenuItem);
    RuleBaseMenu.add(VehiclesRadioButtonMenuItem);
    RuleBaseMenu.add(BugsRadioButtonMenuItem);
    RuleBaseMenu.add(PlantsRadioButtonMenuItem);
    RuleBaseMenu.add(MotorRadioButtonMenuItem);
    setJMenuBar(jMenuBar1);
  }


  /**
   * Closes or repaints the window.
   *
   * @param e the WindowEvent object generated when window is closed or
   *           activated
   *
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    } if (e.getID() == WindowEvent.WINDOW_ACTIVATED) {
      e.getWindow().repaint();
    }
  }



  /**
   * Initializes the Bugs rule base.
   *
   * @param rb the BooleanRuleBase object to be initialized
   *
   */
  public void initBugsRuleBase(BooleanRuleBase rb) {
    RuleVariable bugClass = new RuleVariable(rb, "bugClass");

    bugClass.setLabels("arachnid insect");
    bugClass.setPromptText("What is the bug class?");
    RuleVariable insectType = new RuleVariable(rb, "insectType");

    insectType.setLabels("beetle orthoptera");
    insectType.setPromptText("What is the insect type?");
    RuleVariable species = new RuleVariable(rb, "species");

    species.setLabels("Spider Tick Ladybug Japanese_Beetle Cricket Praying_Mantis");
    species.setPromptText("What is the species?");
    RuleVariable color = new RuleVariable(rb, "color");

    color.setLabels("orange_and_black green_and_black black");
    color.setPromptText("What color is the bug?");
    RuleVariable size = new RuleVariable(rb, "size");

    size.setLabels("small large");
    size.setPromptText("What size is the bug?");
    RuleVariable leg_length = new RuleVariable(rb, "leg_length");

    leg_length.setLabels("short long");
    leg_length.setPromptText("What is the leg length?");
    RuleVariable antennae = new RuleVariable(rb, "antennae");

    antennae.setLabels("0 2");
    antennae.setPromptText("How many antennae does it have?");
    RuleVariable shape = new RuleVariable(rb, "shape");

    shape.setLabels("round elongated");
    shape.setPromptText("What shape is the bug's body?");
    RuleVariable legs = new RuleVariable(rb, "legs");

    legs.setLabels("6 8");
    legs.setPromptText("How many legs does it have?");
    RuleVariable wings = new RuleVariable(rb, "wings");

    wings.setLabels("0 2");
    wings.setPromptText("How many wings does it have?");

    // Note: at this point all variables values are NULL
    Condition cEquals = new Condition("=");
    Condition cNotEquals = new Condition("!=");

    // define rules
    new Rule(rb, "spider", new Clause[]{ new Clause(bugClass, cEquals, "arachnid"),
                                                       new Clause(leg_length, cEquals, "long") }, new Clause(species, cEquals, "Spider"));
    new Rule(rb, "tick", new Clause[]{ new Clause(bugClass, cEquals, "arachnid"),
                                                   new Clause(leg_length, cEquals, "short") }, new Clause(species, cEquals, "Tick"));
    new Rule(rb, "ladybug", new Clause[]{ new Clause(insectType, cEquals, "beetle"),
                                                         new Clause(color, cEquals, "orange_and_black") }, new Clause(species, cEquals, "Ladybug"));
    new Rule(rb, "Japanese_Beetle", new Clause[]{ new Clause(insectType, cEquals, "beetle"),
                                                                        new Clause(color, cEquals, "green_and_black") }, new Clause(species, cEquals, "Japanese_Beetle"));
    new Rule(rb, "cricket", new Clause[]{ new Clause(insectType, cEquals, "orthoptera"),
                                                         new Clause(color, cEquals, "black") }, new Clause(species, cEquals, "Cricket"));
    new Rule(rb, "praying_mantis", new Clause[]{ new Clause(insectType, cEquals, "orthoptera"),
                                                                      new Clause(color, cEquals, "green"),
                                                                      new Clause(size, cEquals, "large") }, new Clause(species, cEquals, "Praying_Mantis"));
    new Rule(rb, "class_is_Arachnid1", new Clause[]{ new Clause(antennae, cEquals, "0"),
                                                                           new Clause(legs, cEquals, "8") }, new Clause(bugClass, cEquals, "arachnid"));
    new Rule(rb, "class_is_Arachnid2", new Clause(wings, cEquals, "0"), new Clause(bugClass, cEquals, "arachnid"));
    new Rule(rb, "class_is_Insect1", new Clause[]{ new Clause(antennae, cEquals, "2"),
                                                                       new Clause(legs, cEquals, "6") }, new Clause(bugClass, cEquals, "insect"));
    new Rule(rb, "class_is_Insect2", new Clause(wings, cEquals, "2"), new Clause(bugClass, cEquals, "insect"));
    new Rule(rb, "typeBeetle", new Clause[]{ new Clause(bugClass, cEquals, "insect"),
                                                               new Clause(size, cEquals, "small"),
                                                               new Clause(shape, cEquals, "round") }, new Clause(insectType, cEquals, "beetle"));
    new Rule(rb, "typeOrthoptera", new Clause[]{ new Clause(bugClass, cEquals, "insect"),
                                                                       new Clause(size, cNotEquals, "small"),
                                                                       new Clause(shape, cEquals, "elongated") }, new Clause(insectType, cEquals, "orthoptera"));
  }


  /**
   * Initializes the plants rule base.
   *
   * @param rb the BooleanRuleBase object to be initialized
   *
   */
  public void initPlantsRuleBase(BooleanRuleBase rb) {
    RuleVariable family = new RuleVariable(rb, "family");

    family.setLabels("cypress pine bald_cypress ");
    family.setPromptText("What family is it?");
    RuleVariable treeClass = new RuleVariable(rb, "treeClass");

    treeClass.setLabels("angiosperm gymnosperm");
    treeClass.setPromptText("What tree class is it?");
    RuleVariable plantType = new RuleVariable(rb, "plantType");

    plantType.setLabels("herb vine tree shrub");
    plantType.setPromptText("What type of plant is it?");
    RuleVariable stem = new RuleVariable(rb, "stem");

    stem.setLabels("woody green");
    stem.setPromptText("What type of stem does it have?");
    RuleVariable stemPosition = new RuleVariable(rb, "stemPosition");

    stemPosition.setLabels("upright creeping");
    stemPosition.setPromptText("What is the stem position?");
    RuleVariable one_main_trunk = new RuleVariable(rb, "one_main_trunk");

    one_main_trunk.setLabels("yes no");
    one_main_trunk.setPromptText("Does it have one main trunk?");
    RuleVariable broad_and_flat_leaves = new RuleVariable(rb, "broad_and_flat_leaves");

    broad_and_flat_leaves.setLabels("yes no");
    broad_and_flat_leaves.setPromptText("Are the leaves broad and flat?");
    RuleVariable leaf_shape = new RuleVariable(rb, "leaf_shape");

    leaf_shape.setLabels("needlelike scalelike");
    leaf_shape.setPromptText("What shape are the leaves?");
    RuleVariable needle_pattern = new RuleVariable(rb, "needle_pattern");

    needle_pattern.setLabels("random even");
    needle_pattern.setPromptText("What is the needle pattern?");
    RuleVariable silver_bands = new RuleVariable(rb, "silver_bands");

    silver_bands.setLabels("yes no");
    silver_bands.setPromptText("Does it have silver bands?");

    // Note: at this point all variables values are NULL
    Condition cEquals = new Condition("=");
    new Condition("!=");

    // define rules
    new Rule(rb, "cypress", new Clause[]{ new Clause(treeClass, cEquals, "gymnosperm"),
                                                         new Clause(leaf_shape, cEquals, "scalelike") },
                                                         new Clause(family, cEquals, "cypress"));
    new Rule(rb, "pine1", new Clause[]{ new Clause(treeClass, cEquals, "gymnosperm"),
                                                     new Clause(leaf_shape, cEquals, "needlelike"),
                                                     new Clause(needle_pattern, cEquals, "random") },
                                                     new Clause(family, cEquals, "pine"));
    new Rule(rb, "pine2", new Clause[]{ new Clause(treeClass, cEquals, "gymnosperm"),
                                                     new Clause(leaf_shape, cEquals, "needlelike"),
                                                     new Clause(needle_pattern, cEquals, "even"),
                                                     new Clause(silver_bands, cEquals, "yes") },
                                                     new Clause(family, cEquals, "pine"));
    new Rule(rb, "baldCypress", new Clause[]{ new Clause(treeClass, cEquals, "gymnosperm"),
                                                                 new Clause(leaf_shape, cEquals, "needlelike"),
                                                                 new Clause(needle_pattern, cEquals, "even"),
                                                                 new Clause(silver_bands, cEquals, "no") },
                                                                 new Clause(family, cEquals, "bald_cypress"));
    new Rule(rb, "angiosperm", new Clause[]{ new Clause(plantType, cEquals, "tree"),
                                                               new Clause(broad_and_flat_leaves, cEquals, "yes") },
                                                               new Clause(treeClass, cEquals, "angiosperm"));
    new Rule(rb, "gymnosperm", new Clause[]{ new Clause(plantType, cEquals, "tree"),
                                                               new Clause(broad_and_flat_leaves, cEquals, "no") },
                                                               new Clause(treeClass, cEquals, "gymnosperm"));
    new Rule(rb, "herb", new Clause(stem, cEquals, "green"), new Clause(plantType, cEquals, "herb"));
    new Rule(rb, "vine", new Clause[]{ new Clause(stem, cEquals, "woody"),
                                                   new Clause(stemPosition, cEquals, "creeping") },
                                                   new Clause(plantType, cEquals, "vine"));
    new Rule(rb, "tree", new Clause[]{ new Clause(stem, cEquals, "woody"),
                                                   new Clause(stemPosition, cEquals, "upright"),
                                                   new Clause(one_main_trunk, cEquals, "yes") },
                                                   new Clause(plantType, cEquals, "tree"));
    new Rule(rb, "shrub", new Clause[]{ new Clause(stem, cEquals, "woody"),
                                                     new Clause(stemPosition, cEquals, "upright"),
                                                     new Clause(one_main_trunk, cEquals, "no") },
                                                     new Clause(plantType, cEquals, "shrub"));
  }



  /**
   * Initializes the vehicles rule base.
   *
   * @param rb the BooleanRuleBase object to be initialized
   *
   */
  public void initVehiclesRuleBase(BooleanRuleBase rb) {
    RuleVariable vehicle = new RuleVariable(rb, "vehicle");

    vehicle.setLabels("Bicycle Tricycle MotorCycle Sports_Car Sedan MiniVan Sports_Utility_Vehicle");
    vehicle.setPromptText("What kind of vehicle is it?");
    RuleVariable vehicleType = new RuleVariable(rb, "vehicleType");

    vehicleType.setLabels("cycle automobile");
    vehicleType.setPromptText("What type of vehicle is it?");
    RuleVariable size = new RuleVariable(rb, "size");

    size.setLabels("small medium large");
    size.setPromptText("What size is the vehicle?");
    RuleVariable motor = new RuleVariable(rb, "motor");

    motor.setLabels("yes no");
    motor.setPromptText("Does the vehicle have a motor?");
    RuleVariable num_wheels = new RuleVariable(rb, "num_wheels");

    num_wheels.setLabels("2 3 4");
    num_wheels.setPromptText("How many wheels does it have?");
    RuleVariable num_doors = new RuleVariable(rb, "num_doors");

    num_doors.setLabels("2 3 4");
    num_doors.setPromptText("How many doors does it have?");

    // Note: at this point all variables values are NULL
    Condition cEquals = new Condition("=");
    new Condition("!=");
    Condition cLessThan = new Condition("<");

    // define rules
    new Rule(rb, "bicycle", new Clause[]{ new Clause(vehicleType, cEquals, "cycle"),
                                                         new Clause(num_wheels, cEquals, "2"),
                                                         new Clause(motor, cEquals, "no") },
                                                         new Clause(vehicle, cEquals, "Bicycle"));
    new Rule(rb, "tricycle", new Clause[]{ new Clause(vehicleType, cEquals, "cycle"),
                                                           new Clause(num_wheels, cEquals, "3"),
                                                           new Clause(motor, cEquals, "no") },
                                                           new Clause(vehicle, cEquals, "Tricycle"));
    new Rule(rb, "motorcycle", new Clause[]{ new Clause(vehicleType, cEquals, "cycle"),
                                                               new Clause(num_wheels, cEquals, "2"),
                                                               new Clause(motor, cEquals, "yes") },
                                                               new Clause(vehicle, cEquals, "Motorcycle"));
    new Rule(rb, "sportsCar", new Clause[]{ new Clause(vehicleType, cEquals, "automobile"),
                                                             new Clause(size, cEquals, "small"),
                                                             new Clause(num_doors, cEquals, "2") },
                                                             new Clause(vehicle, cEquals, "Sports_Car"));
    new Rule(rb, "sedan", new Clause[]{ new Clause(vehicleType, cEquals, "automobile"),
                                                     new Clause(size, cEquals, "medium"),
                                                     new Clause(num_doors, cEquals, "4") },
                                                     new Clause(vehicle, cEquals, "Sedan"));
    new Rule(rb, "miniVan", new Clause[]{ new Clause(vehicleType, cEquals, "automobile"),
                                                         new Clause(size, cEquals, "medium"),
                                                         new Clause(num_doors, cEquals, "3") },
                                                         new Clause(vehicle, cEquals, "MiniVan"));
    new Rule(rb, "SUV", new Clause[]{ new Clause(vehicleType, cEquals, "automobile"),
                                                 new Clause(size, cEquals, "large"),
                                                 new Clause(num_doors, cEquals, "4") },
                                                 new Clause(vehicle, cEquals, "Sports_Utility_Vehicle"));
    new Rule(rb, "Cycle", new Clause(num_wheels, cLessThan, "4"), new Clause(vehicleType, cEquals, "cycle"));
    new Rule(rb, "Automobile", new Clause[]{ new Clause(num_wheels, cEquals, "4"),
                                                               new Clause(motor, cEquals, "yes") },
                                                               new Clause(vehicleType, cEquals, "automobile"));
  }


  /**
   * Initializes the motor rule base.
   *
   * @param rb the FuzzyRuleBase object to be initialized
   *
   */
  public void initMotorRuleBase(FuzzyRuleBase rb) {

    // set correlation method one of Product, Minimize
    rb.setCorrelationMethod(FuzzyDefs.PRODUCT);

    // set inferencing method, one of FuzzyAdd, MinMax, ProductOr
    rb.setInferenceMethod(FuzzyDefs.FUZZYADD);

    // set defuzzification method, one of Centroid, MaxHeight
    rb.setDefuzzifyMethod(FuzzyDefs.CENTROID);

    // create continuous variable
    ContinuousFuzzyRuleVariable temp = new ContinuousFuzzyRuleVariable(rb, "temp", 0, 100);

    temp.addSetShoulder("cold", 0.1, 0, 25, FuzzyDefs.LEFT);
    temp.addSetTriangle("cool", 0.1, 10, 30, 50);
    temp.addSetTrapezoid("medium", 0.1, 25, 40, 60, 75);
    temp.addSetTriangle("warm", 0.1, 50, 70, 90);
    temp.addSetShoulder("hot", 0.1, 75, 100, FuzzyDefs.RIGHT);
    ContinuousFuzzyRuleVariable humidity = new ContinuousFuzzyRuleVariable(rb, "humidity", 0, 100);

    humidity.addSetShoulder("dry", 0.1, 0, 25, FuzzyDefs.LEFT);
    humidity.addSetTriangle("pleasant", 0.1, 10, 30, 50);
    humidity.addSetTrapezoid("comfortable", 0.1, 25, 40, 60, 75);
    humidity.addSetTriangle("sticky", 0.1, 50, 70, 90);
    humidity.addSetShoulder("sweltering", 0.1, 75, 100, FuzzyDefs.RIGHT);

    // create continuous variable
    ContinuousFuzzyRuleVariable motor = new ContinuousFuzzyRuleVariable(rb, "motor", 0, 100);

    motor.addSetShoulder("slow", 0.1, 0, 40, FuzzyDefs.LEFT);
    motor.addSetTrapezoid("medium", 0.1, 25, 40, 60, 75);
    motor.addSetShoulder("fast", 0.1, 60, 100, FuzzyDefs.RIGHT);

    int compareIs = FuzzyOperator.CmpIs ;
    int assignIs = FuzzyOperator.AsgnIs;

    // make conditional rule
    new FuzzyRule(rb, "slowRule",
                           new FuzzyClause[]{ rb.createClause(temp, compareIs, "", "cold"),
                                              rb.createClause(humidity, compareIs, "", "pleasant")},
                                              rb.createClause(motor, assignIs, "", "slow"));

    // make conditional rule
    new FuzzyRule(rb, "mediumRule",
                             new FuzzyClause[]{ rb.createClause(temp, compareIs, "", "medium"),
                                                rb.createClause(humidity, compareIs, "", "comfortable") },
                                                rb.createClause(motor, assignIs, "", "medium"));

    // make conditional rule
    new FuzzyRule(rb, "fastRule",
                           new FuzzyClause[]{ rb.createClause(temp, compareIs, "", "hot"),
                                              rb.createClause(humidity, compareIs, "", "sticky") },
                                              rb.createClause(motor, assignIs, "", "fast"));

    // make conditional rule, "V" stands for very
    new FuzzyRule(rb, "veryFastRule",
                               new FuzzyClause[]{ rb.createClause(temp, compareIs, "V", "hot"),
                                                  rb.createClause(humidity, compareIs, "V", "sticky") },
                                                  rb.createClause(motor, assignIs, "V", "fast"));
  }


  /**
   * Sets values for the vehicles forward chaining demonstration.
   *
   * @param rb the BooleanRuleBase object set for forward chaining
   *
   */
  public void demoVehiclesFC(BooleanRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Vehicles ForwardChain Demo ---\n ");

    // should be a Mini-Van
    rb.setVariableValue("vehicle", null);
    rb.setVariableValue("vehicleType", null);
    rb.setVariableValue("size", "medium");
    rb.setVariableValue("num_wheels", "4");
    rb.setVariableValue("num_doors", "3");
    rb.setVariableValue("motor", "yes");
    rb.displayVariables(traceTextArea);
  }


  /**
   * Sets values for the vehicles backward chaining demonstration.
   *
   * @param rb the BooleanRuleBase object set for backward chaining
   *
   */
  public void demoVehiclesBC(BooleanRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Vehicles BackwardChain Demo ---\n ");

    // should be a minivan
    rb.setVariableValue("vehicle", null);
    rb.setVariableValue("vehicleType", null);
    rb.setVariableValue("size", "medium");
    rb.setVariableValue("num_wheels", "4");
    rb.setVariableValue("num_doors", "3");
    rb.setVariableValue("motor", "yes");
    rb.displayVariables(traceTextArea);
  }


  /**
   * Sets values for the bugs backward chaining demonstration.
   *
   * @param rb the BooleanRuleBase object set for backward chaining
   *
   */
  public void demoBugsBC(BooleanRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Bugs BackwardChain Demo ---");
    rb.setVariableValue("wings", "2");
    rb.setVariableValue("legs", "6");
    rb.setVariableValue("shape", "round");
    rb.setVariableValue("antennae", "2");
    rb.setVariableValue("color", "orange_and_black");
    rb.setVariableValue("leg_length", "long");
    rb.setVariableValue("size", "small");
    rb.displayVariables(traceTextArea);
  }


  /**
   * Sets values for the bugs forward chaining demonstration.
   *
   * @param rb the BooleanRuleBase object set for forward chaining
   *
   */
  public void demoBugsFC(BooleanRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Bugs ForwardChain Demo ---\n");

    // should be a insect, ladybug
    rb.setVariableValue("bugClass", null);
    rb.setVariableValue("insectType", null);
    rb.setVariableValue("wings", "2");
    rb.setVariableValue("legs", "6");
    rb.setVariableValue("shape", "round");
    rb.setVariableValue("antennae", "2");
    rb.setVariableValue("color", "orange_and_black");
    rb.setVariableValue("leg_length", "long");
    rb.setVariableValue("size", "small");
    rb.displayVariables(traceTextArea);
  }


  /**
   * Sets values for the plants backward chaining demonstration.
   *
   * @param rb the BooleanRuleBase object set for backward chaining
   *
   */
  public void demoPlantsBC(BooleanRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Plants BackwardChain Demo ---\n ");

    // should be a pine tree
    rb.setVariableValue("stem", "woody");
    rb.setVariableValue("stemPosition", "upright");
    rb.setVariableValue("one_main_trunk", "yes");
    rb.setVariableValue("broad_and_flat_leaves", "no");
    rb.setVariableValue("leaf_shape", "needlelike");
    rb.setVariableValue("needle_pattern", "random");
    rb.displayVariables(traceTextArea);
  }


  /**
   * Sets values for the plants forward chaining demonstration.
   *
   * @param rb the BooleanRuleBase object set for forward chaining
   *
   */
  public void demoPlantsFC(BooleanRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Plants ForwardChain Demo --- \n ");

    // should be a pine tree
    rb.setVariableValue("stem", "woody");
    rb.setVariableValue("stemPosition", "upright");
    rb.setVariableValue("one_main_trunk", "yes");
    rb.setVariableValue("broad_and_flat_leaves", "no");
    rb.setVariableValue("leaf_shape", "needlelike");
    rb.setVariableValue("needle_pattern", "random");
    rb.displayVariables(traceTextArea);
  }


  /**
   * Fuzzy rules base does not support backward chaining.
   *
   * @param rb the FuzzyRuleBase object
   *
   */
  public void demoMotorBC(FuzzyRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Motor BackwardChain Demo ---");
    traceTextArea.append("\n  Fuzzy Rule Base does not support backward inferencing!");
  }


  /**
   * Sets values for the motor forward chaining demonstration.
   *
   * @param rb the FuzzyRuleBase object set for forward chaining
   *
   */
  public void demoMotorFC(FuzzyRuleBase rb) {
    traceTextArea.append("\n --- Setting Values for Motor ForwardChain Demo ---\n");
    ContinuousFuzzyRuleVariable temp = (ContinuousFuzzyRuleVariable) rb.getVariable("temp");
    ContinuousFuzzyRuleVariable humidity = (ContinuousFuzzyRuleVariable) rb.getVariable("humidity");

    temp.setNumericValue(90.0);
    humidity.setNumericValue(70.0);
    rb.displayVariables(traceTextArea);
  }


  /**
   * Starts the inferencing cycle.
   *
   * @param e the ActionEvent object generated when start was selected
   *
   */
  void StartMenuItem_actionPerformed(ActionEvent e) {
    String goal = null;
    RuleVariable goalVar = (RuleVariable) GoalComboBox.getSelectedItem();

    traceTextArea.append("\n --- Starting Inferencing Cycle --- \n");
    currentRuleBase.displayVariables(traceTextArea);
    if (forwardChainRadioButton.isSelected() == true) {
      currentRuleBase.forwardChain();
    }
    if (backChainRadioButton.isSelected() == true) {
      if (currentRuleBase instanceof BooleanRuleBase) {
        if (goalVar == null) {
          traceTextArea.append("Goal variable is not defined!");  // give warning
        } else {
          goal = goalVar.getName();
        }
      }
      currentRuleBase.backwardChain(goal);
    }
    currentRuleBase.displayVariables(traceTextArea);
    traceTextArea.append("\n --- Ending Inferencing Cycle --- \n");

    // report back chain results if any
    if (goalVar != null) {
      Object result = goalVar.getValue();

      if (result == null) {
        result = "null";
      }
      resultTextField.setText((String) result);
    }
  }


  /**
   * Resets the current rule base.
   *
   * @param e the ActionEvent object generated when reset was selected
   *
   */
  void ResetMenuItem_actionPerformed(ActionEvent e) {

    // Clear the text for TextArea
    traceTextArea.setText("");
    currentRuleBase.reset();
  }


  /**
   * Exits the application.
   *
   * @param e the ActionEvent object generated when exit was selected
   *
   */
  void ExitMenuItem_actionPerformed(ActionEvent e) {
    System.exit(0);
  }


  /**
   *  Displays a dialog and allow the user to set variable values.
   *
   * @param e the ActionEvent object generated when the set values was selected
   */
  void SetValuesMenuItem_actionPerformed(ActionEvent e) {

    // open the dialog to set values in the rulebase
    if (currentRuleBase instanceof BooleanRuleBase) {
      BooleanRuleBaseVariablesDialog dlg = new BooleanRuleBaseVariablesDialog(this, "Set Boolean Rule Base Variables", true);

      dlg.setRuleBase((BooleanRuleBase) currentRuleBase);
      Point loc = this.getLocation();

      dlg.setLocation(loc.x + 20, loc.y + 20);
      dlg.show();
    } else {

      // allow user to set fuzzy rule variables
      FuzzyRuleBaseVariablesDialog dlg = new FuzzyRuleBaseVariablesDialog(this, "Set Fuzzy Rule Base Variables", true);

      dlg.setRuleBase((FuzzyRuleBase) currentRuleBase);
      Point loc = this.getLocation();

      dlg.setLocation(loc.x + 20, loc.y + 20);
      dlg.show();
    }
  }


  /**
   * Sets the defaults.
   *
   * @param e the ActionEvent object generated when default was selected
   *
   */
  void DefaultsMenuItem_actionPerformed(ActionEvent e) {

    // set all rulebase values to their default (demo?) values
    if (forwardChainRadioButton.isSelected()) {
      if (currentRuleBase == vehicles) {
        demoVehiclesFC((BooleanRuleBase) currentRuleBase);
      }
      if (currentRuleBase == plants) {
        demoPlantsFC((BooleanRuleBase) currentRuleBase);
      }
      if (currentRuleBase == bugs) {
        demoBugsFC((BooleanRuleBase) currentRuleBase);
      }
      if (currentRuleBase == motor) {
        demoMotorFC((FuzzyRuleBase) currentRuleBase);
      }
    } else {
      if (currentRuleBase == vehicles) {
        demoVehiclesBC((BooleanRuleBase) currentRuleBase);
      }
      if (currentRuleBase == plants) {
        demoPlantsBC((BooleanRuleBase) currentRuleBase);
      }
      if (currentRuleBase == bugs) {
        demoBugsBC((BooleanRuleBase) currentRuleBase);
      }
      if (currentRuleBase == motor) {
        demoMotorBC((FuzzyRuleBase) currentRuleBase);
      }
    }
  }


  /**
   *   Changes the context from one rule base to another and
   *   clears the text areas.
   */
  void switchRuleBase() {
    traceTextArea.setText("");  // clear the text area
    resultTextField.setText("");
    currentRuleBase.reset();    // reset the rule base
    currentRuleBase.displayRules(traceTextArea);
    currentRuleBase.displayVariables(traceTextArea);
    if (GoalComboBox.getItemCount() > 0) {
      GoalComboBox.removeAllItems();
    }
    Vector<?> goalVars = currentRuleBase.getGoalVariables();

    for (int i = 0; i < goalVars.size(); i++) {
      GoalComboBox.addItem(goalVars.elementAt(i));
    }
  }


  /**
   * Displays the About dialog.
   *
   * @param e the ActionEvent object generated when About was selected
   *
   */
  void AboutMenuItem_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About Rule Application", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }


  /**
   * Switches to the vehicles rule base.
   *
   * @param e the ActionEvent object generated when the vehicles button was clicked
   *
   */
  void VehiclesRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    currentRuleBase = vehicles;
    setTitle("Rule Application - Vehicles Rule Base");
    switchRuleBase();
    GoalComboBox.setSelectedItem(((BooleanRuleBase) currentRuleBase).getVariable("vehicle"));
  }


  /**
   * Switches to the bugs rule base.
   *
   * @param e the ActionEvent object generated when the bugs button was clicked
   *
   */
  void BugsRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    currentRuleBase = bugs;
    setTitle("Rule Application - Bugs Rule Base");
    switchRuleBase();
    GoalComboBox.setSelectedItem(((BooleanRuleBase) currentRuleBase).getVariable("species"));
  }


  /**
   * Switches to the plants rule base.
   *
   * @param e the ActionEvent object generated when the plants button was clicked
   *
   */
  void PlantsRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    currentRuleBase = plants;
    setTitle("Rule Application - Plants Rule Base");
    switchRuleBase();
    GoalComboBox.setSelectedItem(((BooleanRuleBase) currentRuleBase).getVariable("family"));
  }


  /**
   * Switches to the motor rule base.
   *
   * @param e the ActionEvent object generated when the motor button was clicked
   *
   */
  void MotorRadioButtonMenuItem_actionPerformed(ActionEvent e) {
    currentRuleBase = motor;
    setTitle("Rule Application - Motor Fuzzy Rule Base");
    switchRuleBase();

    // fuzzy does not support back chaining, so don't set the goal combo box
  }
}
