package oogasalad.gamerunner.backend;

import oogasalad.gamerunner.backend.fsm.ProgrammableState;
import oogasalad.gamerunner.backend.online.EmptyOnlineRunner;
import oogasalad.gamerunner.backend.online.OnlineRunner;
import oogasalad.gamerunner.backend.online.SocketRunner;
import oogasalad.sharedDependencies.backend.GameLoader;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;

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
    private RuleManager rules = new RuleManager();

    /**
     * The Goals of the game.
     */
    private final List<Goal> goals = new ArrayList<>();

    /**
     * The Players of the game.
     * Players own Ownables.
     */
    private final List<Player> players = new ArrayList<>();

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

    private final String directory;

    private final OnlineRunner onlineRunner;

    private int numPlayers;

    private boolean startedOnline = false;
    private int onlinePlayerNum = -1;


    /////////////////// PLAY THE GAME ///////////////////

    public Game(GameController controller, String directory, int numPlayers, boolean online) {
        this.numPlayers = numPlayers;
        this.controller = controller;
        this.directory = directory;

        if (online) {
            onlineRunner = new SocketRunner(this);
        } else {
            onlineRunner = new EmptyOnlineRunner();
        }
    }

    public void setNumPlayers(int num){
        numPlayers = num;
        if (numPlayers == 2){
            startOnlineGame();
        }
    }

    public void startGame(){
        try {
            initGame(directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGame(String directory) throws FileNotFoundException, ClassNotFoundException {

        GameLoader gl = new GameLoader(directory);

        // players are added by the SocketRunner if online
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player());
        }
        
        interpreter.linkIdManager(ownableIdManager);
        interpreter.linkGame(this);

        gl.loadFSM(interpreter, fsm);
        goals.addAll(gl.loadGoals());
        gl.loadDropZones(ownableIdManager, gameWorld);
        pieceLocations.putAll(gl.loadObjectsAndVariables(ownableIdManager, players, gameWorld));
        rules = gl.loadRules();

        initVariables();

        startTurn();
    }

    public void createOnlineGame(){
        onlineRunner.create();
    }
    public void joinOnlineGame(String code){
        onlineRunner.join(code);
    }
    public String getOnlineGameCode(){
        return onlineRunner.getCode();
    }
    public void startOnlineGame(){
        if (!startedOnline) {
            startedOnline = true;
            onlineRunner.start();
            onlinePlayerNum = onlineRunner.getPlayerNum();
            startGame();
            System.out.println("PLAYER " + onlinePlayerNum);
        }
    }

    private void startTurn(){
        try {
            interpreter.interpret("make :game_available [ ]");
            fsm.setState("INIT");
            fsm.transition();
            sendClickable();
        } catch (Exception e) {
            System.out.println(getLog());
            throw e;
        }
        for (Object o : getLog()){
            System.out.println(o);
        }
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

        Variable<List<Object>> log = new Variable<>(new ArrayList<>());
        log.setOwner(gameWorld);
        ownableIdManager.addObject(log, "log");
    }

    private List<Object> getLog(){
        Variable<List<Object>> v = (Variable<List<Object>>) ownableIdManager.getObject("log");
        return v.get();
    }

    private void sendClickable(){
        if (onlinePlayerNum > -1 && onlinePlayerNum != turn.get().intValue()) return;

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
        clickPiece(selectedObject, true);
    }

    private boolean isPieceAvailable(String id){
        Variable<List<GameObject>> v = (Variable<List<GameObject>>) ownableIdManager.getObject("available");
        for (GameObject o : v.get()){
            if (ownableIdManager.getId(o).equals(id)) return true;
        }
        return false;
    }

    /**
     * reacts to clicking a piece
     */
    public void clickPiece(String selectedObject, boolean send) {
        if (send) onlineRunner.send(selectedObject);

        if (!isPieceAvailable(selectedObject)) return;

        System.out.println("clicking " + selectedObject + " with " + send);

        interpreter.interpret("make :game_available [ ]");

        try {
            fsm.setStateInnerValue(selectedObject);
            fsm.transition();
        } catch (Exception e) {
            System.out.println(getLog());
            throw e;
        }

        sendClickable();

        if (fsm.getCurrentState().equals("DONE")){
            int playerWin = checkGoals();

            // check goals
            if (playerWin != -1){
                // TODO end game
                System.out.println("Player " + playerWin + " wins!");
            }

            fsm.transition();
            setTurn(turn.get());
        }
    }

    /**
     * Goes to the previous state. Will break the game if going to the previous state would change turns or modify the board.
     */
    public void undoClickPiece(){
        interpreter.interpret("make :game_available [ ]");
        fsm.undo();
        sendClickable();
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
    public void loadGame(String directory) throws FileNotFoundException, ClassNotFoundException {
        pieceLocations.clear();

        loadFSM(directory + "/fsm.json");
        loadDropZones(directory + "/layout.json");
        loadObjectsAndVariables(directory);
        loadRules(directory + "/rules.json");
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

        Map<DropZone, List<String[]>> edgeMap = new HashMap<>();

        for (String id : fm.getTagsAtLevel()){

            int x = Integer.parseInt(fm.getString(id, "position", "x"));
            int y = Integer.parseInt(fm.getString(id, "position", "y"));
            int width = Integer.parseInt(fm.getString(id, "position", "width"));
            int height = Integer.parseInt(fm.getString(id, "position", "height"));

            DropZone dz = new DropZone();
            for (String cls : fm.getArray(id, "classes")){
                dz.addClass(cls);
            }

            List<String[]> edges = new ArrayList<>();
            for (String edgeName : fm.getTagsAtLevel(id, "connections")){
                String edge = fm.getString(id, "connections", edgeName);
                edges.add(new String[]{edgeName, edge});
            }
            edgeMap.put(dz, edges);

            ownableIdManager.addObject(dz, id);

            controller.addDropZone(new GameController.DropZoneParameters(id, null,null,x, y, height, width));
        }

        for (DropZone dz : edgeMap.keySet()){
            // [ [ edgeName, edge ] ]
            for (String[] edge : edgeMap.get(dz)){
                DropZone other = (DropZone) ownableIdManager.getObject(edge[1]);
                dz.addOutgoingConnection(other, edge[0]);
            }
        }
    }

    private void loadObjectsAndVariables(String directory) throws FileNotFoundException, ClassNotFoundException {
        Map<String, List<String>> ownMap = loadGameObjects(directory + "/objects.json");
        loadVariables(directory + "/variables.json");

        for (String s : ownMap.keySet()) {
            GameObject mainObj = (GameObject) ownableIdManager.getObject(s);
            for (String o : ownMap.get(s)) {
                Ownable obj = ownableIdManager.getObject(o);
                ownableIdManager.setObjectOwner(obj, mainObj);
            }
        }
    }

    private Map<String, List<String>> loadGameObjects(String file) throws FileNotFoundException {

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

        return ownMap;
    }

    private void loadVariables(String file) throws FileNotFoundException, ClassNotFoundException {
        FileManager fm = new FileManager(file);
        for (String id : fm.getTagsAtLevel()){
            String ownerName = fm.getString(id, "owner");

            String type = fm.getString(id, "type");
            Class<?> clazz = Class.forName(type);
            Object obj = fm.getObject(clazz, id, "value");

            Variable<Object> var;

            try {
                int ownerIndex = Integer.parseInt(ownerName);
                Owner owner = gameWorld;
                if (ownerIndex != -1){
                    owner = players.get(ownerIndex);
                }
                var = new Variable<>(obj, owner);

            } catch (Exception e){
                var = new Variable<>(obj);
                Ownable owner = ownableIdManager.getObject(ownerName);
                ownableIdManager.setObjectOwner(var, owner);
            }

            for (String cls : fm.getArray(id, "classes")){
                var.addClass(cls);
            }

            ownableIdManager.addObject(var, id);
        }
    }

    private void loadRules(String file) throws FileNotFoundException{
        FileManager fm = new FileManager(file);
        for (String id : fm.getTagsAtLevel()){
            for (String ruleName : fm.getTagsAtLevel(id)){
                String rule = fm.getString(id, ruleName);
                rules.addRule(id, ruleName, rule);
            }
        }
    }

    //endregion
    // region PLAYERS

    /**
     * Adds a Player to the game.
     * @param player the Player to add
     */
    public void addPlayer(Player player) {
        players.add(player);
        ((Variable) ownableIdManager.getObject("numPlayers")).set((double) players.size());
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

    //endregion

    public Ownable getVariable(String name){
        if (ownableIdManager.isIdInUse(name)){
            return ownableIdManager.getObject(name);
        }
        return null;
    }
    public Owner getOwner(int num){
        if (num == -1) return gameWorld;
        return players.get(num);
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
        pieceLocations.put(piece, dz);
        controller.movePiece(ownableIdManager.getId(piece), ownableIdManager.getId(dz));
    }

    @Override
    public void removePiece(GameObject piece) {
        if (pieceLocations.containsKey(piece)){
            DropZone dz = pieceLocations.get(piece);
            dz.removeObject(dz.getKey(piece));
            pieceLocations.remove(piece);
        }
        controller.removePiece(ownableIdManager.getId(piece));
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

    @Override
    public void setTurn(double turn) {
        this.turn.set(turn);
        startTurn();
    }

    @Override
    public void putClass(IdManageable obj, String name) {
        obj.addClass(name);
    }

    @Override
    public void removeClass(IdManageable obj, String name) {
        obj.removeClass(name);
    }

    @Override
    public void setObjectImage(Ownable obj, String image) {
        String id = ownableIdManager.getId(obj);
        String imagePath = this.directory + "/assets/" + image;
    }

    @Override
    public void setObjectOwner(Ownable obj, Ownable owner) {
        ownableIdManager.setObjectOwner(obj, owner);
    }

    @Override
    public void setPlayerOwner(Ownable obj, Owner owner) {
        ownableIdManager.setPlayerOwner(obj, owner);
        int ownerNum = -1;
        if (owner != gameWorld) ownerNum = players.indexOf((Player) owner);
    }

    /**
     * Gets the Rules of the game.
     * @return unmodifiable List of Rules
     */
    @Override
    public RuleManager getRules() {
        return rules;
    }

}
