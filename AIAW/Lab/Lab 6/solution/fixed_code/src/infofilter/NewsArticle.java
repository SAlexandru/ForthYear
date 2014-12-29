package infofilter;

import java.io.*;


/**
 * The <code>NewsArticle</code> class defines all the information about a
 * single news article or web page source.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class NewsArticle {
  protected String id;                          // numeric article ID -- valid only for this server
  protected String subject;                     // subject line from header
  protected String body;                        // body or text of news posting
  protected int counts[];                       // raw counts of keys in body
  protected int keywordScore;                   // sum of raw keyword counts
  protected double predictedRating = 0.5;       // predicted rating from back prop network
  protected String userRating = FilterAgent.NEUTRAL_RATING;      // user rating
  protected double rating = 0.5 ;               // numeric user rating value
  protected double score;                       // keywordScore, clusterAvg, or feedbackmatch score or ranking
  protected int clusterId;                      // cluster this article falls into
  protected double clusterScore;                // average score of articles in same cluster

  /**
   * Constructs a news article object with specified ID.
   *
   * @param id the String object that contains the identifier of the news
   *           article
   */
  NewsArticle(String id) {
    this.id = id;
  }


  /**
   * Reads a news article from the given file.
   *
   * @param fileName the String object that contains the name of the file
   *
   */
  void readArticle(String fileName) {
    File f = new File(fileName);
    int size = (int) f.length();
    try {
      FileInputStream in = new FileInputStream(f);
      byte[] data = new byte[size];

      in.read(data, 0, size);
      subject = "Subject: " + fileName;
      body = new String(data);
      id = fileName;
      in.close();
    } catch (IOException e) {
      System.out.println("Error: couldn't read news article from " + fileName + "\n");
    }
  }


  /**
   * Writes a news article to the given file.
   *
   * @param fileName the String object that contains the name of the file
   *
   */
  void writeArticle(String fileName) {
    File f = new File(fileName);
    String dataOut = subject + " " + body;
    int size = (int) dataOut.length();
    byte data[] = new byte[size];

    data = dataOut.getBytes();
    try {
      FileOutputStream out = new FileOutputStream(f);

      out.write(data, 0, size);
      out.flush();
      out.close();
    } catch (IOException e) {
      System.out.println("Error: couldn't write news article to " + fileName + "\n");
    }
  }


  /**
   * Retrieves the subject of this news article.
   *
   * @return the String object that contains the subject of the article
   *
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Sets the subject of this news article to the given string.
   *
   * @param subject the String object that contains the new subject
   *
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }


  /**
   * Retrieves the body of this news article.
   *
   * @return the String object that contains the body of the article
   *
   */
  public String getBody() {
    return body;
  }


  /**
   * Sets the body of this news article to the given string.
   *
   * @param body the String object that contains the new body
   *
   */
  public void setBody(String body) {
    this.body = body;
  }


  /**
   * Retrieves the keyword score for this article.
   *
   * @return the double value for the keyword score
   *
   */
  public int getKeywordScore() {
    return keywordScore;
  }

  /**
   * Sets the keyword score for this article.
   *
   * @return the double value for the keyword score
   *
   */
  public void setKeywordScore(int keywordScore) {
    this.keywordScore = keywordScore;
  }

  /**
  * Retrieves the current score for the given filter type.
  *
  * @param filterType the integer that represents the filter type
  *
  * @return the double value for the score
  *
  */
  public double getScore(int filterType) {

     switch (filterType) {
       case FilterAgent.USE_KEYWORDS:
         return keywordScore ;
       case FilterAgent.USE_CLUSTERS:
         return clusterScore ;
       case FilterAgent.USE_PREDICTED_RATING:
         return predictedRating ;
      }

      return 0.0 ;

  }

  /**
   * Retrieves the user rating for this article.
   *
   * @return the String object that contains the user rating
   *
   */
  public String getUserRating() {
    return userRating;
  }

   /**
   * Retrieves the rating for this article.
   *
   * @return the double value for the rating
   *
   */
  public double getRating() {
    return rating;
  }

   /**
   * Sets the cluster score for this article.
   *
   * @param clusterScore the double value for the score
   *
   */
  public void setClusterScore(double clusterScore) {
    this.clusterScore = clusterScore;
  }

   /**
   * Retrieves the cluster score for this article.
   *
   * @return the double value for the score
   *
   */
  public double getClusterScore() {
    return clusterScore;
  }

   /**
   * Sets the cluster id for this article.
   *
   * @param clusterId the integer value for the cluster id
   *
   */
  public void setClusterId(int clusterId) {
    this.clusterId = clusterId;
  }

   /**
   * Retrieves the cluster id.
   *
   * @return the integer value for the cluster id
   */
  public int getClusterId() {
    return clusterId;
  }

   /**
   * Retrieves the predicted rating for this article.
   *
   * @return the double value that is the predicted rating
   *
   */
  public double getPredictedRating() {
    return predictedRating;
  }

   /**
   * Sets the predicted rating for this article.
   *
   * @param the double value that is the predicted rating
   *
   */
  public void setPredictedRating(double predictedRating) {
    this.predictedRating = predictedRating;
  }


  /**
   * Sets the user rating and the associated rating for this article.
   *
   * @param userRating the String object that contains the user rating
   *
   */
  public void setUserRating(String userRating) {
    this.userRating = userRating;
    if (userRating.equals(FilterAgent.USELESS_RATING)) rating = 0.0 ;
    else if (userRating.equals(FilterAgent.NOTVERY_RATING)) rating = 0.25 ;
    else if (userRating.equals(FilterAgent.NEUTRAL_RATING)) rating = 0.50 ;
    else if (userRating.equals(FilterAgent.MILDLY_RATING)) rating = 0.75 ;
    else if (userRating.equals(FilterAgent.INTERESTING_RATING)) rating = 1.0 ;
  }


  /**
   * Retrieves the profile data, including the raw keyword counts and the
   * numeric user rating, as a string.
   *
   * @return the String object that contains the profile data
   *
   */
  String getProfileString() {
    StringBuffer outString = new StringBuffer("");

    for (int i = 0; i < counts.length; i++) {
      outString.append(counts[i]);
      outString.append(" ");
    }
    outString.append(rating);  // numeric user rating value
    return outString.toString();
  }
}
