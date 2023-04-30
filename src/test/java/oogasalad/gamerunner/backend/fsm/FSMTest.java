package oogasalad.gamerunner.backend.fsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.gamerunner.backend.interpreter.TestGame;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasicState extends State {

  Object value;

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public void setInnerValue(FSM.StateData data, Object value) {
    this.value = value;
  }
}

class InitState extends State {

  @Override
  public void onEnter(FSM.StateData data) {
    IdManager idManager = (IdManager) data.get("idManager");
    Variable<String> var = new Variable<>("hello");
    idManager.addObject(var, "testvar");
  }

  @Override
  public Object getValue() {
    return null;
  }
}

class LeaveState extends State {

  @Override
  public Object getValue() {
    return null;
  }

  @Override
  public void onLeave(FSM.StateData data) {
    IdManager idManager = (IdManager) data.get("idManager");
    Variable<String> var = new Variable<>("hello");
    idManager.addObject(var, "testvarleave");
  }
}

class InitLeaveState extends State {

  @Override
  public void onEnter(FSM.StateData data) {
    IdManager idManager = (IdManager) data.get("idManager");
    Variable<String> var = new Variable<>("hello");
    idManager.addObject(var, "testvarinit");
  }

  @Override
  public Object getValue() {
    return null;
  }

  @Override
  public void onLeave(FSM.StateData data) {
    IdManager idManager = (IdManager) data.get("idManager");
    Variable<String> var = new Variable<>("hello");
    idManager.addObject(var, "testvarleave");
  }
}

public class FSMTest {

  FSM<String> fsm;
  IdManager<Ownable> idManager;

  @BeforeEach
  void setUp() throws Exception {
    idManager = new IdManager<>();
    fsm = new FSM<>(idManager);
  }

  @Test
  void testMakesStates() {
    fsm.putState("INIT", new BasicState());
    fsm.putState("MOVE1", new BasicState());
    fsm.putState("DONE", new BasicState());
    fsm.setState("INIT");
    assertEquals(3, fsm.getStates().size());
  }

  @Test
  void testAddRedundantStates() {
    fsm.putState("INIT", new BasicState());
    fsm.putState("INIT", new BasicState());
    assertEquals(1, fsm.getStates().size());
  }

  @Test
  void testDefaultTransitions() {
    fsm.putState("INIT", new BasicState(), (state, data) -> "MOVE1");
    fsm.putState("MOVE1", new BasicState(), (state, data) -> "DONE");
    fsm.putState("DONE", new BasicState(), (state, data) -> "INIT");

    fsm.setState("INIT");
    assertEquals("INIT", fsm.getCurrentState());
    fsm.transition();
    assertEquals("MOVE1", fsm.getCurrentState());
    fsm.transition();
    assertEquals("DONE", fsm.getCurrentState());
    fsm.transition();
    assertEquals("INIT", fsm.getCurrentState());
  }

  @Test
  void testCustomTransitions() {
    fsm.putState("INIT", new BasicState());
    fsm.putState("MOVE1", new BasicState());
    fsm.putState("DONE", new BasicState());

    fsm.setState("INIT");
    assertEquals("INIT", fsm.getCurrentState());
    fsm.transition((state, data) -> "MOVE1");
    assertEquals("MOVE1", fsm.getCurrentState());
    fsm.transition((state, data) -> "DONE");
    assertEquals("DONE", fsm.getCurrentState());
    fsm.transition((state, data) -> "INIT");
    assertEquals("INIT", fsm.getCurrentState());
    fsm.transition((state, data) -> "DONE");
    assertEquals("DONE", fsm.getCurrentState());
    fsm.transition((state, data) -> "MOVE1");
    assertEquals("MOVE1", fsm.getCurrentState());
  }

  @Test
  void testOnInit() {
    fsm.putState("INIT", new InitState());
    fsm.putState("OTHER", new BasicState());
    fsm.setState("INIT");
    assertTrue(idManager.isIdInUse("testvar"));
  }

  @Test
  void testOnLeave() {
    fsm.putState("INIT", new LeaveState(), (state, data) -> "OTHER");
    fsm.putState("OTHER", new BasicState());
    fsm.setState("INIT");
    fsm.transition();
    assertTrue(idManager.isIdInUse("testvarleave"));
  }

  @Test
  void onInitAndLeave() {
    fsm.putState("INIT", new InitLeaveState(), (state, data) -> "OTHER");
    fsm.putState("OTHER", new BasicState());
    fsm.setState("INIT");
    assertTrue(idManager.isIdInUse("testvarinit"));
    assertFalse(idManager.isIdInUse("testvarleave"));
    fsm.transition();
    assertTrue(idManager.isIdInUse("testvarinit"));
    assertTrue(idManager.isIdInUse("testvarleave"));
  }

  @Test
  void setInternalValue() {
    fsm.putState("INIT", new BasicState());
    fsm.putState("OTHER", new BasicState());
    fsm.setState("INIT");
    fsm.setStateInnerValue("test");
    assertEquals("test", fsm.getCurrentStateObject().getValue());
  }

  @Test
  void setInternalValueWithTransition() {
    fsm.putState("INIT", new BasicState(), (state, data) -> "OTHER");
    fsm.putState("OTHER", new BasicState());
    fsm.setState("INIT");
    fsm.setStateInnerValue("test");
    assertEquals("test", fsm.getCurrentStateObject().getValue());
    fsm.transition();
    assertNotEquals("test", fsm.getCurrentStateObject().getValue());
  }

  private static DropZone addDropZone(int y, int x, IdManager idManager, String name) {
    DropZone dropZone = new DropZone();
    idManager.addObject(dropZone, name);
    return dropZone;
  }


  @Test
  void testProgrammableState() {
    TestGame game = new TestGame();
    idManager = game.getIdManager();
    Interpreter interpreter = game.getInterpreter();
    fsm = new FSM<>(idManager);

    interpreter.linkIdManager(idManager);
    interpreter.linkGame(game);

    ArrayList<DropZone> zones = new ArrayList<>();

    Variable<Double> turn = new Variable<>(0.);
    turn.addListener((value) -> {
      fsm.setState("INIT");
    });
    idManager.addObject(turn, "turn");

    Variable<Double> numPlayers = new Variable<>(2.);
    idManager.addObject(numPlayers, "numPlayers");

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        zones.add(addDropZone(i, j, idManager, i + "," + j));
      }
    }

    GameObject objX = new GameObject(null);
    GameObject objO = new GameObject(null);
    idManager.addObject(objX, "X");
    idManager.addObject(objO, "O");

    Map<String, Map<String, String>> map = Stream.of(new Object[][]{
            {"INIT", Stream.of(new String[][]{
                {"init", "to getAvailable [ ] [ make :game_available [ ] " +
                    "for [ :i 0 3 ] [ " +
                    "for [ :j 0 3 ] [ " +
                    "make :x fromgame + + :i \", :j " +
                    "if == 0 len dzitems :x [ " +
                    "additem :x :game_available " +
                    "] " +
                    "] " +
                    "] ] getAvailable"},
                {"leave", ""},
                {"setValue", ""},
            }).collect(Collectors.toMap(data -> ((String[]) data)[0], data -> ((String[]) data)[1]))},

            {"MOVE", Stream.of(new String[][]{
                {"init", ""},
                {"leave", "getAvailable"},
                {"setValue", "ifelse == :game_turn 0 [ " +
                    "putdzitem :game_X fromgame :game_state_input " +
                    " ] [ " +
                    "putdzitem :game_O fromgame :game_state_input " +
                    "] getAvailable"},
            }).collect(Collectors.toMap(data -> ((String[]) data)[0], data -> ((String[]) data)[1]))},

            {"DONE", Stream.of(new String[][]{
                {"init", "make :game_turn % + :game_turn 1 :game_numPlayers"},
                {"leave", ""},
                {"setValue", ""},
            }).collect(Collectors.toMap(data -> ((String[]) data)[0], data -> ((String[]) data)[1]))}
        }
    ).collect(Collectors.toMap(data -> (String) data[0], data -> (Map<String, String>) data[1]));

    fsm.putState("INIT", new ProgrammableState(interpreter, map.get("INIT").get("init"),
        map.get("INIT").get("leave"),
        map.get("INIT").get("setValue")));

    fsm.putState("MOVE", new ProgrammableState(interpreter, map.get("MOVE").get("init"),
        map.get("MOVE").get("leave"),
        map.get("MOVE").get("setValue")));

    fsm.putState("DONE", new ProgrammableState(interpreter, map.get("DONE").get("init"),
        map.get("DONE").get("leave"),
        map.get("DONE").get("setValue")));

    fsm.setState("INIT");

    Variable v = (Variable) idManager.getObject("available");
    ArrayList<DropZone> expected = new ArrayList<>(zones);
    assertEquals(expected, v.get());
    System.out.println(v.get());

    fsm.transition((state, data) -> "MOVE");
    fsm.setStateInnerValue("0,0");

    assertEquals(objX, zones.get(0).getAllObjects().get(0));

    expected = new ArrayList<>(
        Arrays.asList(zones.get(1), zones.get(2), zones.get(3), zones.get(4), zones.get(5),
            zones.get(6), zones.get(7), zones.get(8)));
    assertEquals(expected, v.get());

    fsm.transition((state, data) -> "DONE");

    assertEquals(1, turn.get());
    assertEquals(2, numPlayers.get());
    assertEquals("INIT", fsm.getCurrentState());

    fsm.transition((state, data) -> "MOVE");

    fsm.setStateInnerValue("0,1");
    assertEquals(objO, zones.get(1).getAllObjects().get(0));
    expected = new ArrayList<>(
        Arrays.asList(zones.get(2), zones.get(3), zones.get(4), zones.get(5), zones.get(6),
            zones.get(7), zones.get(8)));
    assertEquals(expected, v.get());
  }

}
