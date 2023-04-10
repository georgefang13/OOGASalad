package oogasalad.gameeditor.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import oogasalad.gameeditor.backend.goals.EmptyGoal;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.ObjectFactory;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

/**
 * The Game class represents the game itself.
 * It contains Owners such as the GameWorld and Players.
 * It contains the Rules and Goals.
 * @author Michael Bryant
 * @author Max Meister
 */
public class GameEditor {

  /**
   * The Rules of the game.
   */
  private final IdManager<Rule> ruleIdManager = new IdManager<>();

  /**
   * The Goals of the game.
   */
  private final IdManager<Goal> goalIdManager = new IdManager<>();

  /**
   * The Players of the game.
   * Players own Ownables.
   */
  private final IdManager<Player> playerIdManager = new IdManager<>(); //TODO set to make number of players, make arraylist

  /**
   * The IdManager of the game for Ownables.
   */
  private final IdManager<Ownable> ownableIdManager = new IdManager<>();

  /**
   * The GameWorld of the game.
   * The GameWorld owns Ownables not owned by Players.
   */
  private final GameWorld gameWorld = new GameWorld();

  /**
   * The ObjectFactory of the game.
   */
  private final ObjectFactory objectFactory = new ObjectFactory(gameWorld);

  /**
   * Adds a Player to the game.
   * @param player
   */
  public void addPlayer(Player player) {
    playerIdManager.addObject(player);
  }

  /**
   * Adds n Players to the game.
   * @param n the number of Players to add`
   */
  public void addNPlayers(int n) {
    for (int i = 0; i < n; i++) {
      addPlayer(new Player());
    }
  }

  /**
   * Removes a Player from the game, if it exists there.
   * Destroys all Ownables owned by the Player. //TODO throw warning about this
   * @param player
   */
  public void removePlayer(Player player) {
    if(!playerIdManager.isIdInUse(playerIdManager.getId(player))) {
      return;
    }
    //remove all ownables owned by player
    for(Map.Entry<String, Ownable> entry : ownableIdManager) {
      if (entry.getValue().getOwner() == player) {
        ownableIdManager.removeObject(entry.getValue());
      }
    }
    playerIdManager.removeObject(player);
  }

  /**
   * Removes all Players from the game and their Ownables.
   */
  public void removeAllPlayers() {
    playerIdManager.clear();
    ownableIdManager.clear();
    // TODO reconsider
  }

  /**
   * Gets the Players of the game.
   * @return unmodifiable List of Players
   */
  public List<Player> getPlayers() {
    ArrayList<Player> listPlayers= new ArrayList<>();
    for(Map.Entry<String, Player> entry : playerIdManager) {
      listPlayers.add(entry.getValue());
    }
    return Collections.unmodifiableList(listPlayers);
  }


  /**
   * Adds a Rule to the game.
   * @param rule
   */
  public void addRule(Rule rule) {
    ruleIdManager.addObject(rule);
  }

  /**
   * Removes a Rule from the game, if it exists there.
   * @param rule
   */
  public void removeRule(Rule rule) {
    if(!ruleIdManager.isIdInUse(ruleIdManager.getId(rule))) {
      return;
    }
    ruleIdManager.removeObject(rule);
  }

  /**
   * Gets the Rules of the game.
   * @return unmodifiable List of Rules
   */
  public List<Rule> getRuleIdManager() {
    ArrayList<Rule> listRules= new ArrayList<>();
    for(Map.Entry<String, Rule> entry : ruleIdManager) {
      listRules.add(entry.getValue());
    }
    return Collections.unmodifiableList(listRules);
  }

  /**
   * Adds a Goal to the game.
   * @param goal the Goal to add
   */
  public void addGoal(Goal goal) {
    goalIdManager.addObject(goal);
  }

  /**
   * Removes a Goal from the game, if it exists there.
   * @param goal the Goal to remove
   */
  public void removeGoal(Goal goal) {
    if(!goalIdManager.isIdInUse(goalIdManager.getId(goal))) {
      return;
    }
    goalIdManager.removeObject(goal);
  }

  /**
   * Gets the Goals of the game.
   * @return unmodifiable List of Goals
   */
  public List<Goal> getGoalIdManager() {
    ArrayList<Goal> listGoals= new ArrayList<>();
    for(Map.Entry<String, Goal> entry : goalIdManager) {
      listGoals.add(entry.getValue());
    }
    return Collections.unmodifiableList(listGoals);
  }

  /**
   * Gets the GameWorld of the game.
   * @return the GameWorld
   */
  public GameWorld getGameWorld() {
    return gameWorld;
  }


  /**
   * Adds an Ownable to the IdManager and Owner.
   * @param owner the Owner of the Ownable
   * @param ownable the Ownable being added to owner
   */
  public void changeOwner(Owner owner, Ownable ownable) {
    ownable.setOwner(owner);
  }

  //region sendObject API


//  /**
//   * Creates an ownable using ownableFactory for player
//   * Pass in null for any unused parameters (cannot pass null for type)
//   * @param type the string type of ownable
//   * @param owner the owner of the ownable
//   * @param parentOwnable the parent of the ownable
//   */
//  @Deprecated
//  private void createOwnable(String type, Owner owner, Ownable parentOwnable) {
//    Owner destinationOwner = owner;
//    if (owner == null){
//      destinationOwner = gameWorld;
//    }
//    Ownable newOwnable = OwnableFactory.createOwnable(type, destinationOwner);
//    ownableIdManager.addObject(newOwnable, parentOwnable);
//  }

  private void createOwnable(Map<String, String> params) {
    Ownable newOwnable = objectFactory.createOwnable(params);
    ownableIdManager.addObject(newOwnable);
  }

  /**
   * Creates a new player and adds it to the game
   */
  private void createPlayer() {
    addPlayer(new Player());
  }

  /**
   * Creates a new rule and adds it to the game
   */
  private void createRule(Map<String, String> params) {
    Rule newRule = ObjectFactory.createRule(params);
    ruleIdManager.addObject(newRule);
  }

  /**
   * Creates a new goal and adds it to the game
   */
  private void createGoal(Map<String, String> params) {
    Goal newGoal = ObjectFactory.createGoal(params);
    goalIdManager.addObject(newGoal);
  }


  /**
   Method is called in order to send information about a newly constructed   object that was made in the front end sent to the backend. The
   controller sends to the backend for the backend to input these into a
   file
   @Type The class the object belongs to
   @Params The params of the object
   **/
  public void sendObject(ObjectType type, Map<String, String> params) throws IllegalArgumentException{
    switch (type) {
      case PLAYER -> createPlayer();
      case OWNABLE -> createOwnable(params);
      case RULE -> createRule(params);
      case GOAL -> createGoal(params);
      default -> throw new IllegalArgumentException("Invalid type"); //TODO add to properties
    }






//    //TODO this sucks and is sorta hardcoded
//    List<String> parameters = Arrays.asList(params.split(";"));
//    //{"owner=id1", "parentOwnable=id2"}
//    String ownerID = parameters.get(0).substring(parameters.get(0).indexOf("=") + 1);
//    String parentOwnableID = parameters.get(1).substring(parameters.get(1).indexOf("=") + 1);
//    if (ownerID.equals("NULL")){
//      if (parentOwnableID.equals("NULL")){
//        createOwnable(type, null, null);
//      }
//      createOwnable(type, null, getOwnable(parentOwnableID));
//    }
//    else if (parentOwnableID.equals("NULL")) {
//      createOwnable(type, playerIdManager.getObject(ownerID), null);
//    }
//    else {
//      createOwnable(type, playerIdManager.getObject(ownerID), getOwnable(parentOwnableID));
//    }
  }

  //endregion sendObject API


  /**
   * Gets the Owner of an Ownable with id.
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
   * @param id the id of the Ownable
   * @param owner the new owner of the Ownable
   * @throws IllegalArgumentException if owner is null, the Ownable is owned by the GameWorld
   */
  public void setOwner(String id, Owner owner) throws IllegalArgumentException{
    getOwnable(id).setOwner(owner);
  }


  /**
   * Gets an Ownable from the IdManager for a given id.
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

  //TODO TURN
}