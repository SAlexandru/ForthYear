package infofilter;

import java.io.*;
import java.net.*;
import java.util.*;

import ciagent.*;

 
/**
 * The <code>NewsReaderAgent</code> class implements an agent can read
 * one or more news articles from an NNTP host.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class NewsReaderAgent extends CIAgent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 8617793044707310943L;
String newsHost = "news1.attglobal.net";   // name of the news server
  String newsGroup = "comp.ai.neural-nets";  // name of the news group
  Socket news;                               // socket to news server
  PrintWriter newsOut;                       // stream to write to server
  BufferedReader newsIn;                     // stream to read from server
  boolean newsHostConnectionOK = false;      // true if connected
  int numArticles = 20;                      // number of articles to read
  Vector<NewsArticle> articles = new Vector<NewsArticle>();  // list of articles in specified news group
  NewsArticle currentArt;                    // current article
  Vector<?> newsGroups = new Vector<Object>();          // list of news groups of interest


  /**
   * Creates a <code>NewsReaderAgent</code> object.
   *
   */
  public NewsReaderAgent() {
    this("NewsReaderAgent");
  }


  /**
   * Creates a <code>NewsReaderAgent</code> object with the given name.
   *
   * @param name the String object that contains the agent name
   *
   */
  public NewsReaderAgent(String name) {
    super(name);
  }


  /**
   * Sets the news host to the given string.
   *
   * @param newsHost the String object that contains the news host
   *
   */
  public void setNewsHost(String newsHost) {
    this.newsHost = newsHost;
  }


  /**
   * Retrieves the news host from this agent.
   *
   * @return the String object that contains the news host
   *
   */
  public String getNewsHost() {
    return newsHost;
  }


  /**
   * Sets the news group to the given string.
   *
   * @param newsGroup the String object that contains the news group
   *
   */
  public void setNewsGroup(String newsGroup) {
    this.newsGroup = newsGroup;
  }


  /**
   * Retrieves the news group from this agent.
   *
   * @return the String object that contains the news group
   *
   */
  public String getNewsGroup() {
    return newsGroup;
  }


  /**
   * Sets the maximum number of articles read by this agent.
   *
   * @param numArticles the integer value for the number of articles
   *
   */
  public void setNumArticles(int numArticles) {
    this.numArticles = numArticles;
  }


  /**
   * Retrieves the number of articles to be read by this agent.
   *
   * @return the integer value for the number of articles
   *
   */
  public int getNumArticles() {
    return numArticles;
  }


  /**
   * Retrieves the articles.
   *
   * @return the Vector object that contains the articles
   *
   */
  @SuppressWarnings("unchecked")
public Vector<NewsArticle> getArticles() {
    return (Vector<NewsArticle>) articles.clone();
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
   * Retrieves the task description.
   *
   * @return the String object that contains the task description
   *
   */
  public String getTaskDescription() {
    return "Download a news group";
  }


  /**
   * Does nothing.
   */
  public void process() {}


  /**
   * Stops the agent processing.
   *
   */
  public void stop() {
    trace(name + " stopped \n");
    stopAgentProcessing();
  }


  /**
   * Does nothing.
   *
   */
  public void processTimerPop() {}


  /**
   * Processes a CIAgentEvent object (trace, downloadNewsGroup).
   *
   * @param event the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent event) {
    Object source = event.getSource();
    Object arg = event.getArgObject();
    Object action = event.getAction();

    trace(name + ":  CIAgentEvent received by " + name + " from " + source.getClass());
    if (action != null) {
      if (action.equals("trace")) {
        if (((arg != null) && (arg instanceof String))) {
          trace((String) arg);            // display the msg
        }
      } else if (action.equals("downloadNewsGroup")) {

        // download the news group here
        downloadNewsGroup((Vector<?>) arg);  // pass args to method
      }
    }
  }


  /**
   * Sends an article that was read from the news server to anyone
   * listening for it.
   *
   * @param art the NewsArticle object to be sent
   */
  protected void sendArticleToListeners(NewsArticle art) {
    CIAgentEvent event = new CIAgentEvent(this, "addArticle", art);

    notifyCIAgentEventListeners(event);
  }


  /**
   * Connects to the news host, reads the news group, and closes the connection
   * with the news host.
   *
   * @param args the Vector that contains the news host and news group strings
   *
   */
  protected void downloadNewsGroup(Vector<?> args) {
    System.out.println("NewsReaderAgent .. downloading news group");
    String newsHost;
    String newsGroup;

    newsHost = (String) args.elementAt(0);
    newsGroup = (String) args.elementAt(1);

    // open news host
    connectToNewsHost(newsHost);

    // download news group
    if (newsIn != null) readNewsGroup(newsGroup);

    // close news host
    if (newsOut != null) closeNewsHost();

    if ((newsIn == null) || (newsOut == null)) {
        trace("FilterAgent Error - could not connect to NewsHost");
    }
  }


  /**
   *  Creates a socket connection to an NNTP news server.
   *
   * @param newsHost the String object that contains the news host
   */
  public void connectToNewsHost(String newsHost) {
    try {
      news = new Socket(newsHost, 119);
      newsIn = new BufferedReader(new InputStreamReader(news.getInputStream()));
      newsOut = new PrintWriter(news.getOutputStream(), true);
      String reply = newsIn.readLine();

      trace(reply + "\n");
      newsHostConnectionOK = true;
    } catch (Exception e) {
      trace("FilterAgent connectToNewsHost() exception:" + e);
      newsHostConnectionOK = false;
    }
  }


  /**
   * Closes the connection to the NNTP news host.
   */
  void closeNewsHost() {
    try {
      String cmd = "QUIT \n";

      newsOut.println(cmd);
      trace(cmd + " \n");
      newsIn.close();
    } catch (Exception e) {
      trace("FilterAgent closeNewsHost() exception:" + e);
    }
    newsHostConnectionOK = false;  // either way, we're in trouble
  }


  /**
   * Reads an entire news group.
   *
   * @param newsGroup the String object that contains the news group to be read
   */
  public void readNewsGroup(String newsGroup) {
    int maxArticles = numArticles;  // use value set by user (100 max)
    int numArticlesRead = 0;

    articles.clear();  // remove all existing articles from list
    try {
      String cmd = "GROUP " + newsGroup + " \n";

      newsOut.println(cmd);
      String reply = newsIn.readLine();

      trace(cmd + " \n" + reply + "\n");
      StringTokenizer st = new StringTokenizer(reply);
      String s1 = st.nextToken();       // response code
      st.nextToken();       // number of appends
      String s3 = st.nextToken();       // first id
      st.nextToken();       // last id
      st.nextToken();       // newsgroup

      if (s1.equals("411")) {
        trace("Error - invalid news group") ;
        System.out.println("Error - invalid news group");
        return ;       // unknown newsgroup on this news server
      }

      cmd = "STAT " + s3 + "\n";
      newsOut.println(cmd);
      reply = newsIn.readLine();
      trace(cmd + " \n" + reply + "\n");
      String retCode;

      do {

        // trace("");
        cmd = "HEAD \n";
        newsOut.println(cmd);
        reply = newsIn.readLine();
        trace(cmd + " \n" + reply + "\n");
        StringTokenizer tok = new StringTokenizer(reply, " ");

        retCode = tok.nextToken();
        String id = tok.nextToken();
        if (!retCode.equals("221")) {
          continue;
        }

        // now read all header records for this article and parse
        NewsArticle art = parseHeader(id);

        articles.addElement(art);       // add to Vector
        try {
          Thread.sleep(100);            // give up processor so GUI can update
        } catch (InterruptedException e) {}
        cmd = "BODY \n";
        newsOut.println(cmd);
        reply = newsIn.readLine();
        StringTokenizer stok = new StringTokenizer(reply);

        retCode = stok.nextToken();     // response code
        if (!retCode.equals("222")) {   // error?
          articles.removeElement(art);  // bad article
          continue;
        }
        trace(cmd + " \n" + reply + "\n");

        // read the body of the article
        StringBuffer bodyText = new StringBuffer();

        do {
          reply = newsIn.readLine();
          bodyText.append(reply + "\n");
        } while (!reply.equals("."));
        art.setBody(bodyText.toString());
        sendArticleToListeners(art);
        numArticlesRead++;              // show that we read one more article
        cmd = "\n NEXT \n";
        newsOut.println(cmd);
        reply = newsIn.readLine();
        trace(cmd + " \n" + reply + "\n");
        StringTokenizer st2 = new StringTokenizer(reply);

        retCode = st2.nextToken();      // response code
        System.out.println("articles.size() = " + articles.size());
      } while (retCode.equals("223") && (numArticlesRead < maxArticles));  // 421 = no  next article
      currentArt = (NewsArticle) articles.elementAt(0);
      trace(currentArt.body);
    } catch (Exception e) {
      trace("FilterAgent readNewsGroup() exception:" + e);
    }
  }


  /**
   * Reads a single article from the opened NNTP server.
   *
   * @param id the String object that contains the id of the article to be read
   */
  public void readSingleArticle(String id) {
    try {
      String cmd = "STAT " + id + "\n";

      newsOut.println(cmd);
      String reply = newsIn.readLine();

      trace(cmd + " \n" + reply + "\n");
      cmd = "BODY \n";
      newsOut.println(cmd);
      reply = newsIn.readLine();
      trace(cmd + " \n" + reply + "\n");
      do {
        reply = newsIn.readLine();
        trace(reply + "\n");
      } while (!reply.equals("."));
    } catch (Exception e) {
      trace("FilterAgent readSingleArticle() exception:" + e);
    }
  }



  /**
   * Parses the article header and retrieves the subject line.
   *
   * @param id the String object that contains the id of the article
   *
   * @return the NewsArticle object that contains the subject line of the article
   *
   */
  protected NewsArticle parseHeader(String id) {
    NewsArticle art = new NewsArticle(id);

    String line;

    try {
      do {
        line = newsIn.readLine();
        if (line.charAt(0) == 'S') {
          StringTokenizer tagTok = new StringTokenizer(line, " ");

          if (tagTok.nextToken().equals("Subject:")) {
            art.subject = line;
          }
        }
      } while (!line.equals("."));
    } catch (Exception e) {
      trace("Exception:" + e);
    }
    return art;
  }
}
