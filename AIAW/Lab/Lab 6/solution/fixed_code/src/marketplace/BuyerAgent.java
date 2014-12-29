package marketplace;

 
import java.util.*;

import ciagent.*;


/**
 * The <code>BuyerAgent</code> class implements a very simple buyer agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class BuyerAgent extends CIAgent {
  /**
	 * 
	 */
	private static final long serialVersionUID = -8587987742699400497L;
protected BuySellMessage msg;                        // current message being processed
  protected BasicNegotiation current;
  protected Vector<BasicNegotiation> wishList = new Vector<BasicNegotiation>();
  protected BasicNegotiation pending = null;           // item waiting for seller
  protected Hashtable<String, BasicNegotiation> inventory = new Hashtable<String, BasicNegotiation>();     // items we have purchased
  protected long totalSpent = 0;                       // total money spent on items
  protected Hashtable<String, BasicNegotiation> negotiations = new Hashtable<String, BasicNegotiation>();  // transaction history


  /**
   * Creates a <code>BuyerAgent</code> object.
   *
   */
  public BuyerAgent() {
    this("Buyer");
  }


  /**
   * Creates a <code>BuyerAgent</code> object with the given name.
   *
   * @param Name the String object the name of the agent
   *
   */
  public BuyerAgent(String name) {
    super(name);
  }


  /**
   * Registers with the facilitator and initializes its wish list.
   *
   */
  public void initialize() {

    // start a thread running and send an update message every interval secs
    trace(name + ": initialize() \n");
    msg = new BuySellMessage("register:", name, null, null, null, null);
    CIAgentEvent e = new CIAgentEvent(this, "processMessage", msg);

    FacilitatorAgent.register(e);  // add this agent to the Facilitator???

    // add items to buy on wish list
    wishList.addElement(new BasicNegotiation("guitar", 100));
    wishList.addElement(new BasicNegotiation("drums", 200));
    wishList.addElement(new BasicNegotiation("guitar", 100));
    setSleepTime(5 * 1000);        // process every 5 seconds
    setState(CIAgentState.INITIATED);
  }


  /**
   * Retrieves a description of what this agent is doing.
   *
   * @return the String object that contains the description
   *
   */
  public String getTaskDescription() {
    return "Buying stuff...";
  }


  /**
   * Does nothing.
   *
   */
  public void process() {}




  /**
   * Kicks off netotiations if there are items on the wish list and no
   * negotiation is already in progress.
   *
   */
  public void processTimerPop() {
    if ((wishList.size() > 0) && (pending == null)) {
      current = (BasicNegotiation) wishList.firstElement();
      pending = current;               // we have a pending negotiation
      wishList.removeElementAt(0);
      trace(name + " is looking to buy " + current.offer.item + "\n");
      msg = new BuySellMessage("recommend-one", current.offer.item, null, "Seller", current.offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", msg);

      notifyCIAgentEventListeners(e);  // signal interested observer
    }
    trace(name + " has purchased " + inventory.size() + " items for " + totalSpent + "\n");
  }


  /**
   * Processes a CIAgentEvent.
   *
   * @param e the CIAgentEvent object that contains the message to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent e) {
    if (traceLevel > 0) {
      trace(name + ":  CIAgentEvent received by " + name + " from " + e.getSource() + " with args " + e.getArgObject());
    }
    Object arg = e.getArgObject();
    Object action = e.getAction();

    if ((action != null) && (action.equals("processMessage"))) {
      msg = (BuySellMessage) arg;
      if (traceLevel > 0) {
        msg.display();  // show message contents
      }
      processMessage(msg);
    }
  }


  /**
   * Processes a BuySellMessage received in a CIAgentEvent.
   *
   * @param msg the BuySellMessage object to be processed
   *
   */
  public void processMessage(BuySellMessage msg) {

    // facilitator has found a seller for item
    if (msg.sender.equals("Facilitator")) {
      if (msg.performative.equals("tell")) {

        // OK, ask seller what he wants for the item
        BuySellMessage answer = new BuySellMessage("ask", pending.offer.item, msg.replyWith, msg.content, pending.offer.item, name);
        CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

        notifyCIAgentEventListeners(e);  // respond through Facilitator
        return;
      }
    }

    // seller is denying he has any items to sell
    // this is a response to an "ask"
    if (msg.performative.equals("deny")) {
      trace(name + ": Seller denied our 'ask' about " + msg.content + "\n");
      wishList.addElement(pending);  // put back on wish list
      pending = null;                // try again
      return;
    }
    Offer offer = new Offer(msg);  //

    trace(name + ": Offer from " + offer.sender + ": content is " + offer.item + " " + offer.id + " " + offer.price + "\n");
    if (negotiations.containsKey(offer.id)) {
      current = (BasicNegotiation) negotiations.get(offer.id);
    } else {
      current = pending;                    // get pending negotiation
      pending = null;                       // no items pending
      negotiations.put(offer.id, current);  // place on active list
    }
    current.newOffer(offer);  // update the negotiation object

    // seller has agreed and sales transaction is complete
    // transfer item to buyer, seller accepts our last offer
    if (msg.performative.equals("accept-offer")) {
      trace(name + ": OK -- sale of item " + offer.item + " with id " + offer.id + " at price of " + offer.price + " is complete \n");
      BuySellMessage answer = new BuySellMessage("tell", msg.content, msg.replyWith, msg.sender, offer.item, name);

      trace(name + ": " + offer.item + " purchased! \n");
      inventory.put(offer.id, current);
      negotiations.remove(offer.id);
      totalSpent += offer.price;
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);  // respond through Facilitator
    }

    // seller is making a counter offer
    // we can either, make a counter offer, accept, or reject
    if (msg.performative.equals("make-offer")) {
      negotiate(offer, msg);
    }

    // seller is rejecting our last offer - no counter offer
    if (msg.performative.equals("reject-offer")) {
      trace(name + ": " + msg.sender + " rejected our last offer ");
      negotiations.remove(offer.id);  // remove from active list
      wishList.addElement(current);   // put back on wish list
    }
  }



  /**
   * Accepts the offer if it is less than the strike price or offers a
   * counter offer.
   *
   * @param offer the Offer object that contains the current offer
   * @param msg the BuySellMessage object 
   *
   */
  void negotiate(Offer offer, BuySellMessage msg) {
    if (offer.price < current.strikePrice) {

      // accept the offer -- by repeating it to the seller
      BuySellMessage answer = new BuySellMessage("make-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);        // respond through Facilitator
    } else {

      // make a counter offer
      current.lastOffer = offer.price - 25;  //
      BuySellMessage answer = new BuySellMessage("make-offer", offer.item + " " + offer.id + " " + current.lastOffer, offer.item, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);        // respond through Facilitator
    }
  }
}

