package marketplace;

 
import java.util.*;

import ciagent.*;


/**
 * The <code>FacilitatorAgent</code> class implements the global facilitator
 * for the marketplace application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class FacilitatorAgent extends CIAgent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 2405662034107764800L;
private static FacilitatorAgent instance = null;  // Singleton
  protected Random random = new Random();  // used to select agents
  protected Hashtable<String, Object> allAgents = new Hashtable<String, Object>();  // agent name is key, agent object is value
  protected Hashtable<String, Vector<CIAgent>> communities = new Hashtable<String, Vector<CIAgent>>();  // domain name is key, vector of agent object is value
  protected BuySellMessage msg;            // current message being processed



  /**
   * Creates a <code>FacilitatorAgent</code> object.
   *
   * Note: can't be used as a Java Bean without public constructor
   *
   */
  protected FacilitatorAgent() {
    this("Facilitator");
  }


  /**
   * Creates a <code>FacilitatorAgent</code> object.
   *
   * @param name the String object that contains the name of the facilitator
   *
   */
  protected FacilitatorAgent(String name) {
    super(name);
  }


  /**
   * Initializes the agent by setting the sleep time to 10 seconds.
   *
   */
  public void initialize() {
    setSleepTime(10 * 1000);  // sleep for 10 seconds
    setState(CIAgentState.INITIATED);
  }


  /**
   * Clears the agent and communities hashtables.
   *
   */
  public void reset() {
    allAgents = new Hashtable<String, Object>();    // clear all agents
    communities = new Hashtable<String, Vector<CIAgent>>();  // clear all communities

  }


  /**
   * Retrieves the task description (null).
   *
   * @return the String object (null)
   *
   */
  public String getTaskDescription() {

    return null;
  }


  /**
   *  Does nothing.
   */
  public void process() {}





  /**
   * Processes a timer pop by putting out a trace message.
   *
   */
  public void processTimerPop() {
    if (traceLevel > 0) trace("Facilitator: active \n");
  }


  /**
   * Processes a CIAgentEvent.
   *
   * @param e the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent e) {
    if (traceLevel > 0) {
      trace("Facilitator:  CIAgentEvent received by " + name + " from " + e.getSource() + " with args " + e.getArgObject() + "\n");
    }
    Object arg = e.getArgObject();
    Object action = e.getAction();

    if ((action != null) && (action.equals("processMessage"))) {
      msg = (BuySellMessage) arg;
      if (traceLevel > 0) {
        msg.display();  // show message contents
      }
      route(msg);
    }
  }


  /**
   * In the Singleton design pattern, used to get single instance.
   *
   * @return the FacilitatorAgent object
   *
   */
  static public FacilitatorAgent getInstance() {
    if (instance == null) {
      instance = new FacilitatorAgent("Facilitator");
    }
    return instance;
  }


  /**
   * Registers the agent contains in the given event.
   *
   * @param e the CIAgentEvent object that contains the agent to be registered
   *
   */
  public static synchronized void register(CIAgentEvent e) {
    if (instance == null) {
      instance = new FacilitatorAgent();
    }
    instance.allAgents.put(((CIAgent) e.getSource()).getName(), e.getSource());
    ((CIAgent) e.getSource()).addCIAgentEventListener(instance);
    instance.addCIAgentEventListener((CIAgent) e.getSource());
  }


  /**
   * Routes a message to the proper agent.
   *
   * @param msg the BuySellMessage object that contains the message to be
   *            routed
   *
   */
  public synchronized void route(BuySellMessage msg) {
    CIAgent sender = (CIAgent) allAgents.get(msg.sender);

    // agent wants to say they can handle questions concerning content
    if (msg.performative.equals("advertise")) {
      trace("Facilitator: adding " + msg.sender + " to " + msg.content + " community \n");
      if (communities.containsKey(msg.content)) {
        Vector<CIAgent> agents = (Vector<CIAgent>) communities.get(msg.content);

        agents.addElement(sender);
      } else {
        Vector<CIAgent> agents = new Vector<CIAgent>();

        communities.put(msg.content, agents);
        agents.addElement(sender);
      }
      return;
    }

    // agent wants to remove themselves from the community
    if (msg.performative.equals("unadvertise")) {
      trace("Facilitator: removing " + msg.sender + " from " + msg.content + " community \n");
      if (communities.containsKey(msg.content)) {
        Vector<?> agents = (Vector<?>) communities.get(msg.content);

        agents.removeElement(sender);
        if (agents.size() == 0) {
          communities.remove(msg.content);
        }
      }
      return;
    }

    // agent wants facilitator to recommend an agent to process content
    // randomly pick one from the list of advertising seller agents
    if (msg.performative.equals("recommend-one")) {
      String item = msg.content;

      if (communities.containsKey(msg.content)) {
        Vector<?> agents = (Vector<?>) communities.get(msg.content);
        int num = agents.size();
        int index;

        if (num > 1) {
          double rand = random.nextDouble();

          index = (int) (rand * num);
        } else {
          index = 0;
        }
        CIAgent agent = (CIAgent) agents.elementAt(index);  // first agent?

        msg.performative = "tell";
        msg.content = agent.getName();
        msg.receiver = msg.sender;
        msg.sender = name;  // show facilitator is the sender of this message

        // need to construct an anwser message and send it to the sender agent
        trace("Facilitator: Recommended " + agent.getName() + " to " + msg.receiver + " for " + item + "\n");
        sender.postCIAgentEvent(new CIAgentEvent(this, "processMessage", msg));
      } else {
        trace("Facilitator: there are no agents advertising " + msg.content + "\n");
      }
      return;
    }
    trace("Facilitator: routing " + msg.performative + " message from " + msg.sender + " to " + msg.receiver + "\n");

    // route the message to the receiver agent
    CIAgent receiver = (CIAgent) allAgents.get(msg.receiver);

    if (receiver != null) {
      receiver.postCIAgentEvent(new CIAgentEvent(this, "processMessage", msg));
    } else {

      // should tell sender that receiver could not be found?
      trace("Facilitator: receiver " + msg.receiver + " is unknown! \n");
    }
  }
}

