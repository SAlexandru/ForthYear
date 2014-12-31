package marketplace;


/**
 * The <code>BuySellMessage</code> class implements a KQML-like message for
 * the agents in the marketplace application.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class BuySellMessage {
  protected String performative;
  protected String content;
  protected String inReplyTo;
  protected String language;
  protected String ontology;
  protected String receiver;
  protected String replyWith;
  protected String sender;


  /**
   * Creates a <code>BuySellMessage</code> object with the given parameters.
   *
   * @param Performative the String object for the performative
   * @param Content the String object for the content
   * @param InReplyTo the String object used when replying
   * @param Language the String object for the language used in the messages
   * @param Ontology the String object for the ontology used in the messages
   * @param Receiver the String object that represents the receiver
   * @param ReplyWith the String object used when replying
   * @param Sender the String object that represents the sender
   *
   */
  BuySellMessage(String Performative, String Content, String InReplyTo, String Language, String Ontology, String Receiver, String ReplyWith, String Sender) {
    performative = Performative;
    content = Content;
    inReplyTo = InReplyTo;
    language = Language;
    ontology = Ontology;
    receiver = Receiver;
    replyWith = ReplyWith;
    sender = Sender;
  }


  /**
   * Creates a <code>BuySellMessage</code> object with the given parameters.
   *
   * @param Performative the String object for the performative
   * @param Content the String object for the content
   * @param InReplyTo the String object used when replying
   * @param Receiver the String object that represents the receiver
   * @param ReplyWith the String object used when replying
   * @param Sender the String object that represents the sender
   *
   */
  BuySellMessage(String Performative, String Content, String InReplyTo, String Receiver, String ReplyWith, String Sender) {
    performative = Performative;
    content = Content;
    inReplyTo = InReplyTo;
    receiver = Receiver;
    replyWith = ReplyWith;
    sender = Sender;
  }


  /**
   * Displays the information contained in the message.
   *
   */
  public void display() {
    System.out.println("performative: " + performative + "\n" + "content: " + content + "\n" + "inReplyTo: " + inReplyTo + "\n" + "language: " + language + "\n" + "ontology: " + ontology + "\n" + "receiver: " + receiver + "\n" + "replyWith: " + replyWith + "\n" + "sender: " + sender + "\n");
  }
}
