package pamanager;


import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import ciagent.*;


/**
 * The <code>SchedulerAgent</code> class fires events on specified
 * intervals or at a single specified date and time.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class SchedulerAgent extends CIAgent implements Serializable {
  protected int interval = 60000;    // for interval timer
  protected boolean oneShot = false;  // for one-shot timer
  protected Date time = null;         // for one-shot timer
  protected String actionString = "notify" ; // used in events

  /**
   * Creates a <code>SchedulerAgent</code> object.
   *
   */
  public SchedulerAgent() {
    this("Scheduler");
  }


  /**
   * Creates a <code>SchedulerAgent</code> object with the given name.
   *
   * @param name the String object
   *
   */
  public SchedulerAgent(String name) {
    super(name);
  }


  /**
   * Retrieves a string for display that contains information about this
   * agent.
   *
   * @return the String object that contains the display information
   *
   */
  public String getTaskDescription() {
    if (oneShot){
      return "One-shot timer: time = " + time.toString();
    }
    else
    {
      return "Repeating timer: interval = " + (interval/1000);
    }
  }


  /**
   * Sets the interval time to the given number of seconds.
   *
   * @param secs the integer that represents the number of seconds for
   *             the interval
   */
  public void setInterval(int secs) {
    interval = secs * 1000;
  }


  /**
   * Retrieves the interval time, in seconds.
   *
   * @return the integer that represents the interval, in seconds
   */
  public int getInterval() {
    return interval / 1000;
  }



  /**
   * Sets the one-shot flag.
   *
   * @param flag the boolean that indicates whether this is a one-shot
   *             scheduler or not
   */
  public void setOneShot(boolean flag) {
    oneShot = flag;
  }


  /**
   * Retrieves the one-shot flag.
   *
   * @return the boolean value that indicates whether this is a one-shot
   *         scheduler (<code>true</code>) or not (<code>false</code>)
   */
  public boolean getOneShot() {
    return oneShot;
  }


  /**
   * Sets the time for the one-shot timer.
   *
   * @param date the Date object that contains the time when the one-shot
   *        timer should pop
   *
   */
  public void setTime(Date date) {
    time = date;
  }


  /**
   * Retrieves the time setting for the one-shot timer.
   *
   * @return the Date object that contains the time when the one-shot
   *        timer should pop
   *
   */
  public Date getTime() {
    return time;
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
   * @return the String that is sent in the action field of an event
   */
  public String getActionString() {
    return actionString;
  }


  /**
   * Initializes this agent by setting the timers.
   *
   */
  public void initialize() {
    if (oneShot) {
      long currentTime = Calendar.getInstance().getTime().getTime();
      interval = (int)(time.getTime() - currentTime);
    }
    setSleepTime(interval);  // for timer pops
    setAsyncTime(interval);  // not processing async events
    setState(CIAgentState.INITIATED);
  }


  /**
   * Does nothing because this agent only processes timer pops.
   *
   */
  public void process() {}


  /**
   * Processes the timer pop by sending an event to the event listeners
   * for this agent.
   */
  public void processTimerPop() {
    String timeStamp = Calendar.getInstance().getTime().toString() ;
    notifyCIAgentEventListeners(new CIAgentEvent(this, actionString, timeStamp));
    if (oneShot) {
      stopAgentProcessing(); // kill the agent
    }
  }


  /**
   * Does nothing because this agent only processes timer pops.
   *
   * @param e the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent e) {
  }
}

