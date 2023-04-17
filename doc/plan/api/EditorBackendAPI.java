public interface EditorBackend {

  /**
   * Retrieves all saved games.
   *
   * @return a list of GameDetails (title, image, ID)
   */
  public List<GameDetails> getAllGames();

  /**
   * Creates a new blank game
   *
   * @param name the name of the game
   */
  public void createGame(String name);

  /**
   * Gets all the files the game has and calls something in controller to initialize all that
   *
   * @param name of the game
   **/
  public void loadGame(String name);

  /**
   * Creates a new game from a template
   *
   * @param name             the name of the game
   * @param templateLocation the location of the template
   */
  public void createGameFromTemplate(String name, Path templateLocation);

  /**
   * Saves the current game state
   */
  public void save();

  /**
   * Sets the minimum and maximum number of players in the game.
   */
  public void setMinMaxPlayers(int min, int max);

  /**
   * Creates a new ownable.
   *
   * @param name       the name of the ownable
   * @param parameters the parameters of the ownable
   * @return the Ownable object that was created
   */
  public Ownable addOwnable(String name, Map<String, String> parameters);

  /**
   * Edits an ownable.
   *
   * @param id       the id of the ownable
   * @param modifier the modifier to apply to the ownable
   */
  public void editOwnable(String id, OwnableModifier modifier);

  /**
   * Removes an ownable.
   *
   * @param id the id of the ownable
   */
  public void removeOwnable(String id);

  /**
   * Query for the first matching Ownable (GameObject, Variable) based on ID (#) or class (.)
   * example: GameObject board = (Ownable) querySelector(".board");
   *
   * @return the first Ownable that matches the selector
   */
  public Ownable querySelector(String selector);

  /**
   * Query for all matching Ownables (GameObjects, Variables) based on ID (#) or class (.) example:
   * List<GameObject> pieces = (List<GameObject>) querySelectorAll(".bishop.onWhiteSquare");
   *
   * @return the first Ownable that matches the selector
   */
  public List<Ownable> querySelectorAll(String selector);

  /**
   * Gets a list of all board templates, including name and description (contained in
   * BoardTemplateSpecifier)
   *
   * @return the list of all board templates
   */
  public List<BoardTemplateSpecifier> getBoardTemplates();

  /**
   * Add a board template, selected using BoardTemplateSpecifier
   */
  public void addBoardTemplate(String type, List<AdjustableParameter> specifiers);

}