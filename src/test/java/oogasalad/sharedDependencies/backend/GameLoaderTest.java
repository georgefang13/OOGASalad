package oogasalad.sharedDependencies.backend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import oogasalad.gamerunner.backend.fsm.FSM;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.gamerunner.backend.interpreter.TestGame;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLoaderTest {
  private GameLoader gameLoader;

  @BeforeEach
  void setUp() {
    gameLoader = new GameLoader("data/games/tictactoe");
  }

  @Test
  void testGetGoals() throws FileNotFoundException {
    assertEquals(1, gameLoader.loadGoals().size());
  }

  @Test
  void testGetPlayers() throws FileNotFoundException {
    List<Player> players = gameLoader.loadPlayers();
    assertEquals(2, players.size());
    //assert that all players are type Player and are not null and are unique
    for (Player player : players) {
      assertNotNull(player);
    }
    Set<Player> playerSet = new HashSet<>(players);
    assertEquals(players.size(), playerSet.size());
  }

  @Test
  void testGetFSM() throws FileNotFoundException {
    IdManager<Ownable> idManager = new IdManager<>();
    Interpreter interpreter = new Interpreter();
    interpreter.linkIdManager(idManager);
    TestGame game = new TestGame();
    interpreter.linkGame(game);

    FSM<String> testFSM = new FSM<>(idManager);
    gameLoader.loadFSM(interpreter, testFSM);

    assertNotNull(testFSM);
    assertEquals(4, testFSM.getStates().size());
    Set expectedStates = new HashSet<>(Arrays.asList("INIT", "DONE", "SELECTPIECE", "SELECTZONE"));
    assertEquals(expectedStates, new HashSet<>(testFSM.getStates()));
  }

  @Test
  void testGetPieceLocations() throws FileNotFoundException, ClassNotFoundException {
    IdManager<Ownable> idManager = new IdManager<>();
    Interpreter interpreter = new Interpreter();
    interpreter.linkIdManager(idManager);
    interpreter.linkGame(new TestGame());
    GameWorld gw = new GameWorld();
    List<Player> players = gameLoader.loadPlayers();

    gameLoader.loadDropZones(idManager, gw);

    gameLoader.loadObjectsAndVariables(idManager, players, gw);

    Variable testVar = (Variable) idManager.getObject("testvar");
    assertEquals(1500, testVar.get());
    assertTrue(testVar.usesClass("money"));

    ArrayList<DropZone> dropZones = new ArrayList<>();
    ArrayList<GameObject> pieces = new ArrayList<>();
    Iterator<Entry<String, Ownable>> iterator = idManager.iterator();
    while (iterator.hasNext()) {
      Ownable ownable = iterator.next().getValue();
      if(ownable instanceof DropZone) {
        dropZones.add((DropZone) ownable);
      }
      else if (ownable instanceof GameObject) {
        pieces.add((GameObject) ownable);
      }
    }
    assertEquals(11, dropZones.size());
    //check that the contents of each drop zone is correct
    for(DropZone dropZone : dropZones) {
      //check that it is owned by the game world
      assertEquals(gw, dropZone.getOwner());
      //check that its contents are correct
      dropZone.getAllObjects().forEach(gameObject -> {
        assertTrue(pieces.contains(gameObject));
      });
      assertTrue(dropZone.getAllObjects().size() == 5 || dropZone.getAllObjects().size() == 0);
    }
  }
}