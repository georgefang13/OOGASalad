package oogasalad.gamerunner.backend.interpreter;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {
    private Interpreter interpreter;
    private IdManager<Ownable> idManager;
    @BeforeEach
    public void setUp() {
        interpreter = new Interpreter();
        idManager = new IdManager<>();
        interpreter.link(idManager);
    }

    @Test
    public void testAnd(){
        // true and
        String input = "make :a < 1 2 make :b < 2 3 make :x and :a :b";
        interpreter.interpret(input);
        Variable<Boolean> x = (Variable<Boolean>) idManager.getObject("interpreter-:x");
        assertTrue(x.get());

        // false and
        input = "make :a < 1 2 make :b < 3 2 make :x and :a :b";
        interpreter.interpret(input);
        x = (Variable<Boolean>) idManager.getObject("interpreter-:x");
        assertFalse(x.get());

        // and with non-booleans
        input = "make :a 1 make :b 2 make :x and :a :b";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take AND of non-boolean from <Variable :a> = <Value 1.0>", e.getMessage());
        }
    }

    @Test
    public void testArcTangent(){
        // arc tangent
        String input = "make :x 1 make :z atan :x";
        interpreter.interpret(input);
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(45.0, z.get());

        // arc tangent with non-numbers
        input = "make :x < 2 1 make :z atan :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take arctan of non-number from <Variable :x> = <Value false>", e.getMessage());
        }
    }

    @Test
    public void testCosine(){
        // cosine
        String input = "make :x 38 make :z cos :x";
        interpreter.interpret(input);
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(0.788010753606722, z.get());

        // cosine with non-numbers
        input = "make :x < 2 1 make :z cos :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take cosine of non-number from <Variable :x> = <Value false>", e.getMessage());
        }
    }

    @Test
    public void testDifference(){
        // difference
        String input = "make :x 1 make :y 2 make :z - :x :y";
        interpreter.interpret(input);
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(-1.0, z.get());

        // difference with non-numbers
        input = "make :x < 2 1 make :y 2 make :z - :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take difference of non-number from <Variable :x> = <Value false>", e.getMessage());
        }
    }

    @Test
    public void testDoTimes(){
        // do times
        String input = "make :x [ -1 -1 -1 -1 -1 ] dotimes [ :i 5 ] [ setitem :i :x :i ]";
        interpreter.interpret(input);
        Variable<List<Object>> x = (Variable<List<Object>>) idManager.getObject("interpreter-:x");
        for (int i = 0; i < 5; i++){
            assertEquals((double)i, x.get().get(i));
        }

        // dotimes with non-numbers
        input = "dotimes [ :i < 2 1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat non-number of times from <Operator LessThan[2]> = <Value false>", e.getMessage());
        }

        // dotimes with non-variable
        input = "dotimes [ 6 5 ] [ 5 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat with non-variable <Value 6.0>", e.getMessage());
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
        Variable<Boolean> z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // not equal
        input = "make :x 1 make :y 2 make :z == :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // equal with non-numbers
        input = "make :x < 2 1 make :y 2 make :z == :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take equality of non-number from <Variable :x> = <Value false>", e.getMessage());
        }

        // equal with incorrect number of parameters
        input = "make :x 1 make :y 2 make :z == :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Equal", e.getMessage());
        }
    }

    @Test
    public void testFor(){
        // for
        String input = "make :x [ 0 0 0 0 0 ] for [ :i 0 5 1 ] [ global :x setitem :i :x :i ]";
        interpreter.interpret(input);
        Variable<List<Object>> x = (Variable<List<Object>>) idManager.getObject("interpreter-:x");
        for (int i = 0; i < 5; i++){
            assertEquals((double)i, x.get().get(i));
        }

        // for with non-numbers
        input = "for [ :i < 2 1 1 ] [ :i ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat non-number of times from <Operator LessThan[2]> = <Value false>", e.getMessage());
        }

        // for with non-variable
        input = "for [ 6 5 1 ] [ 4 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot loop with non-variable <Value 6.0>", e.getMessage());
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
    public void testGreaterEqual(){
        // greater equal
        String input = "make :x 1 make :y 1 make :z >= :x :y";
        interpreter.interpret(input);
        Variable<Boolean> z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // greater
        input = "make :x 2 make :y 1 make :z >= :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // not greater equal
        input = "make :x 1 make :y 2 make :z >= :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // greater equal with non-numbers
        input = "make :x < 2 1 make :y 2 make :z >= :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take >= of non-number from <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Boolean> z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // not greater than
        input = "make :x 1 make :y 2 make :z > :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // not greater than (equals)
        input = "make :x 1 make :y 1 make :z > :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // greater than with non-numbers
        input = "make :x < 2 1 make :y 2 make :z > :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take > of non-number from <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(3.0, x.get());

        // if false
        input = "make :x 1 make :y < 2 1 if :y [ make :x 3 ]";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(1.0, x.get());

        // if with non-boolean
        input = "make :x 1 make :y 2 if :y [ make :x 3 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take if of non-boolean from <Variable :y> = <Value 2.0>", e.getMessage());
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
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(3.0, x.get(), 0.0001);
        // if false
        input = "make :x 1 make :y < 2 1 ifelse :y [ global :x make :x 3 ] [ global :x make :x 4 ]";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(4.0, x.get(), 0.0001);

        // if with non-boolean
        input = "make :x 1 make :y 2 ifelse :y [ global :x make :x 3 ] [ global :x make :x 4 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take if of non-boolean from <Variable :y> = <Value 2.0>", e.getMessage());
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
        Variable<Double> y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(2.0, y.get(), 0.0001);

        // item with non-list
        input = "make :x 1 make :y item 1 :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take item from non-list <Value 1.0>", e.getMessage());
        }

        // item with non-number
        input = "make :x [ 1 2 3 ] make :y item :x :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot get index non-number from <Variable :x> = <Expression <Value 1.0> <Value 2.0> <Value 3.0> >", e.getMessage());
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
    public void testLessEqual(){
        // less equal
        String input = "make :x 1 make :y 2 make :z <= :x :y";
        interpreter.interpret(input);
        Variable<Boolean> z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // not less equal
        input = "make :x 2 make :y 1 make :z <= :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // not less equal (equals)
        input = "make :x 1 make :y 1 make :z <= :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // less equal with non-numbers
        input = "make :x < 2 1 make :y 2 make :z <= :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take <= of non-number from <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Boolean> z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertTrue(z.get());

        // not less than
        input = "make :x 2 make :y 1 make :z < :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // not less than (equals)
        input = "make :x 1 make :y 1 make :z < :x :y";
        interpreter.interpret(input);
        z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertFalse(z.get());

        // less than with non-numbers
        input = "make :x < 2 1 make :y 2 make :z < :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take < of non-number from <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(5.0, x.get());

        // make user instruction
        input = "make :x 5 to test [ ] [ global :x make :x + :x 2 ] test";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(7.0, x.get());

        // make user instruction that returns a value
        input = "to test [ ] [ 1 ] make :x test";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(1.0, x.get());

        // make user instruction that returns pythagorean distance
        input = "to pythag [ :a :b ] [ sqrt + * :a :a * :b :b ] make :a pythag 3 4";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:a");
        assertEquals(5.0, x.get());


        // pass variables into user instruction
        input = "make :z 0 to test [ :x ] [ global :z make :z :x ] make :y 1 test :y";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:z");
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
            assertEquals("Cannot create function with non-variable argument <Value 1.0>", e.getMessage());
        }

        // make user instruction with input
        input = "to test [ :x ] [ global :z make :z + :z :x ] test 1";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:z");
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
        Variable<Double> y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(-1.0, y.get());

        // minus with non-numbers
        input = "make :x < 2 1 make :y ~ :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot negate non-number <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Double> y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(0.0, y.get());

        // natural log of 3
        input = "make :x 3 make :y ln :x";
        interpreter.interpret(input);
        y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(1.0986122886681096, y.get());

        // natural log with non-numbers
        input = "make :x < 2 1 make :y ln :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take natural log of non-number <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Boolean> y = (Variable<Boolean>) idManager.getObject("interpreter-:y");
        assertEquals(false, y.get());

        // not with non-booleans
        input = "make :x 1 make :y not :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take not of non-boolean <Variable :x> = <Value 1.0>", e.getMessage());
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
        Variable<Boolean> z = (Variable<Boolean>) idManager.getObject("interpreter-:z");
        assertEquals(true, z.get());

        // not equal with non-numbers
        input = "make :x < 2 1 make :y 2 make :z != :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take != of non-number from <Variable :x> = <Value false>", e.getMessage());
        }

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
        Variable<Boolean> x = (Variable<Boolean>) idManager.getObject("interpreter-:x");
        assertEquals(true, x.get());

        // true or
        input = "make :a < 1 2 make :b < 3 2 make :x or :a :b";
        interpreter.interpret(input);
        x = (Variable<Boolean>) idManager.getObject("interpreter-:x");
        assertEquals(true, x.get());

        // false or
        input = "make :a < 2 1 make :b < 3 2 make :x or :a :b";
        interpreter.interpret(input);
        x = (Variable<Boolean>) idManager.getObject("interpreter-:x");
        assertEquals(false, x.get());

        // or with non-booleans
        input = "make :a 1 make :b 2 make :x or :a :b";
        try{
            interpreter.interpret(input);
        } catch (Exception e){
            assertEquals("Cannot take OR of non-boolean from <Variable :a> = <Value 1.0>", e.getMessage());
        }
    }

    @Test
    public void testPower(){
        // power
        String input = "make :x 2 make :y 3 make :z pow :x :y";
        interpreter.interpret(input);
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(8.0, z.get());

        // power multiple times with set
        input = "make :x 2 make :y 3 make :z ( pow :x :y 2 )";
        interpreter.interpret(input);
        z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(64.0, z.get());

        // power with non-numbers
        input = "make :x < 2 1 make :y 3 make :z pow :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take power of non-number <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(6.0, z.get());

        // product with non-numbers
        input = "make :x < 2 1 make :y 3 make :z * :x :y";
        interpreter.interpret(input);
        z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(0.0, z.get());

        // product with set notation
        input = "make :x 2 make :y 3 make :z ( * :x :y 3 )";
        interpreter.interpret(input);
        z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(18.0, z.get());

        // product with incorrect number of parameters
        input = "make :x 1 make :y * :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Invalid syntax: Not enough arguments for operator Product", e.getMessage());
        }
    }

    @Test
    public void testQuotient(){
        // quotient
        String input = "make :x 2 make :y 3 make :z / :x :y";
        interpreter.interpret(input);
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(2./3., z.get());

        // quotient with non-numbers
        input = "make :x < 2 1 make :y 3 make :z / :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take quotient of non-number <Variable :x> = <Value false>", e.getMessage());
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
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertTrue(x.get() < 1);

        input = "make :x rand 0.01";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertTrue(x.get() < 0.01);

        input = "make :x rand 10000000";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertTrue(x.get() > 100);

        // random with non-numbers
        input = "make :x rand < 1 2";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take random with max of non-number <Operator LessThan[2]> = <Value true>", e.getMessage());
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
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertTrue(x.get() < 2);
        assertTrue(x.get() > 1);

        input = "make :x randr 0.01 0.02";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertTrue(x.get() < 0.02);
        assertTrue(x.get() > 0.01);

        input = "make :x randr 10000000 10000001";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertTrue(x.get() > 10000000);
        assertTrue(x.get() < 10000001);

        // random range with non-numbers
        input = "make :x randr < 1 2 3";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take random range with min of non-number <Operator LessThan[2]> = <Value true>", e.getMessage());
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
        Variable<Double> z = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(2.0, z.get());

        // remainder with non-numbers
        input = "make :x < 2 1 make :y 3 make :z % :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take remainder of non-number <Variable :x> = <Value false>", e.getMessage());
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
    public void testRepeat(){
        // repeat
        String input = "make :x 0 repeat 3 [ global :x make :x + :x 1 ]";
        interpreter.interpret(input);
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(3.0, x.get());

        // repeat multiple times with set notation
        input = "make :x 0 ( repeat 3 [ make :x + :x 50 ] 2 [ make :x - :x 20 ] 1 [ make :x + :x 2 ] ) ";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(112.0, x.get());

        // repeat with non-numbers
        input = "make :x 0 repeat < 1 2 [ global :x make :x + :x 1 ]";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat non-number <Operator LessThan[2]> = <Value true> times", e.getMessage());
        }

        // repeat with incorrect parameters
        input = "make :x 0 repeat [ make :x + :x 1 ] + 1 2";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot repeat non-block <Operator Sum[2]>", e.getMessage());
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
        Variable<List<Object>> x = (Variable<List<Object>>) idManager.getObject("interpreter-:x");
        List<Double> expected = new ArrayList<>(Arrays.asList(1.0, 4.0, 3.0));
        assertEquals(expected, x.get());

        // setitem with non-numbers
        input = "make :x [ 1 2 3 ] make :y < 1 2 setitem :x 1 :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take item from non-list <Value 1.0>", e.getMessage());
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
        Variable<Double> y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(0.0, y.get(), 0.0001);

        // sine of 3
        input = "make :x 117 make :y sin :x";
        interpreter.interpret(input);
        y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(0.8910065241883679, y.get());

        // sine with non-numbers
        input = "make :x < 1 2 make :y sin :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take sine of non-number <Variable :x> = <Value true>", e.getMessage());
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
        Variable<Double> x = (Variable<Double>) idManager.getObject("interpreter-:x");
        assertEquals(0.0, x.get(), 0.0001);

        // square root of 4
        input = "make :x 4 make :y sqrt :x";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(2.0, x.get(), 0.0001);

        // square root of 84
        input = "make :x 84 make :y sqrt :x";
        interpreter.interpret(input);
        x = (Variable<Double>) idManager.getObject("interpreter-:y");
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
            assertEquals("Cannot take square root of non-number <Variable :x> = <Value true>", e.getMessage());
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
        Variable<Double> i = (Variable<Double>) idManager.getObject("interpreter-:z");

        assertEquals(0.0, i.get());

        // sum of 1 and 2
        input = "make :x 1 make :y 2 make :z + :x :y";
        interpreter.interpret(input);
        i = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(3.0, i.get());

        // test with set notation
        input = "make :z ( + 1 2 )";
        interpreter.interpret(input);
        i = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(3.0, i.get());

        // test with more set notation
        input = "make :z ( + 1 2 3 4 5 )";
        interpreter.interpret(input);
        i = (Variable<Double>) idManager.getObject("interpreter-:z");
        assertEquals(15.0, i.get());

        // sum with non-numbers
        input = "make :x < 1 2 make :y 3 make :z + :x :y";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot add non-number <Variable :x> = <Value true>", e.getMessage());
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
        Variable<Double> i = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(0.0, i.get());

        // tangent of 3
        input = "make :x 199 make :y tan :x";
        interpreter.interpret(input);
        i = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(0.3443276132896654, i.get());

        // tangent with non-numbers
        input = "make :x < 1 2 make :y tan :x";
        try{
            interpreter.interpret(input);
            fail();
        } catch (Exception e){
            assertEquals("Cannot take tangent of non-number <Variable :x> = <Value true>", e.getMessage());
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
    public void testGetGameVariable(){
        Variable<Double> x = new Variable<>(idManager, 5.0);
        idManager.addObject(x);
        String name = idManager.getId(x);
        String input = "make :y :game_" + name;
        interpreter.interpret(input);
        Variable<Double> y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(5.0, y.get());

        Variable<Integer> x2 = new Variable<>(idManager, 2);
        idManager.addObject(x2);
        name = idManager.getId(x2);
        input = "make :y :game_" + name;
        interpreter.interpret(input);
        y = (Variable<Double>) idManager.getObject("interpreter-:y");
        assertEquals(2, y.get());

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Double> expected = new ArrayList<>(Arrays.asList(1., 2., 3., 4., 5.));
        Variable<List<Integer>> listVar = new Variable<>(idManager, list);
        idManager.addObject(listVar);
        name = idManager.getId(listVar);
        input = "make :y :game_" + name;
        interpreter.interpret(input);
        Variable<List<Double>> yList = (Variable<List<Double>>) idManager.getObject("interpreter-:y");
        assertEquals(expected, yList.get());

    }
}