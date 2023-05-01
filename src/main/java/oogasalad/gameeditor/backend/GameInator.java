package oogasalad.gameeditor.backend;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import oogasalad.gamerunner.backend.Rule;
import oogasalad.gamerunner.backend.interpretables.Goal;
//import oogasalad.sharedDependencies.backend.GameLoader;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ObjectFactory;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;

/**
 * The GameInator class represents the game itself. It contains Owners such as the GameWorld and Players.
 * It contains the Rules and Goals.
 *
 * @author Michael Bryant
 * @author Max Meister
 */
public class GameInator {

  /**
   * The Players of the game. Players own Ownables. Ids of Players are their index in the list. Ex:
   * Player1, Player2, etc. These Ids are constant
   */
  private final ArrayList<Player> players = new ArrayList<>();

  /**
   * The Goals of the game.
   */
  private final ArrayList<Goal> goals = new ArrayList<>();

  /**
   * The IdManager of the game for Ownables.
   */
  private IdManager<Ownable> ownableIdManager = new IdManager<>();

  /**
   * The rule manager of the game for rules and goals.
   */
  private RuleManager ruleManager = new RuleManager();

  /**
   * The GameWorld of the game. The GameWorld owns Ownables not owned by Players.
   */
  private GameWorld gameWorld = new GameWorld();

  /**
   * The ObjectFactory of the game.
   */
  private ObjectFactory objectFactory;

  /**
   * Creates a new Game with no information.
   */
  public GameInator() {
    objectFactory = new ObjectFactory(gameWorld, ownableIdManager, players);
  }

  /**
   * Creates a new Game with the given file name.
   * Removes all current information and overwrites it with the information from the file.
   * @param directory the directory of the files to load
   */
  public GameInator(String directory) {
    //Use gameLoader to load the game from a file
//    GameLoader loader = new GameLoader(directory);
//    players.addAll(loader.getPlayers());
//    ownableIdManager = loader.getOwnableIdManager();
//    gameWorld = loader.getGameWorld();
//    objectFactory = new ObjectFactory(gameWorld, ownableIdManager, players);
  }


  //endregion

  // region PLAYERS

  /**
   * Adds a Player to the game.
   *
   * @param player the Player to add
   */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Adds a Goal to the game.
   *
   * @param goal the goal to add
   */
  public void addGoal(Goal goal) {
    goals.add(goal);
  }


  /**
   * Gets the Players of the game.
   *
   * @return unmodifiable List of Players
   */
  public List<Player> getPlayers() {
    return Collections.unmodifiableList(players);
  }


  /**
   * Gets the GameWorld of the game.
   *
   * @return the GameWorld
   */
  public GameWorld getGameWorld() {
    return gameWorld;
  }


  //region sendObject API

  /**
   * Creates an ownable using objectFactory and adds it to the game through the IdManager (IdManager
   * will configure the owner, id, etc. all within the factory)
   *
   * @param params the parameters of the ownable
   */
  private void createOwnable(Map<ObjectParameter, Object> params) throws IllegalArgumentException{
    objectFactory.createOwnable(params);
  }

  /**
   * Creates a new player and adds it to the game
   */
  private void createPlayer() {
    addPlayer(new Player());
  }

  /**
   * Creates a rule and adds it to the game through the rule manager
   *
   * @param params the parameters of the rule
   */
  private void createRule(Map<ObjectParameter, Object> params) throws IllegalArgumentException{
    String rule = params.get(ObjectParameter.RULE_STR) != null ? params.get(ObjectParameter.RULE_STR).toString() : null;
    String ruleName = params.get(ObjectParameter.RULE_NAME) != null ? params.get(ObjectParameter.RULE_NAME).toString() : null;
    String ruleCls = params.get(ObjectParameter.RULE_CLS) != null ? params.get(ObjectParameter.RULE_CLS).toString() : null;
    ruleManager.addRule(ruleCls, ruleName, rule);
  }

  /**
   * Creates a goal and adds it to the game
   */
  private void createGoal() {
    addGoal(new Goal());
  }


  /**
   * Method is called in order to send information about a newly constructed   object that was made
   * in the front end sent to the backend. The controller sends to the backend for the backend to
   * input these into a file Note: currently it only constructs Ownables with default values. Ex. a
   * variable has no initialized value In the future this would be added Currently, this
   * responsibility is handled by the updateObjectProperties API call
   *
   * @param type The class the object belongs to
   * @param params The params of the object
   **/
  public void sendObject(ObjectType type, Map<ObjectParameter, Object> params)
      throws IllegalArgumentException {
    switch (type) {
      case PLAYER -> createPlayer();
      case OWNABLE -> createOwnable(params);
      case RULE -> createRule(params);
      case GOAL -> createGoal();
      default -> throw new IllegalArgumentException("Invalid type"); //TODO add to properties
    }
  }

  //endregion sendObject API

  //region deleteObject API

  /**
   * Removes an Ownable from the game, if it exists there.
   *
   * @param params the parameters of the ownable
   */
  public void removeOwnable(Map<ObjectParameter, Object> params) throws IllegalArgumentException {
    String id = params.get(ObjectParameter.ID) != null ? params.get(ObjectParameter.ID).toString() : null;
    if (!ownableIdManager.isIdInUse(id)) {
      return;
    }
    ownableIdManager.removeObject(id);
  }

  /**
   * Removes a Player from the game, if there is one.
   * Also removes all ownables owned by the player
   */
  public void removePlayer() {
    //remove all ownables owned by the player
    ownableIdManager.removeObjectsOwnedByOwner(players.get(players.size() - 1));
    if (players.size() > 0) {
      //remove the last player
      players.remove(players.size() - 1);
    }
  }

  /**
   * Removes a rule from the game.
   *
   * @param params of the rule
   */
  public void removeRule(Map<ObjectParameter, Object> params) throws IllegalArgumentException{
    String rule = params.get(ObjectParameter.RULE_STR) != null ? params.get(ObjectParameter.RULE_STR).toString() : null;
    String ruleCls = params.get(ObjectParameter.RULE_CLS) != null ? params.get(ObjectParameter.RULE_CLS).toString() : null;
    ruleManager.removeRule(ruleCls, rule);
  }

  /**
   * Removes a Goal from the game, if there is one.
   */
  public void removeGoal() {
    if (goals.size() > 0) {
      //remove the last player
      goals.remove(goals.size() - 1);
    }
  }

  /**
   * Method is called in order to send a request to the backend to delete an object.
   * If removing a player, the id is not used and can be null
   *
   * @Type The class the object belongs to
   * @Params The params of the object
   **/
  public void deleteObject(ObjectType type, Map<ObjectParameter, Object> params)
      throws IllegalArgumentException {
    switch (type) {
      case PLAYER -> removePlayer();
      case OWNABLE -> removeOwnable(params);
      case RULE -> removeRule(params);
      case GOAL -> removeGoal();
      default -> throw new IllegalArgumentException("Invalid type"); //TODO add to properties
    }
  }

  //endregion deleteObject API

  //region updateObjectProperties API

  /**
   * check if owner id is numeric value
   * @param str
   * @return
   */
  private Integer isNumeric(String str) {
    if (str == null) {
      return null;
    }
    try {
      return  Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Update an Ownable in the game.
   *
   * @param params the parameters of the ownable
   */
  public void updateOwnable(String targetObject, Map<ObjectParameter, Object> params) throws IllegalArgumentException{
    //change to new owner
    String newOwnerNum = params.get(ObjectParameter.OWNER) != null ? params.get(ObjectParameter.OWNER).toString() : null;
    Integer newOwnerInt = isNumeric(newOwnerNum);
    if (newOwnerInt != null && newOwnerInt >= 0 && newOwnerInt <= players.size()){
      Owner newOwner = players.get(newOwnerInt-1);
      Ownable targetOwnable = ownableIdManager.getObject(targetObject);
      targetOwnable.setOwner(newOwner);
    }
    else if(newOwnerNum != null && newOwnerNum.equals("GameWorld")){ //if null it wasnt in there and we dont want to change it
      //set to gameworld
      Owner newOwner = gameWorld;
      Ownable targetOwnable = ownableIdManager.getObject(targetObject);
      targetOwnable.setOwner(newOwner);
    }

    //change parent ownable
    String newParentOwnableId = params.get(ObjectParameter.PARENT_OWNABLE_ID) != null ? params.get(ObjectParameter.PARENT_OWNABLE_ID).toString() : null;
    if (newParentOwnableId != null){
      ownableIdManager.changeParentId(targetObject, newParentOwnableId);
    }

    //change a variable value
    Map<Object, Object> constructorParams = (Map<Object, Object>) params.get(ObjectParameter.CONSTRUCTOR_ARGS);
    Object newValue = constructorParams.get(ObjectParameter.VALUE);
    Ownable targetOwnable = ownableIdManager.getObject(targetObject);
    if (targetOwnable instanceof Variable){
      ((Variable) targetOwnable).set(newValue);
    }
    System.out.println(targetObject);
    // change id of ownable
    String newId = params.get(ObjectParameter.ID) != null ? params.get(ObjectParameter.ID).toString() : null;
    if (newId != null){
      System.out.println(newId);
      ownableIdManager.changeId(targetObject, newId);
    }
  }

  /**
   * Method is called to update information about a modified object in teh front end. The controller
   * sends updates to the Backend by giving the type and params for identification
   *
   * @type The class the object belongs to
   * @Params The updated params of the object
   **/
  public void updateObjectProperties(String targetObject, ObjectType type, Map<ObjectParameter, Object> params)
      throws IllegalArgumentException {
    switch (type) {
      case PLAYER -> {
      }
      case OWNABLE -> updateOwnable(targetObject, params);
      default -> throw new IllegalArgumentException("Invalid type"); //TODO add to properties
    }
  }

  //endregion updateObjectProperties API

  /**
   * Gets an Ownable from the IdManager for a given id.
   *
   * @param id the id of the Ownable
   * @return the Ownable
   * @throws IllegalArgumentException if the id is not in use
   */
  public Ownable getOwnable(String id) throws IllegalArgumentException {
    if (!ownableIdManager.isIdInUse(id)) {
      return null;
    }
    return ownableIdManager.getObject(id);
  }


  /**
   * Gets the IdManager for the Ownables.
   * @return the IdManager for the Ownables
   */
  public IdManager getOwnableIdManager() {
    return ownableIdManager;
  }

}