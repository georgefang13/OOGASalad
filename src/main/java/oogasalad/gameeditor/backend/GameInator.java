package oogasalad.gameeditor.backend;

import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import oogasalad.Controller.GameRunnerController;
import oogasalad.gamerunner.backend.fsm.ProgrammableState;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.ObjectFactory;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;

/**
 * The Game class represents the game itself. It contains Owners such as the GameWorld and Players.
 * It contains the Rules and Goals.
 *
 * @author Michael Bryant
 * @author Max Meister
 */
public class GameInator {

  /**
   * The Rules of the game.
   */
  private final IdManager<Rule> rules = new IdManager<>();

  /**
   * The Goals of the game.
   */
  private final IdManager<Goal> goals = new IdManager<>();

  /**
   * The Players of the game. Players own Ownables. Ids of Players are their index in the list. Ex:
   * Player1, Player2, etc. These Ids are constant
   */
  private final ArrayList<Player> players = new ArrayList<>(); //TODO set to make number of players, make arraylist

  /**
   * The IdManager of the game for Ownables.
   */
  private final IdManager<Ownable> ownableIdManager = new IdManager<>();

  /**
   * The GameWorld of the game. The GameWorld owns Ownables not owned by Players.
   */
  private final GameWorld gameWorld = new GameWorld();


  /////////////////// PLAY THE GAME ///////////////////

  // region LOADING

  private void initPlayers(JsonObject json) {

  }

  private void initOwnables(JsonObject json) {
  }

  private void initRulesAndGoals(JsonObject json) {
  }

  //endregion

  // region PLAYERS

  /**
   * The ObjectFactory of the game.
   */
  private final ObjectFactory objectFactory = new ObjectFactory(gameWorld, ownableIdManager,
      players);

  /**
   * Adds a Player to the game.
   *
   * @param player the Player to add
   */
  public void addPlayer(Player player) {
    players.add(player);
  }


  /**
   * Removes all Players from the game and their Ownables.
   */
  public void removeAllPlayers() {
    players.clear();
    ownableIdManager.clear();
    // TODO reconsider
  }

  /**
   * Gets the Players of the game.
   *
   * @return unmodifiable List of Players
   */
  public List<Player> getPlayers() {
    return players;
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
   * Creates an ownable using ownableFactory for player Pass in null for any unused parameters
   * (cannot pass null for type)
   *
   * @param params the parameters of the ownable
   */
  private void createRule(Map<ObjectParameter, Object> params) {
    Rule newRule = ObjectFactory.createRule(params);
    rules.addObject(newRule);
  }

  /**
   * Creates a new goal and adds it to the game
   */
  private void createGoal(Map<ObjectParameter, Object> params) {
    Goal newGoal = ObjectFactory.createGoal(params);
    goals.addObject(newGoal);
  }


  //NOTE: here is an example params map for creating a new Variable with Owner Player 1,
  // default id "test", and default value 5784

  //Map<ObjectParameter, Object> params = new HashMap<>();
  //params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
  //Map<ObjectParameter, Object> constructor_params = new HashMap<>();
  //constructorParams.put(ObjectParameter.ID, "test"); -> used in IDManager
  //constructorParams.put(ObjectParameter.VALUE, 5784); -> used in constructor
  //constructorParams.put(ObjectParameter.OWNER, "1"); -> used in constructor
  //params.put(ObjectParameter.CONSTRUCTOR_PARAMS, constructorParams);

//  sendObject(OWNABLE, params)

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
      case GOAL -> createGoal(params);
      default -> throw new IllegalArgumentException("Invalid type"); //TODO add to properties
    }
  }

  //endregion sendObject API

  //region deleteObject API

  /**
   * Removes an Ownable from the game, if it exists there.
   *
   * @param id the parameters of the ownable
   */
  public void removeOwnable(String id) {
    if (!ownableIdManager.isIdInUse(id)) {
      return;
    }
    ownableIdManager.removeObject(id);
  }

  /**
   * Removes a Player from the game, if there is one. params the parameters of the player
   */
  public void removePlayer() {
    if (players.size() > 0) {
      //remove the last player
      players.remove(players.size() - 1);
    }
  }


  /**
   * Method is called in order to send a request to the backend to delete an object.
   * If removing a player, the id is not used and can be null
   *
   * @Type The class the object belongs to
   * @Params The params of the object
   **/
  public void deleteObject(ObjectType type, String id)
      throws IllegalArgumentException {
    switch (type) {
      case PLAYER -> removePlayer();
      case OWNABLE -> removeOwnable(id);
//      case RULE -> removeRule(id);
//      case GOAL -> removeGoal(id);
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
  public Integer isNumeric(String str) {
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
    else if(newOwnerNum.equals("GameWorld")){ //if null it wasnt in there and we dont want to change it
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

    // change id of ownable
    String newId = params.get(ObjectParameter.ID) != null ? params.get(ObjectParameter.ID).toString() : null;
    if (newId != null){
      ownableIdManager.changeId(targetObject, newId);
    }
  }

//  /**
//   * Update a goal in the game.
//   *
//   * @param params the parameters of the goal
//   */
//  public void updateGoal(Map<ObjectParameter, String> params) {
////    String ownableType = params.get(ObjectParameter.OWNABLE_TYPE);
////    String parentOwnerName = params.get(ObjectParameter.OWNER);
////    String id = params.get(ObjectParameter.ID);
////    String parentOwnableName = params.get(ObjectParameter.PARENT_OWNABLE);
////    String val = params.get(ObjectParameter.VALUE);
//    //TODO make this updateable for all things
//  }
//
//  /**
//   * Update a rule in the game.
//   *
//   * @param params the parameters of the rule
//   */
//  public void updateRule(Map<ObjectParameter, String> params) {
////    String ownableType = params.get(ObjectParameter.OWNABLE_TYPE);
////    String parentOwnerName = params.get(ObjectParameter.OWNER);
////    String id = params.get(ObjectParameter.ID);
////    String parentOwnableName = params.get(ObjectParameter.PARENT_OWNABLE);
////    String val = params.get(ObjectParameter.VALUE);
//    //TODO make this updateable for all things
//  }

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
//      case RULE -> updateRule(params);
//      case GOAL -> updateGoal(params);
      default -> throw new IllegalArgumentException("Invalid type"); //TODO add to properties
    }
  }

  //endregion updateObjectProperties API

  /**
   * Gets the Owner of an Ownable with id.
   *
   * @param id the id of the Ownable
   * @return the Owner of the Ownable, null if the id is not in use
   */
  public Owner getOwner(String id) {
    if (!ownableIdManager.isIdInUse(id)) {
      return null;
    }
    return getOwnable(id).getOwner();
  }

  /**
   * Sets the Owner of an Ownable with id.
   *
   * @param id    the id of the Ownable
   * @param owner the new owner of the Ownable
   * @throws IllegalArgumentException if owner is null, the Ownable is owned by the GameWorld
   */
  public void setOwner(String id, Owner owner) throws IllegalArgumentException {
    getOwnable(id).setOwner(owner);
  }


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
   *
   * @return the IdManager for the Ownables
   */
  public IdManager getOwnableIdManager() {
    return ownableIdManager;
  }

}