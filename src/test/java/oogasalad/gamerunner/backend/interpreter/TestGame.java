package oogasalad.gamerunner.backend.interpreter;

import com.google.gson.JsonObject;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.gamerunner.backend.GameToInterpreterAPI;
import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestGame implements GameToInterpreterAPI {

    private final IdManager<Rule> rules = new IdManager<>();
    private final IdManager<Goal> goals = new IdManager<>();
    private final ArrayList<Player> players = new ArrayList<>();

    private final IdManager<Ownable> ownableIdManager = new IdManager<>();

    private final GameWorld gameWorld = new GameWorld();

    private final FSM<String> fsm = new FSM<>(ownableIdManager);

    private final Interpreter interpreter = new Interpreter();

    private final Variable<Double> turn = new Variable<>(0.);


    /////////////////// PLAY THE GAME ///////////////////

    public void initGame(int numPlayers) {

        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        interpreter.linkIdManager(ownableIdManager);
        interpreter.linkGame(this);

        initVariables();

        fsm.setState("INIT");
        fsm.transition();
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
            int player = g.test(interpreter, ownableIdManager);
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

    @Override
    public Player getPlayer(int playerNum) {
        return players.get(playerNum);
    }

    public void noFSMInit(int numPlayers){
        for (int i = 0; i < numPlayers; i++){
            players.add(new Player());
        }

        interpreter.linkIdManager(ownableIdManager);
        interpreter.linkGame(this);

        initVariables();
    }

    public Interpreter getInterpreter(){
        return interpreter;
    }

    public IdManager<Ownable> getOwnableIdManager(){
        return ownableIdManager;
    }

    public void setTurn(int i){
        turn.set((double) i);
    }

}
