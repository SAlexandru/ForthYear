package marketplace;

import ciagent.*;


/**
 * The <code>BetterSellerAgent</code> class implements a seller agent that
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
public class BetterSellerAgent extends SellerAgent {


  /**
	 * 
	 */
	private static final long serialVersionUID = 6420338397123237698L;



/**
   * Creates a <code>BetterSellerAgent</code> object.
   *
   */
  public BetterSellerAgent() {
    this("BetterSeller");
  }


  /**
   * Creates a <code>BetterSellerAgent</code> object with the given name.
   *
   * @param name the String object that contains the name of the agent
   *
   */
  public BetterSellerAgent(String name) {
    super(name);
  }



  /**
   * Accepts, rejects or makes a counter offer depending on the offer price.
   *
   * @param offer the Offer object that contains the current offer
   * @param msg the BuySellMessage object
   *
   */
  void negotiate(Offer offer, BuySellMessage msg) {
    if (offer.price >= current.strikePrice) {

      // accept
      BuySellMessage answer = new BuySellMessage("accept-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);  // respond through Facilitator
      return;
    }
    if (offer.price < current.strikePrice) {
      long spread = current.strikePrice - offer.price;

      if (spread > 50) {                 // reject
        BuySellMessage answer = new BuySellMessage("reject-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
        CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

        notifyCIAgentEventListeners(e);  // respond through Facilitator
      } else {                           // counter offer at strike price
        BuySellMessage answer = new BuySellMessage("make-offer", offer.item + " " + offer.id + " " + current.strikePrice, msg.replyWith, msg.sender, offer.item, name);
        CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

        notifyCIAgentEventListeners(e);  // respond through Facilitator
      }
      return;
    }
  }
}

