package pamanager;

import java.io.*;
import java.net.*;
import ciagent.*;
import rule.*;


/**
 * The <code>AirfareAgent</code> class implements an agent that determines if
 * desirable flights are available based on the departure time, the return time,
 * and the cost of the tickets.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P.Bigus and Jennifer Bigus 2001
 *
 */
public class AirfareAgent extends CIAgent implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 215823483184764435L;
protected String departMonth = "JAN";
  protected String departDay = "1";
  protected String origCity = "RST";
  protected String destCity = "MCO";
  protected String returnMonth = "FEB";
  protected String returnDay = "2";
  protected BooleanRuleBase rb = new BooleanRuleBase("Flight");
  protected RuleVariable departs = new RuleVariable(rb, "departs");
  protected RuleVariable returns = new RuleVariable(rb, "returns");
  protected RuleVariable price = new RuleVariable(rb, "price");
  protected String actionString = "notify" ; // default event action

  /**
   * Creates an <code>AirfareAgent</code> instance.
   */
  public AirfareAgent() {
    name = "Airfare";
  }


  /**
   * Creates an <code>AirfareAgent</code> instance with the given name.
   *
   * @param name the String that contains the name of the agent
   */
  public AirfareAgent(String name) {
    super(name);
  }


  /**
   * Sets the month of the departure date for a flight.
   *
   * @param departMonth the String that contains the three letter abbreviation
   *                    for the month (JAN, FEB, MAR, APR, etc.)
   */
  public void setDepartMonth(String departMonth) {
    this.departMonth = departMonth;
  }


  /**
   * Retrieves the month of the departure date for a flight.
   *
   * @return  a String that contains the three letter abbreviation for the month
   *          (JAN, FEB, MAR, APR, etc.)
   */
  public String getDepartMonth() {
    return departMonth;
  }


  /**
   * Sets the day of the departure date for a flight.
   *
   * @param departDay the String that contains the number of day of the month
   */
  public void setDepartDay(String departDay) {
    this.departDay = departDay;
  }


  /**
   * Retrieves the day of the departure date for a flight.
   *
   * @return  a String that contains the number of day of the month
   */
  public String getDepartDay() {
    return departDay;
  }


  /**
   * Sets the month of the departure date for a flight.
   *
   * @param returnMonth the String that contains the three letter abbreviation
   *                    for the month (JAN, FEB, MAR, APR, etc.)
   */
  public void setReturnMonth(String returnMonth) {
    this.returnMonth = returnMonth;
  }


  /**
   * Retrieves the month of the return date for a flight.
   *
   * @return  a String that contains the three letter abbreviation for the month
   *          (JAN, FEB, MAR, APR, etc.)
   */
  public String getReturnMonth() {
    return returnMonth;
  }


  /**
   * Sets the day of the return date for a flight.
   *
   * @param returnDay the String that contains the number of day of the month
   */
  public void setReturnDay(String returnDay) {
    this.returnDay = returnDay;
  }


  /**
   * Retrieves the day of the return date for a flight.
   *
   * @return  a String that contains the number of day of the month
   */
  public String getReturnDay() {
    return returnDay;
  }


  /**
   * Sets the departure city for a flight.
   *
   * @param origCity the String that contains the three letter airport code for
   *        the departure city (RST, MSP, ORD, etc.)
   */
  public void setOrigCity(String origCity) {
    this.origCity = origCity;
  }


  /**
   * Retrieves the departure city for a flight.
   *
   * @return  a String that contains the three letter airport code for the
   *          departure city (RST, MSP, ORD, etc.)
   */
  public String getOrigCity() {
    return origCity;
  }


  /**
   * Sets the destination city for a flight.
   *
   * @param destCity the String that contains the three letter airport code for
   *        the destination city (RST, MSP, ORD, etc.)
   */
  public void setDestCity(String destCity) {
    this.destCity = destCity;
  }


  /**
   * Retrieves the destination city for a flight.
   *
   * @return  a String that contains the three letter airport code for
   *          the destination city (RST, MSP, ORD, etc.)
   */
  public String getDestCity() {
    return destCity;
  }

   /**
   * Sets the action string to be sent by this agent in an EVENT.
   *
   * @param actionString the integer that represents the action
   */
  public void setActionString(String actionString) {
    this.actionString = actionString;
  }


  /**
   * Retrieves the actionString this agent sends in events.
   *
   * @return the String that is sent in the action field of an event.
   */
  public String getActionString() {
    return actionString;
  }


  /**
   * Retrieves a formatted string for display of this agent's current task.
   *
   * @return the String that represents the current task
   */
  public String getTaskDescription() {
    return "Checking flights from " + origCity  + " to " + destCity;
  }


  /**
   * Initializes the agent by initialing the rule base.
   */
  public void initialize() {
    initFlightRuleBase();
    setSleepTime(30000);  // set the time to 30 seconds
    setState(CIAgentState.INITIATED);
  }


  /**
   * Provides the default behavior of this agent which includes going out to
   * a web page to get airfare information, parsing that page, and determining
   * whether the flights and airfares are within certain limits set in the rule
   * base.
   */
  public void process() {
    URL url;
    String parms = "?" + "&dmon=" + departMonth + "&dday=" + departDay + "&orig=" + origCity + "&dest=" + destCity + "&rmon=" + returnMonth + "&rday=" + returnDay;
    HttpURLConnection connection;
    String rank = null;

    try {

      // Connect to the URL that generates the airfare information
      url = new URL("http://www.bigusbooks.com/cgi-local/airfare.pl");
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);

      // Send the cities and dates to the web page
      PrintWriter out = new PrintWriter(connection.getOutputStream());

      out.println(parms);
      out.close();

      // Get the results from the airfare query
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      int i, j = 0, k = 0;
      int x, y;

      i = 0;
      String prices[] = new String[3];
      String flight[][] = new String[3][2];
      String times[][] = new String[3][2];

      while ((inputLine = in.readLine()) != null) {

        // Parse the results page, saving the price and flight information
        x = inputLine.indexOf("Price:");
        if (x != -1) {
          y = inputLine.indexOf('.', x);
          prices[i] = inputLine.substring(x + 8, y + 3);
          i++;
          j = k = 0;
        }    // endif
        x = inputLine.indexOf("Flight");
        if (x != -1) {
          flight[i - 1][j] = inputLine.substring(x);
          j++;
        }    // endif
        x = inputLine.indexOf("Departure Time:");
        if (x != -1) {
          y = inputLine.lastIndexOf(':');
          String hours = inputLine.substring(x + 16, y);

          if (inputLine.indexOf("PM") != -1) {
            int hrs = Integer.parseInt(hours) % 12 + 12;

            hours = Integer.toString(hrs);
          }  // endif
          times[i - 1][k] = hours + inputLine.substring(y + 1, y + 3);
          k++;
        }    // endif
      }      // endwhile
      in.close();

      // For each flight, run it through the rule base to see if it
      // meets the criteria
      for (i = 0; i < 3; ++i) {
        rb.reset();
        trace("Checking departure at " + times[i][0] + " return at " + times[i][1] + " for a price of " + prices[i]);
        departs.setValue(times[i][0]);
        returns.setValue(times[i][1]);
        price.setValue(prices[i]);
        rb.forwardChain();
        rank = rb.getVariable("flightRank").getValue();
        if (rank != null) {
          String msg = "An iterary was found that meets your \"" + rank
                       + "\" flight criteria.\nDeparture from " + origCity
                       + ": " + flight[i][0] + " departs at "
                       + times[i][0].substring(0,2) + ":"
                       + times[i][0].substring(2)
                       + "\nReturn from " + destCity + ": "
                       + flight[i][1] + " departs at "
                       + times[i][1].substring(0,2) + ":"
                       + times[i][1].substring(2)
                       + "\nPrice: $" + prices[i];
          // signal interested observers
          notifyCIAgentEventListeners(new CIAgentEvent(this, actionString, msg));

        }    // endif
      }      // endfor
    } catch (Exception e) {
      String msg = "Problems retrieving information from the web site.\n"
                 + "Please make sure that you are connected to the Internet\n"
                 + "and that the web site is up. To do this, use your browser\n"
                 + "to go to www.bigusbooks.com/aifareQuery.html and enter the\n"
                 + "same dates and cities. If it doesn't work, the server must\n"
                 + "must be having problems and you can try again later.";
      // signal interested observers
      notifyCIAgentEventListeners(new CIAgentEvent(this, actionString, msg));
      e.printStackTrace();
    }
  }


  /**
   * Processes an event by invoking the default behavior of this agent.
   *
   * @param e the CIAgentEvent object
   */
  public void processCIAgentEvent(CIAgentEvent e) {
    if (e.getAction().equalsIgnoreCase("process")) {
      process();
    }
  }


  /**
   * Processes a timer pop which does nothing in this case.
   */
  public void processTimerPop() {}


  /**
   * Initializes the Flight rule base that is used by the agent when
   * evaluating the flight information.
   */
  public void initFlightRuleBase() {

    // define variables
    RuleVariable departureTime = new RuleVariable(rb, "departureTime");

    departureTime.setLabels("morning afternoon evening");
    RuleVariable desiredDeparture = new RuleVariable(rb, "desiredDeparture");

    desiredDeparture.setLabels("yes no");
    RuleVariable returnTime = new RuleVariable(rb, "returnTime");

    returnTime.setLabels("morning afternoon evening");
    RuleVariable desiredReturn = new RuleVariable(rb, "desiredReturn");

    desiredReturn.setLabels("yes no");
    RuleVariable flightRank = new RuleVariable(rb, "flightRank");

    flightRank.setLabels("good better best");

    // define conditions
    Condition cEquals = new Condition("=");
    new Condition("!=");
    Condition cGreaterThan = new Condition(">");
    Condition cLessThan = new Condition("<");

    // define rules
    new Rule(rb, "morningDeparture", new Clause(departs, cLessThan, "1200"), new Clause(departureTime, cEquals, "morning"));
    new Rule(rb, "afternoonDeparture", new Clause[]{ new Clause(departs, cGreaterThan, "1159"),
                                                                               new Clause(departs, cLessThan, "1700") }, new Clause(departureTime, cEquals, "afternoon"));
    new Rule(rb, "eveningDeparture", new Clause(departs, cGreaterThan, "1659"), new Clause(departureTime, cEquals, "evening"));
    new Rule(rb, "morningReturn", new Clause(returns, cLessThan, "1200"), new Clause(returnTime, cEquals, "morning"));
    new Rule(rb, "afternoonReturn", new Clause[]{ new Clause(returns, cGreaterThan, "1159"),
                                                                         new Clause(returns, cLessThan, "1700") }, new Clause(returnTime, cEquals, "afternoon"));
    new Rule(rb, "eveningReturn", new Clause(returns, cGreaterThan, "1659"), new Clause(returnTime, cEquals, "evening"));
    new Rule(rb, "desirableDeparture", new Clause(departureTime, cEquals, "evening"), new Clause(desiredDeparture, cEquals, "yes"));
    new Rule(rb, "undesirableDeparture1", new Clause(departureTime, cEquals, "morning"), new Clause(desiredDeparture, cEquals, "no"));
    new Rule(rb, "undesirableDeparture2", new Clause(departureTime, cEquals, "afternoon"), new Clause(desiredDeparture, cEquals, "no"));
    new Rule(rb, "undesirableReturn", new Clause(returnTime, cEquals, "evening"), new Clause(desiredReturn, cEquals, "no"));
    new Rule(rb, "desirableReturn1", new Clause(returnTime, cEquals, "morning"), new Clause(desiredReturn, cEquals, "yes"));
    new Rule(rb, "desirableReturn2", new Clause(returnTime, cEquals, "afternoon"), new Clause(desiredReturn, cEquals, "yes"));
    new Rule(rb, "bestFlight", new Clause[]{ new Clause(desiredDeparture, cEquals, "yes"),
                                                               new Clause(desiredReturn, cEquals, "yes"),
                                                               new Clause(price, cLessThan, "1000.00") }, new Clause(flightRank, cEquals, "best"));
    new Rule(rb, "betterFlight1", new Clause[]{ new Clause(desiredDeparture, cEquals, "yes"),
                                                                     new Clause(desiredReturn, cEquals, "no"),
                                                                     new Clause(price, cLessThan, "800.00") }, new Clause(flightRank, cEquals, "better"));
    new Rule(rb, "betterFlight2", new Clause[]{ new Clause(desiredDeparture, cEquals, "no"),
                                                                     new Clause(desiredReturn, cEquals, "yes"),
                                                                     new Clause(price, cLessThan, "800.00") }, new Clause(flightRank, cEquals, "better"));
    new Rule(rb, "goodFlight", new Clause[]{ new Clause(desiredDeparture, cEquals, "no"),
                                                               new Clause(desiredReturn, cEquals, "no"),
                                                               new Clause(price, cLessThan, "600.00") }, new Clause(flightRank, cEquals, "good"));
  }

}
