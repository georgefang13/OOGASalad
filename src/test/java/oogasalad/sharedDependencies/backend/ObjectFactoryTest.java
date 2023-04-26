package oogasalad.sharedDependencies.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectFactoryTest {

  private IdManager idManager;
  private ArrayList<Player> players;
  private GameWorld world;
  private GameInator game;

  @BeforeEach
  void setup() {
    game = new GameInator();
    world = game.getGameWorld();
    players = (ArrayList<Player>) game.getPlayers();
    game.addPlayer(new Player());
    game.addPlayer(new Player());
    game.addPlayer(new Player());
    idManager = game.getOwnableIdManager();
  }

  @Test
  public void testCreateVariables() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    // Variable with nothing
    game.sendObject(type, params);
    // Variable with Constructor Value
    constructorParams.put(ObjectParameter.VALUE, 64);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    game.sendObject(type, params);
    // Variable with ID
    params.put(ObjectParameter.ID, "myId");
    game.sendObject(type, params);
    // Variable with owner
    params.put(ObjectParameter.OWNER, "2");
    game.sendObject(type, params);
    // Variable with parent ownable
    params.put(ObjectParameter.PARENT_OWNABLE_ID, "myId");
    game.sendObject(type, params);
    assertEquals(5, idManager.getSimpleIds().size());
  }

  @Test
  public void testCreateGameObjects() {
    // GameObject
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "GameObject");
    Map<Object, Object> constructorParams = new HashMap<>();
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    // GameObject with nothing
    game.sendObject(type, params);
    // GameObject with ID
    params.put(ObjectParameter.ID, "myIdGameObject");
    game.sendObject(type, params);
    // GameObject with owner
    params.put(ObjectParameter.OWNER, "3");
    game.sendObject(type, params);
    // GameObject with parent ownable
    params.put(ObjectParameter.PARENT_OWNABLE_ID, "myIdGameObject");
    game.sendObject(type, params);
    assertEquals(4, idManager.getSimpleIds().size());
  }

  @Test
  public void testDeleteObject() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.VALUE, 64);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    params.put(ObjectParameter.ID, "myId");
    params.put(ObjectParameter.OWNER, "2");
    game.sendObject(type, params);
    assertEquals(1, idManager.getSimpleIds().size());
    game.deleteObject(type, "myId");
    assertEquals(0, idManager.getSimpleIds().size());
  }

  @Test
  public void testUpdateObjectProperties() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.VALUE, 64);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    params.put(ObjectParameter.ID, "myId");
    params.put(ObjectParameter.OWNER, "1");
    game.sendObject(type, params);
    game.sendObject(type, params);
    Map<ObjectParameter, Object> updateParams = new HashMap<>();
    Map<Object, Object> updateConstructorParams = new HashMap<>();
    updateConstructorParams.put(ObjectParameter.VALUE, 30);
    updateParams.put(ObjectParameter.CONSTRUCTOR_ARGS, updateConstructorParams);
    updateParams.put(ObjectParameter.ID, "updatedId");
    updateParams.put(ObjectParameter.OWNER, "2");
    updateParams.put(ObjectParameter.PARENT_OWNABLE_ID, "myId2");
    game.updateObjectProperties("myId", type, updateParams);
    Variable var = (Variable) game.getOwnable("updatedId");
    assertEquals(30, var.get());
    assertEquals(players.get(2-1), var.getOwner());
    updateParams.replace(ObjectParameter.OWNER, "GameWorld");
    updateParams.remove(ObjectParameter.ID);
    updateParams.remove(ObjectParameter.PARENT_OWNABLE_ID);
    updateParams.replace(ObjectParameter.CONSTRUCTOR_ARGS, new HashMap<>());
    game.updateObjectProperties("updatedId", type, updateParams);
    assertEquals(world, game.getOwnable("updatedId").getOwner());
  }

  @Test
  public void testBoardCreator() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "BoardCreator");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.BOARD_TYPE, "createSquareLoop");
    constructorParams.put(ObjectParameter.BOARD_ROWS, "3");
    constructorParams.put(ObjectParameter.BOARD_COLS, "3");
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    //params.put(ObjectParameter.ID, "myIdBoard");
    game.sendObject(type, params);
  }

  @Test
  public void testBoardCreatorMultiple() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "BoardCreator");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.BOARD_TYPE, "createSquareLoop");
    constructorParams.put(ObjectParameter.BOARD_ROWS, "3");
    constructorParams.put(ObjectParameter.BOARD_COLS, "3");
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    //params.put(ObjectParameter.ID, "myIdBoard");
    game.sendObject(type, params);
    game.sendObject(type, params);
    assertEquals(2*8, idManager.getSimpleIds().size());
    //print out the id of all the things in id manager
//    Iterator<Entry<String, Ownable>> it = idManager.iterator();
//    ArrayList<String> ids = new ArrayList<>();
//    while (it.hasNext()) {
//      Entry<String, Ownable> entry = it.next();
//      ids.add(entry.getKey());
//    }
//    //sort the ids
//    Collections.sort(ids);
//    //print out the ids
//    for(String id : ids) {
//      System.out.println(id);
//    }
  }
}
