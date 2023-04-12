package oogasalad.sharedDependencies.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import oogasalad.gameeditor.backend.GameEditor;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
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
  public void testCreateOwnable() {
    Map<ObjectParameter, String> params = Map.of(ObjectParameter.OWNABLE_TYPE, "Variable");
    factory.createOwnable(params);
    //check that the ownable was created by looking at the idManager
    assertEquals(1, idManager.getSimpleIds().size());
  }

}
