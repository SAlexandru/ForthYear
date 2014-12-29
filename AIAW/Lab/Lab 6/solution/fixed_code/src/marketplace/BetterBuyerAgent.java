package marketplace;

import ciagent.*;


/**
 * The <code>BetterBuyerAgent</code> class implements a buyer agent that
 * uses hard-coded logic when negotiating.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class BetterBuyerAgent extends BuyerAgent {


  /**
	 * 
	 */
	private static final long serialVersionUID = 7071431948347249379L;


/**
   * Creates a <code>BetterBuyerAgent</code> object.
   *
   */
  public BetterBuyerAgent() {
    this("BetterBuyer");
  }


  /**
   * Creates a <code>BetterBuyerAgent</code> object with the given name.
   *
   * @param name the String object that contains the name of the agent
   *
   */
  public BetterBuyerAgent(String name) {
    super(name);
  }


  /**
   * Negotiates by accepting an offer if it is less than the current strike
   * price or making a counter offer that is $50 less than the most recent
   * offer.
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
      current.lastOffer = offer.price - 50;  //
      BuySellMessage answer = new BuySellMessage("make-offer", offer.item + " " + offer.id + " " + current.lastOffer, offer.item, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);        // respond through Facilitator
    }
  }
}                                                                      
