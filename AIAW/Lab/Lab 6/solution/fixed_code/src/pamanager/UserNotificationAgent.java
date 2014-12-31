package pamanager;

import java.io.*;
import javax.swing.*;
import java.util.*;
import ciagent.*;

/**
 * The <code>UserNotificationAgent</code> class displays a message when an
 * event is received.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class UserNotificationAgent extends CIAgent implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = -4307065772915344975L;
protected AlertDialog notificationDialog;


  /**
   * Creates a <code>UserNotificationAgent</code> object.
   *
   */
  public UserNotificationAgent() {
    name = "UserNotification";
  }


  /**
   * Creates a <code>UserNotificationAgent</code> object with the given
   * name.
   *
   * @param name the String object that contains the name of the agent
   *
   */
  public UserNotificationAgent(String name) {
    super(name);
  }


  /**
   * Retrieves a string for display that indicates what this agent is
   * doing.
   *
   * @return the String object that contains the task description
   *
   */
  public String getTaskDescription() {
    return "Displaying notify messages";
  }

  public String getMsgText() {
    return notificationDialog.getMsgText() ;
  }

  public void setMsgText(String text) {
    notificationDialog.setMsgText(text) ;
  }

  public void appendMsgText(String text) {
    notificationDialog.appendMsgText(text) ;
  }


  /**
   * Sets the dialog for this agent.
   *
   * @param dlg the JDialog object to be used by this agent to notify
   *        the user of events
   *
   */
  public void setDialog(JDialog dlg) {
    notificationDialog = (AlertDialog)dlg;
  }


  /**
   * Initializes the agent by creating a dialog that will be used
   * to notify the user of events.
   */
  public void initialize() {
    // perform agent specific initialization
    if (notificationDialog != null) {
      notificationDialog.dispose();
    }
    JFrame frame = new JFrame();
    notificationDialog = new AlertDialog(frame, "CIAgent User Notification Agent: " + name, false);
    setSleepTime(5 * 1000);  // 5 seconds
    setState(CIAgentState.INITIATED);
  }

  /**
   * Does nothing.
   *
   */
  public void process() {}


  /**
   * Processes the event by displaying the message associated with the
   * event.
   *
   * @param e the CIAgentEvent object that contains the event received
   *          by this agent
   *
   */
  public void processCIAgentEvent(CIAgentEvent e) {

     // Only process notify events
     if (e.getAction().equalsIgnoreCase("notify")) {
      Date time = Calendar.getInstance().getTime();
      String timeStamp = time.toString();
      String text = (String) e.getArgObject() ;
      String source = e.getSource().getClass().toString() ;
      if (e.getSource() instanceof CIAgent) {
        source = ((CIAgent)e.getSource()).getName();
      }
      notificationDialog.appendMsgText("Event received on "+
        timeStamp + " from " + source + ":\n" + text);
       if (!notificationDialog.isVisible()) notificationDialog.show();
    }
  }


  /**
   * Does nothing.
   *
   */
  public void processTimerPop() { }

}
