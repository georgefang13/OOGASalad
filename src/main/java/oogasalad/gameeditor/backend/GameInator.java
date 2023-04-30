package oogasalad.gameeditor.backend;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.io.FileUtils;

import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.GameLoader;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ObjectFactory;
import oogasalad.sharedDependencies.backend.id.OwnableSearchStream;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
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
  private final IdManager<Ownable> idManager = new IdManager<>();

  /**
   * The rule manager of the game for rules and goals.
   */
  private RuleManager ruleManager = new RuleManager();

  /**
   * The GameWorld of the game. The GameWorld owns Ownables not owned by Players.
   */
  private final GameWorld gameWorld = new GameWorld();

  /**
   * The ObjectFactory of the game.
   */
  private final ObjectFactory objectFactory;

  /**
   * The name of the game.
   * Used for accessing the correct directory for files
   */
  private final String gameName;

  private String gameDirectory;
  private String generalInfoFile;
  private String layoutFile;
  private String variablesFile;
  private String objectsFile;

  /**
   * Creates a new Game with no information.
   */
  public GameInator(String gameName) {
    objectFactory = new ObjectFactory(gameWorld, idManager, players);
    this.gameName = gameName;
    initDirectories();
    buildInitialFiles();
  }

  /**
   * Creates a new Game with the given file name.
   * Removes all current information and overwrites it with the information from the file.
   * @param directory the directory of the files to load
   * @param gameName the name of the game
   */
  public GameInator(String directory, String gameName) {
    this.gameName = gameName;
    initDirectories();
    //Use gameLoader to load the game from a file
    GameLoader loader = new GameLoader(directory);
    try{
      players.addAll(loader.loadPlayers());
      loader.loadDropZones(idManager, gameWorld);
      loader.loadObjectsAndVariables(idManager, players, gameWorld);
      goals.addAll(loader.loadGoals());
      ruleManager = loader.loadRules();
    } catch (Exception e) {
      e.printStackTrace(); //TODO throw error or log
    }

    objectFactory = new ObjectFactory(gameWorld, idManager, players);
  }

  private void initDirectories() {
    gameDirectory = "data/games/" + gameName + "/";
    generalInfoFile = gameDirectory + "general.json";
    layoutFile = gameDirectory + "layout.json";
    variablesFile = gameDirectory + "variables.json";
    objectsFile = gameDirectory + "objects.json";
    //TODO throw error or create if not exist
  }

  private void buildInitialFiles(){

    try {
      Path path = Paths.get(gameDirectory);

      FileUtils.deleteDirectory(new File(gameDirectory)); //TODO this could be a problem in integration

      Files.createDirectory(path);

      Path path2 = Paths.get(gameDirectory + "/assets");
      Files.createDirectory(path2);

      Path path3 = Paths.get(gameDirectory + "/frontend");
      Files.createDirectory(path3);

      buildGeneralInfoFile();

      FileManager fm = new FileManager();
      fm.saveToFile(layoutFile);
      fm.saveToFile(variablesFile);
      fm.saveToFile(objectsFile);
      fm.saveToFile(gameDirectory + "rules.json");
      fm.saveToFile(gameDirectory + "fsm.json");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void buildGeneralInfoFile(){
    FileManager fm = new FileManager();
    fm.addContent(gameName, "name");
    fm.addContent("", "author");
    fm.addContent("", "description");
    fm.addContent("all", "tags");
    fm.addContent("2", "players", "min");
    fm.addContent("2", "players", "max");
    fm.saveToFile(generalInfoFile);
  }


  /**
   * Updates the general.json file with the current information of the game.
   * For the backend, this is only the max number of players.
   */
  private void updateGeneralFile() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    try (FileReader reader = new FileReader(generalInfoFile)) {
      JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
      jsonObject.getAsJsonObject("players").addProperty("max", players.size());

      try (FileWriter writer = new FileWriter(generalInfoFile)) {
        gson.toJson(jsonObject, writer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the String representation of the Owner
   * @param o - the Owner
   * @return the String representation of the Owner
   */
  private int getOwnerType(Owner o) {
    if (o instanceof GameWorld) {
      return 0;
    }
    return players.indexOf((Player) o);
  }

  private void updateVariableFile() {
    FileManager fm = new FileManager();

    idManager.objectStream().filter(obj -> obj instanceof Variable<?>).forEach(var -> {
      Variable v = (Variable) var;
      String id = idManager.getId(v);
      String owner = String.valueOf(getOwnerType(v.getOwner()));
      //if v.get is null, then have type=Null otherwise get the type
      String type = "null";
      if(v.get() != null){
        type = v.get().getClass().getName();
      }

      fm.addContent(owner, id, "owner");
      fm.addContent(type, id, "type");
      fm.addContent(v.get(), id, "value");

    });

    fm.saveToFile(variablesFile);
  }

  private DropZone getLocation(GameObject g){
    List<Ownable> dzs = idManager.objectStream().filter(obj -> obj instanceof DropZone).filter(dz -> {
      DropZone dropZone = (DropZone) dz;
      return dropZone.getAllObjects().contains(g);
    }).toList();

    return dzs.size() > 0 ? (DropZone) dzs.get(0) : null;
  }

  private void updateObjectFile() {
    FileManager fm = new FileManager();
    OwnableSearchStream stream = new OwnableSearchStream(idManager);
    idManager.objectStream().filter(o -> o instanceof GameObject && !(o instanceof DropZone)).forEach(obj -> {
      String objId = idManager.getId(obj);
      int owner = -1;
      if (obj.getOwner() != gameWorld) owner = players.indexOf((Player) obj.getOwner());

      fm.addContent(String.valueOf(owner), objId, "owner");
      obj.getClasses().forEach(cls -> fm.addContent(objId, "classes"));
      if(obj.getClasses().size() == 0)
        fm.addEmptyArray(objId, "classes");

      AtomicBoolean ownsSomething = new AtomicBoolean(false);
      idManager.objectStream()
          .filter(stream.isDirectlyOwnedByOwnable(obj))
          .map(idManager::getId)
          .forEach(s -> {
            fm.addContent(s, objId, "owns");
            ownsSomething.set(true);
          });

      //if obj does not own anything, add empty string
      if (!ownsSomething.get())
        fm.addEmptyArray(objId, "owns");

      DropZone location = getLocation((GameObject) obj);

      try {
        fm.addContent(idManager.getId(location), objId, "location");
      } catch (Exception ignored) {
        fm.addContent("", objId, "location");
      }
    });

    fm.saveToFile(objectsFile);
  }

  private void updateLayoutFile() {
    FileManager fm = new FileManager();

    idManager.objectStream().filter(obj -> obj instanceof DropZone).forEach(dz -> {
      DropZone dropZone = (DropZone) dz;
      String id = idManager.getId(dropZone);
      // get connections
      AtomicBoolean hadConnections = new AtomicBoolean(false);
        dropZone.getEdges().forEach((edgeName, value) -> {
          String edgeId = idManager.getId(value);
          fm.addContent(edgeId, id, "connections", edgeName);
          hadConnections.set(true);
        });
      if (!hadConnections.get()) fm.addContent("", id, "connections");
        // get classes
        dropZone.getClasses().forEach(cls -> fm.addContent(id, "classes"));
        if (dropZone.getClasses().size() == 0){
          fm.addEmptyArray(id, "classes");
        }
    });
    fm.saveToFile(layoutFile);
  }

  private void idManagerFileUpdate() {
    updateLayoutFile();
    updateObjectFile();
    updateVariableFile();
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
    //add player to general.json
    updateGeneralFile();
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
    idManagerFileUpdate();
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
    if (!idManager.isIdInUse(id)) {
      return;
    }
    idManager.removeObject(id);
    idManagerFileUpdate();
  }

  /**
   * Removes a Player from the game, if there is one.
   * Also removes all ownables owned by the player
   */
  public void removePlayer() {
    //remove all ownables owned by the player
    idManager.removeObjectsOwnedByOwner(players.get(players.size() - 1));
    if (players.size() > 0) {
      //remove the last player
      players.remove(players.size() - 1);
    }
    //add player to general.json
    updateGeneralFile();
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
   * @param type The class the object belongs to
   * @param params The params of the object
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
   * @param str owner id
   * @return owner id if it is numeric, null otherwise
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
    Ownable targetOwnable = idManager.getObject(targetObject);
    //change parent ownable
    String newParentOwnableId = params.get(ObjectParameter.PARENT_OWNABLE_ID) != null ? params.get(ObjectParameter.PARENT_OWNABLE_ID).toString() : null;
    if (newParentOwnableId != null){
      idManager.changeParentId(targetObject, newParentOwnableId);
    }

    //change a variable value
    Map<Object, Object> constructorParams = (Map<Object, Object>) params.get(ObjectParameter.CONSTRUCTOR_ARGS);
    Object newValue = constructorParams.get(ObjectParameter.VALUE);
    if (targetOwnable instanceof Variable){
      ((Variable) targetOwnable).set(newValue);
    }

    // change id and owner of ownable
    String newId = params.get(ObjectParameter.ID) != null ? params.get(ObjectParameter.ID).toString() : null;
    if (newId != null){
      idManager.changeId(targetObject, newId);
      updateOwner(newId, params);
    } else {
      updateOwner(targetObject, params);
    }
    idManagerFileUpdate();
  }

  /**
   * Updates the owner of an ownable.
   *
   * @param targetObject the ownable to update
   * @param params the parameters of the ownable
   * @throws IllegalArgumentException if the owner is not valid
   */
  private void updateOwner(String targetObject, Map<ObjectParameter, Object> params) throws IllegalArgumentException {
    String newOwnerNum = params.get(ObjectParameter.OWNER) != null ? params.get(ObjectParameter.OWNER).toString() : null;
    Integer newOwnerInt = isNumeric(newOwnerNum);
    if (newOwnerInt != null && newOwnerInt >= 0 && newOwnerInt <= players.size()){
      Owner newOwner = players.get(newOwnerInt-1);
      idManager.getObject(targetObject).setOwner(newOwner);
    }
    else if(newOwnerNum != null && newOwnerNum.equals("GameWorld")){ //if null it wasn't in there and we dont want to change it
      //set to gameworld
      idManager.getObject(targetObject).setOwner(gameWorld);
    }
  }
  /**
   * Update Rule in the game.
   *
   * @param params the parameters of the rule
   */
  public void updateRule(String targetObject, Map<ObjectParameter, Object> params) throws IllegalArgumentException{
    String rule = params.get(ObjectParameter.RULE_STR) != null ? params.get(ObjectParameter.RULE_STR).toString() : null;
    String ruleName = params.get(ObjectParameter.RULE_NAME) != null ? params.get(ObjectParameter.RULE_NAME).toString() : null;
    String ruleCls = params.get(ObjectParameter.RULE_CLS) != null ? params.get(ObjectParameter.RULE_CLS).toString() : null;
    ruleManager.removeRule(ruleCls, targetObject);
    ruleManager.addRule(ruleCls, ruleName, rule);
  }

  /**
   * Method is called to update information about a modified object in teh front end. The controller
   * sends updates to the Backend by giving the type and params for identification
   *
   * @param type The class the object belongs to
   * @param params The updated params of the object
   **/
  public void updateObjectProperties(String targetObject, ObjectType type, Map<ObjectParameter, Object> params)
      throws IllegalArgumentException {
    switch (type) {
      case PLAYER, GOAL -> {
      }
      case OWNABLE -> updateOwnable(targetObject, params);
      case RULE -> updateRule(targetObject, params);
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
    if (!idManager.isIdInUse(id)) {
      return null;
    }
    return idManager.getObject(id);
  }


  /**
   * Gets the IdManager for the Ownables.
   * @return the IdManager for the Ownables
   */
  public IdManager getOwnableIdManager() {
    return idManager;
  }

  /**
   * Gets the ruleManager for the Rules.
   * @return the ruleManager for the Rules
   */
  public RuleManager getRuleManager() {
    return ruleManager;
  }
}