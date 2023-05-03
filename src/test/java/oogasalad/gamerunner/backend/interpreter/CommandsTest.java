package oogasalad.gamerunner.backend.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.*;

import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.gamerunner.backend.interpreter.commands.math.Sum;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;
import oogasalad.sharedDependencies.backend.id.OwnableSearchStream;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandsTest {

  private Interpreter interpreter;
  private IdManager<Ownable> idManager;
  private TestGame game;
  private static final String LANGUAGE_RESOURCE_PATH = "backend.interpreter.languages";
  ResourceBundle resources;
  private String language = "English";

  @BeforeEach
  public void setUp() {
    interpreter = new Interpreter();
    idManager = new IdManager<>();
    game = new TestGame();
    interpreter.linkIdManager(idManager);
    interpreter.linkGame(game);
    language = "English";
    resources = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + "." + language);
  }

  private void setLanguage(String language) {
    this.language = language;
    resources = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + "." + language);
  }

  private <T> T getVar(String name) {
    return (T) idManager.getObject(name);
  }

  private String checkSubtypeErrorMsg(Token t, String NAME, Class<?> type, Class<?>... subtype) {
    String s = resources.getString("argumentSubtypeError");
    String[] simplenames = new String[subtype.length];
    for (int i = 0; i < subtype.length; i++) {
      simplenames[i] = subtype[i].getSimpleName();
    }
    s = String.format(s, t, NAME, type.getSimpleName(), String.join(" or ", simplenames),
        t.getClass().getSimpleName(), t.SUBTYPE);
    return s;
  }

  private String checkTypeErrorMsg(Token t, String NAME, Class<?>... type) {
    String s = resources.getString("argumentTypeError");
    String[] simplenames = new String[type.length];
    for (int i = 0; i < type.length; i++) {
      simplenames[i] = type[i].getSimpleName();
    }
    s = String.format(s, t, NAME, String.join(" or ", simplenames),
        t == null ? "null" : t.getClass().getSimpleName());
    return s;
  }

  private void useGameVars(){
    interpreter = game.getInterpreter();
    idManager = game.getIdManager();
  }

  @Test
  public void testAddAllItems(){
    String input = "make :x [ 1 2 3 ] make :y [ 4 5 6 ] addallitems :x :y";
    interpreter.interpret(input);
    List<Double> expected = new ArrayList<>(List.of(4., 5., 6., 1., 2., 3.));
    Variable<List<Double>> t = getVar("interpreter-:y");
    assertEquals(expected, t.get());

    input = "to arr [ ] [ return [ 7 8 9 ] ] make :x [ ] addallitems arr :x";
    interpreter.interpret(input);
    expected = new ArrayList<>(List.of(7., 8., 9.));
    t = getVar("interpreter-:x");
    assertEquals(expected, t.get());
  }

  @Test
  public void testAddDropZone(){
    game.noFSMInit(2);
    useGameVars();
    DropZone dz = new DropZone();
    game.addElement(dz, "dz");
    String input = "adddz [ \"board \"thing ] :game_dz \"block.png \"blockHighlight.png 50 50";
    interpreter.interpret(input);
    DropZone dz2 = getVar("DropZone");
    assertTrue(dz2.usesClass("board"));
    assertTrue(dz2.usesClass("thing"));
    assertTrue(dz.hasObject(idManager.getId(dz2)));

    // test with non-string
    input = "adddz [ 1 ] :game_dz \"block.png \"blockHighlight.png 50 50";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Token t = new ValueToken<>(1.);
      assertEquals(checkSubtypeErrorMsg(t, "AddDropZone", ValueToken.class, String.class), e.getMessage());
    }
  }

  @Test
  public void testAddDropZoneItem() {
    game.noFSMInit(2);
    useGameVars();

    DropZone dz = new DropZone();
    idManager.addObject(dz, "dz");
    GameObject obj = new GameObject(null);
    idManager.addObject(obj, "obj");

    // add item to dropzone
    String input = "putdzitem :game_obj :game_dz";
    interpreter.interpret(input);
    List<GameObject> expected = List.of(obj);
    assertEquals(expected, dz.getAllObjects());

    // add item to dropzone with non-string
    input = "putdzitem 1 :game_dz";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Token t = new ValueToken<>(1.);
      assertEquals(checkSubtypeErrorMsg(t, "PutDropZoneItem", ValueToken.class, Ownable.class),
          e.getMessage());
    }
  }

  @Test
  public void testAddItem() {
    // add item to list
    String input = "make :x [ ] additem 1 :x";
    interpreter.interpret(input);
    Variable<List> a = getVar("interpreter-:x");
    List<Double> expected = new ArrayList<>(List.of(1.));
    assertEquals(expected, a.get());

    // add item to list with non-list
    input = "make :x 1 additem 1 :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Token t = new ValueToken<>(1.);
      assertEquals(checkTypeErrorMsg(t, "AddItem", ExpressionToken.class), e.getMessage());
    }

    // add item to list with non-number
    input = "make :x [ ] additem \"1 :x additem \"hello :x";
    interpreter.interpret(input);
    List<String> expected2 = new ArrayList<>(List.of("1", "hello"));
    a = getVar("interpreter-:x");
    assertEquals(expected2, a.get());

    // add variable to list
    input = "make :x [ ] make :y 1 additem :y :x";
    interpreter.interpret(input);
    a = getVar("interpreter-:x");
    assertEquals(expected, a.get());
  }

  @Test
  public void testAddPiece(){
    game.noFSMInit(2);
    useGameVars();

    DropZone dz = new DropZone();
    game.addElement(dz, "dz");

    // add piece to game
    String input = "addpiece curplayer [ \"test \"yes ] :game_dz \"img1 50 50";
    interpreter.interpret(input);
    GameObject go = getVar("GameObject");
    assertTrue(go.usesClass("test"));
    assertTrue(go.usesClass("yes"));
    assertEquals(dz, game.getPieceLocation(go));
    assertEquals("assets/img1", game.getObjImage(go));

    // add piece to game with non-string
    input = "addpiece 1 1 1 1 1 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Token t = new ValueToken<>(1.);
      assertEquals(checkSubtypeErrorMsg(t, "AddPiece", ValueToken.class, Owner.class),
          e.getMessage());
    }
  }

  @Test
  public void testAnd() {
    // true and
    String input = "make :a true make :b < 2 3 make :x and :a :b";
    interpreter.interpret(input);
    Variable<Boolean> x = getVar("interpreter-:x");
    assertTrue(x.get());

    // false and
    input = "make :a true make :b < 3 2 make :x and :a :b";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertFalse(x.get());

    // and with non-booleans
    input = "make :a 1 make :b 2 make :x and :a :b";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Token t = new ValueToken<>(1.);
      assertEquals(checkSubtypeErrorMsg(t, "And", ValueToken.class, Boolean.class), e.getMessage());
    }
  }

  @Test
  public void testArcTangent() {
    // arc tangent
    String input = "make :x 1 make :z atan :x";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(45.0, z.get());

    // arc tangent with non-numbers
    input = "make :x < 2 1 make :z atan :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "ArcTangent", ValueToken.class, Double.class),
          e.getMessage());
    }
  }

  @Test
  public void testAsList(){
    String input = "make :x 1 make :y 2 make :z aslist [ :x :y ]";
    interpreter.interpret(input);
    Variable<List> z = getVar("interpreter-:z");
    List<Double> expected = new ArrayList<>(List.of(1., 2.));
    assertEquals(expected, z.get());
  }

  @Test
  public void testBreak(){
    // break
    String input = "make :x 0 for [ :i 1 10 1 ] [ global :x if <= :i 5 [ make :x :i ] if > :i 5 [ break ] ]";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(5.0, x.get());

    // break in nested loop
    input = "make :x 0 make :y 0 " +
            "repeat 5 [ " +
              "make :x + :x 1 " +
              "repeat 10 [ " +
                "if > :repcount 5 [ break ] " +
                "make :y :repcount " +
              "] " +
            "]";

    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(5.0, x.get());
    assertEquals(5.0, y.get());

  }

  @Test
  public void testCallAndFVar(){
    String input = "make :y 0 to test [ :x ] [ global :y make :game_y :x ] make :func fvar test call :func [ 1 ]";
    interpreter.interpret(input);
    Variable y = getVar("y");
    assertEquals(1.0, y.get());

    // test with function that takes no parameters
    input = "make :y 0 to test [ ] [ global :y make :game_y 6 ] make :func fvar test call :func [ ]";
    interpreter.interpret(input);
    y = getVar("y");
    assertEquals(6.0, y.get());

    // test with function that takes multiple parameters
    input = "make :y 0 to test [ :x :z ] [ global :y make :game_y + :x :z ] make :func fvar test call :func [ 1 2 ]";
    interpreter.interpret(input);
    y = getVar("y");
    assertEquals(3.0, y.get());
  }

  @Test
  public void testContinue(){
    // continue
    String input = "make :game_log [ ] " +
            "make :x 0 " +
            "for [ :i 1 10 1 ] [ " +
            "global :x " +
            "additem :i :game_log " +
            "if > :i 5 [ continue ] " +
            " make :x :i " +
            "]";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    Variable<List<Double>> log = getVar("log");
    assertEquals(5., x.get());
    List<Double> expected = new ArrayList<>(List.of(1., 2., 3., 4., 5., 6., 7., 8., 9.));
    assertEquals(expected, log.get());

    // continue in nested loop
    input = "make :x 0 make :y 0 " +
            "repeat 5 [ " +
              "make :x + :x 1 " +
              "repeat 10 [ " +
                "if > :repcount 5 [ continue ] " +
                "make :y :repcount " +
              "] " +
            "]";

    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(5.0, x.get());
    assertEquals(5.0, y.get());
  }

  @Test
  public void testCosine() {
    // cosine
    String input = "make :x 38 make :z cos :x";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(0.788010753606722, z.get());

    // cosine with non-numbers
    input = "make :x < 2 1 make :z cos :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "Cosine", ValueToken.class, Double.class),
          e.getMessage());
    }
  }

  @Test
  public void testCurPlayer(){
    game.noFSMInit(2);
    useGameVars();
    String input = "make :x curplayer";
    interpreter.interpret(input);
    Variable<Player> x = getVar("interpreter-:x");
    assertEquals(game.getPlayer(0), x.get());

    game.setTurn(1);
    input = "make :x curplayer";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(game.getPlayer(1), x.get());
  }

  @Test
  public void testDel() {
    // del
    String input = "make :x 1 del :x";
    interpreter.interpret(input);
    try {
      getVar("interpreter-:x");
      fail();
    } catch (Exception e) {
      assertEquals("Id \"interpreter-:x\" not found.", e.getMessage());
    }

    // del with non-variables
    input = "make :x 1 del 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Token t = new ValueToken<>(1.);
      assertEquals(checkTypeErrorMsg(t, "Del", VariableToken.class), e.getMessage());
    }

    // del with non-existing variable
    input = "del :yegor";
    interpreter.interpret(input);

    // del with not enough parameters
    input = "del";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Del", e.getMessage());
    }

  }

  @Test
  public void testDifference() {
    // difference
    String input = "make :x 1 make :y 2 make :z - :x :y";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(-1.0, z.get());

    // difference with non-numbers
    input = "make :x < 2 1 make :y 2 make :z - :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "Difference", ValueToken.class, Double.class),
          e.getMessage());
    }
  }

  @Test
  public void testDoTimes() {
    // do times
    String input = "make :x [ -1 -1 -1 -1 -1 ] dotimes [ :i 5 ] [ setitem :i :x :i ]";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    for (int i = 0; i < 5; i++) {
      assertEquals((double) i, x.get().get(i));
    }

    // dotimes with non-numbers
    input = "dotimes [ :i < 2 1 ] [ :i ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "DoTimes", ValueToken.class, Double.class),
          e.getMessage());
    }

    // dotimes with non-variable
    input = "dotimes [ 6 5 ] [ 5 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(6.);
      assertEquals(checkTypeErrorMsg(t, "DoTimes", VariableToken.class), e.getMessage());
    }

    //dotimes with no variable
    input = "dotimes [ 6 ] [ 5 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat with 1 arguments", e.getMessage());
    }

    //dotimes with no repeat number
    input = "dotimes [ :i ] [ 5 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat with 1 arguments", e.getMessage());
    }

    //dotimes with incorrect number of arguments
    input = "dotimes [ :i 6 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator DoTimes", e.getMessage());
    }
  }

  @Test
  public void testDropZoneHasId(){
    DropZone d1 = new DropZone();
    DropZone d2 = new DropZone();
    idManager.addObject(d1, "A");
    idManager.addObject(d2, "B");

    d1.putObject("obj", 1);

    String input = "make :x dzhasid \"obj :game_A";
    interpreter.interpret(input);
    Variable<Boolean> x = getVar("interpreter-:x");
    assertTrue(x.get());

    input = "make :x dzhasid \"obj2 :game_A";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertFalse(x.get());
  }

  @Test
  public void testDropZonePaths(){
    List<DropZone> board = BoardCreator.createGrid(8, 8);
    for (DropZone dz : board) {
      idManager.addObject(dz);
    }
    String id1 = idManager.getId(board.get(0));
    String id2 = idManager.getId(board.get(18));
    String input = "make :p1 dzpaths fromgame \"" + id1 + " make :p2 dzpaths fromgame \"" + id2;
    interpreter.interpret(input);
    Variable<List<String>> p1 = getVar("interpreter-:p1");
    Variable<List<String>> p2 = getVar("interpreter-:p2");
    HashSet<String> expected1 = new HashSet<>(Arrays.asList("Down", "DownRight", "Right"));
    HashSet<String> expected2 = new HashSet<>(Arrays.asList("Up", "UpLeft", "Left", "DownLeft", "Down", "DownRight", "Right", "UpRight"));
    assertEquals(expected1, new HashSet<>(p1.get()));
    assertEquals(expected2, new HashSet<>(p2.get()));
  }

  @Test
  public void testDzNeighbors(){
    List<DropZone> board = BoardCreator.createGrid(8, 8);
    for (DropZone dz : board) {
      idManager.addObject(dz);
    }
    String id1 = idManager.getId(board.get(0));
    String id2 = idManager.getId(board.get(18));
    String input = "make :p1 dzneighbors fromgame \"" + id1 + " make :p2 dzneighbors fromgame \"" + id2;
    interpreter.interpret(input);
    Variable<List<DropZone>> p1 = getVar("interpreter-:p1");
    Variable<List<DropZone>> p2 = getVar("interpreter-:p2");
    HashSet<DropZone> expected1 = new HashSet<>(Arrays.asList(board.get(1), board.get(8), board.get(9)));
    HashSet<DropZone> expected2 = new HashSet<>(Arrays.asList(board.get(17), board.get(19), board.get(10), board.get(9), board.get(11), board.get(26), board.get(25), board.get(27)));
    assertEquals(expected1, new HashSet<>(p1.get()));
    assertEquals(expected2, new HashSet<>(p2.get()));
  }

  @Test
  public void testEqual() {
    // equal
    String input = "make :x 1 make :y 1 make :z == :x :y";
    interpreter.interpret(input);
    Variable<Boolean> z = getVar("interpreter-:z");
    assertTrue(z.get());

    // not equal
    input = "make :x 1 make :y 2 make :z == :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // equal with non-numbers
    input = "make :x < 2 1 make :y 2 make :z == :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // equal with non-numbers
    input = "make :x < 2 1 make :y < 3 2 make :z == :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertTrue(z.get());

    // equal with incorrect number of parameters
    input = "make :x false make :y false make :z == :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Equal", e.getMessage());
    }
  }

  @Test
  public void testFor() {
    // for
    String input = "make :x [ 0 0 0 0 0 ] for [ :i 0 5 1 ] [ global :x setitem :i :x :i ]";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    for (int i = 0; i < 5; i++) {
      assertEquals((double) i, x.get().get(i));
    }

    // for with non-numbers
    input = "for [ :i < 2 1 1 ] [ :i ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "For", ValueToken.class, Double.class), e.getMessage());
    }

    // for with non-variable
    input = "for [ 6 5 1 ] [ 4 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(6.);
      assertEquals(checkTypeErrorMsg(t, "For", VariableToken.class), e.getMessage());
    }

    //for with no variable
    input = "for [ 6 ] [ 5 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat with 1 arguments", e.getMessage());
    }

    //for with no repeat number
    input = "for [ :i ] [ 5 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat with 1 arguments", e.getMessage());
    }

    //for with incorrect number of arguments
    input = "for [ :i 6 9 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator For", e.getMessage());
    }

    // for with stop is less than start
    input = "for [ :i 5 0 1 ] [ :i ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat from 5.0 to 0.0 by 1.0", e.getMessage());
    }

    // for with stop is equal to start
    input = "for [ :i 5 5 1 ] [ :i ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat from 5.0 to 5.0 by 1.0", e.getMessage());
    }

    // for with stop is greater than start and increment is negative
    input = "for [ :i 0 5 -1 ] [ :i ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot repeat from 0.0 to 5.0 by -1.0", e.getMessage());
    }

  }

  @Test
  public void testForEach(){
    String input = "make :x [ 1 2 3 4 5 ] make :y 0 foreach [ :i :x ] [ global :y make :y + :y :i ]";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(15, y.get());
  }

  @Test
  public void testFollowDropZonePath(){
    List<DropZone> board = BoardCreator.createGrid(8, 8);
    for (DropZone dz : board) {
      idManager.addObject(dz);
    }
    String id1 = idManager.getId(board.get(0));
    String input = "make :dz fromgame \"" + id1 + " make :path [ \"Down \"DownRight ] make :p dzfollow :dz :path";
    interpreter.interpret(input);
    Variable<DropZone> p = getVar("interpreter-:p");
    assertEquals(board.get(17), p.get());
  }

  @Test
  public void testGetAttribute() {
    GameObject dropZone = new DropZone();
    Class<?> c = GameObject.class;
    System.out.println(c.isInstance(dropZone));
    idManager.addObject(dropZone, "dz");
    GameObject go = new GameObject(null);
    idManager.addObject(go, "obj");
    String input = "make :x == null attr :game_obj \"owner";
    interpreter.interpret(input);
    Variable<Boolean> x = getVar("interpreter-:x");
    assertEquals(true, x.get());
  }

  @Test
  public void testGetDropZoneItem() {
    game.noFSMInit(2);
    useGameVars();

    DropZone dropZone = new DropZone();
    GameObject obj = new GameObject(null);
    idManager.addObject(dropZone, "dz");
    idManager.addObject(obj, "obj");
    String input = "putdzitem :game_obj :game_dz make :x item 0 dzitems :game_dz";
    System.out.println(obj.getDefaultId());
    interpreter.interpret(input);
    Variable<Ownable> x = getVar("interpreter-:x");
    assertEquals(obj, x.get());

    GameObject obj2 = new GameObject(null);
    idManager.addObject(obj2, "obj2");

    input = "putdzitem :game_obj2 :game_dz make :x item 1 dzitems :game_dz";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(obj2, x.get());

    // try with a non-dropzone
    input = "make :x dzitem \"obj 2";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(2.);
      assertEquals(checkSubtypeErrorMsg(t, "GetDropZoneItem", ValueToken.class, DropZone.class),
          e.getMessage());
    }

  }

  @Test
  public void testGetDropZoneItems() {
    game.noFSMInit(2);
    useGameVars();

    DropZone dropZone = new DropZone();
    idManager.addObject(dropZone, "dz");
    GameObject obj = new GameObject(null);
    idManager.addObject(obj, "obj");
    GameObject obj2 = new GameObject(null);
    idManager.addObject(obj2, "obj2");
    String input = "putdzitem :game_obj :game_dz putdzitem :game_obj2 :game_dz make :x dzitems :game_dz";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    assertEquals(2, x.get().size());
    assertTrue(x.get().contains(obj));
    assertTrue(x.get().contains(obj2));

    // try with a non-dropzone
    input = "make :x dzitems \"obj";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>("obj");
      assertEquals(checkSubtypeErrorMsg(t, "GetDropZoneItems", ValueToken.class, DropZone.class),
          e.getMessage());
    }

  }

  @Test
  public void testGetId() {
    game.noFSMInit(2);
    useGameVars();

    GameObject obj = new GameObject(null);
    idManager.addObject(obj, "obj");
    String input = "make :x getid :game_obj \"available [ ]";
    interpreter.interpret(input);
    Variable<String> x = getVar("interpreter-:x");
    assertEquals("obj", x.get());


    Variable<Double> var = new Variable<>(5., null);
    idManager.addObject(var, "var");
    input = "make :y fromgame \"var make :varname getid :y";
    interpreter.interpret(input);
    Variable<String> y = getVar("interpreter-:varname");
    assertEquals("var", y.get());
  }

  @Test
  public void testGetObjDz(){
    game.noFSMInit(2);
    useGameVars();
    List<DropZone> board = BoardCreator.createGrid(2, 2);
    for (DropZone dz : board) {
      game.addElement(dz);
    }
    // add items to dropZones
    GameObject p1 = new GameObject(game.getPlayer(0));
    GameObject p2 = new GameObject(game.getPlayer(0));
    GameObject p3 = new GameObject(game.getPlayer(1));
    GameObject p4 = new GameObject(game.getPlayer(1));
    game.addElement(p1, "p1");
    game.putInDropZone(p1, board.get(0));
    game.addElement(p2, "p2");
    game.putInDropZone(p2, board.get(1));
    game.addElement(p3, "p3");
    game.putInDropZone(p3, board.get(2));
    game.addElement(p4, "p4");
    game.putInDropZone(p4, board.get(3));

    String input = "make :x objdz :game_p1";
    interpreter.interpret(input);
    Variable<DropZone> x = getVar("interpreter-:x");
    assertEquals(board.get(0), x.get());

    input = "make :x objdz :game_p2 make :y objdz :game_p3 make :z objdz :game_p4";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    Variable<DropZone> y = getVar("interpreter-:y");
    Variable<DropZone> z = getVar("interpreter-:z");
    assertEquals(board.get(1), x.get());
    assertEquals(board.get(2), y.get());
    assertEquals(board.get(3), z.get());


    // null case
    game.addElement(new GameObject(game.getPlayer(0)), "p5");
    input = "make :x == null objdz :game_p5";
    interpreter.interpret(input);
    System.out.println(((Variable)getVar("interpreter-:x")).get());
    Variable<Boolean> b = getVar("interpreter-:x");
    assertTrue(b.get());




  }

  @Test
  public void testGetObjsByClass(){
    // three objects with class "obj"
    GameObject obj1 = new GameObject(null);
    obj1.addClass("obj");
    GameObject obj2 = new GameObject(null);
    obj2.addClass("obj");
    GameObject obj3 = new GameObject(null);
    obj3.addClass("obj");
    idManager.addObject(obj1, "obj1");
    idManager.addObject(obj2, "obj2");
    idManager.addObject(obj3, "obj3");
    String input = "make :x fromgameclass \"obj";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    assertEquals(3, x.get().size());
    assertTrue(x.get().contains(obj1));
    assertTrue(x.get().contains(obj2));
    assertTrue(x.get().contains(obj3));

    // board, some are selected, some are not, one other object is selected
    List<DropZone> tiles = BoardCreator.createGrid(3, 3);
    for (DropZone tile : tiles){
      tile.addClass("board");
      idManager.addObject(tile);
    }
    tiles.get(0).addClass("selected");
    obj1.addClass("selected");
    input = "make :x fromgameclass \"board make :y fromgameclass \"board.selected make :z fromgameclass \"selected";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(new HashSet(tiles), new HashSet(x.get()));

    Variable<List<Object>> y = getVar("interpreter-:y");
    assertEquals(1, y.get().size());
    assertTrue(y.get().contains(tiles.get(0)));

    Variable<List<Object>> z = getVar("interpreter-:z");
    assertEquals(2, z.get().size());
    assertTrue(z.get().contains(tiles.get(0)));
    assertTrue(z.get().contains(obj1));

    // fromgameclass with a non-string
    input = "make :x fromgameclass 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.);
      assertEquals(checkSubtypeErrorMsg(t, "GetFromGameClass", ValueToken.class, String.class),
          e.getMessage());
    }

    // fromgameclass with wrong number of arguments
    input = "make :x fromgameclass";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator GetFromGameClass", e.getMessage());
    }

  }

  @Test
  public void testGetFromGameVariable() {
    Variable<?> xvar = new Variable<>("test");
    idManager.addObject(xvar, "0,1");

    String input = "make :y fromgame + + 0 \", 1 make :x + :y 1";
    interpreter.interpret(input);
    Variable<String> x = getVar("interpreter-:x");
    assertEquals("test1", x.get());

    // test fromgame from string variable
    Variable<?> yvar = new Variable<>("test2");
    idManager.addObject(yvar, "0,2");
    input = "make :y \"0,2 make :x fromgame :y";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals("test2", x.get());

    // test without enough arguments
    input = "make :x fromgame";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator FromGame", e.getMessage());
    }

  }

  @Test
  public void testGetObjectChildren(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj1 = new GameObject(null);
    Variable<String> var = new Variable<>("test", null);
    idManager.addObject(obj1, "obj1");
    idManager.addObject(var, "obj2", "obj1");
    String input = "make :x objchildren :game_obj1";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    assertEquals(1, x.get().size());
    assertTrue(x.get().contains(var.get()));


    // with wrong number of parameters
    input = "make :x objchildren";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator GetObjChildren", e.getMessage());
    }

    // with incorrect types
    input = "make :x objchildren 1";
    try{
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals(checkSubtypeErrorMsg(new ValueToken<>(1.), "GetObjChildren", ValueToken.class, Ownable.class),
              e.getMessage());
    }
  }


  @Test
  public void testGetObjectChildrenByClass(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj1 = new GameObject(null);
    Variable<String> var1 = new Variable<>("test1", null);
    Variable<String> var2 = new Variable<>("test2", null);
    var1.addClass("class1");
    var2.addClass("class2");
    idManager.addObject(obj1, "obj1");
    idManager.addObject(var1, "var1", "obj1");
    idManager.addObject(var2, "var2", "obj1");
    String input = "make :x objchildrenofclass :game_obj1 \"class1";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    assertEquals(1, x.get().size());
    assertTrue(x.get().contains(var1.get()));

    input = "make :x objchildrenofclass :game_obj1 \"class2";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(1, x.get().size());
    assertTrue(x.get().contains(var2.get()));

    // with wrong number of parameters
    input = "make :x objchildrenofclass :game_obj1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator GetObjChildrenByClass", e.getMessage());
    }

    // with incorrect types
    input = "make :x objchildrenofclass 1 \"class1";
    try{
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals(checkSubtypeErrorMsg(new ValueToken<>(1.), "GetObjChildrenByClass", ValueToken.class, Ownable.class),
          e.getMessage());
    }

  }

  @Test
  public void testGetObjectsFromPlayer(){
    game.noFSMInit(2);
    useGameVars();
    // two players, one object each
    Player p1 = game.getPlayer(0);
    Player p2 = game.getPlayer(1);
    GameObject obj1 = new GameObject(p1);
    GameObject obj2 = new GameObject(p2);
    GameObject obj3 = new GameObject(p1);
    GameObject obj4 = new GameObject(p2);
    obj1.addClass("obj");
    obj2.addClass("obj");
    obj3.addClass("obj");
    obj4.addClass("obj");
    obj3.addClass("thing");
    obj4.addClass("thing");

    Variable<?> xvar = new Variable<>("test", p1);
    xvar.addClass("pvar");
    Variable<?> yvar = new Variable<>("test", p2);
    yvar.addClass("pvar");

    idManager.addObject(xvar, "0,1");
    idManager.addObject(yvar, "0,2");

    idManager.addObject(obj1, "obj1");
    idManager.addObject(obj2, "obj2");
    idManager.addObject(obj3, "obj3");
    idManager.addObject(obj4, "obj4");

    // single class
    String input = "make :x fromplayer curplayer \"obj make :y fromplayer getplayer 1 \"obj";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    Variable<List<Object>> y = getVar("interpreter-:y");
    assertEquals(2, x.get().size());
    assertEquals(2, y.get().size());
    assertTrue(x.get().contains(obj1));
    assertTrue(y.get().contains(obj2));

    // multiple classes
    input = "make :x fromplayer curplayer \"obj.thing";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(1, x.get().size());
    assertTrue(x.get().contains(obj3));

    // fromplayer with a non-number
    input = "make :x fromplayer \"1 \"4";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>("1");
      assertEquals(checkSubtypeErrorMsg(t, "GetObjectsFromPlayer", ValueToken.class, Player.class),
          e.getMessage());
    }

    // fromplayer with wrong number of arguments
    input = "make :x fromplayer";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator GetObjectsFromPlayer", e.getMessage());
    }

  }

  @Test
  public void testGetRule(){
    game.noFSMInit(2);
    useGameVars();
    RuleManager rm = game.getRules();
    String func = """
            to available [ ] [
              return 1
            ]
            """;
    rm.addRule("piece", "available", func);
    GameObject obj1 = new GameObject(game.getPlayer(0));
    obj1.addClass("piece");
    game.addElement(obj1, "obj");
    String input = "make :x getrule :game_obj \"available [ ]";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(1., x.get());
  }

  @Test
  public void testGoToNextPlayer(){
    game.noFSMInit(2);
    useGameVars();
    String input = "gotonextplayer";
    interpreter.interpret(input);
    assertEquals(1.0, ((Variable) getVar("turn")).get());

    interpreter.interpret(input);
    assertEquals(0., ((Variable) getVar("turn")).get());
  }

  @Test
  public void testGreaterEqual() {
    // greater equal
    String input = "make :x 1 make :y 1 make :z >= :x :y";
    interpreter.interpret(input);
    Variable<Boolean> z = getVar("interpreter-:z");
    assertTrue(z.get());

    // greater
    input = "make :x 2 make :y 1 make :z >= :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertTrue(z.get());

    // not greater equal
    input = "make :x 1 make :y 2 make :z >= :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // greater equal with non-numbers
    input = "make :x < 2 1 make :y 2 make :z >= :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "GreaterEqual", ValueToken.class, Double.class),
          e.getMessage());
    }

    // greater equal with incorrect number of parameters
    input = "make :x 1 make :y 2 make :z >= :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator GreaterEqual",
          e.getMessage());
    }
  }

  @Test
  public void testGreaterThan() {
    // greater than
    String input = "make :x 2 make :y 1 make :z > :x :y";
    interpreter.interpret(input);
    Variable<Boolean> z = getVar("interpreter-:z");
    assertTrue(z.get());

    // not greater than
    input = "make :x 1 make :y 2 make :z > :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // not greater than (equals)
    input = "make :x 1 make :y 1 make :z > :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // greater than with non-numbers
    input = "make :x < 2 1 make :y 2 make :z > :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "GreaterThan", ValueToken.class, Double.class),
          e.getMessage());
    }

    // greater than with incorrect number of parameters
    input = "make :x 1 make :y 2 make :z > :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator GreaterThan", e.getMessage());
    }
  }

  @Test
  public void testHasClass(){
    game.noFSMInit(4);
    useGameVars();
    game.getPlayer(0).addClass("red");
    game.getPlayer(1).addClass("red");
    game.getPlayer(2).addClass("blue");
    game.getPlayer(3).addClass("blue");

    // has class
    String input = "make :a hasclass curplayer \"red make :b hasclass curplayer \"blue";
    interpreter.interpret(input);
    Variable<Boolean> a = getVar("interpreter-:a");
    Variable<Boolean> b = getVar("interpreter-:b");
    assertTrue(a.get());
    assertFalse(b.get());

    // hasclass with an object
    GameObject obj = new GameObject(null);
    obj.addClass("red");
    game.addElement(obj, "obj");
    input = "make :a hasclass fromgame \"obj \"red";
    interpreter.interpret(input);
    a = getVar("interpreter-:a");
    assertTrue(a.get());

    // has class with incorrect number of parameters
    input = "make :a hasclass curplayer";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator HasClass", e.getMessage());
    }

    // has class with non-string
    input = "make :a hasclass curplayer 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkSubtypeErrorMsg(t, "HasClass", ValueToken.class, String.class),
          e.getMessage());
    }

    // has class with non-player
    input = "make :a hasclass 1 \"red";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkSubtypeErrorMsg(t, "HasClass", ValueToken.class, IdManageable.class),
          e.getMessage());
    }

  }

  @Test
  public void testIf() {
    // if true
    String input = "make :x 1 make :y true if :y [ make :x 3 ]";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(3.0, x.get());

    // if false
    input = "make :x 1 make :y < 2 1 if :y [ make :x 3 ]";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(1.0, x.get());

    // if with non-boolean
    input = "make :x 1 make :y 2 if :y [ make :x 3 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(2.0);
      assertEquals(checkSubtypeErrorMsg(t, "If", ValueToken.class, Boolean.class), e.getMessage());
    }

    // if with incorrect number of parameters
    input = "make :x 1 make :y true if :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator If", e.getMessage());
    }
  }

  @Test
  public void testIfDropZoneEmpty(){
    DropZone d1 = new DropZone();
    idManager.addObject(d1, "A");

    String input = "make :x dzempty :game_A";
    interpreter.interpret(input);
    Variable<Boolean> x = getVar("interpreter-:x");
    assertTrue(x.get());

    d1.putObject("obj", 1);

    input = "make :x dzempty :game_A";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertFalse(x.get());
  }

  @Test
  public void testIfElse() {
    // if true
    String input = "make :x 1 make :y true ifelse :y [ global :x make :x 3 ] [ global :x make :x 4 ]";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(3.0, x.get(), 0.0001);
    // if false
    input = "make :x 1 make :y < 2 1 ifelse :y [ global :x make :x 3 ] [ global :x make :x 4 ]";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(4.0, x.get(), 0.0001);

    // if with non-boolean
    input = "make :x 1 make :y 2 ifelse :y [ global :x make :x 3 ] [ global :x make :x 4 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(2.0);
      assertEquals(checkSubtypeErrorMsg(t, "IfElse", ValueToken.class, Boolean.class),
          e.getMessage());
    }

    // if with incorrect number of parameters
    input = "make :x 1 make :y true ifelse :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator IfElse", e.getMessage());
    }
  }

  @Test
  public void testItem() {
    // item
    String input = "make :x [ 1 2 3 ] make :y item 1 :x";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(2.0, y.get(), 0.0001);

    // item with non-list
    input = "make :x 1 make :y item 1 :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkTypeErrorMsg(t, "Item", ExpressionToken.class), e.getMessage());
    }

    // item with non-number
    input = "make :x [ 1 2 3 ] make :y item :x :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      Environment env = new Environment();
      ExpressionToken t = new ExpressionToken();
      t.addToken(new ValueToken<>(1.), env);
      t.addToken(new ValueToken<>(2.), env);
      t.addToken(new ValueToken<>(3.), env);

      assertEquals(checkTypeErrorMsg(t, "Item", ValueToken.class), e.getMessage());
    }

    // item out of bounds
    input = "make :x [ 1 2 3 ] make :y item 4 :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Cannot get index 4 from list of size 3", e.getMessage());
    }
  }

  @Test
  public void testLength() {
    // length
    String input = "make :x [ 1 2 3 ] make :y len :x";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(3.0, y.get(), 0.0001);

    // length with non-list
    input = "make :x 1 make :y len :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkTypeErrorMsg(t, "Length", ExpressionToken.class), e.getMessage());
    }
  }

  @Test
  public void testLessEqual() {
    // less equal
    String input = "make :x 1 make :y 2 make :z <= :x :y";
    interpreter.interpret(input);
    Variable<Boolean> z = getVar("interpreter-:z");
    assertTrue(z.get());

    // not less equal
    input = "make :x 2 make :y 1 make :z <= :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // not less equal (equals)
    input = "make :x 1 make :y 1 make :z <= :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertTrue(z.get());

    // less equal with non-numbers
    input = "make :x < 2 1 make :y 2 make :z <= :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "LessEqual", ValueToken.class, Double.class),
          e.getMessage());
    }

    // less equal with incorrect number of parameters
    input = "make :x 1 make :y 2 make :z <= :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator LessEqual", e.getMessage());
    }
  }

  @Test
  public void testLessThan() {
    // less than
    String input = "make :x 1 make :y 2 make :z < :x :y";
    interpreter.interpret(input);
    Variable<Boolean> z = getVar("interpreter-:z");
    assertTrue(z.get());

    // not less than
    input = "make :x 2 make :y 1 make :z < :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // not less than (equals)
    input = "make :x 1 make :y 1 make :z < :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertFalse(z.get());

    // less than with non-numbers
    input = "make :x < 2 1 make :y 2 make :z < :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "LessThan", ValueToken.class, Double.class),
          e.getMessage());
    }

    // less than with incorrect number of parameters
    input = "make :x 1 make :y 2 make :z < :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator LessThan", e.getMessage());
    }
  }

  @Test
  public void testListContains(){
    String input = "make :x [ 1 2 3 ] make :y listhas 1 :x";
    interpreter.interpret(input);
    Variable<Boolean> y = getVar("interpreter-:y");
    assertTrue(y.get());

    input = "make :x [ 1 2 3 ] make :y listhas 4 :x";
    interpreter.interpret(input);
    assertFalse(y.get());

    input = "make :x [ 1 2 3 ] make :y listhas 1.0 :x";
    interpreter.interpret(input);
    assertTrue(y.get());

    input = "make :x [ 1 2 3 ] make :y listhas 1.1 :x";
    interpreter.interpret(input);
    assertFalse(y.get());

    // test with non-list
    input = "make :x 1 make :y listhas 1 :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkTypeErrorMsg(t, "ListContains", ExpressionToken.class), e.getMessage());
    }


  }

  @Test
  public void testListIndex(){
    String input = "make :x [ 1 2 3 ] make :y index 1 :x";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(0., y.get());

    input = "make :y index 2 :x";
    interpreter.interpret(input);
    assertEquals(1., y.get());

    input = "make :y index 3 :x";
    interpreter.interpret(input);
    assertEquals(2., y.get());

    input = "make :y index 4 :x";
    interpreter.interpret(input);
    assertEquals(-1., y.get());
  }

  @Test
  public void testMakeAllAvailable(){
    idManager.addObject(new Variable<>(new ArrayList<>()), "available");
    String input = "make :x [ 1 2 3 ] makeallavailable :x";
    interpreter.interpret(input);
    Variable<List<Double>> available = getVar("available");
    assertEquals(1.0, available.get().get(0));
    assertEquals(2.0, available.get().get(1));
    assertEquals(3.0, available.get().get(2));
    assertEquals(3, available.get().size());
  }
  @Test
  public void testMakeAvailable(){
    idManager.addObject(new Variable<>(new ArrayList<>()), "available");
    String input = "make :x 1 makeavailable :x";
    interpreter.interpret(input);
    Variable<List<Double>> available = getVar("available");
    assertEquals(1.0, available.get().get(0));
    assertEquals(1, available.get().size());
  }

  @Test
  public void testMakeUserInstruction() {
    // make user instruction, don't make global variable
    String input = "make :x 5 to test [ ] [ make :x + :x 2 ] test";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(5.0, x.get());

    // make user instruction
    input = "make :x 5 to test [ ] [ global :x make :x + :x 2 ] test";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(7.0, x.get());

    // make user instruction that returns a value
    input = "to test [ ] [ return 1 ] make :x test";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(1.0, x.get());

    // make user instruction that returns pythagorean distance
    input = "to pythag [ :a :b ] [ return sqrt + * :a :a * :b :b ] make :a pythag 3 4";
    interpreter.interpret(input);
    x = getVar("interpreter-:a");
    assertEquals(5.0, x.get());

    // pass variables into user instruction
    input = "make :z 0 to test [ :x ] [ global :z make :z :x ] make :y 1 test :y";
    interpreter.interpret(input);
    x = getVar("interpreter-:z");
    assertEquals(1.0, x.get());

    // make user instruction with incorrect number of parameters
    input = "to test [ ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator MakeUserInstruction",
          e.getMessage());
    }

    // make user instruction with incorrect number of parameters
    input = "to test";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator MakeUserInstruction",
          e.getMessage());
    }

    // make user instruction with non-variables
    input = "to test [ 1 ] [ ] test";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkTypeErrorMsg(t, "MakeUserInstruction", VariableToken.class),
          e.getMessage());
    }

    // make user instruction with input
    input = "to test [ :x ] [ global :z make :z + :z :x ] test 1";
    interpreter.interpret(input);
    x = getVar("interpreter-:z");
    assertEquals(2.0, x.get());

    // make user instruction with input and incorrect number of parameters
    input = "to test [ :x ] [ :x ] test";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator test", e.getMessage());
    }

  }

  @Test
  public void testMinus() {
    // minus
    String input = "make :x 1 make :y ~ :x";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(-1.0, y.get());

    // minus with non-numbers
    input = "make :x < 2 1 make :y ~ :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "Minus", ValueToken.class, Double.class),
          e.getMessage());
    }

    // minus with incorrect number of parameters
    input = "make :x 1 make :y ~";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Minus", e.getMessage());
    }
  }

  @Test
  public void testMovePiece(){
    game.noFSMInit(2);
    useGameVars();

    List<DropZone> dropZones = BoardCreator.createGrid(2, 2);
    for (DropZone dz : dropZones) {
      game.addElement(dz);
    }
    GameObject p1 = new GameObject(game.getPlayer(0));
    game.addElement(p1, "p1");
    game.putInDropZone(p1, dropZones.get(0));

    assertTrue(dropZones.get(0).hasObject("p1"));

    String input = "make :x :game_p1 make :next_dz fromgame \"DropZone2 movepiece :x :next_dz";
    interpreter.interpret(input);
    assertTrue(dropZones.get(1).getAllObjects().contains(p1));
    assertTrue(dropZones.get(1).hasObject("p1"));
    assertFalse(dropZones.get(0).getAllObjects().contains(p1));
    assertFalse(dropZones.get(0).hasObject("p1"));
  }

  @Test
  public void testNaturalLog() {
    // natural log
    String input = "make :x 1 make :y ln :x";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(0.0, y.get());

    // natural log of 3
    input = "make :x 3 make :y ln :x";
    interpreter.interpret(input);
    y = getVar("interpreter-:y");
    assertEquals(1.0986122886681096, y.get(), 0.000000000000001);

    // natural log with non-numbers
    input = "make :x < 2 1 make :y ln :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "NaturalLog", ValueToken.class, Double.class),
          e.getMessage());
    }

    // natural log with incorrect number of parameters
    input = "make :x 1 make :y ln";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator NaturalLog", e.getMessage());
    }
  }

  @Test
  public void testNot() {
    // not
    String input = "make :x true make :y not :x";
    interpreter.interpret(input);
    Variable<Boolean> y = getVar("interpreter-:y");
    assertEquals(false, y.get());

    // not with non-booleans
    input = "make :x 1 make :y not :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkSubtypeErrorMsg(t, "Not", ValueToken.class, Boolean.class), e.getMessage());
    }

    // not with incorrect number of parameters
    input = "make :x 1 make :y not";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Not", e.getMessage());
    }
  }

  @Test
  public void testNotEqual() {
    // not equal
    String input = "make :x 1 make :y 2 make :z != :x :y";
    interpreter.interpret(input);
    Variable<Boolean> z = getVar("interpreter-:z");
    assertEquals(true, z.get());

    // not equal with non-numbers
    input = "make :x < 2 1 make :y 2 make :z != :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertEquals(true, z.get());

    // not equal with incorrect number of parameters
    input = "make :x 1 make :y != :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator NotEqual", e.getMessage());
    }
  }

  @Test
  public void testNull(){
    // null
    String input = "make :x null make :y == :x null";
    interpreter.interpret(input);
    Variable<Boolean> y = getVar("interpreter-:y");
    assertTrue(y.get());
  }

  @Test
  public void testOr() {
    // true or
    String input = "make :a true make :b < 2 3 make :x or :a :b";
    interpreter.interpret(input);
    Variable<Boolean> x = getVar("interpreter-:x");
    assertEquals(true, x.get());

    // true or
    input = "make :a true make :b < 3 2 make :x or :a :b";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(true, x.get());

    // false or
    input = "make :a < 2 1 make :b < 3 2 make :x or :a :b";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(false, x.get());

    // or with non-booleans
    input = "make :a 1 make :b 2 make :x or :a :b";
    try {
      interpreter.interpret(input);
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkSubtypeErrorMsg(t, "Or", ValueToken.class, Boolean.class), e.getMessage());
    }
  }

  @Test
  public void testPlayerOwner(){
    game.noFSMInit(4);
    useGameVars();
    Variable<Double> v1 = new Variable<>(1.0, game.getPlayer(0));
    Variable<Double> v2 = new Variable<>(2.0, game.getPlayer(1));
    Variable<Double> v3 = new Variable<>(3.0, game.getPlayer(2));
    Variable<Double> v4 = new Variable<>(4.0, game.getPlayer(3));
    idManager.addObject(v1, "v1");
    idManager.addObject(v2, "v2");
    idManager.addObject(v3, "v3");
    idManager.addObject(v4, "v4");
    String input = "make :x owner :game_v1";
    interpreter.interpret(input);
    Variable<Player> x = getVar("interpreter-:x");
    assertEquals(game.getPlayer(0), x.get());

    input = "make :x owner :game_v2";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(game.getPlayer(1), x.get());

    GameObject o = new GameObject(null);
    idManager.addObject(o, "o", v3);
    input = "make :x owner :game_o";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(game.getPlayer(2), x.get());
  }

  @Test
  public void testPower() {
    // power
    String input = "make :x 2 make :y 3 make :z pow :x :y";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(8.0, z.get());

    // power multiple times with set
    input = "make :x 2 make :y 3 make :z ( pow :x :y 2 )";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertEquals(64.0, z.get());

    // power with non-numbers
    input = "make :x < 2 1 make :y 3 make :z pow :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "Power", ValueToken.class, Double.class),
          e.getMessage());
    }

    // power with incorrect number of parameters
    input = "make :x 1 make :y pow :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Power", e.getMessage());
    }
  }

  @Test
  public void testProduct() {
    // product
    String input = "make :x 2 make :y 3 make :z * :x :y";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(6.0, z.get());

    // product with non-numbers
    input = "make :x < 2 1 make :y 3 make :z * :x :y";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertEquals(0.0, z.get());

    // product with set notation
    input = "make :x 2 make :y 3 make :z ( * :x :y 3 )";
    interpreter.interpret(input);
    z = getVar("interpreter-:z");
    assertEquals(18.0, z.get());

    // product with incorrect number of parameters
    input = "make :x 1 make :y * :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Product", e.getMessage());
    }

    // product with a string
    input = "make :x 1 make :y \"hello make :z * :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>("hello");
      assertEquals(
          checkSubtypeErrorMsg(t, "Product", ValueToken.class, Double.class, Boolean.class),
          e.getMessage());
    }
  }

  @Test
  public void testPutClass(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj = new GameObject(null);
    idManager.addObject(obj, "obj");
    String input = "putclass \"test :game_obj";
    interpreter.interpret(input);
    assertTrue(obj.usesClass("test"));

    input = "putclass \"test2 :game_obj";
    interpreter.interpret(input);
    assertTrue(obj.usesClass("test2"));

    // test with non-string
    input = "putclass 1 :game_obj";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkSubtypeErrorMsg(t, "PutClass", ValueToken.class, String.class),
          e.getMessage());
    }
  }

  @Test
  public void testQuotient() {
    // quotient
    String input = "make :x 2 make :y 3 make :z / :x :y";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(2. / 3., z.get());

    // quotient with non-numbers
    input = "make :x < 2 1 make :y 3 make :z / :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "Quotient", ValueToken.class, Double.class),
          e.getMessage());
    }

    // quotient with incorrect number of parameters
    input = "make :x 1 make :y / :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Quotient", e.getMessage());
    }
  }

  @Test
  public void testRandom() {
    // random
    String input = "make :x rand 1";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertTrue(x.get() < 1);

    input = "make :x rand 0.01";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertTrue(x.get() < 0.01);

    input = "make :x rand 10000000";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertTrue(x.get() > 100);

    // random with non-numbers
    input = "make :x rand true";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "Random", ValueToken.class, Double.class),
          e.getMessage());
    }

    // random with incorrect number of parameters
    input = "make :x rand";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Random", e.getMessage());
    }
  }

  @Test
  public void testRandomRange() {
    // random range
    String input = "make :x randr 1 2";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertTrue(x.get() < 2);
    assertTrue(x.get() > 1);

    input = "make :x randr 0.01 0.02";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertTrue(x.get() < 0.02);
    assertTrue(x.get() > 0.01);

    input = "make :x randr 10000000 10000001";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertTrue(x.get() > 10000000);
    assertTrue(x.get() < 10000001);

    // random range with non-numbers
    input = "make :x randr true 3";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "RandomRange", ValueToken.class, Double.class),
          e.getMessage());
    }

    // random range with incorrect number of parameters
    input = "make :x randr 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator RandomRange", e.getMessage());
    }
  }

  @Test
  public void testRemainder() {
    // remainder
    String input = "make :x 2 make :y 3 make :z % :x :y";
    interpreter.interpret(input);
    Variable<Double> z = getVar("interpreter-:z");
    assertEquals(2.0, z.get());

    // remainder with non-numbers
    input = "make :x < 2 1 make :y 3 make :z % :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(false);
      assertEquals(checkSubtypeErrorMsg(t, "Remainder", ValueToken.class, Double.class),
          e.getMessage());
    }

    // remainder with incorrect number of parameters
    input = "make :x 1 make :y % :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Remainder", e.getMessage());
    }
  }

  @Test
  public void testRemoveClass(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj = new GameObject(null);
    obj.addClass("test");
    idManager.addObject(obj, "obj");
    assertTrue(obj.usesClass("test"));

    String input = "removeclass \"test :game_obj";
    interpreter.interpret(input);
    assertFalse(obj.usesClass("test"));

    // test with non-string
    input = "removeclass 1 :game_obj";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.0);
      assertEquals(checkSubtypeErrorMsg(t, "RemoveClass", ValueToken.class, String.class),
              e.getMessage());
    }
  }

  @Test
  public void testRemoveItem() {
    // remove item
    String input = "make :x [ 1 2 3 ] delitem 1 :x";
    interpreter.interpret(input);
    Variable<List<Double>> y = getVar("interpreter-:x");
    assertEquals(2, y.get().size());
    assertEquals(1.0, y.get().get(0), 0.0001);
    assertEquals(3.0, y.get().get(1), 0.0001);

    // remove item with non-numbers
    input = "make :x [ 1 2 3 ] delitem [ 1 2 ] :x ";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ExpressionToken t = new ExpressionToken();
      t.addToken(new ValueToken<>(1.), new Environment());
      t.addToken(new ValueToken<>(2.), new Environment());
      assertEquals(checkTypeErrorMsg(t, "RemoveItem", ValueToken.class, VariableToken.class),
          e.getMessage());
    }

    // remove item with incorrect number of parameters
    input = "make :x [ 1 2 3 ] make :y delitem :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator RemoveItem", e.getMessage());
    }
  }

  @Test
  public void testRemovePiece(){
    game.noFSMInit(2);
    useGameVars();
    GameObject obj1 = new GameObject(game.getPlayer(0));
    GameObject obj2 = new GameObject(game.getPlayer(1));
    List<DropZone> board = BoardCreator.createGrid(3, 3);
    game.addElement(obj1, "obj1");
    game.addElement(obj2, "obj2");
    for (DropZone dz : board) {
      game.addElement(dz);
    }
    game.putInDropZone(obj1, board.get(0));

    // remove piece
    String input = "removepiece :game_obj1";
    interpreter.interpret(input);
    assertEquals(0, board.get(0).getAllObjects().size());
    assertFalse(idManager.isIdInUse("obj1"));


  }

  @Test
  public void testReturn(){
    // return null
    String input = "to f [ ] [ make :game_log [ ] return; additem 1 :game_log ] f";
    interpreter.interpret(input);
    Variable<List<Double>> log = getVar("log");
    assertEquals(0, log.get().size());

    // return with number
    input = "to f [ ] [ return 1 ] make :x f";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(1.0, x.get());

    // return from inside if statement
    input = "to f [ :x ] [ if > :x 0 [ return 1 ] return 2 ] make :x f 5";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(1.0, x.get());

    // return from inside a loop
    input = "to f [ :x ] [ repeat :x [ return 1 ] return 2 ] make :x f 5";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(1.0, x.get());

    // return with too few parameters
    input = "to f [ ] [ return ] make :x f";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Return", e.getMessage());
    }

  }

  @Test
  public void testRepeat() {
    // repeat
    String input = "make :x 0 repeat 3 [ global :x make :x + :x 1 ]";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(3.0, x.get());

    // repeat multiple times with set notation
    input = "make :x 0 ( repeat 3 [ make :x + :x 50 ] 2 [ make :x - :x 20 ] 1 [ make :x + :x 2 ] ) ";
    interpreter.interpret(input);
    x = getVar("interpreter-:x");
    assertEquals(112.0, x.get());

    // repeat with non-numbers
    input = "make :x 0 repeat true [ global :x make :x + :x 1 ]";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "Repeat", ValueToken.class, Double.class),
          e.getMessage());
    }

    // repeat with incorrect parameters
    input = "make :x 0 repeat [ make :x + :x 1 ] + 1 2";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      OperatorToken t = new Sum();
      assertEquals(checkTypeErrorMsg(t, "Repeat", ExpressionToken.class), e.getMessage());
    }

    // repeat with incorrect number of parameters
    input = "make :x 0 repeat 3";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Repeat", e.getMessage());
    }
  }

  @Test
  public void testSetItem() {
    // setitem
    String input = "make :x [ 1 2 3 ] make :y 4 setitem 1 :x :y";
    interpreter.interpret(input);
    Variable<List<Object>> x = getVar("interpreter-:x");
    List<Double> expected = new ArrayList<>(Arrays.asList(1.0, 4.0, 3.0));
    assertEquals(expected, x.get());

    // setitem with non-numbers
    input = "make :x [ 1 2 3 ] make :y true setitem :x 1 :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.);
      assertEquals(checkTypeErrorMsg(t, "SetItem", ExpressionToken.class), e.getMessage());
    }

    // setitem with incorrect number of parameters
    input = "make :x [ 1 2 3 ] make :y 4 setitem :x 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator SetItem", e.getMessage());
    }
  }

  @Test
  public void setVariableFromId(){
    game.noFSMInit(2);
    useGameVars();

    Variable<Double> x = new Variable<>(0.0, null);
    idManager.addObject(x, "x");

    String input = "setfromid \"x 5";
    interpreter.interpret(input);
    assertEquals(5.0, x.get());
  }

  @Test
  public void setObjectImage(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj = new GameObject(game.getPlayer(0));
    game.addElement(obj, "obj");
    String input = "setobjimg :game_obj \"test_image.png";
    interpreter.interpret(input);
    assertEquals("assets/test_image.png", game.getObjImage(obj));
  }

  @Test
  public void testSetPlayerOwner(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj = new GameObject(game.getPlayer(0));
    game.addElement(obj, "obj");

    String input = "setplayerowner :game_obj getplayer 1";
    interpreter.interpret(input);
    System.out.println("Player 0: " + game.getPlayer(0) + ", Player 1: " + game.getPlayer(1));
    assertEquals(game.getPlayer(1), obj.getOwner());

    // test with non-player
    input = "setplayerowner :game_obj 1";
    try {
        interpreter.interpret(input);
        fail();
    } catch (Exception e) {
        ValueToken<?> t = new ValueToken<>(1.);
        assertEquals(checkSubtypeErrorMsg(t, "SetPlayerOwner", ValueToken.class, Owner.class), e.getMessage());
    }

  }

  @Test
  public void setObjectOwner(){
    game.noFSMInit(2);
    useGameVars();

    GameObject obj = new GameObject(game.getPlayer(0));
    game.addElement(obj, "obj");
    GameObject obj2 = new GameObject(game.getPlayer(0));
    game.addElement(obj2, "obj2");

    String input = "setobjowner :game_obj :game_obj2";
    interpreter.interpret(input);
    OwnableSearchStream sstream = new OwnableSearchStream(idManager);
    List<Ownable> owned = idManager.objectStream().filter(sstream.isOwnedByOwnable(obj2)).toList();
    assertEquals(1, owned.size());
    assertEquals(obj, owned.get(0));

    // test with non-player
    input = "setobjowner :game_obj 1";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(1.);
      assertEquals(checkSubtypeErrorMsg(t, "SetObjOwner", ValueToken.class, Ownable.class), e.getMessage());
    }

  }

  @Test
  public void testSine() {
    // sine
    String input = "make :x 0 make :y sin :x";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:y");
    assertEquals(0.0, y.get(), 0.0001);

    // sine of 3
    input = "make :x 117 make :y sin :x";
    interpreter.interpret(input);
    y = getVar("interpreter-:y");
    assertEquals(0.8910065241883679, y.get());

    // sine with non-numbers
    input = "make :x true make :y sin :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "Sine", ValueToken.class, Double.class), e.getMessage());
    }

    // sine with incorrect number of parameters
    input = "sin";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Sine", e.getMessage());
    }
  }

  @Test
  public void testSquareRoot() {
    // square root
    String input = "make :x 0 make :y sqrt :x";
    interpreter.interpret(input);
    Variable<Double> x = getVar("interpreter-:x");
    assertEquals(0.0, x.get(), 0.0001);

    // square root of 4
    input = "make :x 4 make :y sqrt :x";
    interpreter.interpret(input);
    x = getVar("interpreter-:y");
    assertEquals(2.0, x.get(), 0.0001);

    // square root of 84
    input = "make :x 84 make :y sqrt :x";
    interpreter.interpret(input);
    x = getVar("interpreter-:y");
    assertEquals(9.16515138991168, x.get(), 0.0001);

    // square root with set notation
    input = "make :x 8 make :y ( sqrt :x :x )";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals(
          "Invalid syntax: Set called with operator <Operator SquareRoot SquareRoot[1]> that takes less than 2 arguments",
          e.getMessage());
    }

    // square root with non-numbers
    input = "make :x true make :y sqrt :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "SquareRoot", ValueToken.class, Double.class),
          e.getMessage());
    }

    // square root with incorrect number of parameters
    input = "sqrt";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator SquareRoot", e.getMessage());
    }
  }

  @Test
  public void testSum() {
    // sum
    String input = "make :x 0 make :y 0 make :z + :x :y";
    interpreter.interpret(input);
    Variable<Double> i = getVar("interpreter-:z");

    assertEquals(0.0, i.get());

    // sum of 1 and 2
    input = "make :x 1 make :y 2 make :z + :x :y";
    interpreter.interpret(input);
    i = getVar("interpreter-:z");
    assertEquals(3.0, i.get());

    // test with set notation
    input = "make :z ( + 1 2 )";
    interpreter.interpret(input);
    i = getVar("interpreter-:z");
    assertEquals(3.0, i.get());

    // test with more set notation
    input = "make :z ( + 1 2 3 4 5 )";
    interpreter.interpret(input);
    i = getVar("interpreter-:z");
    assertEquals(15.0, i.get());

    // sum with non-numbers
    input = "make :x true make :y 3 make :z + :x :y";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "Sum", ValueToken.class, Double.class, String.class),
          e.getMessage());
    }

    // sum with incorrect number of parameters
    input = "make :x 1 make :y + :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Sum", e.getMessage());
    }
  }

  @Test
  public void testTangent() {
    // tangent
    String input = "make :x 0 make :y tan :x";
    interpreter.interpret(input);
    Variable<Double> i = getVar("interpreter-:y");
    assertEquals(0.0, i.get());

    // tangent of 3
    input = "make :x 199 make :y tan :x";
    interpreter.interpret(input);
    i = getVar("interpreter-:y");
    assertEquals(0.3443276132896654, i.get());

    // tangent with non-numbers
    input = "make :x true make :y tan :x";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      ValueToken<?> t = new ValueToken<>(true);
      assertEquals(checkSubtypeErrorMsg(t, "Tangent", ValueToken.class, Double.class),
          e.getMessage());
    }

    // tangent with incorrect number of parameters
    input = "tan";
    try {
      interpreter.interpret(input);
      fail();
    } catch (Exception e) {
      assertEquals("Invalid syntax: Not enough arguments for operator Tangent", e.getMessage());
    }
  }

  @Test
  public void testGame_() {
    Variable<Double> x = new Variable<>(5.0);
    idManager.addObject(x);
    String name = idManager.getId(x);
    String input = "make :y :game_" + name + " make :z + 1 :y";
    interpreter.interpret(input);
    Variable<Double> y = getVar("interpreter-:z");
    assertEquals(6.0, y.get());

    Variable<Integer> x2 = new Variable<>(2);
    idManager.addObject(x2);
    name = idManager.getId(x2);
    input = "make :y + 1 :game_" + name;
    interpreter.interpret(input);
    y = getVar("interpreter-:y");
    assertEquals(3, y.get());

    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    List<Double> expected = new ArrayList<>(Arrays.asList(1., 2., 3., 4., 5.));
    Variable<List<Integer>> listVar = new Variable<>(list);
    idManager.addObject(listVar);
    name = idManager.getId(listVar);
    input = "make :y :game_" + name;
    interpreter.interpret(input);
    Variable<List<Double>> yList = getVar("interpreter-:y");
    assertEquals(expected, yList.get());

  }

  @Test
  public void testErrorsInOtherLanguages() {

    String[] languages = {"Spanish", "Portuguese", "Chinese"};

    for (String language : languages) {
      interpreter.setLanguage(language);
      setLanguage(language);

      String input = "make :x true make :y 3 make :z + :x :y";
      try {
        interpreter.interpret(input);
        fail();
      } catch (Exception e) {
        ValueToken<?> t = new ValueToken<>(true);
        assertEquals(checkSubtypeErrorMsg(t, "Sum", ValueToken.class, Double.class, String.class),
            e.getMessage());
        System.out.println(e.getMessage());
      }

      input = "dotimes [ 6 5 ] [ 5 ]";
      try {
        interpreter.interpret(input);
        fail();
      } catch (Exception e) {
        ValueToken<?> t = new ValueToken<>(6.);
        assertEquals(checkTypeErrorMsg(t, "DoTimes", VariableToken.class), e.getMessage());
        System.out.println(e.getMessage());
      }
    }
  }

  @Test
  public void getObjectAttribute() {
    GameObject obj = new GameObject(null);
    Variable<Double> score = new Variable<>(5.0);
    idManager.addObject(obj, "obj");
    idManager.addObject(score, "score", obj);
    String input = "attr :game_obj \"score";
    interpreter.interpret(input);

  }
}