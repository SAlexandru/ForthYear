package ciagent;


/**
 * The <code>CIAgentEventListener</code> defines the interface for the event
 * listener.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 1997, 2001
 *
 */
public interface CIAgentEventListener extends java.util.EventListener {


  /**
   * Processes the event.
   *
   * @param e the CIAgentEvent to be processed
   */
  public void processCIAgentEvent(CIAgentEvent e);


  /**
   * Adds the event to the asynchronous event queue.
   *
   * @param e the event to be added to the event queue
   */
  public void postCIAgentEvent(CIAgentEvent e);
}
