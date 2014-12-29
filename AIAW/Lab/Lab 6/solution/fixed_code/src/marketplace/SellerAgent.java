package marketplace;

 
import java.util.*;

import ciagent.*;


/**
 * The <code>SellerAgent</code> class implements a very simple seller agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class SellerAgent extends CIAgent {
  /**
	 * 
	 */
	private static final long serialVersionUID = -6005373009917502287L;
private long seed = 0;
  protected BuySellMessage msg;                        // current message being processed
  protected BasicNegotiation current;
  protected long income = 0;                           // total money earned
  protected Vector<BasicNegotiation> inventory = new Vector<BasicNegotiation>();           // items we have for sale
  protected Hashtable<String, BasicNegotiation> negotiations = new Hashtable<String, BasicNegotiation>();  // transaction history


  /**
   * Creates a <code>SellerAgent</code> object.
   *
   */
  public SellerAgent() {
    this("Seller");
  }


  /**
   * Creates a <code>SellerAgent</code> object with the given name.
   *
   * @param name the String object that contains the name of the agent
   *
   */
  public SellerAgent(String name) {
    super(name);
  }


  /**
   * Retrieves the task description that indicates what this agent is doing.
   *
   * @return the String object that contains the task description
   *
   */
  public String getTaskDescription() {
     return "Selling stuff...";
  }


  /**
   * Registers with the facilitator, initalizes its inventory, and advertises
   * the items it has for sale.
   *
   */
  public void initialize() {

    // start a thread running and send an update message every interval secs
    trace(name + ": initialize() \n");
    msg = new BuySellMessage("register:", name, null, null, null, null);
    CIAgentEvent e = new CIAgentEvent(this, "processMessage", msg);

    FacilitatorAgent.register(e);  // add this agent to the Facilitator???
    inventory.addElement(new BasicNegotiation("guitar", 100));  // item and strike price
    inventory.addElement(new BasicNegotiation("drums", 225));
    inventory.addElement(new BasicNegotiation("guitar", 100));  // item and strike price

    // advertise all items for sale
    Enumeration<BasicNegotiation> enumeration = inventory.elements();

    while (enumeration.hasMoreElements()) {
      current = (BasicNegotiation) enumeration.nextElement();
      msg = new BuySellMessage("advertise", current.offer.item, null, null, current.offer.item, name);
      e = new CIAgentEvent(this, "processMessage", msg);
      notifyCIAgentEventListeners(e);  // signal interested observer
    }
    setSleepTime(15 * 1000);  // 15 seconds
    setState(CIAgentState.INITIATED);
  }


  /**
   * Does nothing.
   *
   */
  public void process() {}


   /**
   * Processes a timer pop by displaying a trace message.
   *
   */
  public void processTimerPop() {
    trace(name + " has " + inventory.size() + " items in inventory. \n");
    trace(name + " has earned " + income + ".\n");
  }


  /**
   * Processes a CIAgentEvent.
   *
   * @param e the CIAgentEvent object to be processed
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
   * Generates a unique id for an item in inventory.
   *
   * @return the String object that contains the unique id
   *
   */
  String genId() {
    seed++;
    return name + seed;
  }


  /**
   * Processes the BuySellMessage received in a CIAgentEvent object.
   *
   * @param msg the BuySellMessage object to be processed
   *
   */
  public void processMessage(BuySellMessage msg) {
    if (msg.performative.equals("ask")) {
      String item = msg.content;

      // see if we have any items left
      if (itemInInventory(item)) {

        // start a new negotiation
        String id = genId();

        current = removeItemFromInventory(item);
        current.offer = new Offer(msg.sender, item, id, 0);
        current.lastOffer = current.strikePrice + 100;
        negotiations.put(id, current);
        BuySellMessage answer = new BuySellMessage("make-offer", item + " " + id + " " + current.lastOffer, msg.replyWith, msg.sender, item, name);
        CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

        notifyCIAgentEventListeners(e);  // respond through Facilitator
      } else {

        // deny we have any to sell
        BuySellMessage answer = new BuySellMessage("deny", item, msg.replyWith, msg.sender, item, name);
        CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

        trace(name + ": deny we have any " + item + " to sell to " + msg.sender + "\n");
        notifyCIAgentEventListeners(e);  // respond through Facilitator
      }
      return;
    }
    Offer offer = new Offer(msg);

    trace(name + ": Offer from " + offer.sender + ": content is " + offer.item + " " + offer.id + " " + offer.price + "\n");
    current = (BasicNegotiation) negotiations.get(offer.id);
    if (current == null) {

      // we must have sold this item --- reject offer
      BuySellMessage answer = new BuySellMessage("reject-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);  // respond through Facilitator
      return;
    }
    current.newOffer(offer);

    // buyer has made an offer, we can accept, make a counter offer, or reject
    if (msg.performative.equals("make-offer")) {
      negotiate(offer, msg);
    }

    // buyer has received and acknowledged our accept-offer
    // remove item from active list, add $ to income
    // unadvertise this item with the Facilitator
    if (msg.performative.equals("tell")) {
      negotiations.remove(offer.id);   // remove from negotiations list
      income += offer.price;
      BuySellMessage msg2 = new BuySellMessage("unadvertise", current.offer.item, null, null, current.offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", msg2);

      notifyCIAgentEventListeners(e);  // signal interested observer
    }
  }


  /**
   * Negotiates by accepting or rejecting offers.
   *
   * @param offer the Offer object for the current offer
   * @param msg the BuySellMessage object
   *
   */
  void negotiate(Offer offer, BuySellMessage msg) {
    if (offer.price > current.strikePrice) {

      // accept
      BuySellMessage answer = new BuySellMessage("accept-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);  // respond through Facilitator
      return;
    }
    if (offer.price < current.strikePrice) {

      // reject
      rejectOffer(offer);
      return;
    }
    BuySellMessage answer = new BuySellMessage("make-offer", offer.item + " " + offer.id + " " + current.lastOffer, msg.replyWith, msg.sender, offer.item, name);
    CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

    notifyCIAgentEventListeners(e);  // respond through Facilitator
  }



  /**
   * Breaks off a negotiation (for whatever reason), returns the item to
   * inventory, and removes the negotiation from the active list.
   *
   * @param offer the Offer object that contains the offer being rejected
   *
   */
  void rejectOffer(Offer offer) {
    BuySellMessage answer = new BuySellMessage("reject-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
    CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

    notifyCIAgentEventListeners(e);  // respond through Facilitator
    negotiations.remove(offer.id);   // no further negotiations
    current.offer.id = null;  // offer id has expired
    inventory.addElement(current);  // place item back in inventory
  }


  /**
   * Checks if an item is in inventory
   *
   * @param item the String object that contains the item to be checked
   *
   * @return returns true if the item is in stock
   *
   */
  boolean itemInInventory(String item) {
    Enumeration<BasicNegotiation> enumeration = inventory.elements();
    boolean haveItem = false;

    while (enumeration.hasMoreElements()) {
      BasicNegotiation stockItem = (BasicNegotiation) enumeration.nextElement();

      if (stockItem.getItem().equals(item)) {
        haveItem = true;
        break;
      } else {
        continue;
      }
    }
    return haveItem;
  }



  /**
   * Removes an item from inventory.
   *
   * @param item the String object that contains the itme to be removed
   *
   * @return the BasicNegotiation object containing the item from inventory
   *         or null if we have no items
   *
   */
  BasicNegotiation removeItemFromInventory(String item) {
    Enumeration<BasicNegotiation> enumeration = inventory.elements();
    BasicNegotiation stockItem = null;

    while (enumeration.hasMoreElements()) {
      stockItem = (BasicNegotiation) enumeration.nextElement();
      if (stockItem.getItem().equals(item)) {
        inventory.removeElement(stockItem);
        break;
      } else {
        stockItem = null;  // this isn't it
        continue;
      }
    }
    return stockItem;
  }
}

