package oogasalad.gamerunner.backend.interpreter;

import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.GameToInterpreterAPI;
import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.TextObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGame implements GameToInterpreterAPI {

    private final RuleManager rules = new RuleManager();
    private final List<Goal> goals = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();

    private final IdManager<Ownable> idManager = new IdManager<>();

    private final GameWorld gameWorld = new GameWorld();

    private final FSM<String> fsm = new FSM<>(idManager);

    private final Interpreter interpreter = new Interpreter();

    private final Variable<Double> turn = new Variable<>(0.);

    private final Map<Ownable, DropZone> pieceLocations = new HashMap<>();

    private final Map<Ownable, String> objImages = new HashMap<>();

    private List<String> log = new ArrayList<>();


    /////////////////// PLAY THE GAME ///////////////////

    public void initGame(int numPlayers) {

        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        interpreter.linkIdManager(idManager);
        interpreter.linkGame(this);

        initVariables();

        fsm.setState("INIT");
        fsm.transition();
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
        for (Goal g : goals){
            Player player = g.test(interpreter, idManager);
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
    public void loadGame(String directory) {
        // TODO
        // get num players

        // load in players and player FSM

        // load in ownables


        // load in rules and goals
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
    public void movePiece(GameObject piece, DropZone dz) {
        DropZone oldDz = pieceLocations.get(piece);
        if (oldDz != null){
            oldDz.removeObject(oldDz.getKey(piece));
        }
        dz.putObject(idManager.getId(piece), piece);
    }

    @Override
    public void removePiece(GameObject piece) {
        if (pieceLocations.containsKey(piece)){
            DropZone dz = pieceLocations.get(piece);
            dz.removeObject(dz.getKey(piece));
            pieceLocations.remove(piece);
        }
        idManager.removeObject(piece);
    }

    public void noFSMInit(int numPlayers){
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        interpreter.linkIdManager(idManager);
        interpreter.linkGame(this);

        initVariables();
    }

    public Interpreter getInterpreter(){
        return interpreter;
    }

    public IdManager<Ownable> getIdManager(){
        return idManager;
    }

    public void addElement(Ownable element, String id){
        idManager.addObject(element, id);
    }

    public void addElement(Ownable element) {
        idManager.addObject(element);
    }
    @Override
    public void putInDropZone(Ownable element, DropZone dropZone){
        pieceLocations.put(element, dropZone);
        System.out.println(element + " " + idManager.objectStream().toList());
        dropZone.putObject(idManager.getId(element), element);
    }

    @Override
    public void increaseTurn() {
        turn.set((turn.get() + 1) % players.size());
    }

    @Override
    public void setTurn(double turn) {
        this.turn.set(turn);
    }

    @Override
    public void setObjectImage(Ownable obj, String image) {
        objImages.put(obj, "assets/" + image);
    }

    @Override
    public void addObject(Ownable obj, DropZone dz, String image, double width, double height) {
        idManager.addObject(obj);
        putInDropZone(obj, dz);
        objImages.put(obj, "assets/" + image);
    }

//    @Override
//    public void addObject(Ownable obj, DropZone dz, String image, double width, double height) {
//        idManager.addObject(obj);
//        putInDropZone(obj, dz);
//        String imagePath = "assets/" + image;
//        objImages.put(obj, imagePath);
//    }

    public String getObjImage(Ownable obj) {
        return objImages.get(obj);
    }

    @Override
    public void addDropZone(DropZone dz, DropZone location, String image, String highlight, double width, double height) {
        idManager.addObject(dz);
        putInDropZone(dz, location);
    }

    @Override
    public void setPieceHighlight(Ownable piece, String highlight) {

    }

    @Override
    public void updateTextObject(TextObject obj) {
        String id = idManager.getId(obj);
        log.add(id + "-" + obj.getText());
    }

    @Override
    public void addTextObject(TextObject obj, DropZone dz) {
        idManager.addObject(obj);
        putInDropZone(obj, dz);
        String id = idManager.getId(obj);
        log.add(id + "-" + obj.getText());
    }

    @Override
    public RuleManager getRules() {
        return rules;
    }

    public void setTurn(int i){
        turn.set((double) i);
    }

    public List<String> getLog(){
        return log;
    }

}
