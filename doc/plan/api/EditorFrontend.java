public interface EditorFrontend {

  /**
   * Creates a view with a parameter of javafx content that can be displayed
   *
   * @param Node that contains the javafx components to be displayed
   * @return a View
   **/
  public View createView(Node content);

  /**
   * Get a list of all the players that the user has created
   *
   * @return list of player objects
   **/
  public List<Players> getPlayers();

  /**
   * get a specific player using their ID
   *
   * @return a player object based on their ID
   **/
  public Player getPlayer(String playerID);

  /**
   * gets all the rules the user has created
   *
   * @return List of rules
   **/
  public List<Rules> getRules();

  /**
   * Uses the ruleID to get a specifc rule that the user created
   *
   * @return a specific Rule
   **/
  public Rule getRule(String ruleID);

  /**
   * Gets a list of all of the game objects the user has created
   *
   * @return List of all GameObjects
   **/
  public List<GameObject> getGameObjects();


  /**
   * gets a specifc gameObject by its id
   *
   * @return a GameObject based on its ID
   **/
  public GameObject getGameObject(String gameObjectID);

}


  /**
   * Gets the most =clicked/selected component the user interacted with, if last thing clicked
   * wasn't a component, return null
   *
   * @return the active component
   **/
  public Component getActiveComponent();
  

  

