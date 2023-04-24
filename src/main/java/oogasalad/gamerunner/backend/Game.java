package oogasalad.gamerunner.backend;

import oogasalad.Controller.GameRunnerController;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.fsm.ProgrammableState;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * The Game class represents the game itself.
 * It contains Owners such as the GameWorld and Players.
 * It contains the Rules and Goals.
 * @author Michael Bryant
 * @author Max Meister
 */
public class Game implements GameToInterpreterAPI{

    /**
     * The Rules of the game.
     */
    private final IdManager<Rule> rules = new IdManager<>();

    /**
     * The Goals of the game.
     */
    private final List<Goal> goals = new ArrayList<>();

    /**
     * The Players of the game.
     * Players own Ownables.
     */
    private final ArrayList<Player> players = new ArrayList<>();

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

    private final Interpreter interpreter = new Interpreter();

    private final Variable<Double> turn = new Variable<>(0.);

    private final Map<Ownable, DropZone> pieceLocations = new HashMap<>();

    private final GameController controller;


    /////////////////// PLAY THE GAME ///////////////////


    public Game(GameController controller, String directory, int numPlayers) {
        this.controller = controller;

        initGame(numPlayers, directory);
    }
    private void initGame(int numPlayers, String directory){

        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }
        
        interpreter.linkIdManager(ownableIdManager);
        interpreter.linkGame(this);

        try {
            loadGame(directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        initVariables();

        startTurn();

    }

    private void startTurn(){
        fsm.setState("INIT");
        fsm.transition();
        sendClickable();
    }
    
    private void initVariables(){
        turn.setOwner(gameWorld);
        if (!ownableIdManager.isIdInUse("turn")) {
            ownableIdManager.addObject(turn, "turn");
        }

        Variable<Double> numPlayersVar = new Variable<>((double) players.size());
        numPlayersVar.setOwner(gameWorld);
        ownableIdManager.addObject(numPlayersVar, "playerCount");

        Variable<List<GameObject>> available = new Variable<>(new ArrayList<>());
        available.setOwner(gameWorld);
        ownableIdManager.addObject(available, "available");
    }

    private void sendClickable(){
        Variable<List<GameObject>> v = (Variable<List<GameObject>>) ownableIdManager.getObject("available");

        List<String> ids = new ArrayList<>();
        for (GameObject o : v.get()){
            ids.add(ownableIdManager.getId(o));
        }

        controller.setClickable(ids);
    }
    
    /**
     * reacts to clicking a piece
     */
    public void clickPiece(String selectedObject) {
        interpreter.interpret("make :game_available [ ]");

        fsm.setStateInnerValue(selectedObject);
        fsm.transition();

        sendClickable();

        if (fsm.getCurrentState().equals("DONE")){
            startTurn();
            int playerWin = checkGoals();
            // check goals
            if (playerWin != -1){
                // TODO end game
                System.out.println("Player " + playerWin + " wins!");
            }
        }
    }

    public void keyDown(String key) {

    }

    public void keyUp(String key) {

    }

    private int checkGoals() {
        for (Goal g : goals){
            Player player = g.test(interpreter, ownableIdManager);
            if (player != null){
                return players.indexOf(player);
            }
        }
        return -1;
    }

    // region LOADING

    /**
     * Loads a Game from a file.
     * @param directory the name of the file to load from
     */
    public void loadGame(String directory) throws FileNotFoundException {
        pieceLocations.clear();

        loadFSM(directory + "/fsm.json");
        loadDropZones(directory + "/layout.json");
        loadGameObjects(directory + "/objects.json");
        loadVariables(directory + "/variables.json");
    }

    private void loadFSM(String file) throws FileNotFoundException {

        FileManager fm = new FileManager(file);

        // states
        for (String stateName : fm.getTagsAtLevel("states")){
            String onEnter = fm.getString("states", stateName, "init");
            String onLeave = fm.getString("states", stateName, "leave");
            String setValue = fm.getString("states", stateName, "setValue");
            String to = fm.getString("states", stateName, "to");

            ProgrammableState ps = new ProgrammableState(interpreter, onEnter, onLeave, setValue);

            fsm.putState(stateName, ps, (prevstate, data) -> {
                interpreter.interpret(to);
                IdManager idManager = (IdManager) data.get("idManager");
                Variable<String> output = (Variable<String>) idManager.getObject("state_output");
                return output.get();
            });
        }

        // goals
        List<String> goals = StreamSupport.stream(fm.getArray("goals").spliterator(), false).toList();
        for (String g : goals){
            Goal goal = new Goal();
            goal.addInstruction(g);
            this.goals.add(goal);
        }

    }

    private void loadDropZones(String file) throws FileNotFoundException {
        FileManager fm = new FileManager(file);

        Map<DropZone, String[]> edgeMap = new HashMap<>();

        for (String id : fm.getTagsAtLevel()){

            int x = Integer.parseInt(fm.getString(id, "position", "x"));
            int y = Integer.parseInt(fm.getString(id, "position", "y"));
            int width = Integer.parseInt(fm.getString(id, "position", "width"));
            int height = Integer.parseInt(fm.getString(id, "position", "height"));

            DropZone dz = new DropZone();
            for (String cls : fm.getArray(id, "classes")){
                dz.addClass(cls);
            }

            for (String edgeName : fm.getTagsAtLevel(id, "connections")){
                String edge = fm.getString(id, "connections", edgeName);
                edgeMap.put(dz, new String[]{edgeName, edge});
            }

            ownableIdManager.addObject(dz, id);

            controller.addDropZone(new GameRunnerController.DropZoneParameters(id, x, y, height, width));
        }

        for (DropZone dz : edgeMap.keySet()){
            // [ edgeName, edge ]
            String[] edge = edgeMap.get(dz);
            DropZone other = (DropZone) ownableIdManager.getObject(edge[1]);
            dz.addOutgoingConnection(other, edge[0]);
        }
    }

    private void loadGameObjects(String file) throws FileNotFoundException {

        FileManager fm = new FileManager(file);

        Map<String, List<String>> ownMap = new HashMap<>();

        for (String id : fm.getTagsAtLevel()){
            String image = fm.getString(id, "image");
            double size = Double.parseDouble(fm.getString(id, "size"));
            String owner = fm.getString(id, "owner");
            String location = fm.getString(id, "location");
            List<String> owns = StreamSupport.stream(fm.getArray(id, "owns").spliterator(), false).toList();

            image = System.getProperty("user.dir") + "/" + file.substring(0, file.lastIndexOf("/")) + "/assets/" + image;

            Owner own = null;
            if (!owner.isEmpty()){
                own = players.get(Integer.parseInt(owner));
            }

            GameObject obj = new GameObject(own);

            for (String cls : fm.getArray(id, "classes")){
                obj.addClass(cls);
            }

            ownableIdManager.addObject(obj, id);
            controller.addPiece(id, image, location, size);

            DropZone dz = (DropZone) ownableIdManager.getObject(location);

            putInDropZone(obj, dz, id);

            ownMap.put(id, owns);
        }

        for (String s : ownMap.keySet()){
            GameObject mainObj = (GameObject) ownableIdManager.getObject(s);
            for (String o : ownMap.get(s)){
                GameObject obj = (GameObject) ownableIdManager.getObject(o);
                ownableIdManager.setOwner(obj, mainObj);
            }
        }
    }

    private void loadVariables(String file) throws FileNotFoundException {
        FileManager fm = new FileManager(file);
        for (String id : fm.getTagsAtLevel()){
            String owner = fm.getString(id, "owner");
            String value = fm.getString(id, "value");
            String type = fm.getString(id, "type");

//            Variable var = new Variable();
            // TODO: replace with GSON thing
        }
    }

    private void loadRules(){

    }

    //endregion

    // region PLAYERS

    /**
     * Adds a Player to the game.
     * @param player the Player to add
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes a Player from the game, if it exists there.
     * Destroys all Ownables owned by the Player. //TODO throw warning about this
     * @param player the Player to remove
     */
    public void removePlayer(Player player) {
        players.remove(player);
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
     * @return unmodifiable List of Players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public Player getPlayer(int playerNum) {
        return players.get(playerNum);
    }

    @Override
    public DropZone getPieceLocation(Ownable piece) {
        if (pieceLocations.containsKey(piece)){
            return pieceLocations.get(piece);
        }
        return null;
    }

    @Override
    public void movePiece(GameObject piece, DropZone dz, String name) {
        DropZone oldDz = pieceLocations.get(piece);
        if (oldDz != null){
            oldDz.removeObject(oldDz.getKey(piece));
        }
        dz.putObject(name, piece);
        controller.movePiece(ownableIdManager.getId(piece), ownableIdManager.getId(dz));
    }

    @Override
    public void removePiece(GameObject piece) {
        if (pieceLocations.containsKey(piece)){
            DropZone dz = pieceLocations.get(piece);
            dz.removeObject(dz.getKey(piece));
            pieceLocations.remove(piece);
        }
        ownableIdManager.removeObject(piece);
    }

    @Override
    public void putInDropZone(Ownable element, DropZone dropZone, String name){
        if (pieceLocations.containsKey(element)){
            DropZone dz = pieceLocations.get(element);
            dz.removeObject(dz.getKey(element));
        }
        pieceLocations.put(element, dropZone);
        dropZone.putObject(name, element);
    }

    @Override
    public void increaseTurn() {
        turn.set((turn.get() + 1) % players.size());
    }

    //endregion

    // region RULES AND GOALS

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
        goals.add(goal);
    }

    /**
     * Removes a Goal from the game, if it exists there.
     * @param goal the Goal to remove
     */
    public void removeGoal(Goal goal) {
        goals.remove(goal);
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

    public void init(int i) {
    }

    // endregion

}
