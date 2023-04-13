package oogasalad.sharedDependencies.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectFactoryTest {

  private ObjectFactory factory;
  private IdManager<Ownable> idManager;
  private ArrayList<Player> players;
  private GameWorld world;
  @BeforeEach
  void setup() {
    world = new GameWorld();
    idManager = new IdManager<>();
    players = new ArrayList<>();
    for(int i = 0; i < 5; i++) {
      players.add(new Player());
    }
    factory = new ObjectFactory(world, idManager, players);
  }

  @Test
  public void testCreateOwnables() {
    Map<ObjectParameter, String> params = Map.of(ObjectParameter.OWNABLE_TYPE, "Variable");
    factory.createOwnable(params);
    //check that the ownable was created by looking at the idManager
    assertEquals(1, idManager.getSimpleIds().size());
    params = Map.of(ObjectParameter.OWNABLE_TYPE, "GameObject", ObjectParameter.OWNER, "Player1");
    factory.createOwnable(params);
    assertEquals(2, idManager.getSimpleIds().size());
    Ownable ownable = idManager.getObject("GameObject");
    //check that the owner is type Player
    assertEquals(Player.class, ownable.getOwner().getClass());
    params = Map.of(ObjectParameter.OWNABLE_TYPE, "GameObject", ObjectParameter.OWNER, "");
    factory.createOwnable(params);
    assertEquals(3, idManager.getSimpleIds().size());
    ownable = idManager.getObject("GameObject2");
    //check that the owner is type GameWorld
    assertEquals(GameWorld.class, ownable.getOwner().getClass());
  }

  @Test
  public void testCreateWithId() {
    Map<ObjectParameter, String> params = Map.of(ObjectParameter.OWNABLE_TYPE, "Variable", ObjectParameter.ID, "test");
    factory.createOwnable(params);
    assertEquals(1, idManager.getSimpleIds().size());
    Ownable ownable = idManager.getObject("test");
    //check that the ownable is not null
    assertEquals(Variable.class, ownable.getClass());
    params = Map.of(ObjectParameter.OWNABLE_TYPE, "GameObject", ObjectParameter.ID, "test2");
    factory.createOwnable(params);
    assertEquals(2, idManager.getSimpleIds().size());
    ownable = idManager.getObject("test2");
    //check that the ownable is not null
    assertEquals(GameObject.class, ownable.getClass());
  }

  @Test
  public void testParentOwnable() {
    Map<ObjectParameter, String> params = Map.of(ObjectParameter.OWNABLE_TYPE, "GameObject", ObjectParameter.ID, "parent");
    factory.createOwnable(params);
    assertEquals(1, idManager.getSimpleIds().size());
    params = Map.of(ObjectParameter.OWNABLE_TYPE, "Variable", ObjectParameter.PARENT_OWNABLE, "parent");
    factory.createOwnable(params);
    assertEquals(2, idManager.getSimpleIds().size());
    Ownable ownable = idManager.getObject("Variable");
    //check that the ownable is not null
    assertEquals(Variable.class, ownable.getClass());
    //check that child.getId() contains parent.getId()
    assertEquals("parent.Variable", idManager.getId(ownable));
  }

  @Test
  public void testBoardCreator() {
    Map<ObjectParameter, String> params = Map.of(ObjectParameter.OWNABLE_TYPE, "BoardCreator", ObjectParameter.BOARD_CREATOR_TYPE, "createGrid", ObjectParameter.BOARD_CREATOR_PARAM_1, "5", ObjectParameter.BOARD_CREATOR_PARAM_2, "5");
    factory.createOwnable(params);
    assertEquals(25, idManager.getSimpleIds().size());
    params = Map.of(ObjectParameter.OWNABLE_TYPE, "BoardCreator", ObjectParameter.BOARD_CREATOR_TYPE, "createSquareLoop", ObjectParameter.BOARD_CREATOR_PARAM_1, "5", ObjectParameter.BOARD_CREATOR_PARAM_2, "2");
    factory.createOwnable(params);
    assertEquals(25+10, idManager.getSimpleIds().size());
    System.out.println(idManager.getSimpleIds());
//    params = Map.of(ObjectParameter.OWNABLE_TYPE, "BoardCreator", ObjectParameter.BOARD_CREATOR_TYPE, "create1DLoop", ObjectParameter.BOARD_CREATOR_PARAM_1, "5");
//    factory.createOwnable(params);
//    assertEquals(25+10+5, idManager.getSimpleIds().size());

  }

}
