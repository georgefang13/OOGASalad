package oogasalad.gamerunner.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.EmptyRule;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
  public GameInator game;
  public IdManager<Ownable> ownableIdManager;
  public IdManager<Rule> ruleIdManager;
  public IdManager<Goal> goalIdManager;
  public IdManager<Player> playerIdManager;
  public GameWorld gameworld;

  @BeforeEach
  public void setUp() {
    game = new GameInator();
    ownableIdManager = new IdManager<>();
    ruleIdManager = new IdManager<>();
    goalIdManager = new IdManager<>();
    playerIdManager = new IdManager<>();
    gameworld = new GameWorld();
  }

  @Test
  public void addPlayer() {
    assertTrue(game.getPlayers().isEmpty());
    game.addPlayer(new Player());
    assertEquals(1, game.getPlayers().size());
    game.addPlayer(new Player());
    assertEquals(2, game.getPlayers().size());
  }

  @Test
  public void getPlayers() {
    assertTrue(game.getPlayers().isEmpty());
    Player player = new Player();
    game.addPlayer(player);
    assertEquals(player, game.getPlayers().get(0));
  }

  @Test
  public void addRule() {
    assertTrue(game.getRules().isEmpty());
    game.addRule(new EmptyRule());
    assertEquals(1, game.getRules().size());
    game.addRule(new EmptyRule());
    assertEquals(2, game.getRules().size());
  }

  @Test
  public void removeRule() {
    assertTrue(game.getRules().isEmpty());
    game.addRule(new EmptyRule());
    assertEquals(1, game.getRules().size());
    game.removeRule(game.getRules().get(0));
    assertTrue(game.getRules().isEmpty());
  }

  @Test
  public void getRules() {
    assertTrue(game.getRules().isEmpty());
    EmptyRule rule = new EmptyRule();
    game.addRule(rule);
    assertEquals(rule, game.getRules().get(0));
  }

  @Test
  public void addGoal() {
    assertTrue(game.getGoals().isEmpty());
    game.addGoal(new Goal());
    assertEquals(1, game.getGoals().size());
    game.addGoal(new Goal());
    assertEquals(2, game.getGoals().size());
  }

  @Test
  public void removeGoal() {
    assertTrue(game.getGoals().isEmpty());
    game.addGoal(new Goal());
    assertEquals(1, game.getGoals().size());
    game.removeGoal(game.getGoals().get(0));
    assertTrue(game.getGoals().isEmpty());
  }

  @Test
  public void getGoals() {
    assertTrue(game.getGoals().isEmpty());
    Goal goal = new Goal();
    game.addGoal(goal);
    assertEquals(goal, game.getGoals().get(0));
  }

  @Test
  public void getGameWorld() {
    assertInstanceOf(GameWorld.class, game.getGameWorld());
  }

  @Test
  public void changeOwner() {
    assertNull(game.getOwnable("test"));
    Player player1 = new Player();
    Player player2 = new Player();
    game.addPlayer(player1);
    game.addPlayer(player2);
    Map<ObjectParameter, String> params = Map.of(ObjectParameter.OWNABLE_TYPE, "GameObject", ObjectParameter.ID, "test", ObjectParameter.OWNER, "Player1");
    game.sendObject(ObjectType.OWNABLE, params);
    assertEquals(player1, game.getOwnable("test").getOwner());
    assertNotEquals(player2, game.getOwnable("test").getOwner());
    game.changeOwner(player2, game.getOwnable("test"));
    assertEquals(player2, game.getOwnable("test").getOwner());
    assertNotEquals(player1, game.getOwnable("test").getOwner());
  }
//
//  @Test
//  public void createOwnable() {
//    Player player = new Player();
//    game.addPlayer(player);
//
//    assertNull(game.getOwnable("EmptyGameObject"));
//    game.sendObject("GameObject", "owner=Player;parentOwnable=NULL"); //TODO CHANGE ; OR MAKE MAP
//    assertNotNull(game.getOwnable("EmptyGameObject"));
//    assertEquals(player, game.getOwnable("EmptyGameObject").getOwner());
//
//    assertNull(game.getOwnable("EmptyGameObject2"));
//    game.sendObject("GameObject", "owner=NULL;parentOwnable=NULL");
//    assertNotNull(game.getOwnable("EmptyGameObject2"));
//    assertEquals(game.getGameWorld(), game.getOwnable("EmptyGameObject2").getOwner());
//
//    assertNull(game.getOwnable("PlayerPiece"));
//    game.sendObject("PlayerPiece", "owner=Player;parentOwnable=NULL");
//    assertNotNull(game.getOwnable("PlayerPiece"));
//    assertEquals(player, game.getOwnable("PlayerPiece").getOwner());
//
//    assertNull(game.getOwnable("PlayerPiece2"));
//    game.sendObject("PlayerPiece", "owner=NULL;parentOwnable=NULL");
//    assertNotNull(game.getOwnable("PlayerPiece2"));
//    assertEquals(game.getGameWorld(), game.getOwnable("PlayerPiece2").getOwner());
//  }
//
//  @Test
//  public void getOwner() {
//    assertNull(game.getOwnable("EmptyGameObject"));
//    Player player1 = new Player();
//    game.addPlayer(player1);
//    game.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
//    assertEquals(player1, game.getOwnable("EmptyGameObject").getOwner());
//  }
//
//  @Test
//  public void setOwner() {
//    assertNull(game.getOwnable("EmptyGameObject"));
//    Player player1 = new Player();
//    Player player2 = new Player();
//    game.addPlayer(player1);
//    game.addPlayer(player2);
//    game.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
//    game.setOwner("EmptyGameObject", player2);
//    assertNotEquals(player1, game.getOwnable("EmptyGameObject").getOwner());
//    assertEquals(player2, game.getOwnable("EmptyGameObject").getOwner());
//  }
//
//  @Test
//  public void getOwnable() {
//    assertNull(game.getOwnable("EmptyGameObject"));
//    Player player1 = new Player();
//    game.addPlayer(player1);
//    game.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
//    assertEquals("EmptyGameObject", game.getOwnable("EmptyGameObject").getDefaultId());
//  }
}