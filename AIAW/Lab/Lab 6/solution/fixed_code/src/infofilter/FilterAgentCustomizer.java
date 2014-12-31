package infofilter;

import java.awt.*;
import java.beans.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;


/**
 * The <code>FilterAgentCustomizer</code> class implements customizer for
 * filter agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class FilterAgentCustomizer extends JDialog implements Customizer {
  /**
	 * 
	 */
	private static final long serialVersionUID = 677937617190213030L;
JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton createProfileButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField keywordTextField = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList<?> keywordList = new JList();
  JButton addButton = new JButton();
  JButton removeButton = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton changeButton = new JButton();
  Vector keywords;
  Vector<String> originalKeywords;

  FilterAgent agent;
  JButton trainNNButton = new JButton();  // the agent bean we are customizing


  /**
   * Creates a <code>FilterAgentCustomizer</code> object with the given frame,
   * title, and modality.
   *
   * @param frame the Frame object for the customizer
   * @param title the String object that contains the title of the customizer
   * @param modal the boolean flag that indicates the modality
   *
   */
  public FilterAgentCustomizer(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * Creates a <code>FilterAgentCustomizer</code> object.
   *
   */
  public FilterAgentCustomizer() {
    this(null, "FilterAgent Customizer", false);
  }

   /**
   *  Sets the object to be customized
   *
   * @param obj the Object object to be customized
   */
  public void setObject(Object obj) {
    agent = (FilterAgent) obj;
    getDataFromBean();
   }

  /**
   * Initializes the GUI controls for the customizer.
   *
   * @throws Exception if any error occurred during initialization
   *
   */
  void jbInit() throws Exception {
    panel1.setPreferredSize(new Dimension(400, 400));
    panel1.setLayout(borderLayout1);
    createProfileButton.setText("Create Profile");
    createProfileButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createProfileButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    jPanel2.setLayout(null);
    jLabel1.setText("Keyword");
    jLabel1.setBounds(new Rectangle(38, 34, 120, 17));
    keywordTextField.setBounds(new Rectangle(37, 57, 208, 21));
    jScrollPane1.setBounds(new Rectangle(38, 91, 207, 228));
    addButton.setText("Add");
    addButton.setBounds(new Rectangle(276, 91, 88, 27));
    addButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addButton_actionPerformed(e);
      }
    });
    removeButton.setText("Remove");
    removeButton.setBounds(new Rectangle(277, 211, 87, 27));
    removeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeButton_actionPerformed(e);
      }
    });
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setHgap(15);
    changeButton.setText("Change");
    changeButton.setBounds(new Rectangle(277, 147, 84, 27));
    changeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        changeButton_actionPerformed(e);
      }
    });
    keywordList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        keywordList_mouseClicked(e);
      }
    });
    trainNNButton.setText("Train NNs");
    trainNNButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        trainNNButton_actionPerformed(e);
      }
    });
    jPanel1.setAlignmentX((float) 0.2);
    jPanel1.setPreferredSize(new Dimension(573, 37));
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(createProfileButton, null);
    jPanel1.add(trainNNButton, null);
    jPanel1.add(cancelButton, null);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, null);
    jPanel2.add(keywordTextField, null);
    jPanel2.add(jLabel1, null);
    jPanel2.add(addButton, null);
    jPanel2.add(changeButton, null);
    jPanel2.add(removeButton, null);
    jScrollPane1.getViewport().add(keywordList, null);
  }


  /**
   * Closes the window when Cancel is pressed.
   *
   * @param e the ActionEvent object generated when the cancel button was
   *          pressed
   *
   */
  void cancelButton_actionPerformed(ActionEvent e) {
      dispose();
  }

  /**
  * Sets the flags for the FilterAgent to start training the neural nets
  * on its own thread.
  *
  * @param e the ActionEvent object generated when the train button was
  *          pressed
  */
  void trainNNButton_actionPerformed(ActionEvent e) {
    // prompt user to avoid unintentional data loss
    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
           "This will reset the neural networks and  \n " +
           "any neural network training will be lost.\n" +
           "Are you sure you want to do this?",
           "Train Cluster and Rating Neural Networks",
           JOptionPane.YES_NO_OPTION))  {

       agent.buildRatingNet();
       agent.buildClusterNet();
    }

  }

  /**
   * Creates a profile when the Create Profile button is pressed.
   *
   * @param e the ActionEvent object generated when the create profile
   *          button is pressed
   *
   */
  void createProfileButton_actionPerformed(ActionEvent e) {

    // prompt user to avoid unintentional data loss
    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
           "This will erase the existing profile data  \n " +
           "any neural network training will be lost.\n" +
           "Are you sure you want to do this?",
           "Create Filter Profile",
           JOptionPane.YES_NO_OPTION))  {

      setDataOnBean() ; // change keywords, clear neural nets, create profile

      dispose();
    }
  }



  /**
   * Adds a keyword when the Add button is pressed.
   *
   * @param e the ActionEvent object generated when the add button is pressed
   *
   */
  void addButton_actionPerformed(ActionEvent e) {
    keywords.addElement(keywordTextField.getText().trim());
    keywordList.setListData(keywords);
  }


  /**
   * Changes a keyword when the Change button is pressed.
   *
   * @param e the ActionEvent object generated when the change button is
   *          pressed
   *
   */
  void changeButton_actionPerformed(ActionEvent e) {
    int index = keywordList.getSelectedIndex();

    if (index < 0) {
      return;  // nothing selected, can't change it
    }
    String value = keywordTextField.getText().trim();

    keywords.setElementAt(value, index);  // update model
    keywordList.setListData(keywords);    //update view
  }


  /**
   * Removes a keyword from the list.
   *
   * @param e the ActionEvent object generated when the remove button is
   *          pressed
   *
   */
  void removeButton_actionPerformed(ActionEvent e) {
    int index = keywordList.getSelectedIndex();

    if (index < 0) {
      return;  // nothing selected, can't change it
    }
    keywords.removeElementAt(index);
    keywordList.setListData(keywords);
  }


  /**
   * Sets the keywords for the filter agent.
   *
   * @param keys the String[] that contains the keywords
   *
   */
  private void setKeywords(String[] keys) {
    keywords = new Vector<String>();
    for (int i = 0; i < keys.length; i++) {
      keywords.addElement(keys[i]);
    }
    originalKeywords = (Vector) keywords.clone();  // save copy of original data
    keywordList.setListData(keywords);
  }


  /**
   * Retrieves the keywords.
   *
   * @return the String[] that contains the keywords
   *
   */
  private String[] getKeywords() {
    String[] keys = new String[keywords.size()];

    for (int i = 0; i < keys.length; i++) {
      keys[i] = (String) keywords.elementAt(i);
    }
    return keys;
  }


  /**
   * Sets the keyword text, depending on the mouse click.
   *
   * @param e the MouseEvent object generated from the mouse click
   *
   */
  void keywordList_mouseClicked(MouseEvent e) {
    int index = keywordList.getSelectedIndex();

    if (index < 0) {
      return;  // nothing selected, can't change it
    }
    keywordTextField.setText((String) keywords.elementAt(index));
  }


   /**
   *  Gets data from the bean and sets the GUI controls.
   */
  public void getDataFromBean() {
      setKeywords(agent.getKeywords());
  }


  /**
   *  Takes data from GUI and sets properties on the agent bean.
   */
  public void setDataOnBean() {

      // empty the InfoFilter.prf file
      File prf = new File("InfoFilter.prf");

      prf.delete();
      File dat = new File("InfoFilter.dat");

      dat.delete();

      agent.setKeywords(getKeywords());
      agent.writeProfileDataDefinition();
  }
}
