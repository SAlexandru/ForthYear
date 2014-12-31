package infofilter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import learn.BackProp;
import learn.DataSet;
import learn.KMapNet;
import ciagent.CIAgent;
import ciagent.CIAgentEvent;
import ciagent.CIAgentState;



/**
 * The <code>FilterAgent</code> class implements an agent which scores
 * news articles using a keyword list, maintains a user profile, and
 * filters articles
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class FilterAgent extends CIAgent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 7567699033461002936L;
transient InfoFilterFrame infoFilter;
  protected String[] keywords;           // user specified keywords
  protected KMapNet clusterNet;
  protected BackProp ratingNet;
  protected boolean buildClusterNet = false;
  protected boolean clusterNetTrained = false;
  protected boolean buildRatingNet = false;
  protected boolean ratingNetTrained = false ;


  public static final String fileName = "filterAgent.ser" ;

  public static final int USE_KEYWORDS = 0 ;
  public static final int USE_CLUSTERS = 1 ;
  public static final int USE_PREDICTED_RATING = 2 ;

  public static final String  USELESS_RATING = "Useless";
  public static final String  NOTVERY_RATING = "Not very useful";
  public static final String  NEUTRAL_RATING = "Neutral";
  public static final String  MILDLY_RATING = "Mildly interesting";
  public static final String  INTERESTING_RATING = "Interesting";

  /**
   * Creates a <code>FilterAgent</code> object.
   *
   */
  public FilterAgent() {
    this("FilterAgent");
  }


  /**
   * Creates a <code>FilterAgent</code> object with the given name.
   *
   * @param name the String object that contains the name of this agent
   *
   */
  public FilterAgent(String name) {
    super(name);

    // create the default keyword list
    keywords = new String[10];  // default list
    keywords[0] = "java";
    keywords[1] = "agent";
    keywords[2] = "fuzzy";
    keywords[3] = "intelligent";
    keywords[4] = "neural";
    keywords[5] = "network";
    keywords[6] = "genetic";
    keywords[7] = "rule";
    keywords[8] = "learning";
    keywords[9] = "inferencing";

  }

  /**
   * Retrieves the task description used for display purposes.
   *
   * @return the String object that contains the task description
   *
   */
  public String getTaskDescription() {
    return "Filter articles";
  }


  /**
   * Initializes this agent by setting the sleep timer.
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
   * Processes a timer pop by performing neural net training, if requested.
   *
   */
  public void processTimerPop() {

    if (!buildClusterNet && !buildRatingNet){
      status("Idle") ;
      return ;
    }

    if (buildClusterNet) {
     try {
      status("Training Cluster network");
      buildClusterNet = false;
      trainClusterNet();             // train the network
      infoFilter.clusterNetTrained();  // signal application
      clusterNetTrained = true ;
      status("Cluster network trained");
      } catch (Exception e) {
        status("ClusterNet - No data " + e.toString()) ;
      }
    } else if (buildRatingNet) {
      try {
      status("Training Rating network");
      buildRatingNet = false;
      trainRatingNet();               // train the network
      infoFilter.ratingNetTrained();
      ratingNetTrained = true ;
      status("Rating network trained");
      saveToFile(fileName) ; // serialize the agent out
      } catch (Exception e) {
        status("RatingNet - No data " + e.toString()) ;
      }
     } else if (clusterNetTrained && ratingNetTrained) {
       status("Trained, Serialized") ;
     }
  }


  /**
   * Processes a CIAgentEvent.
   *
   * @param e the CIAgentEvent object to be processed
   *
   */
  public void processCIAgentEvent(CIAgentEvent e) {
    trace(name + ":  CIAgentEvent received by " + name + " from " + e.getSource() + " with arg " + e.getArgObject());

    // set flag for autonomous thread to see
    String arg = (String) e.getArgObject();

    if (arg.equals("buildClusterNet")) {
      buildClusterNet = true;
    } else if (arg.equals("buildRatingNet")) {
      buildRatingNet = true;
    }
  }

  /**
  *  Retrieves the list of keywords in the FilterAgent profile.
  *
  * @return a String[] of keywords
  */
  public String[] getKeywords() {
    return keywords ;
  }

  /**
  *  Sets the list of keywords used in the FilterAgent profile.
  *
  * @param keywords a String[] that contains the list of keywords
  */
  public void setKeywords(String[] keywords) {
    this.keywords = keywords ;
  }

  /**
   * Triggers an autonomous build of Kohonen Map.
   *
   */
  public void buildClusterNet() {
    buildClusterNet = true;
  }

  /**
   * Triggers an autonomous build of a back prop network.
   *
   */
  public void buildRatingNet() {
    buildRatingNet = true;
  }


  /**
   * Indicates whether the cluster net is trained.
   *
   * @return true if the cluster net is trained and false if it is not
   */
  public boolean isClusterNetTrained() {
    return clusterNetTrained ;
  }

  /**
   * Indicates whether the rating net is trained.
   *
   * @return true if the rating net is trained and false if it is not
   */
  public boolean isRatingNetTrained() {
    return ratingNetTrained ;
  }

  /**
   * Scores a single article.
   *
   * @param article the NewsArticle object to be scored
   * @param filterType the integer that indicates the type of filter
   *                 (USE_KEYWORDS, USE_CLUSTERS, USE_PREDICTED_RATING)
   *
   */
  void score(NewsArticle article, int filterType) {
    article.counts = countWordMultiKeys(keywords, article.body);
    int size = article.counts.length;
    int sum = 0;

    for (int i = 0; i < size; i++) {
      sum += article.counts[i];
    }
    article.setKeywordScore(sum); // total of all keyword hits

    // based on the type of filter the user wants
    // fill in the score slot of each article
    switch (filterType) {
      case USE_KEYWORDS :  // keyword
        article.setKeywordScore(sum);
        break;
      case USE_CLUSTERS :  // cluster
        if (clusterNet != null) {

          //  pass through network --- get clusterID
          double[] inputRec = new double[size + 2];

          for (int i = 0; i < size; i++) {
            inputRec[i] = (double) article.counts[i];
          }
          inputRec[size] = article.getKeywordScore();
          inputRec[size + 1] = article.getRating();
          article.setClusterId(clusterNet.getCluster(inputRec));
        }
        break;
      case USE_PREDICTED_RATING :  // neural rating
        if (ratingNet != null) {

          //  pass through network --- get score
          double[] inputRec = new double[size + 2];

          for (int i = 0; i < size; i++) {
            inputRec[i] = article.counts[i];
          }
          inputRec[size] = article.getKeywordScore();
          inputRec[size + 1] = article.getRating();
          article.setPredictedRating(ratingNet.getPrediction(inputRec));
        }
        break;
    }

    // OK, now do an automatic feedback pass
    // so user doesn't have to do it for each article
    // User can override via Feedback menu option
    if (sum == 0) {
      article.setUserRating(USELESS_RATING) ;
    } else if (sum < 2) {
      article.setUserRating(NOTVERY_RATING);
     } else if (sum < 4) {
      article.setUserRating(NEUTRAL_RATING);
     } else if (sum < 6) {
      article.setUserRating(MILDLY_RATING);
    } else {
      article.setUserRating(INTERESTING_RATING);
     }
  }

  /**
   * Scores all loaded articles using the given filter type.
   *
   * @param articles the vector of NewsArticles object to be scored
   * @param filterType the integer that indicates the type of filter
   *                 (USE_KEYWORDS, USE_CLUSTERS, USE_PREDICTED_RATING)
   *
   */
  void score(Vector<?> articles, int filterType) {
    try {
      Enumeration<?> enumeration = articles.elements();
      

      while (enumeration.hasMoreElements()) {
        trace("");
        NewsArticle article = (NewsArticle) enumeration.nextElement();

        score(article, filterType);
      }
    } catch (Exception e) {
      trace("Exception:" + e);
    }
    if (filterType == USE_CLUSTERS) {
      computeClusterAverages(articles);
    }
  }


  /**
   * Computes the average score for each cluster
   * and sets the score of each article in each cluster
   * to that average value.
   *
   * @param articles the Vector object that contains the articles for which
   *                 the cluster score is set
   *
   */
  void computeClusterAverages(Vector<?> articles) {
    int numClusters = 4;  // we are using 4 for now
    int sum[] = new int[numClusters];
    int numArticles[] = new int[numClusters];
    double avgs[] = new double[numClusters];
    Enumeration<?> enumeration = articles.elements();

    while (enumeration.hasMoreElements()) {
      NewsArticle article = (NewsArticle) enumeration.nextElement();
      int cluster = article.getClusterId();

      sum[cluster] += article.getKeywordScore();  // sum of counts
      numArticles[cluster]++;       // bump counter
    }

    // now compute the average score for each cluster
    for (int i = 0; i < numClusters; i++) {
      if (numArticles[i] > 0) {
        avgs[i] = (double) sum[i] / (double) numArticles[i];
      } else {
        avgs[i] = 0.0;
      }
      trace(" cluster " + i + " avg = " + avgs[i] + "\n");
    }
    enumeration = articles.elements();
    while (enumeration.hasMoreElements()) {
      NewsArticle article = (NewsArticle) enumeration.nextElement();

      article.setClusterScore(avgs[article.clusterId]);
    }
  }



  /**
   * Count the number of occurrences of the specified keys in the text.
   *
   * @param keys the String[] objects that contain the keys
   * @param text the String object that contains the text
   *
   * @return the int[] that contains the counts
   *
   */
  int[] countWordMultiKeys(String[] keys, String text) {
    StringTokenizer tok = new StringTokenizer(text);
    int counts[] = new int[keys.length];
    Vector table[] = new Vector[50];  // up to length 50
    Hashtable<String, Integer> keyHash = new Hashtable<String, Integer>();

    trace("Searching for keywords ... \n");
    for (int i = 0; i < keys.length; i++) {
      int len = keys[i].length();

      if (table[len] == null) {
        table[len] = new Vector<Object>();
      }
      table[len].addElement(keys[i]);
      keyHash.put(keys[i], new Integer(i));
      counts[i] = 0;
    }
    while (tok.hasMoreTokens()) {
      String token = tok.nextToken();
      int len = token.length();

      if ((len < 50) && (table[len] != null)) {
        Vector<?> searchList = table[len];
        Enumeration<?> enumeration = searchList.elements();

        while (enumeration.hasMoreElements()) {
          String key = (String) enumeration.nextElement();

          if (token.equalsIgnoreCase(key)) {
            Integer index = (Integer) keyHash.get(key);

            counts[index.intValue()]++;  // found another one
            continue;
          }
        }
      }
    }
    for (int i = 0; i < keys.length; i++) {
      trace("key = " + keys[i] + " count = " + counts[i] + "\n");
    }
    return counts;
  }



  /**
   * Writes the infoFilter metadata file (infofilter.dfn) that is read
   * by the neural networks for training. The current keywords are used as
   * field names
   *
   */
  void writeProfileDataDefinition() {
    try {
      FileWriter writer = new FileWriter("infofilter.dfn");
      BufferedWriter out = new BufferedWriter(writer);

      for (int i = 0; i < keywords.length; i++) {
        out.write("continuous ");
        out.write(keywords[i]);
        out.newLine();
      }
      out.write("continuous ClassField");  // user rating value
      out.newLine();
      out.flush();
      out.close();
    } catch (IOException e) {
      trace("Error: FilterAgent couldn't create 'infofilter.dfn' \n");
    }
  }


  /**
   * Appends the given article to the profile.
   *
   * @param currentArt the NewsArticle object to be added
   *
   */
  void addArticleToProfile(NewsArticle currentArt) {
    try {
      FileWriter writer = new FileWriter("infofilter.dat", true);
      BufferedWriter out = new BufferedWriter(writer);

      out.write(currentArt.getProfileString());
      out.newLine();
      out.flush();
      out.close();
    } catch (IOException e) {
      trace("Error: FilterAgent couldn't append article to profile \n");
    }
  }


  /**
   * Appends the given set of articles to the profile.
   *
   * @param articles the Vector object that contains the articles to be
   *        added
   *
   */
  void addAllArticlesToProfile(Vector<?> articles) {
    try {
      FileWriter writer = new FileWriter("infofilter.dat", true);
      BufferedWriter out = new BufferedWriter(writer);
      Enumeration<?> enumeration = articles.elements();

      while (enumeration.hasMoreElements()) {
        NewsArticle art = (NewsArticle) enumeration.nextElement();

        out.write(art.getProfileString());
        out.newLine();
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      trace("Error: FilterAgent couldn't append article to profile \n");
    }
  }


  /**
   * Trains the cluster network.
   *
   */
  void trainClusterNet() {
    DataSet dataSet = new DataSet("ProfileData", "infofilter");
    dataSet.loadDataFile();  // load the data set

    // create a KMap neural network
    // specify the newsfilter.dat as the training data
    // cluster it into 4 clusters
    clusterNet = new KMapNet("InfoFilter Cluster Profile");

    clusterNet.setDataSet(dataSet);
    clusterNet.setNumRecs(dataSet.getNumRecords());
    clusterNet.setFieldsPerRec(dataSet.getFieldsPerRec());
    clusterNet.setData(dataSet.getNormalizedData());  // get vector of data

    // create network, all fields are inputs
    clusterNet.createNetwork(clusterNet.getFieldsPerRec(), 2, 2);
    int maxNumPasses = 20;
    int numRecs = clusterNet.getNumRecs();

    // train the network
    for (int i = 0; i < maxNumPasses; i++) {
      for (int j = 0; j < numRecs; j++) {
        clusterNet.cluster();  // train
      }
      Thread.yield();          // after each pass
    }
    clusterNet.setMode(1);  // lock the network weights
    for (int i = 0; i < clusterNet.getNumRecs(); i++) {
      clusterNet.cluster();  // test
      // clusterNet.display_network() ;
    }
  }


  /**
   * Trains the rating network.
   *
   */
  void trainRatingNet() {
    DataSet dataSet = new DataSet("ProfileData", "infofilter");

    dataSet.loadDataFile();  // load the data set
    ratingNet = new BackProp("InfoFilter Score Model");

    ratingNet.setDataSet(dataSet);
    ratingNet.setNumRecs(dataSet.getNumRecords());
    ratingNet.setFieldsPerRec(dataSet.getNormFieldsPerRec());
    ratingNet.setData(dataSet.getNormalizedData());  // get vector of data
    int numOutputs = dataSet.getClassFieldSize();
    int numInputs = ratingNet.getFieldsPerRec() - numOutputs;

    ratingNet.createNetwork(numInputs, 2 * numInputs, numOutputs);
    int maxNumPasses = 2500;  // default -- could be on applet
    int numRecs = ratingNet.getNumRecs();

    for (int i = 0; i < maxNumPasses; i++) {
      for (int j = 0; j < numRecs; j++) {
        ratingNet.process();  // train
      }
      Thread.yield();        // after each pass
    }
    trace("\n Back Prop Passes Completed: " + maxNumPasses + "  RMS Error = " + ratingNet.getAveRMSError() + " \n");
    ratingNet.setMode(1);  // lock the network

    // do a final pass and display the results
    for (int i = 0; i < ratingNet.getNumRecs(); i++) {
      ratingNet.process();  // test
      //  ratingNet.display_network() ;
    }
  }


   /**
   * Sends a status event to all registered listeners.
   *
   * @param msg the String that is the message portion of the status event
   */
  public void status(String msg) {

    // create a data event
    CIAgentEvent event = new CIAgentEvent(this, "status", msg);

    // and send it to any registered listeners
    notifyCIAgentEventListeners(event);
  }


   /**
   * Reads a serialized FilterAgent from the specified file.
   *
   * @param     fileName the String object that contains the name of the
   *                     file containing the serialized FilterAgent
   *
   * @exception ClassNotFoundException if any class file is not found
   * @exception IOException on any IO exception
   * @return    the de-serialized FilterAgent bean
   */
  public static FilterAgent restoreFromFile(String fileName) throws ClassNotFoundException, IOException  {
    FilterAgent       restoredBean = null;
    FileInputStream   saveFileIn   = new FileInputStream  (fileName);
    ObjectInputStream inStream     = new ObjectInputStream(saveFileIn);

    restoredBean = (FilterAgent)(inStream.readObject());
    System.out.println("Successfully read FilterAgent from " + fileName) ;
    return restoredBean;
  }


   /**
   * Writes a serialized version of this FilterAgent to the specified file.
   *
   * @param fileName the String that contains the name of the file to write
   *
    */
  public void saveToFile(String fileName)   {
   try {
    FileOutputStream   saveFileOut = new FileOutputStream  (fileName);
    ObjectOutputStream outStream   = new ObjectOutputStream(saveFileOut);

    outStream.writeObject(this);
    System.out.println("Successfully saved FilterAgent to " + fileName) ;
   } catch (IOException e) {
      System.out.println("FilterAgent.saveToFile() error: " + e.toString());
   }
  }
}

