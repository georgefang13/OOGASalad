package oogasalad.sharedDependencies.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectFactoryTest {

  private IdManager<Ownable> idManager;
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
    constructorParams.put("value", 64);
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
    constructorParams.put("value", 64);
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
    constructorParams.put("value", 64);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    params.put(ObjectParameter.ID, "myId");
    params.put(ObjectParameter.OWNER, "2");
    game.sendObject(type, params);
    game.sendObject(type, params);
    params.put(ObjectParameter.PARENT_OWNABLE_ID, "myId");
    game.sendObject(type, params);
    Map<ObjectParameter, Object> updateParams = new HashMap<>();
    Map<Object, Object> updateConstructorParams = new HashMap<>();
    updateConstructorParams.put("value", 30);
    updateParams.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    updateParams.put(ObjectParameter.ID, "updatedId");
    updateParams.put(ObjectParameter.OWNER, "3");
    updateParams.put(ObjectParameter.PARENT_OWNABLE_ID, "myId2");
    game.updateObjectProperties("myId3", type, params);
  }

  @Test
  public void testBoardCreator() {
    //TODO

  }

}
