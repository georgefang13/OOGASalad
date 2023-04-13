package oogasalad.gamerunner.backend;

import static org.junit.jupiter.api.Assertions.*;

import oogasalad.gameeditor.backend.GameInator;
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
  public GameInator gameEditor;
  public IdManager<Ownable> ownableIdManager;
  public IdManager<Rule> ruleIdManager;
  public IdManager<Goal> goalIdManager;
  public IdManager<Player> playerIdManager;
  public GameWorld gameworld;

  @BeforeEach
  public void setUp() {
    gameEditor = new GameInator();
    ownableIdManager = new IdManager<>();
    ruleIdManager = new IdManager<>();
    goalIdManager = new IdManager<>();
    playerIdManager = new IdManager<>();
    gameworld = new GameWorld();
  }

  @Test
  public void addPlayer() {
    assertTrue(gameEditor.getPlayers().isEmpty());
    gameEditor.addPlayer(new Player());
    assertEquals(1, gameEditor.getPlayers().size());
    gameEditor.addPlayer(new Player());
    assertEquals(2, gameEditor.getPlayers().size());
  }

  @Test
  public void addNPlayers() {
    assertTrue(gameEditor.getPlayers().isEmpty());
    gameEditor.addNPlayers(5);
    assertEquals(5, gameEditor.getPlayers().size());
    gameEditor.addNPlayers(7);
    assertEquals(12, gameEditor.getPlayers().size());
  }

  @Test
  public void removePlayer() {
    assertTrue(gameEditor.getPlayers().isEmpty());
    gameEditor.addNPlayers(5);
    assertEquals(5, gameEditor.getPlayers().size());
    gameEditor.removePlayer(gameEditor.getPlayers().get(0));
    assertEquals(4, gameEditor.getPlayers().size());
  }

  @Test
  public void removeAllPlayers() {
    assertTrue(gameEditor.getPlayers().isEmpty());
    gameEditor.addNPlayers(5);
    assertEquals(5, gameEditor.getPlayers().size());
    gameEditor.removeAllPlayers();
    assertTrue(gameEditor.getPlayers().isEmpty());
  }

  @Test
  public void getPlayers() {
    assertTrue(gameEditor.getPlayers().isEmpty());
    Player player = new Player();
    gameEditor.addPlayer(player);
    assertEquals(player, gameEditor.getPlayers().get(0));
  }

  @Test
  public void addRule() {
    assertTrue(gameEditor.getRules().isEmpty());
    gameEditor.addRule(new EmptyRule());
    assertEquals(1, gameEditor.getRules().size());
    gameEditor.addRule(new EmptyRule());
    assertEquals(2, gameEditor.getRules().size());
  }

  @Test
  public void removeRule() {
    assertTrue(gameEditor.getRules().isEmpty());
    gameEditor.addRule(new EmptyRule());
    assertEquals(1, gameEditor.getRules().size());
    gameEditor.removeRule(gameEditor.getRules().get(0));
    assertTrue(gameEditor.getRules().isEmpty());
  }

  @Test
  public void getRules() {
    assertTrue(gameEditor.getRules().isEmpty());
    EmptyRule rule = new EmptyRule();
    gameEditor.addRule(rule);
    assertEquals(rule, gameEditor.getRules().get(0));
  }

  @Test
  public void addGoal() {
    assertTrue(gameEditor.getGoals().isEmpty());
    gameEditor.addGoal(new Goal());
    assertEquals(1, gameEditor.getGoals().size());
    gameEditor.addGoal(new Goal());
    assertEquals(2, gameEditor.getGoals().size());
  }

  @Test
  public void removeGoal() {
    assertTrue(gameEditor.getGoals().isEmpty());
    gameEditor.addGoal(new Goal());
    assertEquals(1, gameEditor.getGoals().size());
    gameEditor.removeGoal(gameEditor.getGoals().get(0));
    assertTrue(gameEditor.getGoals().isEmpty());
  }

  @Test
  public void getGoals() {
    assertTrue(gameEditor.getGoals().isEmpty());
    Goal goal = new Goal();
    gameEditor.addGoal(goal);
    assertEquals(goal, gameEditor.getGoals().get(0));
  }

  @Test
  public void getGameWorld() {
    assertInstanceOf(GameWorld.class, gameEditor.getGameWorld());
  }

  @Test
  public void changeOwner() {
    assertNull(gameEditor.getOwnable("EmptyGameObject"));
    Player player1 = new Player();
    Player player2 = new Player();
    gameEditor.addPlayer(player1);
    gameEditor.addPlayer(player2);
    gameEditor.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
    assertEquals(player1, gameEditor.getOwnable("EmptyGameObject").getOwner());
    assertNotEquals(player2, gameEditor.getOwnable("EmptyGameObject").getOwner());
    gameEditor.changeOwner(player2, gameEditor.getOwnable("EmptyGameObject"));
    assertEquals(player2, gameEditor.getOwnable("EmptyGameObject").getOwner());
    assertNotEquals(player1, gameEditor.getOwnable("EmptyGameObject").getOwner());
  }

  @Test
  public void createOwnable() {
    Player player = new Player();
    gameEditor.addPlayer(player);

    assertNull(gameEditor.getOwnable("EmptyGameObject"));
    gameEditor.sendObject("GameObject", "owner=Player;parentOwnable=NULL"); //TODO CHANGE ; OR MAKE MAP
    assertNotNull(gameEditor.getOwnable("EmptyGameObject"));
    assertEquals(player, gameEditor.getOwnable("EmptyGameObject").getOwner());

    assertNull(gameEditor.getOwnable("EmptyGameObject2"));
    gameEditor.sendObject("GameObject", "owner=NULL;parentOwnable=NULL");
    assertNotNull(gameEditor.getOwnable("EmptyGameObject2"));
    assertEquals(gameEditor.getGameWorld(), gameEditor.getOwnable("EmptyGameObject2").getOwner());

    assertNull(gameEditor.getOwnable("PlayerPiece"));
    gameEditor.sendObject("PlayerPiece", "owner=Player;parentOwnable=NULL");
    assertNotNull(gameEditor.getOwnable("PlayerPiece"));
    assertEquals(player, gameEditor.getOwnable("PlayerPiece").getOwner());

    assertNull(gameEditor.getOwnable("PlayerPiece2"));
    gameEditor.sendObject("PlayerPiece", "owner=NULL;parentOwnable=NULL");
    assertNotNull(gameEditor.getOwnable("PlayerPiece2"));
    assertEquals(gameEditor.getGameWorld(), gameEditor.getOwnable("PlayerPiece2").getOwner());
  }

  @Test
  public void getOwner() {
    assertNull(gameEditor.getOwnable("EmptyGameObject"));
    Player player1 = new Player();
    gameEditor.addPlayer(player1);
    gameEditor.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
    assertEquals(player1, gameEditor.getOwnable("EmptyGameObject").getOwner());
  }

  @Test
  public void setOwner() {
    assertNull(gameEditor.getOwnable("EmptyGameObject"));
    Player player1 = new Player();
    Player player2 = new Player();
    gameEditor.addPlayer(player1);
    gameEditor.addPlayer(player2);
    gameEditor.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
    gameEditor.setOwner("EmptyGameObject", player2);
    assertNotEquals(player1, gameEditor.getOwnable("EmptyGameObject").getOwner());
    assertEquals(player2, gameEditor.getOwnable("EmptyGameObject").getOwner());
  }

  @Test
  public void getOwnable() {
    assertNull(gameEditor.getOwnable("EmptyGameObject"));
    Player player1 = new Player();
    gameEditor.addPlayer(player1);
    gameEditor.sendObject("GameObject", "owner=Player;parentOwnable=NULL");
    assertEquals("EmptyGameObject", gameEditor.getOwnable("EmptyGameObject").getDefaultId());
  }
}