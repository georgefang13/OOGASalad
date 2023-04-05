package oogasalad.gamerunner.backend;

import java.util.ArrayList;
import java.util.Collection;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.id.IdManager;
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
 */
public class Game {

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
  private final ArrayList<Player> players = new ArrayList<>();

  /**
   * The IdManager of the game for Ownables.
   */
  private final IdManager<Ownable> ownableIdManager = new IdManager();

  /**
   * The GameWorld of the game.
   * The GameWorld owns Ownables not owned by Players.
   */
  private final GameWorld gameWorld = new GameWorld(ownableIdManager);

  //TODO worry about the below stuff later
  // Was made before refactoring to use IdManager
  // Needs updating

  /**
   * Adds a Player to the game.
   * @param player
   */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Adds n Players to the game.
   * @param n the number of Players to add`
   */
  public void addNPlayers(int n) {
    for (int i = 0; i < n; i++) {
      addPlayer(new Player(ownableIdManager));
    }
  }

//  /**
//   * Removes a Player from the game, if it exists there.
//   * Destroys all Ownables owned by the Player. //TODO throw warning about this
//   * @param player
//   */
//  public void removePlayer(Player player) {
//    if(!players.contains(player)) {
//      return;
//    }
//    //remove all ownables owned by player
//    for (Ownable ownable : )
//      ownableIdManager.removeOwnable(ownable.getId());
//    }
//
//    players.remove(player);
//  }

//  /**
//   * Removes all Players from the game and their Ownables.
//   */
//  public void removeAllPlayers() {
//    while (!players.isEmpty()) {
//      removePlayer(players.get(0));
//    }
//  }
//
//  /**
//   * Gets the Players of the game.
//   * @return unmodifiable List of Players
//   */
//  public List<Player> getPlayers() {
//    return Collections.unmodifiableList(players);
//  }
//
//  /**
//   * Adds a Rule to the game.
//   * @param rule
//   */
//  public void addRule(Rule rule) {
//    rules.add(rule);
//  }
//
//  /**
//   * Removes a Rule from the game, if it exists there.
//   * @param rule
//   */
//  public void removeRule(Rule rule) {
//    if(!rules.contains(rule)) {
//      return;
//    }
//    rules.remove(rule);
//  }
//
//  /**
//   * Gets the Rules of the game.
//   * @return unmodifiable List of Rules
//   */
//  public List<Rule> getRules() {
//    return Collections.unmodifiableList(rules);
//  }
//
//  /**
//   * Adds a Goal to the game.
//   * @param goal the Goal to add
//   */
//  public void addGoal(Goal goal) {
//    goals.add(goal);
//  }
//
//  /**
//   * Removes a Goal from the game, if it exists there.
//   * @param goal the Goal to remove
//   */
//  public void removeGoal(Goal goal) {
//    if(!goals.contains(goal)) {
//      return;
//    }
//    goals.remove(goal);
//  }
//
//  /**
//   * Gets the Goals of the game.
//   * @return unmodifiable List of Goals
//   */
//  public List<Goal> getGoals() {
//    return Collections.unmodifiableList(goals);
//  }
//
//  /**
//   * Gets the GameWorld of the game.
//   * @return the GameWorld
//   */
//  public GameWorld getGameWorld() {
//    return gameWorld;
//  }
////
//  /**
//   * Adds an Ownable to the game for a specific owner.
//   * Adds the Ownable to the IdManager and Owner.
//   * @param ownable the Ownable to add
//   * @param owner the Owner of the Ownable
//   */
//  public void addOwnable(Owner owner) {
//    ownable
//    ownableIdManager.addObject();
//  }

//  //TODO remove owner
//
//  //TODO changer owner
//
//  /**
//   * Adds an Ownable to the GameWorld
//   * Adds the Ownable to the IdManager and GameWorld.
//   * @param ownable the Ownable to add
//   */
//  public void addOwnable(Ownable ownable) {
//    addOwnable(ownable, gameWorld);
//  }
//
//  /**
//   * Gets the Owner of an Ownable with id.
//   * @param id the id of the Ownable
//   * @return the Owner of the Ownable
//   */
//  public Owner getOwner(String id) { //TODO catch exception
//    if (!ownableIdManager.isIdInUse(id)) {
//      return null;
//    }
//    return ownableIdManager.getOwner(id);
//  }
//
//  /**
//   * Gets an Ownable from the IdManager for a given id.
//   * @param id the id of the Ownable
//   * @return the Ownable
//   */
//  public Ownable getOwnable(String id) { //TODO catch exception
//    if (!ownableIdManager.isIdInUse(id)) {
//      return null;
//    }
//    return ownableIdManager.getOwnable(id);
//  }

}