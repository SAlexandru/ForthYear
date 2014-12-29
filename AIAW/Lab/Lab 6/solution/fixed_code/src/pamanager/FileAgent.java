package pamanager;

import java.io.*;
import javax.swing.*;
import java.util.*;
import ciagent.*;


/**
 * The <code>FileAgent</code> class implements an agent that monitors
 * a file to see if it was modified, deleted, or if its size exceeds a
 * certain threshold.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class FileAgent extends CIAgent {

  /**
	 * 
	 */
	private static final long serialVersionUID = 2658603559232851213L;
// conditions to check for
  public static final int MODIFIED = 0;
  public static final int DELETED = 1;
  public static final int THRESHOLD = 2;

  // actions to take when conditions are true
  public static final int ALERT = 0;    // display a dialog
  public static final int EXECUTE = 1;  // start a process
  public static final int EVENT = 2;    // send an event to another agent

  protected int condition;
  protected int action = EVENT;         // default action is to send an event

  protected String fileName;
  protected File file;
  protected long lastChanged;
  protected int threshold;

  protected JDialog actionDialog;

  protected String actionString ; // action event string

  protected String parms;


  /**
   * Creates the agent.
   */
  public FileAgent() {
    name = "Watch";
  }


  /**
   * Creates the agent with the specified name.
   *
   * @param name the String that contains the name of the agent
   */
  public FileAgent(String name) {
    super(name);
  }


  /**
   * Retrieves a description of the task this agent is performing.
   *
   * @return the display string
   */
  public String getTaskDescription() {
    return "Watching filename=" + fileName + "condition=" + condition;
  }


  /**
   * Sets the name of the file to be watched.
   *
   * @param fileName the String that contains the name of the file
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
    file = new File(fileName);
    lastChanged = file.lastModified();
  }


  /**
   * Retrieves the name of the file being watched.
   *
   * @return the name of the file
   */
  public String getFileName() {
    return fileName;
  }


  /**
   * Sets the condition to be monitored to MODIFIED, DELETED, or THRESHOLD.
   *
   * @param cond the integer that represents the condition being checked
   */
  public void setCondition(int cond) {
    condition = cond;
  }


  /**
   * Retrieves the condition being monitored.
   *
   * @return the integer that represents the condition being monitored
   *         (0=MODIFIED, 1=DELETED, 2=THRESHOLD)
   */
  public int getCondition() {
    return condition;
  }


  /**
   * Sets the threshold value.
   *
   * @param thresh the threshold value
   */
  public void setThreshold(int thresh) {
    threshold = thresh;
  }


  /**
   * Retrieves the threshold value.
   *
   * @return the threshold value
   */
  public int getThreshold() {
    return threshold;
  }


  /**
   * Sets the action to be performed by this agent to ALERT, EXECUTE, or
   * EVENT.
   *
   * @param action the integer that represents the action
   */
  public void setAction(int action) {
    this.action = action;
  }


  /**
   * Retrieves action this agent performs.
   *
   * @return the integer that represents the action (0=ALERT, 1=EXECUT,
   *         2=EVENT)
   */
  public int getAction() {
    return action;
  }

   /**
   * Sets the action string to be sent by this agent in an EVENT.
   *
   * @param actionString the integer that represents the action
   */
  public void setActionString(String actionString) {
    this.actionString = actionString;
  }


  /**
   * Retrieves the actionString this agent sends in events.
   *
   * @return the String that is sent in the action field of an event.
   */
  public String getActionString() {
    return actionString;
  }



  /**
   * Sets the parameters used when this agent performs an ALERT or EXECUTE
   * action.
   *
   * @param params the String that contains the parameters for the action
   */
  public void setParms(String params) {
    parms = params;
  }


  /**
   * Retrieve the parameters used by the agent when performing an action.
   *
   * @return the String that contains the parameter
   */
  public String getParms() {
    return parms;
  }


  /**
   * Sets the dialog used by the agent when performing the ALERT or EXECUTE
   * actions.
   *
   * @param dlg the JDialog that is used with an ALERT or EXECUTE action
   */
  public void setDialog(JDialog dlg) {
    actionDialog = dlg;
  }


  /**
   * Initializes the agent.
   */
  public void initialize() {

    // set up dialogs
    if (actionDialog != null) {
      actionDialog.dispose();
    }
    JFrame frame = new JFrame();

    if (action == ALERT) {
      actionDialog = new AlertDialog(frame, name + ": Alert", false);
    }
    if (action == EXECUTE) {
      actionDialog = new ExecuteDialog(frame, name + ": Execute", false);
    }

    // set sleep time
    setSleepTime(15 * 1000);  // 15 seconds
    setState(CIAgentState.INITIATED);
  }


  /**
   * Performs the default processing for this agent by checking the condition
   * and performing the action is the condition is met.
   */
  public void process() {
    if (checkCondition()) {
      performAction();
    }
  }


  /**
   * Does nothing when an event is received.
   *
   * @param e the CIAgentEvent received by this agent
   */
  public void processCIAgentEvent(CIAgentEvent e) {}


  /**
   * Performs the default processing for this agent when a timer pop occurs.
   */
  public void processTimerPop() {
    process();
  }


  /**
   * Checks the condition being monitored by this agent.
   *
   * @return <code>true</code> if the condition occurred or <code>false
   *         </code> if the condition did not occur
   */
  private boolean checkCondition() {
    boolean truth = false;

    switch (condition) {
      case MODIFIED :
        truth = changed();
        break;
      case DELETED :        // was file deleted?
        truth = !exists();  // see if file exists
        break;
      case THRESHOLD :
        truth = threshold > length();
        break;
    }
    return truth;
  }


  /**
   * Peforms the ALERT, EXECUTE, or EVENT action.
   */
  void performAction() {
    Date time = Calendar.getInstance().getTime(); 
    String timeStamp = time.toString();

    switch (action) {
      case ALERT :
        trace(timeStamp + " " + name + ": Alert fired \n");
        ((AlertDialog) actionDialog).appendMsgText(timeStamp + " - " + parms);
        actionDialog.show();
        break;
      case EXECUTE :
        trace(name + ": Executing command \n");
        executeCmd(parms);
        break;
      case EVENT :
        // signal interested observer
        notifyCIAgentEventListeners(new CIAgentEvent(this, actionString,
              "Watch condition on " + fileName + " was triggered!"));
        break;
    }
  }


  /**
   * Executes the command string that was set up as a parameter for the
   * EXECUTE action.
   *
   * @param the String that contains the command to be executed in the
   *        proper format for the platform on which this agent is running
   *
   * @param cmd the String object
   *
   * @return the integer that represents the exit value of the command
   *         process
   */
  public int executeCmd(String cmd) {
    Process process = null;
    String line;

    String osType = (System.getProperty("os.name")).toUpperCase();
    trace(cmd);  // echo command and parameters

    actionDialog.show();
    try {
      if (osType.equals("WINDOWS 95")) {
        process = Runtime.getRuntime().exec("command.com /c " + cmd + "\n");
        BufferedReader data = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = data.readLine()) != null) {
          trace(line);
        }
        data.close();
      } else if (osType.equals("AIX") || osType.equals("UNIX")) {
        process = Runtime.getRuntime().exec(cmd + "\n");
        BufferedReader data = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = data.readLine()) != null) {
          trace(line);
        }
        data.close();

      } else if (osType.equals("WINDOWS NT")) {
        process = Runtime.getRuntime().exec("cmd /C " + cmd + "\n");
        BufferedReader data = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = data.readLine()) != null) {
          trace(line);
        }
        data.close();
      } else if (osType.equals("OS/2")) {
        process = Runtime.getRuntime().exec("cmd.exe /c " + cmd + "\n");
        BufferedReader data = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((line = data.readLine()) != null) {
          trace(line);
        }
        data.close();
      } else {
        // this is an unsupported or unexpected OS name ... inform the user
        trace("FileAgent Error -- unsupported OS or run-time environment") ;
    }
    } catch (IOException err) {
      trace("Error: EXEC failed, " + err.toString());
      err.printStackTrace();
      return -1;
    }
    if (((ExecuteDialog) actionDialog).getCancel() == true) {
      stopAgentProcessing();
    }
    if (process != null) {
      return process.exitValue();
    }
    else {
      return -1;
    }
  }


  /**
   * Checks to see if the file exists.
   *
   * @return <code>true</code> if the file exists or <code>false</code> if
   *          the file does not exist
   */
  protected boolean exists() {
    return file.exists();
  }


  /**
   * Checks to see if the file has changed.
   *
   * @return <code>true</code> if the file has changed or <code>false</code>
   *          if the file has not changed
   */
  protected boolean changed() {
    long changeTime = lastChanged;

    lastChanged = file.lastModified();
    return !(lastChanged == changeTime);
  }


  /**
   * Retrieves the size of the file in bytes.
   *
   * @return the size of the file in bytes
   */
  protected long length() {
    return file.length();
  }


  /**
   * Retrieves the time the file was last modified.
   *
   * @return the time the file was modified
   */
  protected long lastModified() {
    return file.lastModified();
  }
}
