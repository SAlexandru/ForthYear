package infofilter;


import ciagent.CIAgentEvent;
import ciagent.CIAgentEventListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Customizer;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class URLReaderAgentCustomizer
  extends JDialog
  implements Customizer, CIAgentEventListener
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 72403189530251349L;
JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton QueryButton = new JButton();
  JButton CancelButton = new JButton();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JComboBox<String> paramsComboBox = new JComboBox<String>();
  JComboBox<String> uRLComboBox = new JComboBox<String>();
  JLabel jLabel3 = new JLabel();
  URLReaderAgent agent;
  JLabel jLabel4 = new JLabel();
  JTextField nameTextField = new JTextField();
  JLabel jLabel5 = new JLabel();
  
  public URLReaderAgentCustomizer(Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    try
    {
      jbInit();
      pack();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public URLReaderAgentCustomizer()
  {
    this(null, "URLReaderAgent Customizer", false);
  }
  
  public void setObject(Object obj)
  {
    this.agent = ((URLReaderAgent)obj);
    getDataFromBean();
    this.agent.addCIAgentEventListener(this);
  }
  
  void jbInit()
    throws Exception
  {
    this.jLabel5.setText("URL:");
    this.jLabel5.setBounds(new Rectangle(23, 66, 106, 17));
    this.panel1.setLayout(this.borderLayout1);
    this.QueryButton.setText("Get URL");
    this.QueryButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        URLReaderAgentCustomizer.this.QueryButton_actionPerformed(e);
      }
    });
    this.CancelButton.setText("Cancel");
    this.CancelButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        URLReaderAgentCustomizer.this.CancelButton_actionPerformed(e);
      }
    });
    this.jPanel2.setLayout(null);
    this.jLabel1.setText("Parameter string:");
    this.jLabel1.setBounds(new Rectangle(19, 152, 143, 17));
    this.paramsComboBox.setBounds(new Rectangle(18, 190, 371, 24));
    this.paramsComboBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        URLReaderAgentCustomizer.this.paramsComboBox_actionPerformed(e);
      }
    });
    this.paramsComboBox.setEditable(true);
    String sampleParms = "?&dmon=JUL&dday=1&orig=RST&dest=MCO&rmon=JUL&rday=8";
    

    this.paramsComboBox.addItem(sampleParms);
    this.uRLComboBox.setBounds(new Rectangle(19, 93, 368, 25));
    this.uRLComboBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        URLReaderAgentCustomizer.this.uRLComboBox_actionPerformed(e);
      }
    });
    this.uRLComboBox.setEditable(true);
    this.uRLComboBox.addItem("http://www.bigusbooks.com");
    this.uRLComboBox.addItem("http://www.research.ibm.com/able");
    this.uRLComboBox.addItem("http://www.fipa.org");
    this.uRLComboBox.addItem("http://www.bigusbooks.com/cgi-local/airfare.pl");
    this.jLabel3.setBounds(new Rectangle(214, 128, 41, 17));
    this.panel1.setMinimumSize(new Dimension(400, 300));
    this.panel1.setPreferredSize(new Dimension(400, 300));
    this.jLabel4.setText("Name");
    this.jLabel4.setBounds(new Rectangle(22, 20, 41, 17));
    this.nameTextField.setBounds(new Rectangle(110, 18, 139, 21));
    getContentPane().add(this.panel1);
    this.panel1.add(this.jPanel1, "South");
    this.jPanel1.add(this.QueryButton, null);
    this.jPanel1.add(this.CancelButton, null);
    this.panel1.add(this.jPanel2, "Center");
    this.jPanel2.add(this.jLabel3, null);
    this.jPanel2.add(this.paramsComboBox, null);
    this.jPanel2.add(this.jLabel1, null);
    this.jPanel2.add(this.uRLComboBox, null);
    this.jPanel2.add(this.jLabel5, null);
    this.jPanel2.add(this.jLabel4, null);
    this.jPanel2.add(this.nameTextField, null);
  }
  
  void paramsComboBox_actionPerformed(ActionEvent e) {}
  
  void QueryButton_actionPerformed(ActionEvent e)
  {
    setDataOnBean();
    CIAgentEvent event = new CIAgentEvent(this, "getURLText", null);
    
    this.agent.postCIAgentEvent(event);
    
    dispose();
  }
  
  void CancelButton_actionPerformed(ActionEvent e)
  {
    dispose();
  }
  
  public void getDataFromBean()
  {
    this.nameTextField.setText(this.agent.getName());
    URL url = this.agent.getURL();
    if (url == null) {
      this.uRLComboBox.setSelectedIndex(0);
    } else {
      this.uRLComboBox.setSelectedItem(url);
    }
    this.paramsComboBox.setSelectedItem(this.agent.getParamString());
  }
  
  public void setDataOnBean()
  {
    String name = this.nameTextField.getText().trim();
    
    this.agent.setName(name);
    String url = (String)this.uRLComboBox.getSelectedItem();
    try
    {
      this.agent.setURL(new URL(url));
    }
    catch (MalformedURLException e)
    {
      System.out.println("URLReaderAgent: Bad URL specification");
    }
    String paramString = (String)this.paramsComboBox.getSelectedItem();
    
    this.agent.setParamString(paramString);
  }
  
  public void processCIAgentEvent(CIAgentEvent event)
  {
   // Object source = event.getSource();
   // Object arg = event.getArgObject();
    Object action = event.getAction();
    if ((action != null) && 
      (!action.equals("trace"))) {
      if (action.equals("addURLText")) {
        System.out.println("URL text read by agent ");
      }
    }
  }
  
  public void postCIAgentEvent(CIAgentEvent event)
  {
    processCIAgentEvent(event);
  }
  
  void uRLComboBox_actionPerformed(ActionEvent e) {}
}

