package marketplace;


import ciagent.*;
import rule.*;


/**
 * The <code>BestSellerAgent</code> class implements a buyer agent with the
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
public class BestSellerAgent extends SellerAgent implements Effector {
  /**
	 * 
	 */
	private static final long serialVersionUID = 5002925486878424620L;
protected BooleanRuleBase rb = new BooleanRuleBase("BestSeller");
  protected RuleVariable offerDelta;
  protected RuleVariable spread;
  protected RuleVariable firstOffer;
  protected Offer offer;  // the current offer


  /**
   * Creates a <code>BestSellerAgent</code> object.
   *
   */
  public BestSellerAgent() {
    this("BestSeller");
  }


  /**
   * Creates a <code>BestSellerAgent</code> object with the given name.
   *
   * @param name the String object that contains the name
   *
   */
  public BestSellerAgent(String name) {
    super(name);
  }


  /**
   * Initializes the BestSeller rule base.
   *
   */
  public void initialize() {
    initBestSellerRuleBase();
    super.initialize();
  }


  /**
   * Used by action rules in rule base to make or accept offers.
   *
   * @param obj the Object object implementing the effector
   * @param eName the String object that contains the name of the effector
   * @param args the String object that contains the arguments for the effector
   *
   * @return the long object
   *
   */
  public long effector(Object obj, String eName, String args) {
    if (eName.equals("make-offer")) {

      // rule base decided to counter-offer
      long delta = (new Long(offerDelta.getValue())).longValue();

      if (delta == -100) {
        current.lastOffer = current.strikePrice;
      } else {
        current.lastOffer = offer.price + delta;  //
      }
      BuySellMessage answer = new BuySellMessage("make-offer", offer.item + " " + offer.id + " " + current.lastOffer, offer.item, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);             // respond through Facilitator
      return 0;
    }
    if (eName.equals("reject-offer")) {

      // rule base decided to reject the offer
      rejectOffer(offer);
      return 0;
    }
    if (eName.equals("accept-offer")) {

      // rule base decided to accept the offer
      BuySellMessage answer = new BuySellMessage("accept-offer", msg.content, msg.replyWith, msg.sender, offer.item, name);
      CIAgentEvent e = new CIAgentEvent(this, "processMessage", answer);

      notifyCIAgentEventListeners(e);  // respond through Facilitator
      return 0;
    }
    return 1;  // unknown effector method
  }


  /**
   * Uses forward chaining and the rule base to determine whether to
   * accept or reject an offer.
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
    if (offer.price < current.strikePrice) {
      spread.setValue("<0");  // below asking price
    } else {                  // above asking price
      long delta = offer.price - current.strikePrice;

      if (delta < 25) {
        spread.setValue("0-25");
      } else if (delta < 50) {
        spread.setValue("25-50");
      } else if (delta >= 50) {
        spread.setValue(">50");
      }
    }

    // if buyer echos the lastOffer, then he is agreeing
    if (offer.price == current.lastOffer) {
      spread.setValue(">50");  // accept via rule action
    }
    if (current.iteration == 0) {
      firstOffer.setValue("yes");
    } else {
      firstOffer.setValue("no");
    }
    current.iteration++;  // increment iteration count
    if (current.iteration > 10) {  // could be randomized
      rejectOffer(offer);
      return;                      // break off negotiations
    }
    rb.forwardChain();  // inference
    if (offerDelta.getValue() == null) {
      trace(name + " rule base couldn't decide what to do -- so reject it.\n");
      rejectOffer(offer);
    }
  }



  /**
   * Initializes the BestSeler rule base.
   *
   */
  public void initBestSellerRuleBase() {
    offerDelta = new RuleVariable(rb, "offerDelta");
    offerDelta.setLabels("-100 0 30 60 100");
    firstOffer = new RuleVariable(rb, "firstOffer");
    firstOffer.setLabels("yes no");
    spread = new RuleVariable(rb, "spread");
    spread.setLabels("<0 0-25 25-50 >50");

    // Note: at this point all variables values are NULL
    Condition cEquals = new Condition("=");
    Condition cNotEquals = new Condition("!=");

    // define rules
    new Rule(rb, "first", new Clause(firstOffer, cEquals, "yes"),
                                       new Clause(offerDelta, cEquals, "50"));  // counter
    new Rule(rb, "second1", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, "25-50") },
                                                         new Clause(offerDelta, cEquals, "40"));  // counter
    new Rule(rb, "second2", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, "0-25") },
                                                         new Clause(offerDelta, cEquals, "0"));  // accept
    new Rule(rb, "second3", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, ">50") },
                                                         new Clause(offerDelta, cEquals, "0"));  // accept
    new Rule(rb, "second4", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                         new Clause(spread, cEquals, "<0") },
                                                         new Clause(offerDelta, cEquals, "-100"));  // reject

    // action rule
    new Rule(rb, "accept", new Clause(offerDelta, cEquals, "0"), new EffectorClause("accept-offer", "0"));

    // action rule
    new Rule(rb, "reject", new Clause[]{ new Clause(firstOffer, cEquals, "no"),
                                                       new Clause(offerDelta, cEquals, "-100") },
                                                       new EffectorClause("reject-offer", "0"));

    // action rule
    new Rule(rb, "counter", new Clause(offerDelta, cNotEquals, "0"),
                                           new EffectorClause("make-offer", null));  // use offerDelta

    // define this object as effector implementor
    rb.addEffector(this, "make-offer");
    rb.addEffector(this, "reject-offer");
    rb.addEffector(this, "accept-offer");
  }
}
