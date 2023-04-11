package oogasalad.gamerunner.backend;

import com.google.gson.JsonObject;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.OwnableFactory;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;

import java.util.*;

/**
 * The Game class represents the game itself.
 * It contains Owners such as the GameWorld and Players.
 * It contains the Rules and Goals.
 * @author Michael Bryant
 * @author Max Meister
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
  private final IdManager<Player> playerIdManager = new IdManager<>();

  /**
   * The IdManager of the game for Ownables.
   */
  private final IdManager<Ownable> ownableIdManager = new IdManager<>();

  /**
   * The GameWorld of the game.
   * The GameWorld owns Ownables not owned by Players.
   */
  private final GameWorld gameWorld = new GameWorld();

  private final FSM<String> fsm = new FSM<>(ownableIdManager);

  private int numPlayers = 1;


  /////////////////// PLAY THE GAME ///////////////////

  public void initGame() {
    Variable<Double> turn = new Variable<>(0.);
    turn.setOwner(gameWorld);
    ownableIdManager.addObject(turn, "turn");

    Variable<Double> numPlayersVar = new Variable<>((double) numPlayers);
    numPlayersVar.setOwner(gameWorld);
    ownableIdManager.addObject(numPlayersVar, "playerCount");

    Variable<List<GameObject>> available = new Variable<>(new ArrayList<>());
    available.setOwner(gameWorld);
    ownableIdManager.addObject(available, "available");

    fsm.setState("INIT");
    fsm.transition();
  }
  /**
   * reacts to clicking a piece
   */
  public void clickPiece(String selectedObject) {
    fsm.setStateInnerValue(selectedObject);
    fsm.transition();

    if (fsm.getCurrentState().equals("DONE")){
      fsm.setState("INIT");
      // check goals
      if (checkGoals() != -1){
        // TODO end game
      }
    }
  }

  private int checkGoals() {
    for (Map.Entry<String, Goal> goal : goals){
      Goal g = goal.getValue();
      int player = g.evaluate(ownableIdManager, playerIdManager);
      if (player != -1){
        return player;
      }
    }
    return -1;
  }

  // region LOADING

    /**
     * Loads a Game from a file.
     * @param directory the name of the file to load from
     */
    public void loadGame(String directory) {
      // TODO
      // get num players

      // load in players and player FSM

      // load in ownables


      // load in rules and goals
    }

    private void initPlayers(JsonObject json) {
      numPlayers = 1;
    }

    private void initOwnables(JsonObject json) {}

    private void initRulesAndGoals(JsonObject json) {}

  //endregion

  // region PLAYERS

  /**
   * Adds a Player to the game.
   * @param player the Player to add
   */
  public void addPlayer(Player player) {
    playerIdManager.addObject(player);
  }

  /**
   * Removes a Player from the game, if it exists there.
   * Destroys all Ownables owned by the Player. //TODO throw warning about this
   * @param player the Player to remove
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

  //endregion

  // region RULES AND GOALS

  /**
   * Adds a Rule to the game.
   * @param rule the Rule to add
   */
  public void addRule(Rule rule) {
    rules.addObject(rule);
  }

  /**
   * Removes a Rule from the game, if it exists there.
   * @param rule the Rule to remove
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
   * Gets the GameWorld of the game.
   * @return the GameWorld
   */
  public GameWorld getGameWorld() {
    return gameWorld;
  }

  // endregion

  // region OWNABLES

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
    Ownable newOwnable = OwnableFactory.createOwnable(type, destinationOwner);
    ownableIdManager.addObject(newOwnable, parentOwnable);
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
    return ownableIdManager.getObject(id).getOwner();
  }

  /**
   * Sets the Owner of an Ownable with id.
   * @param id the id of the Ownable
   * @param owner the new owner of the Ownable
   * @throws IllegalArgumentException if owner is null, the Ownable is owned by the GameWorld
   */
  public void setOwner(String id, Owner owner) throws IllegalArgumentException{
    ownableIdManager.getObject(id).setOwner(owner);
  }

  // endregion

}