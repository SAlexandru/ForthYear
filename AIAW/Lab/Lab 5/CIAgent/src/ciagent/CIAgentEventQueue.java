package ciagent;

import ciagent.CIAgentEvent;

import java.util.*;
import java.io.*;

/**
 * The <code>CIAgentEventListener</code> class defines queue that contains
 * agent events.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class CIAgentEventQueue implements Serializable {
  private Vector eventQueue;


  /**
   * Creates an event queue.
   */
  public CIAgentEventQueue() {
    eventQueue = new Vector();
  }


  /**
   * Adds an event to the end of the queue.
   *
   * @param event the CIAgentEvent to be added to the queue
   */
  public synchronized void addEvent(CIAgentEvent event) {
    eventQueue.addElement(event);
  }


  /**
   * Retrieves the first element from the queue (if any) after removing it from
   * the queue.
   *
   * @return the first event on the queue
   */
  public synchronized CIAgentEvent getNextEvent() {
    if (eventQueue.size() == 0) {
      return null;
    } else {
      CIAgentEvent event = (CIAgentEvent) eventQueue.elementAt(0);

      eventQueue.removeElementAt(0);
      return event;
    }
  }


  /**
   * Retrieves the first element from the queue (if any) without removing it.
   *
   * @return the first event on the queue
   */
  public synchronized CIAgentEvent peekEvent() {
    if (eventQueue.size() == 0) {
      return null;
    } else {
      return (CIAgentEvent) eventQueue.elementAt(0);
    }
  }
}
