package ciagent;


/**
 * The <code>CIAgentEvent</code> class defines the common event that is sent by
 * an intelligent agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 1997, 2001
 *
 */
public class CIAgentEvent extends java.util.EventObject {
  /**
	 * 
	 */
	private static final long serialVersionUID = 2168079563312950924L;
private Object argObject = null;
  private String action = null;


  /**
   * Creates an extremely simple <code>CIAgentEvent</code> object.
   *
   * @param source the Object that is a source of this event
   */
  public CIAgentEvent(Object source) {
    super(source);
  }


  /**
   * Creates a more complex <code>CIAgentEvent</code> object that includes an
   * object related to the event.
   *
   * @param source    the Object that is a source of this event
   * @param argObject the Object that is used to supply additional data about
   *                  this event
   */
  public CIAgentEvent(Object source, Object argObject) {
    this(source);
    this.argObject = argObject;
  }


  /**
   * Creates a more complex <code>CIAgentEvent</code> object that includes an
   * object related to the event and an action.
   *
   * @param source    the Object that is a source of this event
   * @param action    the String that represents the action for this event
   * @param argObject the Object that is used to supply additional data about
   *                  this event
   */
  public CIAgentEvent(Object source, String action, Object argObject) {
    this(source);
    this.action = action;
    this.argObject = argObject;
  }


  /**
   * Retrieves the object related to this event.
   *
   * @return the argument Object (if any)
   */
  public Object getArgObject() {
    return argObject;
  }


  /**
   * Retrieves the action related to this event.
   *
   * @return the action (if any)
   */
  public String getAction() {
    return action;
  }


  /**
   * Converts the event to a string formatted for display or printing.
   *
   * @return the formatted <code>String</code> object
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();

    buf.append("CIAgent ");
    buf.append("source: " + source);
    buf.append("action: " + action);
    buf.append("argObject: " + argObject);
    return buf.toString();
  }
}
