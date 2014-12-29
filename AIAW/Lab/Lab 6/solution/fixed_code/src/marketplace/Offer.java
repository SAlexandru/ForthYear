package marketplace;

 
import java.util.*;


/**
 * The <code>Offer</code> class implements the offer used by the buyer and
 * seller agents in the marketplace application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class Offer {
  protected String sender;  // who made the offer
  protected String item;    // what item is offered
  protected String id;      // the unique object id
  protected long price;     // the offered sales price


  /**
   * Creates a <code>Offer</code> object that contains the given item.
   *
   * @param item the String object that contains the item for which an offer
   *             is being made
   *
   */
  Offer(String item) {
    sender = "";
    this.item = item;
    id = "";
    price = 0;
  }


  /**
   * Creates a <code>Offer</code> object with the given parameters.
   *
   * @param sender the String object that contains who is making the offer
   * @param item the String object that contains the item
   * @param id the String object that contains the item identifier
   * @param price the long value for the offer price
   *
   */
  Offer(String sender, String item, String id, long price) {
    this.sender = sender;
    this.item = item;
    this.id = id;
    this.price = price;
  }


  /**
   * Creates a <code>Offer</code> object using information in the BuySellMessage.
   *
   * @param msg the BuySellMessage object that contains information about
   *            the offer being made
   *
   */
  Offer(BuySellMessage msg) {

    // split content into item, id, price
    StringTokenizer s = new StringTokenizer(msg.content, " ");  //space delimited
    item = s.nextToken();
    id = s.nextToken();
    price = new Long(s.nextToken()).longValue();
    sender = msg.sender;
  }
}
