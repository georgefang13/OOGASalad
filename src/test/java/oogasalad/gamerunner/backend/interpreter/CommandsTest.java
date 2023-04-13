package oogasalad.gamerunner.backend.interpreter;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.gamerunner.backend.interpreter.commands.control.UserInstruction;
import oogasalad.gamerunner.backend.interpreter.commands.operators.Sum;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {
    private Interpreter interpreter;
    private IdManager<Ownable> idManager;
    private static final String LANGUAGE_RESOURCE_PATH = "backend.interpreter.languages";
    ResourceBundle resources;
    private String language = "English";
    @BeforeEach
    public void setUp() {
        interpreter = new Interpreter();
        idManager = new IdManager<>();
        interpreter.link(idManager);
        language = "English";
        resources = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + "." + language);
    }

    private void setLanguage(String language){
        this.language = language;
        resources = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + "." + language);
    }

    private <T> T getVar(String name){
        return (T) idManager.getObject(name);
    }

    private String checkSubtypeErrorMsg(Token t, String NAME, Class<?> type, Class<?>... subtype){
        String s = resources.getString("argumentSubtypeError");
        String[] simplenames = new String[subtype.length];
        for (int i = 0; i < subtype.length; i++){
            simplenames[i] = subtype[i].getSimpleName();
        }
        s = String.format(s, t, NAME, type.getSimpleName(), String.join(" or ", simplenames), t.getClass().getSimpleName(), t.SUBTYPE);
        return s;
    }

    private String checkTypeErrorMsg(Token t, String NAME, Class<?>... type){
        String s = resources.getString("argumentTypeError");
        String[] simplenames = new String[type.length];
        for (int i = 0; i < type.length; i++){
            simplenames[i] = type[i].getSimpleName();
        }
        s = String.format(s, t, NAME, String.join(" or ", simplenames), t == null ? "null" : t.getClass().getSimpleName());
        return s;
    }

    @Test
    public void testAddDropZoneItem(){
        DropZone dz = new DropZone("dz");
        idManager.addObject(dz, "dz");

        // add item to dropzone
        String input = "putdzitem \"obj 1 :game_dz";
        interpreter.interpret(input);
        List<Double> expected = new ArrayList<>(List.of(1.));
        assertEquals(expected, dz.getAllObjects());

        // add item to dropzone with non-string
        input = "putdzitem 1 1 :game_dz";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Token t = new ValueToken<>(1.);
            assertEquals(checkSubtypeErrorMsg(t, "PutDropZoneItem", ValueToken.class, String.class), e.getMessage());
        }
    }

    @Test
    public void testAddItem(){
        // add item to list
        String input = "make :x [ ] additem 1 :x";
        interpreter.interpret(input);
        Variable<List> a = getVar("interpreter-:x");
        List<Double> expected = new ArrayList<>(List.of(1.));
        assertEquals(expected, a.get());

        // add item to list with non-list
        input = "make :x 1 additem 1 :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Token t = new ValueToken<>(1.);
            assertEquals(checkTypeErrorMsg(t, "AddItem", ExpressionToken.class), e.getMessage());
        }

        // add item to list with non-number
        input = "make :x [ ] additem \"1 :x additem \"hello :x";
        interpreter.interpret(input);
        List<String> expected2 = new ArrayList<>(List.of("1", "hello"));
        a = getVar("interpreter-:x");
        assertEquals(expected2, a.get());
    }

    @Test
    public void testAnd(){
        // true and
        String input = "make :a < 1 2 make :b < 2 3 make :x and :a :b";
        interpreter.interpret(input);
        Variable<Boolean> x = getVar("interpreter-:x");
        assertTrue(x.get());

        // false and
        input = "make :a < 1 2 make :b < 3 2 make :x and :a :b";
        interpreter.interpret(input);
        x = getVar("interpreter-:x");
        assertFalse(x.get());

        // and with non-booleans
        input = "make :a 1 make :b 2 make :x and :a :b";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Token t = new ValueToken<>(1.);
            assertEquals(checkSubtypeErrorMsg(t, "And", ValueToken.class, Boolean.class), e.getMessage());
        }
    }

    @Test
    public void testArcTangent(){
        // arc tangent
        String input = "make :x 1 make :z atan :x";
        interpreter.interpret(input);
        Variable<Double> z = getVar("interpreter-:z");
        assertEquals(45.0, z.get());

        // arc tangent with non-numbers
        input = "make :x < 2 1 make :z atan :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "ArcTangent", ValueToken.class, Double.class), e.getMessage());
        }
    }

    @Test
    public void testCall(){
        String input = "make :x fvar + make :y call :x [ 1 2 ]";
        interpreter.interpret(input);
        Variable<Double> y = getVar("interpreter-:y");
        assertEquals(3., y.get());

        input = "to pythag [ :a :b ] [ make :c sqrt ( + ( * :a :a ) ( * :b :b ) ) :c ] make :x fvar pythag make :y call :x [ 3 4 ]";
        interpreter.interpret(input);
        y = getVar("interpreter-:y");
        assertEquals(5., y.get());

        // call with wrong number of arguments
        input = "make :x fvar + make :y call :x [ 1 2 3 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Token t = new ValueToken<>(1.);
            assertEquals("Incorrect number of arguments passed to operator Sum. Expected 2 but got 3.", e.getMessage());
        }

        // call with non-function
        input = "make :x 1 make :y call :x [ 1 2 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Token t = new ValueToken<>(1.);
            assertEquals(checkSubtypeErrorMsg(t, "Call", ValueToken.class, OperatorToken.class), e.getMessage());
        }
    }

    @Test
    public void testCosine(){
        // cosine
        String input = "make :x 38 make :z cos :x";
        interpreter.interpret(input);
        Variable<Double> z = getVar("interpreter-:z");
        assertEquals(0.788010753606722, z.get());

        // cosine with non-numbers
        input = "make :x < 2 1 make :z cos :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "Cosine", ValueToken.class, Double.class), e.getMessage());
        }
    }

    @Test
    public void testDel(){
        // del
        String input = "make :x 1 del :x";
        interpreter.interpret(input);
        try {
            getVar("interpreter-:x");
            fail();
        } catch (Exception e){
            assertEquals("Id \"interpreter-:x\" not found.", e.getMessage());
        }

        // del with non-variables
        input = "make :x 1 del 1";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Token t = new ValueToken<>(1.);
            assertEquals(checkTypeErrorMsg(t, "Del", VariableToken.class), e.getMessage());
        }

        // del with non-existing variable
        input = "del :yegor";
        interpreter.interpret(input);

        // del with not enough parameters
        input = "del";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Del", e.getMessage());
        }

    }

    @Test
    public void testDifference(){
        // difference
        String input = "make :x 1 make :y 2 make :z - :x :y";
        interpreter.interpret(input);
        Variable<Double> z = getVar("interpreter-:z");
        assertEquals(-1.0, z.get());

        // difference with non-numbers
        input = "make :x < 2 1 make :y 2 make :z - :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "Difference", ValueToken.class, Double.class), e.getMessage());
        }
    }

    @Test
    public void testDropZonePaths(){
        List<DropZone> grid = BoardCreator.createGrid(5, 5);
        for (DropZone d : grid){
            idManager.addObject(d, d.getId());
        }

        String input = "make :x fromgame \"3,3 make :y dzpaths :x";
        interpreter.interpret(input);
        Variable<List<String>> y = getVar("interpreter-:y");
        List<String> expected = new ArrayList<>(List.of("Up", "Down", "Left", "Right", "UpLeft", "UpRight", "DownLeft", "DownRight"));

        assertEquals(new HashSet<>(expected), new HashSet<>(y.get()));

        // dzpaths with non-dropzones
        input = "make :x 1 make :y dzpaths :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.);
            assertEquals(checkSubtypeErrorMsg(t, "DropZonePaths", ValueToken.class, DropZone.class), e.getMessage());
        }

        // dzpaths with non-existing dropzone
        input = "make :x fromgame \"8,8 make :y dzpaths :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals(checkTypeErrorMsg(null, "DropZonePaths", ValueToken.class) , e.getMessage());
        }
    }

    @Test
    public void testDoTimes(){
        // do times
        String input = "make :x [ -1 -1 -1 -1 -1 ] dotimes [ :i 5 ] [ setitem :i :x :i ]";
        interpreter.interpret(input);
        Variable<List<Object>> x = getVar("interpreter-:x");
        for (int i = 0; i < 5; i++){
            assertEquals((double)i, x.get().get(i));
        }

        // dotimes with non-numbers
        input = "dotimes [ :i < 2 1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "DoTimes", ValueToken.class, Double.class), e.getMessage());
        }

        // dotimes with non-variable
        input = "dotimes [ 6 5 ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(6.);
            assertEquals(checkTypeErrorMsg(t, "DoTimes", VariableToken.class), e.getMessage());
        }

        //dotimes with no variable
        input = "dotimes [ 6 ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat with 1 arguments", e.getMessage());
        }

        //dotimes with no repeat number
        input = "dotimes [ :i ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat with 1 arguments", e.getMessage());
        }

        //dotimes with incorrect number of arguments
        input = "dotimes [ :i 6 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator DoTimes", e.getMessage());
        }
    }

    @Test
    public void testEqual(){
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
        input = "make :x > 1 2 make :y > 3 4 make :z == :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Equal", e.getMessage());
        }
    }

    @Test
    public void testFollowDropZonePath(){
        List<DropZone> grid = BoardCreator.createGrid(8, 8);
        for (DropZone d : grid){
            idManager.addObject(d, d.getId());
        }

        // follow the path of a knight, starting in the top left corner
        String input = "make :x fromgame \"0,0 make :y dzfollow :x [ \"Down \"DownRight ]";
        interpreter.interpret(input);
        Variable<DropZone> y = getVar("interpreter-:y");
        assertEquals("2,1", y.get().getId());

    }

    @Test
    public void testDropZoneFollowUntilBlocked(){
        List<DropZone> grid = BoardCreator.createGrid(8, 8);
        for (DropZone d : grid){
            idManager.addObject(d, d.getId());
        }
        /*
        | 0,0 | 0,1 | 0,2 | XXX | 0,4 | 0,5 | 0,6 | 0,7 |
        | 1,0 | 1,1 | 1,2 | 1,3 | 1,4 | 1,5 | OOO | 1,7 |
        | 2,0 | 2,1 | 2,2 | 2,3 | 2,4 | XXX | 2,6 | 2,7 |
        | 3,0 | 3,1 | 3,2 | 3,3 | 3,4 | 3,5 | 3,6 | 3,7 |
        | 4,0 | 4,1 | 4,2 | OOO | 4,4 | 4,5 | 4,6 | 4,7 |
        | 5,0 | 5,1 | 5,2 | 5,3 | 5,4 | 5,5 | 5,6 | 5,7 |
        | 6,0 | 6,1 | 6,2 | 6,3 | 6,4 | 6,5 | 6,6 | 6,7 |
        | 7,0 | 7,1 | 7,2 | 7,3 | 7,4 | 7,5 | 7,6 | 7,7 |
         */
        ((DropZone) idManager.getObject("0,3")).putObject("obj", "X");
        ((DropZone) idManager.getObject("2,5")).putObject("obj", "X");
        ((DropZone) idManager.getObject("1,6")).putObject("obj", "O");

        String input = "to isBlocked [ :x ] [ == \"X dzitem \"obj :x ] make :x fromgame \"4,3 make :y dzfollowtoblock :x [ \"Up ] fvar isBlocked";
        interpreter.interpret(input);
        Variable<List<DropZone>> y = getVar("interpreter-:y");
        List<DropZone> expected = new ArrayList<>(List.of(
                (DropZone) idManager.getObject("3,3"),
                (DropZone) idManager.getObject("2,3"),
                (DropZone) idManager.getObject("1,3")
        ));

        assertEquals(expected, y.get());

        input = "make :y dzfollowtoblock :x [ \"Up \"Up ] fvar isBlocked";
        interpreter.interpret(input);
        y = getVar("interpreter-:y");
        expected = new ArrayList<>(List.of( (DropZone) idManager.getObject("2,3") ));
        assertEquals(expected, y.get());

        input = "make :y dzfollowtoblock :x [ \"Up \"Right ] fvar isBlocked";
        interpreter.interpret(input);
        y = getVar("interpreter-:y");
        expected = new ArrayList<>(List.of( (DropZone) idManager.getObject("3,4") ));
        assertEquals(expected, y.get());

        input = "to isBlocked [ :x ] [ == \"O dzitem \"obj :x ] make :y dzfollowtoblock :x [ \"UpRight ] fvar isBlocked";
        interpreter.interpret(input);
        y = getVar("interpreter-:y");
        expected = new ArrayList<>(List.of( (DropZone) idManager.getObject("3,4"), (DropZone) idManager.getObject("2,5") ));
        assertEquals(expected, y.get());

        input = "make :y dzfollowtoblock :x [ \"Right \"DownRight ] fvar isBlocked";
        interpreter.interpret(input);
        y = getVar("interpreter-:y");
        expected = new ArrayList<>(List.of( (DropZone) idManager.getObject("5,5"), (DropZone) idManager.getObject("6,7") ));
        assertEquals(expected, y.get());

        // test with no path
        input = "make :y dzfollowtoblock :x \"Up fvar isBlocked";
        try {
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>("Up");
            assertEquals(checkTypeErrorMsg(t, "DropZoneFollowUntilBlocked", ExpressionToken.class), e.getMessage());
        }

        // test with function that takes no inputs
        input = "to isBlocked2 [ ] [ == \"X dzitem \"obj :x ] make :y dzfollowtoblock :x [ \"UpRight ] fvar isBlocked2";
        try {
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Incorrect number of arguments passed to operator isBlocked2. Expected 0 but got 1.", e.getMessage());
        }

    }

    @Test
    public void testFor(){
        // for
        String input = "make :x [ 0 0 0 0 0 ] for [ :i 0 5 1 ] [ global :x setitem :i :x :i ]";
        interpreter.interpret(input);
        Variable<List<Object>> x = getVar("interpreter-:x");
        for (int i = 0; i < 5; i++){
            assertEquals((double)i, x.get().get(i));
        }

        // for with non-numbers
        input = "for [ :i < 2 1 1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "For", ValueToken.class, Double.class), e.getMessage());
        }

        // for with non-variable
        input = "for [ 6 5 1 ] [ 4 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(6.);
            assertEquals(checkTypeErrorMsg(t, "For", VariableToken.class), e.getMessage());
        }

        //for with no variable
        input = "for [ 6 ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat with 1 arguments", e.getMessage());
        }

        //for with no repeat number
        input = "for [ :i ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat with 1 arguments", e.getMessage());
        }

        //for with incorrect number of arguments
        input = "for [ :i 6 9 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator For", e.getMessage());
        }

        // for with stop is less than start
        input = "for [ :i 5 0 1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat from 5.0 to 0.0 by 1.0", e.getMessage());
        }

        // for with stop is equal to start
        input = "for [ :i 5 5 1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat from 5.0 to 5.0 by 1.0", e.getMessage());
        }

        // for with stop is greater than start and increment is negative
        input = "for [ :i 0 5 -1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat from 0.0 to 5.0 by -1.0", e.getMessage());
        }

    }

    @Test
    public void testForeach(){
        String input = "make :x [ 0 1 2 3 4 ] make :game_log [ ] foreach [ :i :x ] [ additem :i :game_log ]";
        interpreter.interpret(input);
        Variable<List<Object>> game_log = getVar("log");
        Variable<List<Object>> x = getVar("interpreter-:x");
        assertEquals(x.get(), game_log.get());

        // foreach with non-list
        input = "foreach [ :i 5 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(5.);
            assertEquals(checkTypeErrorMsg(t, "Foreach", ExpressionToken.class), e.getMessage());
        }

        // foreach with non-variable
        input = "foreach [ 5 [ 1 2 3 ] ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(5.);
            assertEquals(checkTypeErrorMsg(t, "Foreach", VariableToken.class), e.getMessage());
        }

        // foreach with incorrect type for variable name
        input = "foreach [ 5 [ 1 2 3 ] ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(5.);
            assertEquals(checkTypeErrorMsg(t, "Foreach", VariableToken.class), e.getMessage());
        }

        // foreach with incorrect number of header arguments
        input = "foreach [ 5 ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Foreach header must have 2 arguments", e.getMessage());
        }

        // foreach with incorrect number of header arguments
        input = "foreach [ :i :x 5 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Foreach header must have 2 arguments", e.getMessage());
        }

        // foreach with incorrect number of arguments
        input = "foreach [ :i :x ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Foreach", e.getMessage());
        }
    }

    @Test
    public void testFVar(){
        String input = "to add2 [ :x ] [ make :y :x + 2 :y ] make :z fvar add2";
        interpreter.interpret(input);
        Variable<OperatorToken> z = getVar("interpreter-:z");
        assertEquals("add2", z.get().SUBTYPE);

        input = "make :z fvar +";
        interpreter.interpret(input);
        z = getVar("interpreter-:z");
        assertEquals("Sum", z.get().SUBTYPE);

        // fvar with a non-function
        input = "make :z fvar 5";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(5.);
            assertEquals(checkTypeErrorMsg(t, "FVar", OperatorToken.class), e.getMessage());
        }

        // fvar with no arguments
        input = "make :z fvar";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator FVar", e.getMessage());
        }
    }

    @Test
    public void testGetAttribute(){
        GameObject dropZone = new DropZone("A");
        Class<?> c = GameObject.class;
        System.out.println(c.isInstance(dropZone));
        idManager.addObject(dropZone, "dz");
        String input = "make :x attr :game_dz \"id";
        interpreter.interpret(input);
        Variable<String> x = getVar("interpreter-:x");
        assertEquals("A", x.get());

        idManager.removeObject(dropZone);

        dropZone = new DropZone("Banana");
        System.out.println(c.isInstance(dropZone));
        idManager.addObject(dropZone, "dz");
        input = "make :x attr :game_dz \"id";
        interpreter.interpret(input);
        x = getVar("interpreter-:x");
        assertEquals("Banana", x.get());

    }

    @Test
    public void getDropZoneItem(){
        DropZone dropZone = new DropZone("A");
        idManager.addObject(dropZone, "dz");
        String input = "putdzitem \"obj 1 :game_dz make :x dzitem \"obj :game_dz";
        interpreter.interpret(input);
        Variable<Double> x = getVar("interpreter-:x");
        assertEquals(1., x.get());

        input = "putdzitem \"obj 2 :game_dz make :x dzitem \"obj :game_dz";
        interpreter.interpret(input);
        x = getVar("interpreter-:x");
        assertEquals(2., x.get());

        // try with a non-dropzone
        input = "make :x dzitem \"obj 2";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(2.);
            assertEquals(checkSubtypeErrorMsg(t, "GetDropZoneItem", ValueToken.class, DropZone.class), e.getMessage());
        }

    }

    @Test
    public void getDropZoneItems(){
        DropZone dropZone = new DropZone("A");
        idManager.addObject(dropZone, "dz");
        String input = "putdzitem \"obj1 1 :game_dz putdzitem \"obj2 2 :game_dz make :x dzitems :game_dz";
        interpreter.interpret(input);
        Variable<List<Object>> x = getVar("interpreter-:x");
        assertEquals(2, x.get().size());
        assertTrue(x.get().contains(1.));
        assertTrue(x.get().contains(2.));

        // try with a non-dropzone
        input = "make :x dzitems \"obj";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>("obj");
            assertEquals(checkSubtypeErrorMsg(t, "GetDropZoneItems", ValueToken.class, DropZone.class), e.getMessage());
        }

    }

    @Test
    public void testGetFromGameVariable(){
        Variable<?> xvar = new Variable<>("test");
        idManager.addObject(xvar, "0,1");

        String input = "make :x fromgame + + 0 \", 1";
        interpreter.interpret(input);
        Variable<String> x = getVar("interpreter-:x");
        assertEquals("test", x.get());

        // test fromgame from string variable
        Variable<?> yvar = new Variable<>("test2");
        idManager.addObject(yvar, "0,2");
        input = "make :y \"0,2 make :x fromgame :y";
        interpreter.interpret(input);
        x = getVar("interpreter-:x");
        assertEquals("test2", x.get());

        // test without enough arguments
        input = "make :x fromgame";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator FromGame", e.getMessage());
        }


    }

    @Test
    public void testGreaterEqual(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "GreaterEqual", ValueToken.class, Double.class), e.getMessage());
        }

        // greater equal with incorrect number of parameters
        input = "make :x 1 make :y 2 make :z >= :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator GreaterEqual", e.getMessage());
        }
    }

    @Test
    public void testGreaterThan(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "GreaterThan", ValueToken.class, Double.class), e.getMessage());
        }

        // greater than with incorrect number of parameters
        input = "make :x 1 make :y 2 make :z > :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator GreaterThan", e.getMessage());
        }
    }

    @Test
    public void testIf(){
        // if true
        String input = "make :x 1 make :y < 1 2 if :y [ make :x 3 ]";
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(2.0);
            assertEquals(checkSubtypeErrorMsg(t, "If", ValueToken.class, Boolean.class), e.getMessage());
        }

        // if with incorrect number of parameters
        input = "make :x 1 make :y < 1 2 if :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator If", e.getMessage());
        }
    }

    @Test
    public void testIfElse(){
        // if true
        String input = "make :x 1 make :y < 1 2 ifelse :y [ global :x make :x 3 ] [ global :x make :x 4 ]";
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(2.0);
            assertEquals(checkSubtypeErrorMsg(t, "IfElse", ValueToken.class, Boolean.class), e.getMessage());
        }

        // if with incorrect number of parameters
        input = "make :x 1 make :y < 1 2 ifelse :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator IfElse", e.getMessage());
        }
    }

    @Test
    public void testItem(){
        // item
        String input = "make :x [ 1 2 3 ] make :y item 1 :x";
        interpreter.interpret(input);
        Variable<Double> y = getVar("interpreter-:y");
        assertEquals(2.0, y.get(), 0.0001);

        // item with non-list
        input = "make :x 1 make :y item 1 :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.0);
            assertEquals(checkTypeErrorMsg(t, "Item", ExpressionToken.class), e.getMessage());
        }

        // item with non-number
        input = "make :x [ 1 2 3 ] make :y item :x :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            Environment env = new Environment();
            ExpressionToken t = new ExpressionToken();
            t.addToken(new ValueToken<>(1.), env);
            t.addToken(new ValueToken<>(2.), env);
            t.addToken(new ValueToken<>(3.), env);

            assertEquals(checkTypeErrorMsg(t, "Item", ValueToken.class), e.getMessage());
        }

        // item out of bounds
        input = "make :x [ 1 2 3 ] make :y item 4 :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot get index 4 from list of size 3", e.getMessage());
        }
    }

    @Test
    public void testLength(){
        // length
        String input = "make :x [ 1 2 3 ] make :y len :x";
        interpreter.interpret(input);
        Variable<Double> y = getVar("interpreter-:y");
        assertEquals(3.0, y.get(), 0.0001);

        // length with non-list
        input = "make :x 1 make :y len :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.0);
            assertEquals(checkTypeErrorMsg(t, "Length", ExpressionToken.class), e.getMessage());
        }
    }

    @Test
    public void testLessEqual(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "LessEqual", ValueToken.class, Double.class), e.getMessage());
        }

        // less equal with incorrect number of parameters
        input = "make :x 1 make :y 2 make :z <= :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator LessEqual", e.getMessage());
        }
    }

    @Test
    public void testLessThan(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "LessThan", ValueToken.class, Double.class), e.getMessage());
        }

        // less than with incorrect number of parameters
        input = "make :x 1 make :y 2 make :z < :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator LessThan", e.getMessage());
        }
    }

    @Test
    public void testMakeUserInstruction(){
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
        input = "to test [ ] [ 1 ] make :x test";
        interpreter.interpret(input);
        x = getVar("interpreter-:x");
        assertEquals(1.0, x.get());

        // make user instruction that returns pythagorean distance
        input = "to pythag [ :a :b ] [ sqrt + * :a :a * :b :b ] make :a pythag 3 4";
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator MakeUserInstruction", e.getMessage());
        }

        // make user instruction with incorrect number of parameters
        input = "to test";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator MakeUserInstruction", e.getMessage());
        }

        // make user instruction with non-variables
        input = "to test [ 1 ] [ ] test";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.0);
            assertEquals(checkTypeErrorMsg(t, "MakeUserInstruction", VariableToken.class), e.getMessage());
        }

        // make user instruction with input
        input = "to test [ :x ] [ global :z make :z + :z :x ] test 1";
        interpreter.interpret(input);
        x = getVar("interpreter-:z");
        assertEquals(2.0, x.get());

        // make user instruction with input and incorrect number of parameters
        input = "to test [ :x ] [ :x ] test";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator test", e.getMessage());
        }

    }

    @Test
    public void testMinus(){
        // minus
        String input = "make :x 1 make :y ~ :x";
        interpreter.interpret(input);
        Variable<Double> y = getVar("interpreter-:y");
        assertEquals(-1.0, y.get());

        // minus with non-numbers
        input = "make :x < 2 1 make :y ~ :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "Minus", ValueToken.class, Double.class), e.getMessage());
        }

        // minus with incorrect number of parameters
        input = "make :x 1 make :y ~";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Minus", e.getMessage());
        }
    }

    @Test
    public void testNaturalLog(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "NaturalLog", ValueToken.class, Double.class), e.getMessage());
        }

        // natural log with incorrect number of parameters
        input = "make :x 1 make :y ln";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator NaturalLog", e.getMessage());
        }
    }

    @Test
    public void testNot(){
        // not
        String input = "make :x < 1 2 make :y not :x";
        interpreter.interpret(input);
        Variable<Boolean> y = getVar("interpreter-:y");
        assertEquals(false, y.get());

        // not with non-booleans
        input = "make :x 1 make :y not :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.0);
            assertEquals(checkSubtypeErrorMsg(t, "Not", ValueToken.class, Boolean.class), e.getMessage());
        }

        // not with incorrect number of parameters
        input = "make :x 1 make :y not";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Not", e.getMessage());
        }
    }

    @Test
    public void testNotEqual(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator NotEqual", e.getMessage());
        }
    }

    @Test
    public void testOr(){
        // true or
        String input = "make :a < 1 2 make :b < 2 3 make :x or :a :b";
        interpreter.interpret(input);
        Variable<Boolean> x = getVar("interpreter-:x");
        assertEquals(true, x.get());

        // true or
        input = "make :a < 1 2 make :b < 3 2 make :x or :a :b";
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
        try{
            interpreter.interpret(input);
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.0);
            assertEquals(checkSubtypeErrorMsg(t, "Or", ValueToken.class, Boolean.class), e.getMessage());
        }
    }

    @Test
    public void testPower(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "Power", ValueToken.class, Double.class), e.getMessage());
        }

        // power with incorrect number of parameters
        input = "make :x 1 make :y pow :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Power", e.getMessage());
        }
    }

    @Test
    public void testProduct(){
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
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Product", e.getMessage());
        }

        // product with a string
        input = "make :x 1 make :y \"hello make :z * :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch(Exception e){
            ValueToken<?> t = new ValueToken<>("hello");
            assertEquals(checkSubtypeErrorMsg(t, "Product", ValueToken.class, Double.class, Boolean.class), e.getMessage());
        }
    }

    @Test
    public void testQuotient(){
        // quotient
        String input = "make :x 2 make :y 3 make :z / :x :y";
        interpreter.interpret(input);
        Variable<Double> z = getVar("interpreter-:z");
        assertEquals(2./3., z.get());

        // quotient with non-numbers
        input = "make :x < 2 1 make :y 3 make :z / :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "Quotient", ValueToken.class, Double.class), e.getMessage());
        }

        // quotient with incorrect number of parameters
        input = "make :x 1 make :y / :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Quotient", e.getMessage());
        }
    }

    @Test
    public void testRandom(){
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
        input = "make :x rand < 1 2";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "Random", ValueToken.class, Double.class), e.getMessage());
        }

        // random with incorrect number of parameters
        input = "make :x rand";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Random", e.getMessage());
        }
    }

    @Test
    public void testRandomRange(){
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
        input = "make :x randr < 1 2 3";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "RandomRange", ValueToken.class, Double.class), e.getMessage());
        }

        // random range with incorrect number of parameters
        input = "make :x randr 1";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator RandomRange", e.getMessage());
        }
    }

    @Test
    public void testRemainder(){
        // remainder
        String input = "make :x 2 make :y 3 make :z % :x :y";
        interpreter.interpret(input);
        Variable<Double> z = getVar("interpreter-:z");
        assertEquals(2.0, z.get());

        // remainder with non-numbers
        input = "make :x < 2 1 make :y 3 make :z % :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(false);
            assertEquals(checkSubtypeErrorMsg(t, "Remainder", ValueToken.class, Double.class), e.getMessage());
        }

        // remainder with incorrect number of parameters
        input = "make :x 1 make :y % :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Remainder", e.getMessage());
        }
    }

    @Test
    public void RemoveItem(){
        // remove item
        String input = "make :x [ 1 2 3 ] delitem 1 :x";
        interpreter.interpret(input);
        Variable<List<Double>> y = getVar("interpreter-:x");
        assertEquals(2, y.get().size());
        assertEquals(1.0, y.get().get(0), 0.0001);
        assertEquals(3.0, y.get().get(1), 0.0001);

        // remove item with non-numbers
        input = "make :x [ 1 2 3 ] delitem [ 1 2 ] :x ";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ExpressionToken t = new ExpressionToken();
            t.addToken(new ValueToken<>(1.), new Environment());
            t.addToken(new ValueToken<>(2.), new Environment());
            assertEquals(checkTypeErrorMsg(t, "RemoveItem", ValueToken.class, VariableToken.class), e.getMessage());
        }

        // remove item with incorrect number of parameters
        input = "make :x [ 1 2 3 ] make :y delitem :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator RemoveItem", e.getMessage());
        }
    }

    @Test
    public void testRepeat(){
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
        input = "make :x 0 repeat < 1 2 [ global :x make :x + :x 1 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "Repeat", ValueToken.class, Double.class), e.getMessage());
        }

        // repeat with incorrect parameters
        input = "make :x 0 repeat [ make :x + :x 1 ] + 1 2";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            OperatorToken t = new Sum();
            assertEquals(checkTypeErrorMsg(t, "Repeat", ExpressionToken.class), e.getMessage());
        }

        // repeat with incorrect number of parameters
        input = "make :x 0 repeat 3";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Repeat", e.getMessage());
        }
    }

    @Test
    public void testSetItem(){
        // setitem
        String input = "make :x [ 1 2 3 ] make :y 4 setitem 1 :x :y";
        interpreter.interpret(input);
        Variable<List<Object>> x = getVar("interpreter-:x");
        List<Double> expected = new ArrayList<>(Arrays.asList(1.0, 4.0, 3.0));
        assertEquals(expected, x.get());

        // setitem with non-numbers
        input = "make :x [ 1 2 3 ] make :y < 1 2 setitem :x 1 :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(1.);
            assertEquals(checkTypeErrorMsg(t, "SetItem", ExpressionToken.class), e.getMessage());
        }

        // setitem with incorrect number of parameters
        input = "make :x [ 1 2 3 ] make :y 4 setitem :x 1";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator SetItem", e.getMessage());
        }
    }

    @Test
    public void testSine(){
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
        input = "make :x < 1 2 make :y sin :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "Sine", ValueToken.class, Double.class), e.getMessage());
        }

        // sine with incorrect number of parameters
        input = "sin";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Sine", e.getMessage());
        }
    }

    @Test
    public void testSquareRoot(){
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
            assertEquals("Invalid syntax: Set called with operator <Operator SquareRoot[1]> that takes less than 2 arguments", e.getMessage());
        }

        // square root with non-numbers
        input = "make :x < 1 2 make :y sqrt :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "SquareRoot", ValueToken.class, Double.class), e.getMessage());
        }

        // square root with incorrect number of parameters
        input = "sqrt";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator SquareRoot", e.getMessage());
        }
    }

    @Test
    public void testSum(){
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
        input = "make :x < 1 2 make :y 3 make :z + :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "Sum", ValueToken.class, Double.class, String.class), e.getMessage());
        }

        // sum with incorrect number of parameters
        input = "make :x 1 make :y + :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Sum", e.getMessage());
        }
    }

    @Test
    public void testTangent(){
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
        input = "make :x < 1 2 make :y tan :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            ValueToken<?> t = new ValueToken<>(true);
            assertEquals(checkSubtypeErrorMsg(t, "Tangent", ValueToken.class, Double.class), e.getMessage());
        }

        // tangent with incorrect number of parameters
        input = "tan";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Tangent", e.getMessage());
        }
    }

    @Test
    public void testGame_(){
        Variable<Double> x = new Variable<>(5.0);
        idManager.addObject(x);
        String name = idManager.getId(x);
        String input = "make :y :game_" + name;
        interpreter.interpret(input);
        Variable<Double> y = getVar("interpreter-:y");
        assertEquals(5.0, y.get());

        Variable<Integer> x2 = new Variable<>(2);
        idManager.addObject(x2);
        name = idManager.getId(x2);
        input = "make :y :game_" + name;
        interpreter.interpret(input);
        y = getVar("interpreter-:y");
        assertEquals(2, y.get());

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
    public void testErrorsInOtherLanguages(){

        String[] languages = {"Spanish", "Portuguese", "Chinese"};

        for (String language : languages) {
            interpreter.setLanguage(language);
            setLanguage(language);

            String input = "make :x < 1 2 make :y 3 make :z + :x :y";
            try {
                interpreter.interpret(input);
                fail();
            } catch (Exception e) {
                ValueToken<?> t = new ValueToken<>(true);
                assertEquals(checkSubtypeErrorMsg(t, "Sum", ValueToken.class, Double.class, String.class), e.getMessage());
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
    public void getObjectAttribute(){
        GameObject obj = new GameObject(null);
        Variable<Double> score = new Variable<>(5.0);
        idManager.addObject(obj, "obj");
        idManager.addObject(score, "score", obj);
        String input = "attr :game_obj \"score";
        interpreter.interpret(input);

    }
}