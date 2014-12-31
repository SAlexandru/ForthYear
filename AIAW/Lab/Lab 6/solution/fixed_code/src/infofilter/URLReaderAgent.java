package infofilter;

import java.io.*;
import java.net.*;
import ciagent.*;

 

/**
 * The <code>URLReaderAgent</code> class implements an agent that reads
 * web pages and optionally pass parameters to the page.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2000
 *
 */
public class URLReaderAgent extends CIAgent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 7023191283823678829L;
URL url = null;             // URL specification
  String paramString = null;  // optional param string for queries
  String contents = "";       // the contents of the URL or URL response


  /**
   * Creates a <code>URLReaderAgent</code> object.
   *
   */
  public URLReaderAgent() {
    this("URLReaderAgent");
  }


  /**
   * Creates a <code>URLReaderAgent</code> object with the given name.
   *
   * @param name the String object that contains the name of the agent
   *
   */
  public URLReaderAgent(String name) {
    super(name);
  }


  /**
   * Sets the URL of the web page.
   *
   * @param url the URL object for the web page
   *
   */
  public void setURL(URL url) {
    this.url = url;
  }


  /**
   * Retrieves the URL of the web page.
   *
   * @return the URL object for the web page
   *
   */
  public URL getURL() {
    return url;
  }


  /**
   * Sets the parameter string to be sent to the web page .
   *
   * @param paramString the String object that contains the parameters
   *
   */
  public void setParamString(String paramString) {
    this.paramString = paramString;
  }


  /**
   * Retrieves the web page parameter string.
   *
   * @return the String object that contains the parameters
   *
   */
  public String getParamString() {
    return paramString;
  }


  /**
   * Retrieves the contents of the web page.
   *
   * @return the String object that contains the web page contents
   *
   */
  public String getContents() {
    return contents;
  }


  /**
   * Retrieves the task description for this agent.
   *
   * @return the String object that contains the task description
   *
   */
  public String getTaskDescription() {
    return "Read a URL";
  }


  /**
   * Initializes the agent by setting the sleep time to 5 seconds.
   *
   */
  public void initialize() {
    setSleepTime(5 * 1000);  // every 5 seconds
    setState(CIAgentState.INITIATED);
  }


  /**
   * Does nothing.
   */
  public void process() {}


  /**
   * Does nothing.
   */
  public void processTimerPop() {
  }


  /**
   * Processes a CIAgentEvent (trace or getURLText).
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    Object source = event.getSource();
    Object arg = event.getArgObject();
    Object action = event.getAction();

    trace("\n" + name + ":  CIAgentEvent received by " + name + " from " + source.getClass());
    if (action != null) {
      if (action.equals("trace")) {
        if (((arg != null) && (arg instanceof String))) {
          trace((String) arg);  // display the msg
        }
      } else if (action.equals("getURLText")) {

        // read the URL here
        String text = getURLText();
        if (text != null) {
          // send back in event
          NewsArticle article = new NewsArticle("URL") ;
          article.setSubject("URL: " + url.toString()) ;
          article.setBody(text) ;
          sendArticleToListeners(article);
        }
      }
    }
  }


  /**
   * Sends the URL text to anyone listening for it.
   *
   * @param article the NewArticle object that contains the URL text
   */
  protected void sendArticleToListeners(NewsArticle article) {
    System.out.println("URLReaderAgent -- sending URL text to listeners ");
    CIAgentEvent event = new CIAgentEvent(this, "addArticle", article);

    notifyCIAgentEventListeners(event);
  }


  /**
   *  Reads a single URl and optionally passes a paramString to it
   *  (if it is a CGI bin).
   *
   * @return the String object that contains the web page content
   */
  protected String getURLText() {
    HttpURLConnection connection;
    StringBuffer body = new StringBuffer();

    System.out.println("URLReaderAgent ... starting to read URL ");
    try {
      connection = (HttpURLConnection) url.openConnection();
      System.out.println("Opened connection");

      // process params if any
      if ((paramString != null) && (paramString.length() > 0)) {
        connection.setDoOutput(true);
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        out.println(paramString);
        out.close();
      }
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        body.append(inputLine);
        body.append("\n");
      }
      in.close();
    } catch (Exception e) {
      trace("Error: URLReaderAgent could not connect to URL : " + e.toString());
      return null ;
    }
    contents = body.toString();  // save as string in agent
    return contents;
  }
}
