package marketplace;

import ciagent.*;
import rule.*;


/**
 * The <code>BestBuyerAgent</code> class implements a buyer agent with the
 * best negotiating skills in the marketplace application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class BestBuyerAgent extends BuyerAgent implements Effector {
  /**
	 * 
	 */
	private static final long serialVersionUID = 8138585200879733383L;
protected BooleanRuleBase rb = new BooleanRuleBase("BestBuyer");
  protected RuleVariable offerDelta;
  protected RuleVariable spread;
  protected RuleVariable firstOffer;
  protected Offer offer;  // the current offer


  /**
   * Creates a <code>BestBuyerAgent</code> object.
   *
   */
  public BestBuyerAgent() {
    this("BestBuyer");
  }


  /**
   * Creates a <code>BestBuyerAgent</code> object with the given name.
   *
   * @param name the String object that contains the name
   *
   */
  public BestBuyerAgent(String name) {
    super(name);
  }



  /**
   * Initializes the rule base for this buyer agent.
   *
   */
  public void initialize() {
    initBestBuyerRuleBase();
    super.initialize();  // call base class initialization
  }


  /**
   * Method effector used by action rules in rule base to make or accept offers
   *
   * @param obj the Object object that implements the effector
   * @param eName the String object that contains the effector name
   * @param args the String object that contains the effector arguments
   *
   * @return the long value that represents the status of the effector execution
   *
   */
  public long effector(Object obj, String eName, String args) {
    if (eName.equals("make-offer")) {

      // rule base decided to counter-offer
      long delta = (new Long(offerDelta.getValue())).longValue();

      current.lastOffer = offer.price - delta;  //
      BuySellMessage answer = new BuySellMessage("make-offer", offer.item + " " + offer.id + " " + current.lastOffer, offer.item, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);           // respond through Facilitator
      return 0;
    }
    if (eName.equals("accept-offer")) {

      // rule base decided to accept the offer
      // just resend it to the seller
      BuySellMessage answer = new BuySellMessage("make-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);  // respond through Facilitator
      return 0;
    }
    return 1;  // unknown effector method
  }



  /**
   * Uses the rule base and forward chaining to decide whether to accept
   * an offer or make a counter offer.
   *
   * @param offer the Offer object that contains the current offer
   * @param msg the BuySellMessage object
   *
   */
  void negotiate(Offer offer, BuySellMessage msg) {

    // figure out if 1st time or second time or other
    // compute spread
    // let rule base figure out our response
    // if deltaoffer is 0 then we should accept
    rb.reset();  // allow all rules to fire, clear vars
    this.offer = offer;  // make visible to rule base effectors
    if (offer.price >= current.strikePrice) {
      long delta = offer.price - current.strikePrice;

      if (delta < 25) {
        spread.setValue("<25");
      } else if (delta < 50) {
        spread.setValue("25-50");
      } else if (delta >= 50) {
        spread.setValue(">50");
      }
    } else {
      spread.setValue("0");  // meets our price, accept
    }
    if (current.prevOffer.id.equals("")) {
      firstOffer.setValue("yes");
    } else {
      firstOffer.setValue("no");
    }
    offerDelta.setValue(null);
    rb.forwardChain();  // inference
    if (offerDelta.getValue() == null) {
      trace(name + " rule base couldn't decide what to do.\n");
    }
  }



  /**
   * Initializes the BestBuyer rule base.
   *
   */
  public void initBestBuyerRuleBase() {
    offerDelta = new RuleVariable(rb, "offerDelta");
    offerDelta.setLabels("0 25 50 100");
    firstOffer = new RuleVariable(rb, "firstOffer");
    firstOffer.setLabels("yes no");
    spread = new RuleVariable(rb, "spread");
    spread.setLabels("<25 25-50 >50");

    // Note: at this point all variables values are NULL
    Condition cEquals = new Condition("=");
    Condition cNotEquals = new Condition("!=");

    // define rules
    new Rule(rb, "first", new Clause(firstOffer, cEquals, "yes"),
                                       new Clause(offerDelta, cEquals, "75"));  // counter
    new Rule(rb, "second1", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, ">50") },
                                                         new Clause(offerDelta, cEquals, "50"));  // counter
    new Rule(rb, "second2", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, "25-50") },
                                                         new Clause(offerDelta, cEquals, "25"));  // counter
    new Rule(rb, "second3", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, "<25") },
                                                         new Clause(offerDelta, cEquals, "0"));  // accept

    // action rule
    new Rule(rb, "accept", new Clause(offerDelta, cEquals, "0"),
                                         new EffectorClause("accept-offer", "0"));

    // action rule
    new Rule(rb, "counter", new Clause(offerDelta, cNotEquals, "0"),
                                           new EffectorClause("make-offer", null));  // use offerDelta

    // define this object as effector implementor
    rb.addEffector(this, "make-offer");
    rb.addEffector(this, "accept-offer");
  }
}
