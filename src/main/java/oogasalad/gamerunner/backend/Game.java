package oogasalad.gamerunner.backend;

import oogasalad.gamerunner.backend.online.EmptyOnlineRunner;
import oogasalad.gamerunner.backend.online.OnlineRunner;
import oogasalad.gamerunner.backend.online.SocketRunner;
import oogasalad.sharedDependencies.backend.GameLoader;
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
