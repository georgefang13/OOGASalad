package oogasalad.gameeditor.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.OwnableFactory;
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
  private final IdManager<Rule> rules = new IdManager<>();

  /**
   * The Goals of the game.
   */
  private final IdManager<Goal> goals = new IdManager<>();

  /**
   * The Players of the game.
   * Players own Ownables.
   */
  private final IdManager<Player> playerIdManager = new IdManager();

  /**
   * The IdManager of the game for Ownables.
   */
  private final IdManager<Ownable> ownableIdManager = new IdManager();

  /**
   * The OwnableFactory of the game.
   */
  OwnableFactory ownableFactory = new OwnableFactory();

  /**
   * The GameWorld of the game.
   * The GameWorld owns Ownables not owned by Players.
   */
  private final GameWorld gameWorld = new GameWorld();

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
    rules.addObject(rule);
  }

  /**
   * Removes a Rule from the game, if it exists there.
   * @param rule
   */
  public void removeRule(Rule rule) {
    if(!rules.isIdInUse(rules.getId(rule))) {
      return;
    }
    rules.removeObject(rule);
  }

  /**
   * Gets the Rules of the game.
   * @return unmodifiable List of Rules
   */
  public List<Rule> getRules() {
    ArrayList<Rule> listRules= new ArrayList<>();
    for(Map.Entry<String, Rule> entry : rules) {
      listRules.add(entry.getValue());
    }
    return Collections.unmodifiableList(listRules);
  }

  /**
   * Adds a Goal to the game.
   * @param goal the Goal to add
   */
  public void addGoal(Goal goal) {
    goals.addObject(goal);
  }

  /**
   * Removes a Goal from the game, if it exists there.
   * @param goal the Goal to remove
   */
  public void removeGoal(Goal goal) {
    if(!goals.isIdInUse(goals.getId(goal))) {
      return;
    }
    goals.removeObject(goal);
  }

  /**
   * Gets the Goals of the game.
   * @return unmodifiable List of Goals
   */
  public List<Goal> getGoals() {
    ArrayList<Goal> listGoals= new ArrayList<>();
    for(Map.Entry<String, Goal> entry : goals) {
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

  /**
   * Creates an ownable using ownableFactory for player
   * Pass in null for any unused parameters (cannot pass null for type)
   * @param type the string type of ownable
   * @param owner the owner of the ownable
   * @param parentOwnable the parent of the ownable
   */
  private void createOwnable(String type, Owner owner, Ownable parentOwnable) {
    Owner destinationOwner = owner;
    if (owner == null){
      destinationOwner = gameWorld;
    }
    Ownable newOwnable = ownableFactory.createOwnable(type, destinationOwner);
    ownableIdManager.addObject(newOwnable, parentOwnable);
  }

  /**
   Method is called in order to send information about a newly constructed   object that was made in the front end sent to the backend. The
   controller sends to the backend for the backend to input these into a
   file
   @Type The class the object belongs to
   @Params The params of the object
   **/
  public void sendObject(String type, String params) {
    //TODO this sucks and is sorta hardcoded
    List<String> parameters = Arrays.asList(params.split(";"));
    //{"owner=id1", "parentOwnable=id2"}
    String ownerID = parameters.get(0).substring(parameters.get(0).indexOf("=") + 1);
    String parentOwnableID = parameters.get(1).substring(parameters.get(1).indexOf("=") + 1);
    if (ownerID.equals("NULL")){
      if (parentOwnableID.equals("NULL")){
        createOwnable(type, null, null);
      }
      createOwnable(type, null, getOwnable(parentOwnableID));
    }
    else if (parentOwnableID.equals("NULL")) {
      createOwnable(type, playerIdManager.getObject(ownerID), null);
    }
    else {
      createOwnable(type, playerIdManager.getObject(ownerID), getOwnable(parentOwnableID));
    }
  }


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