package oogasalad.gamerunner.backend;

import static org.junit.jupiter.api.Assertions.*;

import oogasalad.gameeditor.backend.GameEditor;
import oogasalad.gameeditor.backend.goals.EmptyGoal;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.EmptyRule;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
  public GameEditor game;
  public IdManager<Ownable> ownableIdManager;
  public IdManager<Rule> ruleIdManager;
  public IdManager<Goal> goalIdManager;
  public IdManager<Player> playerIdManager;
  public GameWorld gameworld;

  @BeforeEach
  public void setUp() {
    game = new GameEditor();
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
  public void addNPlayers() {
    assertTrue(game.getPlayers().isEmpty());
    game.addNPlayers(5);
    assertEquals(5, game.getPlayers().size());
    game.addNPlayers(7);
    assertEquals(12, game.getPlayers().size());
  }

  @Test
  public void removePlayer() {
    assertTrue(game.getPlayers().isEmpty());
    game.addNPlayers(5);
    assertEquals(5, game.getPlayers().size());
    game.removePlayer();
    assertEquals(4, game.getPlayers().size());
  }

  @Test
  public void removeAllPlayers() {
    assertTrue(game.getPlayers().isEmpty());
    game.addNPlayers(5);
    assertEquals(5, game.getPlayers().size());
    game.removeAllPlayers();
    assertTrue(game.getPlayers().isEmpty());
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
    assertTrue(game.getRuleIdManager().isEmpty());
    game.addRule(new EmptyRule());
    assertEquals(1, game.getRuleIdManager().size());
    game.addRule(new EmptyRule());
    assertEquals(2, game.getRuleIdManager().size());
  }

  @Test
  public void removeRule() {
    assertTrue(game.getRuleIdManager().isEmpty());
    game.addRule(new EmptyRule());
    assertEquals(1, game.getRuleIdManager().size());
    game.removeRule(game.getRuleIdManager().get(0));
    assertTrue(game.getRuleIdManager().isEmpty());
  }

  @Test
  public void getRules() {
    assertTrue(game.getRuleIdManager().isEmpty());
    EmptyRule rule = new EmptyRule();
    game.addRule(rule);
    assertEquals(rule, game.getRuleIdManager().get(0));
  }

  @Test
  public void addGoal() {
    assertTrue(game.getGoalIdManager().isEmpty());
    game.addGoal(new EmptyGoal());
    assertEquals(1, game.getGoalIdManager().size());
    game.addGoal(new EmptyGoal());
    assertEquals(2, game.getGoalIdManager().size());
  }

  @Test
  public void removeGoal() {
    assertTrue(game.getGoalIdManager().isEmpty());
    game.addGoal(new EmptyGoal());
    assertEquals(1, game.getGoalIdManager().size());
    game.removeGoal(game.getGoalIdManager().get(0));
    assertTrue(game.getGoalIdManager().isEmpty());
  }

  @Test
  public void getGoals() {
    assertTrue(game.getGoalIdManager().isEmpty());
    EmptyGoal goal = new EmptyGoal();
    game.addGoal(goal);
    assertEquals(goal, game.getGoalIdManager().get(0));
  }

  @Test
  public void getGameWorld() {
    assertInstanceOf(GameWorld.class, game.getGameWorld());
  }

//  @Test
//  public void changeOwner() {
//    assertNull(game.getOwnable("EmptyGameObject"));
//    Player player1 = new Player();
//    Player player2 = new Player();
//    game.addPlayer(player1);
//    game.addPlayer(player2);
//    game.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
//    assertEquals(player1, game.getOwnable("EmptyGameObject").getOwner());
//    assertNotEquals(player2, game.getOwnable("EmptyGameObject").getOwner());
//    game.changeOwner(player2, game.getOwnable("EmptyGameObject"));
//    assertEquals(player2, game.getOwnable("EmptyGameObject").getOwner());
//    assertNotEquals(player1, game.getOwnable("EmptyGameObject").getOwner());
//  }
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