package ciagent;

import java.util.*;

/**
 * The <code>AgentPlatform</code> interface defines the behavior of an
 * agent platform.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents with Java
 * (C) Joseph P.Bigus and Jennifer Bigus 2001
 *
 */
public interface AgentPlatform {


  /**
   * Returns a list of registered agents.
   *
   * @return the Vector object that contains the agents
   */
  public Vector<?> getAgents();

   /**
   * Returns an agent with the specified name.
   *
   * @return the CIAgent object or null if not found
   */
  public CIAgent getAgent(String name);
}
