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

    private final String directory;

    public GameLoader(String directory) {
        this.directory = directory;
    }

    /**
     * load players from configuration file
     * @throws FileNotFoundException if file is not found
     */
    public List<Player> loadPlayers() throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/general.json");
        int max = Integer.parseInt(fm.getString("players", "max"));
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            players.add(new Player());
        }
        return players;
    }

    /**
     * Get the finite state generated from configuration files
     */
    public void loadFSM(Interpreter interpreter, FSM<String> fsm) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/fsm.json");

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

    }

    /**
     * Access list of goals in configuration files
     * @return list containing goals found in configuration files
     */
    public List<Goal> loadGoals() throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/fsm.json");
        List<String> goalStrs = StreamSupport.stream(fm.getArray("goals").spliterator(), false).toList();
        List<Goal> goals = new ArrayList<>();
        for (String g : goalStrs){
            Goal goal = new Goal();
            goal.addInstruction(g);
            goals.add(goal);
        }
        return goals;
    }

    public void loadDropZones(IdManager<Ownable> idManager, GameWorld gw) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/layout.json");

        Map<DropZone, List<String[]>> edgeMap = new HashMap<>();
        Map<String, DropZone> zones = new HashMap<>();

        for (String id : fm.getTagsAtLevel()){
            //assume all dropzones are owned by the game world
            DropZone dz = new DropZone(gw);
            for (String cls : fm.getArray(id, "classes")){
                dz.addClass(cls);
            }

            List<String[]> edges = new ArrayList<>();
            for (String edgeName : fm.getTagsAtLevel(id, "connections")){
                String edge = fm.getString(id, "connections", edgeName);
                edges.add(new String[]{edgeName, edge});
            }
            edgeMap.put(dz, edges);

            idManager.addObject(dz, id);
            zones.put(id, dz);
        }

        for (DropZone dz : edgeMap.keySet()){
            // [ [ edgeName, edge ] ]
            for (String[] edge : edgeMap.get(dz)){
                DropZone other = zones.get(edge[1]);
                dz.addOutgoingConnection(other, edge[0]);
            }
        }
    }

    /**
     * Loads GameObjects and variables into the idManager given, setting owners as players or the GameWorld
     * @param idManager idManager to load into
     * @param players players to set as owners
     * @param gameWorld gameWorld to set as owner
     * @throws FileNotFoundException if file is not found
     * @throws ClassNotFoundException if class of variable is not found
     */
    public Map<Ownable, DropZone> loadObjectsAndVariables(IdManager<Ownable> idManager, List<Player> players, GameWorld gameWorld) throws FileNotFoundException, ClassNotFoundException {
        Map<Ownable, DropZone> pieceLocations = new HashMap<>();

        Map<String, List<String>> ownMap = loadGameObjects(idManager, players, gameWorld, pieceLocations);
        loadVariables(idManager, players, gameWorld);
        for (String s : ownMap.keySet()) {
            GameObject mainObj = (GameObject) idManager.getObject(s);
            for (String o : ownMap.get(s)) {
                Ownable obj = idManager.getObject(o);
                idManager.setObjectOwner(obj, mainObj);
            }
        }
        return pieceLocations;
    }

    private Map<String, List<String>> loadGameObjects(IdManager<Ownable> idManager, List<Player> players, GameWorld gameWorld, Map<Ownable, DropZone> pieceLocations) throws FileNotFoundException {

        FileManager fm = new FileManager(directory + "/objects.json");

        Map<String, List<String>> ownMap = new HashMap<>();

        for (String id : fm.getTagsAtLevel()){
            String owner = fm.getString(id, "owner");
            String location = fm.getString(id, "location");
            List<String> owns = StreamSupport.stream(fm.getArray(id, "owns").spliterator(), false).toList();

            Owner own = gameWorld;
            if (!owner.isEmpty() && Integer.parseInt(owner) != -1){
                int playerNum = Integer.parseInt(owner);
                if (playerNum >= players.size()){
                    continue;
                }
                own = players.get(playerNum);
            }

            GameObject obj = new GameObject(own);
            fm.getArray(id, "classes").forEach(obj::addClass);

            //reassign id to everything after the "." if it has one
            if (id.contains(".")){
                id = id.substring(id.indexOf(".") + 1);
            }

            idManager.addObject(obj, id);
            DropZone dz = (DropZone) idManager.getObject(location);
            putInDropZone(obj, dz, id, pieceLocations);
            ownMap.put(id, owns);
        }

        return ownMap;
    }

    private void loadVariables(IdManager<Ownable> idManager, List<Player> players, GameWorld gameWorld) throws FileNotFoundException, ClassNotFoundException {
        FileManager fm = new FileManager(directory + "/variables.json");

        for (String id : fm.getTagsAtLevel()){
            String ownerStr = fm.getString(id, "owner");
            int ownerNum;
            if (ownerStr.isEmpty()) ownerNum = -1;
            else ownerNum = Integer.parseInt(ownerStr);

            if (ownerNum >= players.size()){
                continue;
            }

            String type = fm.getString(id, "type");
            if(type.equals("null")) {
                type = "java.lang.Object";
            }
            Class<?> clazz = Class.forName(type);
            Object obj = fm.getObject(clazz, id, "value");

            Variable<Object> var;

            Owner owner = ownerNum != -1 ? players.get(ownerNum) : gameWorld;
            var = new Variable<>(obj, owner);

            for (String cls : fm.getArray(id, "classes")){
                var.addClass(cls);
            }

            //reassign id to everything after the "." if it has one
            if (id.contains(".")){
                id = id.substring(id.indexOf(".") + 1);
            }

            idManager.addObject(var, id);
        }
    }

    public RuleManager loadRules() throws FileNotFoundException{
        RuleManager rules = new RuleManager();
        FileManager fm = new FileManager(directory + "/rules.json");
        for (String id : fm.getTagsAtLevel()){
            for (String ruleName : fm.getTagsAtLevel(id)){
                String rule = fm.getString(id, ruleName);
                rules.addRule(id, ruleName, rule);
            }
        }
        return rules;
    }

    private void putInDropZone(Ownable element, DropZone dropZone, String name, Map<Ownable, DropZone> pieceLocations){
        if (pieceLocations.containsKey(element)){
            DropZone dz = pieceLocations.get(element);
            dz.removeObject(dz.getKey(element));
        }
        pieceLocations.put(element, dropZone);
        dropZone.putObject(name, element);
    }

}
