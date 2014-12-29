package infofilter;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import java.util.*;
import java.beans.*;

import ciagent.*;


/**
 * The <code>InfoFilterFrame</code> class implements the GUI and the
 * logic for the InfoFilter application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class InfoFilterFrame extends JFrame implements CIAgentEventListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = -5293818336829049276L;
JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem resetMenuItem = new JMenuItem();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane3 = new JScrollPane();
  JTextArea articleTextArea = new JTextArea();
  GridLayout gridLayout1 = new GridLayout();
  GridLayout gridLayout2 = new GridLayout();
  JMenuItem downloadNewsGroupMenuItem = new JMenuItem();
  JMenuItem saveArticleMenuItem = new JMenuItem();
  JMenuItem loadArticleMenuItem = new JMenuItem();
  JMenuItem exitMenuItem = new JMenuItem();
  JMenu jMenu1 = new JMenu();
  JMenu jMenu2 = new JMenu();
  JMenu jMenu3 = new JMenu();
  JMenu jMenu5 = new JMenu();
  JMenuItem keywordsMenuItem = new JMenuItem();
  JMenuItem addArticleMenuItem = new JMenuItem();
  JMenuItem addAllMenuItem = new JMenuItem();
  ButtonGroup useButtonGroup = new ButtonGroup();
  ButtonGroup feedbackButtonGroup = new ButtonGroup();
  JMenuItem aboutMenuItem = new JMenuItem();
  JMenuItem cutMenuItem = new JMenuItem();
  java.awt.FileDialog openFileDialog;
  java.awt.FileDialog saveFileDialog;
  protected String[] columnNameList = { COL_SUBJECT, COL_SCORE, COL_RATING };
  protected Object[][] data = null;
  final static int NUM_COLS = 3;
  final static int COL_SUBJECTID = 0;
  private final static String COL_SUBJECT = "Subject";
  final static int COL_SCOREID = 1;
  private final static String COL_SCORE = "Score";
  final static int COL_RATINGID = 2;
  private final static String COL_RATING = "Rating";
  Vector<NewsArticle> articles = new Vector<NewsArticle>();            // list of downloaded articles
  FilterAgent filterAgent = new FilterAgent();
  NewsReaderAgent newsReaderAgent = new NewsReaderAgent();
  URLReaderAgent uRLReaderAgent = new URLReaderAgent();
  NewsArticle currentArt;                     // currently selected article
  boolean scored = false;                     // true if articles were scored
  int filterType = 0;
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable articleTable = new JTable();
  private TableModel articleTableModel = null;
  JMenuItem downloadURLMenuItem = new JMenuItem();
  JCheckBoxMenuItem useKeywordsCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem useClustersCheckBoxMenuItem = new JCheckBoxMenuItem();
  JCheckBoxMenuItem useFeedbackCheckBoxMenuItem = new JCheckBoxMenuItem();

  String titleBarText = "CIAgent InfoFilter Application" ;
  JLabel filterAgentStatusLabel = new JLabel();

  /**
   * Creates a <code>InfoFilterFrame</code> object.
   *
   */
  public InfoFilterFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
      init();  // do local intializations
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initializes and starts the agents in the application.
   *
   */
  private void init() {
    // see if a serialized FilterAgent exists
    try {
     FilterAgent tmpFilterAgent = FilterAgent.restoreFromFile(FilterAgent.fileName);
     if (tmpFilterAgent != null) filterAgent = tmpFilterAgent;
    } catch (Exception e) {
      // no error, just catch the exception and use default/new agent
    }
    filterAgent.infoFilter = this;
    filterAgent.addCIAgentEventListener(this);  // for trace msgs

    newsReaderAgent.addCIAgentEventListener(this);
    newsReaderAgent.initialize();               // intialize it
    newsReaderAgent.startAgentProcessing();     // start it running
    uRLReaderAgent.addCIAgentEventListener(this);
    uRLReaderAgent.initialize();                // initialize it
    uRLReaderAgent.startAgentProcessing();      // start it running
    filterAgent.initialize();            // initialize it
    filterAgent.startAgentProcessing();  // start the Filter agent thread
    openFileDialog = new java.awt.FileDialog(this);
    openFileDialog.setMode(FileDialog.LOAD);
    openFileDialog.setTitle("Open");
    saveFileDialog = new java.awt.FileDialog(this);
    saveFileDialog.setMode(FileDialog.SAVE);
    saveFileDialog.setTitle("Save");
  }


  /**
   * Initializes the GUI controls.
   *
   * @throws Exception if any errors occur during initialization
   *
   */
  private void jbInit() throws Exception {
    useFeedbackCheckBoxMenuItem.setText("using Feedback");
    useFeedbackCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useFeedbackCheckBoxMenuItem_actionPerformed(e);
      }
    });
    if (filterAgent.isRatingNetTrained()) {
      useFeedbackCheckBoxMenuItem.setEnabled(true);
    } else {
      useFeedbackCheckBoxMenuItem.setEnabled(false);
    }
    useClustersCheckBoxMenuItem.setText("using Clusters");
    useClustersCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useClustersCheckBoxMenuItem_actionPerformed(e);
      }
    });
    if (filterAgent.isClusterNetTrained()) {
     useClustersCheckBoxMenuItem.setEnabled(true);
     } else {
     useClustersCheckBoxMenuItem.setEnabled(false);
    }
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(500, 395));
    this.setTitle(titleBarText + " - using Keywords");
    menuFile.setText("File");
    resetMenuItem.setText("Clear all");
    resetMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resetMenuItem_actionPerformed(e);
      }
    });
    jLabel1.setText("Articles");
    articleTable.setPreferredSize(new Dimension(500, 300));
    jScrollPane3.setPreferredSize(new Dimension(500, 200));
    jPanel1.setLayout(gridLayout1);
    jPanel2.setLayout(gridLayout2);
    downloadNewsGroupMenuItem.setText("Download news group...");
    downloadNewsGroupMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        downloadNewsGroupMenuItem_actionPerformed(e);
      }
    });
    saveArticleMenuItem.setText("Save article...");
    saveArticleMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveArticleMenuItem_actionPerformed(e);
      }
    });
    saveArticleMenuItem.setEnabled(false) ;
    loadArticleMenuItem.setText("Load Article...");
    loadArticleMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadArticleMenuItem_actionPerformed(e);
      }
    });
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exitMenuItem_actionPerformed(e);
      }
    });
    jMenu1.setText("Profile");
    jMenu2.setText("Edit");
    jMenu3.setText("Filter");
    jMenu5.setText("Help");
    keywordsMenuItem.setText("Customize...");
    keywordsMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        keywordsMenuItem_actionPerformed(e);
      }
    });
    addArticleMenuItem.setText("Add article");
    addArticleMenuItem.setEnabled(false) ;
    addArticleMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addArticleMenuItem_actionPerformed(e);
      }
    });
    addAllMenuItem.setText("Add all articles");
    addAllMenuItem.setEnabled(false) ;
    addAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAllMenuItem_actionPerformed(e);
      }
    });

    aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aboutMenuItem_actionPerformed(e);
      }
    });
    jPanel1.setMinimumSize(new Dimension(500, 200));
    jPanel2.setPreferredSize(new Dimension(500, 100));
    downloadURLMenuItem.setText("Download URL...");
    downloadURLMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        downloadURLMenuItem_actionPerformed(e);
      }
    });
    useKeywordsCheckBoxMenuItem.setText("using Keywords");
    useKeywordsCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useKeywordsCheckBoxMenuItem_actionPerformed(e);
      }
    });
    useKeywordsCheckBoxMenuItem.setSelected(true);
    filterAgentStatusLabel.setText("FilterAgent status:");
    useButtonGroup.add(useKeywordsCheckBoxMenuItem);
    useButtonGroup.add(useClustersCheckBoxMenuItem);
    useButtonGroup.add(useFeedbackCheckBoxMenuItem);
    aboutMenuItem.setText("About");
    cutMenuItem.setText("Cut");
    cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cutMenuItem_actionPerformed(e);
      }
    });
    menuFile.add(resetMenuItem);
    menuFile.addSeparator();
    menuFile.add(downloadNewsGroupMenuItem);
    menuFile.add(downloadURLMenuItem);
    menuFile.addSeparator();
    menuFile.add(saveArticleMenuItem);
    menuFile.add(loadArticleMenuItem);
    menuFile.addSeparator();
    menuFile.add(exitMenuItem);
    menuBar1.add(menuFile);
    menuBar1.add(jMenu1);
    menuBar1.add(jMenu2);
    menuBar1.add(jMenu3);
    menuBar1.add(jMenu5);
    this.setJMenuBar(menuBar1);
    this.getContentPane().add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1, null);
    jPanel1.add(filterAgentStatusLabel, null);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, null);
    setUpTheTable();
    jScrollPane1.getViewport().add(articleTable, null);
    this.getContentPane().add(jScrollPane3, BorderLayout.SOUTH);
    jScrollPane3.getViewport().add(articleTextArea, null);
    jMenu1.add(keywordsMenuItem);
    jMenu1.addSeparator();
    jMenu1.add(addArticleMenuItem);
    jMenu1.add(addAllMenuItem);
    jMenu3.add(useKeywordsCheckBoxMenuItem);
    jMenu3.add(useClustersCheckBoxMenuItem);
    jMenu3.add(useFeedbackCheckBoxMenuItem);
    jMenu5.add(aboutMenuItem);
    jMenu2.add(cutMenuItem);
  }


  /**
   *  Defines model for the articles JTable component.
   */
  public void setUpTheTable() {
    articleTable = new JTable();

    // Get the data from the Data Set
    data = getTableData();

    // Create a model of the data.
    articleTableModel = new AbstractTableModel() {

      /**
		 * 
		 */
		private static final long serialVersionUID = -8868431326664067555L;

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

      // The default implementations of these methods in
      // AbstractTableModel would work, but we can refine them.
      public String getColumnName(int theCol) {
        return columnNameList[theCol];
      }

      public Class<? extends Object> getColumnClass(int theCol) {
        return getValueAt(0, theCol).getClass();
      }

      public boolean isCellEditable(int theRow, int theCol) {
        boolean canEdit = false;

        switch (theCol) {
          case COL_SUBJECTID :
          case COL_SCOREID :
            canEdit = false;
            break;
          case COL_RATINGID :
            canEdit = true;
            break;
          default :
            canEdit = false;
            break;
        }  // endswitch
        return canEdit;
      }

      public void setValueAt(Object theValue, int theRow, int theCol) {
      String lclString = ((String) theValue).trim();

        switch (theCol) {
          case COL_SUBJECTID :
            break;
           case COL_SCOREID :
            break;
          case COL_RATINGID :
            data[theRow][theCol] = lclString;
            if (currentArt != null) {
              currentArt.setUserRating(lclString);
            }
            break;
        }  // end switch
      }
    };
    articleTable = new JTable(articleTableModel);
    articleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // add code to detect table selection events
    // may be able to remove this **FIX** jpb
    ListSelectionModel rowSM = articleTable.getSelectionModel();

    rowSM.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {

        // ignore extra messages
        if (e.getValueIsAdjusting()) {
          return;
        }
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (lsm.isSelectionEmpty()) {

          // no rows selected
        } else {
          int selectedRow = lsm.getMinSelectionIndex();

          if (selectedRow == -1) {
            return;  //nothing selected
          }
          if (articles.size() > 0) {
            currentArt = (NewsArticle) articles.elementAt(selectedRow);
            articleTextArea.setText(currentArt.body);  // clear the display area
            articleTextArea.setCaretPosition(0);  // move cursor to start of article
            // updateTable();
          } else {
            currentArt = null;
          }
        }
      }
    });
    (articleTable.getColumn(COL_SUBJECT)).setPreferredWidth(200);
    (articleTable.getColumn(COL_SCORE)).setPreferredWidth(30);
     (articleTable.getColumn(COL_RATING)).setPreferredWidth(30);
    JComboBox<String> lclClassComboBox = new JComboBox<String>();

    lclClassComboBox.addItem(FilterAgent.USELESS_RATING);
    lclClassComboBox.addItem(FilterAgent.NOTVERY_RATING);
    lclClassComboBox.addItem(FilterAgent.NEUTRAL_RATING);
    lclClassComboBox.addItem(FilterAgent.MILDLY_RATING);
    lclClassComboBox.addItem(FilterAgent.INTERESTING_RATING);
    (articleTable.getColumnModel().getColumn(COL_RATINGID)).setCellEditor(new DefaultCellEditor(lclClassComboBox));
    articleTable.setCellSelectionEnabled(true);
  }


  /**
   * Retrieves the articles, subjects, scores, and ratings from the table.
   *
   * @return the Object[][] that contains the objects from the table
   */
  private Object[][] getTableData() {
    Object[][] lclArray = null;

    if (articles.size() == 0) {
      lclArray = new Object[1][NUM_COLS];
      lclArray[0][0] = "";
      lclArray[0][1] = "";
      lclArray[0][2] = "";
      return lclArray;
    }  // no articles yet !!!!
    if (articles != null) {
      lclArray = new Object[articles.size()][NUM_COLS];
    }
    for (int i = 0; i < articles.size(); i++) {
      NewsArticle article = (NewsArticle) articles.elementAt(i);

      lclArray[i][0] = article.getSubject();
      lclArray[i][1] = String.valueOf(article.getScore(filterType));
      lclArray[i][2] = article.getUserRating();
    }
    return lclArray;
  }


  /**
   *  Change the contents of the SCORE column in the table only!
   */
  private void updateTableData() {
    if (articles.size() == 0) {
      return;  // no agent data yet !!!!
    }
    for (int i = 0; i < articles.size(); i++) {
      NewsArticle article = (NewsArticle) articles.elementAt(i);
      String score = "0.0";

      switch (filterType) {
        case FilterAgent.USE_KEYWORDS:
          score = String.valueOf(article.getKeywordScore());
          break ;
        case FilterAgent.USE_CLUSTERS:
         score = String.valueOf(article.getClusterScore());
         break ;
        case FilterAgent.USE_PREDICTED_RATING:
         score = String.valueOf(article.getPredictedRating()) ;
         break ;
       }
       data[i][COL_SCOREID] = score;
    }
    return;
  }


  /**
   *  Updates the table data and sends an event to refresh the screen.
   */
  private void updateTable() {
    updateTableData();
    TableModelEvent e = new TableModelEvent(articleTableModel);

    articleTable.tableChanged(e);
  }


  /**
   *  Refreshes the table with changed data.
   */
  private void refreshTable() {
    data = getTableData();
    updateTable();
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
   * Displays the About dialog.
   *
   * @param e the ActionEvent object when About was selected
   *
   */
  public void helpAbout_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About InfoFilter Application", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }



  /**
   * Closes or repaints the window.
   *
   * @param e the WindowEvent object generated to close or activate the window
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
   * Cuts an article from the table.
   *
   * @param e the ActionEvent object generated when Cut was selected
   *
   */
  void cutMenuItem_actionPerformed(ActionEvent e) {
    int selectedRow = articleTable.getSelectedRow();

    if ((selectedRow == -1) || (articles.size() == 0)) {
      return;  //nothing selected
    }
    System.out.println("cut() row " + selectedRow + " selected ");
    articles.removeElementAt(selectedRow);  // remove from vector
    articleTextArea.setText("");            // clear the display area
    currentArt = null;
    refreshTable();  // update the table model and refresh display
    selectedRow = articleTable.getSelectedRow();
    if (selectedRow < articles.size()) {
      currentArt = (NewsArticle) articles.elementAt(selectedRow);  // get newly selected article
      articleTextArea.setText(currentArt.getBody());
      articleTextArea.setCaretPosition(0);
    }
    if (articles.size() == 0) {
      addAllMenuItem.setEnabled(false) ;
      addArticleMenuItem.setEnabled(false) ;
      saveArticleMenuItem.setEnabled(false) ;
    }
  }


  /**
   * Filters the articles based on keywords.
   *
   * @param e the ActionEvent object generated when the Use keywords box
   *          was checked
   *
   */
  void useKeywordsCheckBoxMenuItem_actionPerformed(ActionEvent e) {
    filterType = FilterAgent.USE_KEYWORDS;
    filterArticles();
    this.setTitle(titleBarText + " - using Keywords");
  }


  /**
   * Clusters the articles.
   *
   * @param e the ActionEvent object generated when the Use clusters box
   *          was checked
   *
   */
  void useClustersCheckBoxMenuItem_actionPerformed(ActionEvent e) {
    filterType = FilterAgent.USE_CLUSTERS ;
    filterArticles();
    this.setTitle(titleBarText + " - using Clusters");

  }


  /**
   * Filters the articles based on feedback.
   *
   * @param e the ActionEvent object generated when Use feedback box was
   *          checked
   *
   */
  void useFeedbackCheckBoxMenuItem_actionPerformed(ActionEvent e) {
    filterType = FilterAgent.USE_PREDICTED_RATING;
    filterArticles();
    this.setTitle(titleBarText + " - using Predicted Ratings");
  }



  /**
   * Enables using clusters for filtering after the network was trained.
   *
   */
  public void clusterNetTrained() {
    useClustersCheckBoxMenuItem.setEnabled(true);
  }


  /**
   * Enables using feedback for filtering after the network was trained.
   *
   */
  public void ratingNetTrained() {
    useFeedbackCheckBoxMenuItem.setEnabled(true);
  }



  /**
   * Filter the articles by scoring and sorting the articles, then refresh the
   * table.
   *
   */
  public void filterArticles() {
    filterAgent.score(articles, filterType);
    articles = insertionSort(articles); // sorted articles list

    refreshTable();
    if (articles.size() > 0) {
      currentArt = (NewsArticle) articles.elementAt(0);
    }
  }



  /**
   * Sorts the articles by decreasing order of score.
   *
   * @param articles the Vector object that contains the articles to be sorted
   *
   * @return the Vector object that contains the sorted articles
   *
   */
  Vector<NewsArticle> insertionSort(Vector<NewsArticle> articles) {
    int i, j;
    int size = articles.size();
    NewsArticle sortedList[] = new NewsArticle[articles.size()];

    articles.copyInto(sortedList);
    NewsArticle temp;

    for (i = 1; i < size; i++) {
      temp = sortedList[i];

        j = i;
      while ((j > 0) && (sortedList[j - 1].getScore(filterType) < temp.getScore(filterType))) {
        sortedList[j] = sortedList[j - 1];
        j = j - 1;
      }
      sortedList[j] = temp;
    }
    Vector<NewsArticle> outList = new Vector<NewsArticle>();

     for (i = 0; i < size; i++) {
      temp = sortedList[i];
      outList.addElement(temp);
    }
    return outList;
  }


  /**
   * Opens a filter agent customizer.
   *
   * @param e the ActionEvent object generated when keywords was selected
   *
   */
  void keywordsMenuItem_actionPerformed(ActionEvent e) {

   Class<?> customizerClass = filterAgent.getCustomizerClass();

    if (customizerClass == null) {
      trace("Error can't find FilterAgent customizer class");
      return;
    }

    // found a customizer, now open it
    Customizer customizer = null;

    try {
      customizer = (Customizer) customizerClass.newInstance();
    } catch (Exception exc) {
      System.out.println("Error opening customizer - " + exc.toString());
      return;  // bail out
    }
    Point pos = this.getLocation();
    JDialog dlg = (JDialog) customizer;

    dlg.setLocation(pos.x + 20, pos.y + 20);
    customizer.setObject(filterAgent);
    dlg.show();

  }


  /**
   * Adds an article to the profile.
   *
   * @param e the ActionEvent object generated when Add article was selected
   *
   */
  void addArticleMenuItem_actionPerformed(ActionEvent e) {

    // open the profile file and append the
    // score data for this article
    filterAgent.addArticleToProfile(currentArt);
  }


  /**
   * Adds all articles to the profile.
   *
   * @param e the ActionEvent object generated when Add all was selected
   *
   */
  void addAllMenuItem_actionPerformed(ActionEvent e) {

    // open the profilefile and append the
    // score data for all articles
    filterAgent.addAllArticlesToProfile(articles);
  }




  /**
   * Exits the application.
   *
   * @param e the ActionEvent object generated when exit was selected
   *
   */
  void exitMenuItem_actionPerformed(ActionEvent e) {
    fileExit_actionPerformed(e);
  }


  /**
   * Loads and scores an article.
   *
   * @param e the ActionEvent object generated when load article was
   *          selected
   *
   */
  void loadArticleMenuItem_actionPerformed(ActionEvent e) {

    // Action from Open... Show the OpenFileDialog
    openFileDialog.show();
    String fileName = openFileDialog.getFile();

    if (fileName != null) {
      NewsArticle art = new NewsArticle(fileName);

      art.readArticle(fileName);
      articles.addElement(art);
      filterAgent.score(art, filterType);  // score the article
      refreshTable();
      articleTextArea.setText(art.getBody());
      articleTextArea.setCaretPosition(0);  // move cursor to start of article
    }
  }


  /**
   * Saves an article.
   *
   * @param e the ActionEvent object generated when save was selected
   *
   */
  void saveArticleMenuItem_actionPerformed(ActionEvent e) {

    // Action from Save... Show the SaveFileDialog
    saveFileDialog.show();
    String fileName = saveFileDialog.getFile();

    if (fileName != null) {
      int index = articleTable.getSelectedRow();

      if (index != -1) {
        NewsArticle art = (NewsArticle) articles.elementAt(index);

        art.writeArticle(fileName);
      }
    }
  }


  /**
   * Resets the application.
   *
   * @param e the ActionEvent object generated when reset was selected
   *
   */
  void resetMenuItem_actionPerformed(ActionEvent e) {
    System.out.println("Reset action requested");
    articles.clear();             // remove all articles from the list
    refreshTable();               // update the table
    articleTextArea.setText("");  // clear the text area
    addAllMenuItem.setEnabled(false) ;
    addArticleMenuItem.setEnabled(false) ;
    saveArticleMenuItem.setEnabled(false) ;
  }


  /**
   * Displays the About dialog.
   *
   * @param e the ActionEvent object generated when About was selected
   *
   */
  void aboutMenuItem_actionPerformed(ActionEvent e) {
    AboutDialog dlg = new AboutDialog(this, "About InfoFilter Application", true);
    Point loc = this.getLocation();

    dlg.setLocation(loc.x + 50, loc.y + 50);
    dlg.show();
  }


  /**
   * Opens the NewsReaderAgent Customizer dialog and allows the user
   * to download articles.
   *
   * @param e the ActionEvent object generated when download Newsgroup was selected
   */
  void downloadNewsGroupMenuItem_actionPerformed(ActionEvent e) {
    Class<?> customizerClass = newsReaderAgent.getCustomizerClass();

    if (customizerClass == null) {
      trace("Error can't find NewsReaderAgent customizer class");
      return;
    }

    // found a customizer, now open it
    Customizer customizer = null;

    try {
      customizer = (Customizer) customizerClass.newInstance();
    } catch (Exception exc) {
      System.out.println("Error opening customizer - " + exc.toString());
      return;  // bail out
    }
    Point pos = this.getLocation();
    JDialog dlg = (JDialog) customizer;

    dlg.setLocation(pos.x + 20, pos.y + 20);
    customizer.setObject(newsReaderAgent);
    dlg.show();
  }


  /**
   * Scores an article and adds it to the table.
   *
   * @param art the NewsArticle object to be added
   *
   */
  protected void addArticle(NewsArticle art) {
    articles.addElement(art);            // add to Vector
    filterAgent.score(art, filterType);  // score the article
    refreshTable();
    articleTextArea.setText(art.getBody());
    articleTextArea.setCaretPosition(0);  // move cursor to start of article
    addArticleMenuItem.setEnabled(true) ;
    addAllMenuItem.setEnabled(true) ;
    saveArticleMenuItem.setEnabled(true) ;
  }


   /**
   * Displays a message in the bottom pane of the application.
   *
   * @param msg the String object that contains the message to be displayed
   *
   */
  synchronized void trace(String msg) {
    articleTextArea.append(msg);
    articleTextArea.append("\n");
  }


  /**
   * Processes a CIAgentEvent.
   *
   * @param event the CIAgentEvent object to be processed
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    Object arg = event.getArgObject();
    Object action = event.getAction();

    if (action != null) {
      if (action.equals("trace")) {
        if (((arg != null) && (arg instanceof String))) {
          trace((String) arg);     // display the msg
        }
      } else if (action.equals("addArticle")) {
        addArticle((NewsArticle) arg);  // an Agent sent an article to us
      } else if (action.equals("status")) {
        filterAgentStatusLabel.setText("FilterAgent status: " + (String)arg);
      }
    }
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


  /**
   * Opens the URLReaderAgent customizer and allows the user to get a web page.
   *
   * @param e the ActionEvent object generated when download URL was selected
   */
  void downloadURLMenuItem_actionPerformed(ActionEvent e) {
    Class<?> customizerClass = uRLReaderAgent.getCustomizerClass();

    if (customizerClass == null) {
       trace("Error can't find URLReaderAgent customizer class");
       return;
    }

    // found a customizer, now open it
    Customizer customizer = null;

    try {
      customizer = (Customizer) customizerClass.newInstance();
    } catch (Exception exc) {
      trace("Error opening URLReaderAgent customizer - " + exc.toString());
      return;  // bail out
    }
    Point pos = this.getLocation();
    JDialog dlg = (JDialog) customizer;

    dlg.setLocation(pos.x + 20, pos.y + 20);
    customizer.setObject(uRLReaderAgent);
    dlg.show();
  }
}
