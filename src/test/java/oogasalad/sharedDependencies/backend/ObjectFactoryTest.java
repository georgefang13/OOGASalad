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
  public void testCreateOwnables() {
    // Variable
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put("value", 64);
    params.put(ObjectParameter.ID, "myId");
    params.put(ObjectParameter.OWNER, "2");
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
//    params.put(ObjectParameter.PARENT_OWNABLE_ID, constructorParams);
    game.sendObject(type, params);
    assertEquals(1, idManager.getSimpleIds().size());

    // GameObject
    ObjectType type2 = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params2 = new HashMap<>();
    params2.put(ObjectParameter.OWNABLE_TYPE, "GameObject");
    Map<Object, Object> constructorParams2 = new HashMap<>();
//    constructorParams2.put("value", 64);
    params2.put(ObjectParameter.ID, "myId2");
    params2.put(ObjectParameter.OWNER, "3");
    params2.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams2);
//    params2.put(ObjectParameter.PARENT_OWNABLE_ID, constructorParams);
    game.sendObject(type2, params2);
    assertEquals(2, idManager.getSimpleIds().size());
  }

  @Test
  public void testCreateWithId() {

  }

  @Test
  public void testParentOwnable() {

  }

  @Test
  public void testBoardCreator() {
    //TODO

  }

}
