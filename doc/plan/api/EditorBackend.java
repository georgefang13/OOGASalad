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
   * Creates a new game from a template
   *
   * @param name             the name of the game
   * @param templateLocation the location of the template
   */
  public void createGameFromTemplate(String name, Path templateLocation);

  /**
   * Creates a new player.
   *
   * @return the name of the new player
   */
  public String addPlayer();

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


}