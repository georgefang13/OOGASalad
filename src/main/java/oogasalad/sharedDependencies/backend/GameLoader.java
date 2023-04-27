package oogasalad.sharedDependencies.backend;

import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.fsm.ProgrammableState;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.id.IdManager;
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
 * Loads necessary information to create a Game from configuration files
 *
 * @author Michael Bryant
 * @author Ethan Horowitz
 */
public class GameLoader {

    private final RuleManager rules = new RuleManager();
    private final List<Goal> goals = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private final IdManager<Ownable> ownableIdManager = new IdManager<>();
    private final GameWorld gameWorld = new GameWorld();
    private final FSM<String> fsm;
    private final Interpreter interpreter;
    private final Map<Ownable, DropZone> pieceLocations = new HashMap<>();

    public GameLoader(String directory) {
        fsm = new FSM<>(ownableIdManager);
        interpreter = new Interpreter();
        interpreter.linkIdManager(ownableIdManager);
        try {
            loadGame(directory);
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Access list of goals in configuration files
     * @return Unmodifiable list containing goals found in configuration files
     */
    public List<Goal> getGoals(){
        return Collections.unmodifiableList(goals);
    }

    /**
     * Access list of players in configuration files
     * @return Unmodifiable list containing players found in configuration files
     */
    public List<Player> getPlayers(){
        return Collections.unmodifiableList(players);
    }

    /**
     * Access IdManager associated with Ownables from configuration files
     * @return specific IdManager<Ownable> instance
     */
    public IdManager<Ownable> getOwnableIdManager(){
        return ownableIdManager;
    }

    /**
     * Access game world generated from configuration files
     * @return GameWorld instance made from configuration files
     */
    public GameWorld getGameWorld(){
        return gameWorld;
    }

    /**
     * Get the finite state generated from configuration files
     * @return FSM
     */
    public FSM<String> getFSM(){
        return fsm;
    }

    /**
     * Access map specifying which DropZone each object should start in
     * @return Unmodifiable map containing ownables and associated locations
     */
    public Map<Ownable, DropZone> getPieceLocations(){
        return Collections.unmodifiableMap(pieceLocations);
    }

    private void loadGame(String directory) throws FileNotFoundException, ClassNotFoundException {
        pieceLocations.clear();

        loadPlayers(directory + "/general.json");
        loadFSM(directory + "/fsm.json");
        loadDropZones(directory + "/layout.json");
        loadObjectsAndVariables(directory);
        loadRules(directory + "/rules.json");
    }

    private void loadPlayers(String file) throws FileNotFoundException {
        FileManager fm = new FileManager(file);
        int max = Integer.parseInt(fm.getString("players", "max"));
        for (int i = 0; i < max; i++) {
            players.add(new Player());
        }
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
            //assume all dropzones are owned by the game world
            DropZone dz = new DropZone(gameWorld);
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
                ownableIdManager.setOwner(obj, mainObj);
            }
        }
    }

    private Map<String, List<String>> loadGameObjects(String file) throws FileNotFoundException {

        FileManager fm = new FileManager(file);

        Map<String, List<String>> ownMap = new HashMap<>();

        for (String id : fm.getTagsAtLevel()){
            String owner = fm.getString(id, "owner");
            String location = fm.getString(id, "location");
            List<String> owns = StreamSupport.stream(fm.getArray(id, "owns").spliterator(), false).toList();

            Owner own = gameWorld;
            if (!owner.isEmpty()){
                own = players.get(Integer.parseInt(owner));
            }

            GameObject obj = new GameObject(own);

            for (String cls : fm.getArray(id, "classes")){
                obj.addClass(cls);
            }

            ownableIdManager.addObject(obj, id);

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
                ownableIdManager.setOwner(var, owner);
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

    private void putInDropZone(Ownable element, DropZone dropZone, String name){
        if (pieceLocations.containsKey(element)){
            DropZone dz = pieceLocations.get(element);
            dz.removeObject(dz.getKey(element));
        }
        pieceLocations.put(element, dropZone);
        dropZone.putObject(name, element);
    }

}
