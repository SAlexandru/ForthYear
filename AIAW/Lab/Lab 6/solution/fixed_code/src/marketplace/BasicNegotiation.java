package marketplace;


/**
 * The <code>BasicNegotiation</code> class implements the negotiation
 * object used in the marketplace application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class BasicNegotiation {
  protected Offer offer;       // current offer for this item
  protected long strikePrice;  // price where we make a deal
  protected long lastOffer;    // last price we offered
  protected Offer prevOffer;   // previous offer for this item
  protected int iteration;     // # of back and forth offers



  /**
   * Creates a <code>BasicNegotiation</code> object
   * used to add items to inventory or wishList.
    *
   * @param item the String object
   * @param strikePrice the long object
   *
   */
  BasicNegotiation(String item, long strikePrice) {
    offer = new Offer(item);
    this.strikePrice = strikePrice;
    lastOffer = 0;
    iteration = 0;
  }



  /**
   * Creates a <code>BasicNegotiation</code> object
   * used for a new negotiation.
   *
   * @param offer the Offer object that is part of the negotiation
   * @param strikePrice the long value that is the strike price in the
   *                    negotiation
   *
   */
  BasicNegotiation(Offer offer, long strikePrice) {
    this.offer = offer;
    lastOffer = 0;
    iteration = 0;
    this.strikePrice = strikePrice;
  }


  /**
   * Sets the current offer to the new offer.
   *
   * @param newOffer the Offer object that contains the new offer
   *
   */
  public void newOffer(Offer newOffer) {
    prevOffer = offer;
    offer = newOffer;
  }


  /**
   * Retrieves the item from  the offer that is part of this negotiation.
   *
   * @return the String object that represents the item
   *
   */
  String getItem() {
    return offer.item;
  }
}
