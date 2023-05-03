package oogasalad.gamerunner.backend;

import oogasalad.Controller.GameController;
import oogasalad.gamerunner.backend.online.EmptyOnlineRunner;
import oogasalad.gamerunner.backend.online.OnlineRunner;
import oogasalad.gamerunner.backend.online.SocketRunner;
import oogasalad.sharedDependencies.backend.GameLoader;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.TextObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;

import java.io.FileNotFoundException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Game class represents the game itself.
 * It contains Owners such as the GameWorld and Players.
 * It contains the Rules and Goals.
 * @author Michael Bryant
 * @author Max Meister
 */
public class Game implements GameToInterpreterAPI{

    private static final Logger LOG = LogManager.getLogger(Game.class);

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
    private final IdManager<Ownable> idManager = new IdManager<>();

    /**
     * The GameWorld of the game.
     * The GameWorld owns Ownables not owned by Players.
     */
    private final GameWorld gameWorld = new GameWorld();

    private final FSM<String> fsm = new FSM<>(idManager);

    private final Interpreter interpreter = new Interpreter();

    private final Variable<Double> turn = new Variable<>(0.);

    private final Map<Ownable, DropZone> pieceLocations = new HashMap<>();

    private final GameController controller;

    private final String directory;

    private final OnlineRunner onlineRunner;

    private final int numPlayers;
    private int numOnlinePlayers;

    private boolean startedOnline = false;

    // the position (which turn you are) in the game. -1 if not playing online
    private int onlinePlayerNum = -1;


    /////////////////// PLAY THE GAME ///////////////////

    public Game(GameController controller, String directory, int numPlayers, boolean online) {
        this.numPlayers = numPlayers;
        numOnlinePlayers = 0;
        this.controller = controller;
        this.directory = directory;

        if (online) {
            onlineRunner = new SocketRunner(this);
        } else {
            onlineRunner = new EmptyOnlineRunner();
        }
    }

    /**
     * Sets the number of online players in the game. Starts the game when the desired number of players is reached.
     * @param num the number of online players
     */
    public void setNumOnlinePlayers(int num){
        numOnlinePlayers = num;
        if (numOnlinePlayers == numPlayers){
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
        
        interpreter.linkIdManager(idManager);
        interpreter.linkGame(this);

        gl.loadFSM(interpreter, fsm);
        goals.addAll(gl.loadGoals());
        gl.loadDropZones(idManager, gameWorld);
        pieceLocations.putAll(gl.loadObjectsAndVariables(idManager, players, gameWorld));
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
        }
    }

    /**
     * sends the online game code to the controller
     * @param code the code to send
     */
    public void sendCode(String code){
        controller.passGameId(code);
    }

    private void startTurn(){
        try {
            interpreter.interpret("make :game_available [ ]");
            fsm.setState("INIT");
            fsm.transition();
            sendClickable();
            if (getLog().size() > 0) {
                LOG.info(getLog());
            }
        } catch (Exception e) {
            LOG.error("Game log when interpreter error: " + getLog());
            LOG.error(e.getMessage());
            throw e;
        }
    }
    
    private void initVariables(){
        turn.setOwner(gameWorld);
        if (!idManager.isIdInUse("turn")) {
            idManager.addObject(turn, "turn");
        }

        Variable<Double> numPlayersVar = new Variable<>((double) players.size());
        numPlayersVar.setOwner(gameWorld);
        idManager.addObject(numPlayersVar, "playerCount");

        Variable<List<GameObject>> available = new Variable<>(new ArrayList<>());
        available.setOwner(gameWorld);
        idManager.addObject(available, "available");

        Variable<List<Object>> log = new Variable<>(new ArrayList<>());
        log.setOwner(gameWorld);
        idManager.addObject(log, "log");
    }

    private List<Object> getLog(){
        Variable<List<Object>> v = (Variable<List<Object>>) idManager.getObject("log");
        return v.get();
    }

    private void sendClickable(){
        if (onlinePlayerNum > -1 && onlinePlayerNum != turn.get().intValue()) return;

        Variable<List<GameObject>> v = (Variable<List<GameObject>>) idManager.getObject("available");

        List<String> ids = new ArrayList<>();
        for (GameObject o : v.get()){
            ids.add(idManager.getId(o));
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
        Variable<List<GameObject>> v = (Variable<List<GameObject>>) idManager.getObject("available");
        for (GameObject o : v.get()){
            if (idManager.getId(o).equals(id)) return true;
        }
        return false;
    }

    /**
     * reacts to clicking a piece
     */
    public void clickPiece(String selectedObject, boolean send) {
        if (send) onlineRunner.send(selectedObject);

        if (!isPieceAvailable(selectedObject)) return;
        
        interpreter.interpret("make :game_available [ ]");

        try {
            fsm.setStateInnerValue(selectedObject);
            fsm.transition();
        } catch (Exception e) {
            LOG.error("Game Log when interpreter error: " + getLog());
            LOG.error(e.getMessage());
            throw e;
        }

        List<Object> log = getLog();
        if (log.size() > 0){
            LOG.info("Game Log: " + getLog());
        }
        sendClickable();

        if (fsm.getCurrentState().equals("DONE")){
            int playerWin = checkGoals();

            // check goals
            if (playerWin != -1){
                controller.endGame(playerWin);
            }
            else {
                fsm.transition();
                setTurn(turn.get());
            }


        }
    }

    /**
     * Goes to the previous state. Will break the game if going to the previous state would change turns or modify the board.
     */
    public void undoClickPiece(){
        undoClickPiece(true);
    }
    public void undoClickPiece(boolean send){
        interpreter.interpret("make :game_available [ ]");
        fsm.undo();
        if (send) onlineRunner.send("^undo");
        sendClickable();
    }

    private int checkGoals() {
        for (Goal g : goals){
            Player player = g.test(interpreter, idManager);
            if (player != null){
                return players.indexOf(player);
            }
        }
        return -1;
    }

    /**
     * Gets the Players of the game.
     * @return unmodifiable List of Players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Ownable getVariable(String name){
        if (idManager.isIdInUse(name)){
            return idManager.getObject(name);
        }
        return null;
    }
    public Owner getOwner(int num){
        if (num == -1) return gameWorld;
        return players.get(num);
    }

    @Override
    public Owner getPlayer(int playerNum) {
        if (playerNum == -1) return gameWorld;
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
    public void movePiece(GameObject piece, DropZone dz) {
        DropZone oldDz = pieceLocations.get(piece);
        if (oldDz != null){
            oldDz.removeObject(oldDz.getKey(piece));
        }
        dz.putObject(idManager.getId(piece), piece);
        pieceLocations.put(piece, dz);
        controller.movePiece(idManager.getId(piece), idManager.getId(dz));
    }

    @Override
    public void removePiece(GameObject piece) {
        if (pieceLocations.containsKey(piece)){
            DropZone dz = pieceLocations.get(piece);
            dz.removeObject(dz.getKey(piece));
            pieceLocations.remove(piece);
        }
        controller.removePiece(idManager.getId(piece));
        idManager.removeObject(piece);
        if (piece instanceof TextObject textPiece){
            textPiece.unLink();
        }
    }

    @Override
    public void putInDropZone(Ownable element, DropZone dropZone){
        if (pieceLocations.containsKey(element)){
            DropZone dz = pieceLocations.get(element);
            dz.removeObject(dz.getKey(element));
        }
        pieceLocations.put(element, dropZone);
        dropZone.putObject(idManager.getId(element), element);
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
    public void setObjectImage(Ownable obj, String image) {
        String id = idManager.getId(obj);
        String imagePath = this.directory + "/assets/" + image;
        controller.setObjectImage(id, imagePath);
    }

    @Override
    public void addObject(Ownable obj, DropZone dz, String image, double width, double height) {
        idManager.addObject(obj);
        putInDropZone(obj, dz);
        String id = idManager.getId(obj);
        String imagePath = this.directory + "/assets/" + image;
        controller.addPiece(id, imagePath, idManager.getId(dz), false, "#ff0000", (int) height, (int) width);
    }

    @Override
    public void addDropZone(DropZone dz, DropZone location, String image, String highlight, double width, double height) {

    }

    @Override
    public void setPieceHighlight(Ownable piece, String highlight) {

        String id = idManager.getId(piece);
        System.out.println("Setting highlight to " + id + " " + highlight);
        String imagePath = highlight;
        if (!imagePath.startsWith("#")){
            imagePath = this.directory + "/assets/" + imagePath;
        }
        controller.setPieceHighlight(id, imagePath);
    }

    @Override
    public void updateTextObject(TextObject obj) {
        controller.updateTextObject(idManager.getId(obj), obj.getText());
    }

    @Override
    public void addTextObject(TextObject obj, DropZone dz) {
        idManager.addObject(obj);
        putInDropZone(obj, dz);
        String id = idManager.getId(obj);
        controller.addTextObject(id, obj.getText(), idManager.getId(dz));
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
