package oogasalad.sharedDependencies.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
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
  void testGetGoals() {
    assertEquals(1, gameLoader.getGoals().size());
  }

  @Test
  void testGetPlayers() {
    assertEquals(2, gameLoader.getPlayers().size());
    //assert that all players are type Player and are not null and are unique
    for (Player player : gameLoader.getPlayers()) {
      assertNotNull(player);
    }
    Set<Player> playerSet = new HashSet<>(gameLoader.getPlayers());
    assertEquals(gameLoader.getPlayers().size(), playerSet.size());
  }

  @Test
  void testGetOwnableIdManager() {
    assertNotNull(gameLoader.getOwnableIdManager());
    int numDropZones = 9+2;
    int numPieces = 5*2;
    ArrayList<String> ids = new ArrayList<>(gameLoader.getOwnableIdManager().getSimpleIds().keySet());
    Set expectedIds = new HashSet<>(Arrays.asList("pieceX0", "pieceX1", "XDropZone", "pieceO0", "pieceX2", "pieceX3", "pieceX4", "0,0", "pieceO1", "0,1", "1,0", "pieceO2", "0,2", "1,1", "2,0", "pieceO3", "1,2", "2,1", "pieceO4", "2,2", "ODropZone"));
    assertEquals(expectedIds, new HashSet<>(ids));
    assertEquals(numDropZones+numPieces, gameLoader.getOwnableIdManager().getSimpleIds().size());
    ArrayList<Ownable> ownables = new ArrayList<>();
    Iterator<Entry<String, Ownable>> iterator = gameLoader.getOwnableIdManager().iterator();
    while (iterator.hasNext()) {
      ownables.add(iterator.next().getValue());
    }
    assertEquals(numDropZones+numPieces, ownables.size());
    for(Ownable ownable : ownables) {
      assertNotNull(ownable);
      String id = gameLoader.getOwnableIdManager().getId(ownable);
      //since none of the pieces own any other pieces, they should all be in the simpleIds map
      assertTrue(gameLoader.getOwnableIdManager().getSimpleIds().containsKey(id));
    }
  }

  @Test
  void testGetGameWorld() {
    assertNotNull(gameLoader.getGameWorld());
    ArrayList<Ownable> ownables = new ArrayList<>();
    Iterator<Entry<String, Ownable>> iterator = gameLoader.getOwnableIdManager().iterator();
    while (iterator.hasNext()) {
      ownables.add(iterator.next().getValue());
    }
    int numDropZones = 9+2;
    int numPieces = 5*2;
    assertEquals(numDropZones+numPieces, ownables.size());
    HashSet<Ownable> gameWorldObjs = new HashSet<>();
    HashSet<Ownable> playerObjs = new HashSet<>();
    for(Ownable ownable : ownables) {
      assertNotNull(ownable);
      if(ownable.getOwner() instanceof Player) {
        playerObjs.add(ownable);
      }
      else {
        gameWorldObjs.add(ownable);
      }
    }
    assertEquals(numDropZones, gameWorldObjs.size());
    assertEquals(numPieces, playerObjs.size());
  }

  @Test
  void testGetFSM() {
    assertNotNull(gameLoader.getFSM());
    assertEquals(4, gameLoader.getFSM().getStates().size());
    Set expectedStates = new HashSet<>(Arrays.asList("INIT", "DONE", "SELECTPIECE", "SELECTZONE"));
    assertEquals(expectedStates, new HashSet<>(gameLoader.getFSM().getStates()));
    try {
      gameLoader.getFSM().setState("INIT"); //FIXME fails seemingly because id manager has not been linked
      assertEquals("INIT", gameLoader.getFSM().getCurrentState());
      gameLoader.getFSM().transition();
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void testGetPieceLocations() {
    ArrayList<DropZone> dropZones = new ArrayList<>();
    ArrayList<GameObject> pieces = new ArrayList<>();
    Iterator<Entry<String, Ownable>> iterator = gameLoader.getOwnableIdManager().iterator();
    while (iterator.hasNext()) {
      Ownable ownable = iterator.next().getValue();
      if(ownable instanceof DropZone) {
        dropZones.add((DropZone) ownable);
      }
      else {
        pieces.add((GameObject) ownable);
      }
    }
    assertEquals(11, dropZones.size());
    //check that the contents of each drop zone is correct
    for(DropZone dropZone : dropZones) {
      //check that it is owned by the game world
      assertEquals(gameLoader.getGameWorld(), dropZone.getOwner());
      //check that its contents are correct
      dropZone.getAllObjects().forEach(gameObject -> {
        assertTrue(pieces.contains(gameObject));
      });
      assertTrue(dropZone.getAllObjects().size() == 5 || dropZone.getAllObjects().size() == 0);
    }
  }
}