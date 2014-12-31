package infofilter;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;
import java.beans.*;

import ciagent.*;


/**
 * The <code>NewsReaderAgentCustomizer</code> class implements the
 * customizer for the news reader agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class NewsReaderAgentCustomizer extends JDialog implements Customizer, CIAgentEventListener, Runnable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 7559993529229675957L;
JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton DownloadButton = new JButton();
  JButton CancelButton = new JButton();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JComboBox<String> NewsGroupComboBox = new JComboBox<String>();
  JLabel jLabel2 = new JLabel();
  JComboBox<String> NewsHostComboBox = new JComboBox<String>();
  JTextField maxArticlesTextField = new JTextField();
  JLabel jLabel3 = new JLabel();
  JProgressBar DownloadProgressBar = new JProgressBar();
  JLabel statusLabel = new JLabel();
  JRadioButton AllArticlesRadioButton = new JRadioButton();
  JRadioButton MaxArticlesRadioButton = new JRadioButton();
  ButtonGroup group = new ButtonGroup();
  InfoFilterFrame infoFilterFrame;  // reference to owning frame
  NewsReaderAgent agent;            // the agent bean we are customizing
  int numArticles = 0;              // the number of articles downloaded
  boolean downloadInProgress = false;
  Thread runnit = null;


  /**
   * Creates a <code>NewsReaderAgentCustomizer</code> object with the given
   * frame, title, and modality.
   *
   * @param frame the Frame object for this customizer
   * @param title the String object that contains the title for this customizer
   * @param modal the boolean flag that indicates modality
   *
   */
  public NewsReaderAgentCustomizer(Frame frame, String title, boolean modal) {
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
   * Creates a <code>NewsReaderAgentCustomizer</code> object.
   *
   */
  public NewsReaderAgentCustomizer() {
    this(null, "NewsReaderAgent Customizer", false);
  }


  /**
   * Sets the object to be customized.
   *
   * @param obj the Object to be customized
   */
  public void setObject(Object obj) {
    agent = (NewsReaderAgent) obj;
    getDataFromBean();
    agent.addCIAgentEventListener(this);
  }


  /**
   * Initializes the combo box.
   *
   */
  protected void init() {
    NewsHostComboBox.addItem("news1.attglobal.net");
    NewsHostComboBox.setSelectedIndex(0);
    NewsGroupComboBox.addItem("comp.ai.fuzzy");
    NewsGroupComboBox.addItem("comp.ai.neural-nets");
    NewsGroupComboBox.addItem("comp.ai");
    NewsGroupComboBox.addItem("comp.ai.genetic");
    NewsGroupComboBox.addItem("comp.ai.shells");
    NewsGroupComboBox.setSelectedIndex(0);
  }


  /**
   * Initializes the GUI controls.
   *
   * @throws Exception if any errors occur during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    DownloadButton.setText("Download");
    DownloadButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DownloadButton_actionPerformed(e);
      }
    });
    CancelButton.setText("Cancel");
    CancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        CancelButton_actionPerformed(e);
      }
    });
    jPanel2.setLayout(null);
    jLabel1.setText("News host (NNTP Server)");
    jLabel1.setBounds(new Rectangle(9, 43, 143, 17));
    NewsGroupComboBox.setBounds(new Rectangle(159, 94, 230, 24));
    NewsGroupComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        NewsGroupComboBox_actionPerformed(e);
      }
    });
    NewsGroupComboBox.setEditable(true);
    jLabel2.setText("News group ");
    jLabel2.setBounds(new Rectangle(9, 95, 107, 17));
    NewsHostComboBox.setBounds(new Rectangle(160, 42, 228, 25));
    NewsHostComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        NewsHostComboBox_actionPerformed(e);
      }
    });
    NewsHostComboBox.setEditable(true);
    maxArticlesTextField.setText("10");
    maxArticlesTextField.setBounds(new Rectangle(277, 154, 79, 21));
    jLabel3.setBounds(new Rectangle(214, 128, 41, 17));
    DownloadProgressBar.setBounds(new Rectangle(42, 191, 304, 23));
    statusLabel.setText("status");
    statusLabel.setBounds(new Rectangle(18, 229, 364, 20));
    AllArticlesRadioButton.setText("All articles");
    AllArticlesRadioButton.setBounds(new Rectangle(74, 152, 103, 25));
    MaxArticlesRadioButton.setText("Limit to ");
    MaxArticlesRadioButton.setBounds(new Rectangle(186, 153, 84, 25));
    panel1.setMinimumSize(new Dimension(400, 300));
    panel1.setPreferredSize(new Dimension(400, 300));
    group.add(AllArticlesRadioButton);
    group.add(MaxArticlesRadioButton);
    MaxArticlesRadioButton.setSelected(true);
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(DownloadButton, null);
    jPanel1.add(CancelButton, null);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1, null);
    jPanel2.add(NewsHostComboBox, null);
    jPanel2.add(NewsGroupComboBox, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(MaxArticlesRadioButton, null);
    jPanel2.add(AllArticlesRadioButton, null);
    jPanel2.add(DownloadProgressBar, null);
    jPanel2.add(statusLabel, null);
    jPanel2.add(maxArticlesTextField, null);
  }


  /**
   * Does nothing.
   *
   * @param e the ActionEvent object
   *
   */
  void NewsHostComboBox_actionPerformed(ActionEvent e) {}


  /**
   * Does nothing.
   *
   * @param e the ActionEvent object
   *
   */
  void NewsGroupComboBox_actionPerformed(ActionEvent e) {}


  /**
   * Gets the data from the UI then posts an event to the agent to download
   * the news group.
   *
   * @param e the ActionEvent object generated when the download button was pressed
   *
   */
  void DownloadButton_actionPerformed(ActionEvent e) {

    // first get user data from the customizer panel and set values on agent
    setDataOnBean();
    downloadInProgress = true;
    runnit = new Thread(this);
    runnit.start();
    String newsHost = (String) NewsHostComboBox.getSelectedItem();
    String newsGroup = (String) NewsGroupComboBox.getSelectedItem();
    Vector<String> args = new Vector<String>();

    args.addElement(newsHost);
    args.addElement(newsGroup);
    CIAgentEvent event = new CIAgentEvent(this, "downloadNewsGroup", args);

    agent.postCIAgentEvent(event);  // have agent download asynchronously
  }


  /**
   * Cancels the customizer.
   *
   * @param e the ActionEvent object generated when the cancel button was pressed
   *
   */
  void CancelButton_actionPerformed(ActionEvent e) {
    dispose();
  }


  /**
   * Sets the info filter frame to the given value.
   *
   * @param frame the InfoFilterFrame object that contains the new frame
   *
   */
  public void setInfoFilterFrame(InfoFilterFrame frame) {
    infoFilterFrame = frame;
  }


  /**
   * Gets data from the bean and sets the GUI controls.
   */
  public void getDataFromBean() {

    //  nameTextField.setText(agent.getName()) ;
    NewsHostComboBox.setSelectedItem(agent.getNewsHost());
    NewsGroupComboBox.setSelectedItem(agent.getNewsGroup());
  }


  /**
   *  Take data from GUI and sets properties on the agent bean.
   */
  public void setDataOnBean() {

    //     String name = nameTextField.getText() ;
    //     agent.setName(name) ;
    if (AllArticlesRadioButton.isSelected()) {
      agent.setNumArticles(100);  // set arbitrary limit, or add logic to real entire newsgroup ???
    } else {
      agent.setNumArticles(new Integer(maxArticlesTextField.getText().trim()).intValue());
    }
    agent.setNewsHost((String) NewsHostComboBox.getSelectedItem());
    agent.setNewsGroup((String) NewsGroupComboBox.getSelectedItem());
    numArticles = 0;
    DownloadProgressBar.setMinimum(0);
    DownloadProgressBar.setMaximum(agent.getNumArticles());
    DownloadProgressBar.setStringPainted(true);
  }


  /**
   * Updates the progress bar during a download.
   */
  public void run() {
    do {
      DownloadProgressBar.setValue(numArticles);  // update progress bar
      invalidate();
      repaint();
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {}
    } while (downloadInProgress);
  }


  /**
   * Processes a CIAgentEvent (trace or addArticle).
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    Object arg = event.getArgObject();
    Object action = event.getAction();

    if (action != null) {
      if (action.equals("trace")) {
        // statusLabel.setText("Trace: " + (String)arg) ;
       } else if (action.equals("addArticle")) {
        statusLabel.setText("Article: " + ((NewsArticle) arg).getSubject());
        DownloadProgressBar.setValue(++numArticles);  // newsReaderAgent sent an article to us
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
