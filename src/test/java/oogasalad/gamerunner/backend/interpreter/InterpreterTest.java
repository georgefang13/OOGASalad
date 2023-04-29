package oogasalad.gamerunner.backend.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterpreterTest {

  private Tokenizer tokenizer;
  private Evaluator evaluator;
  private Parser parser;
  private Environment env;
  private Interpreter interpreter;
  private IdManager<Ownable> idManager;

  @BeforeEach
  public void setUp() {
    idManager = new IdManager<>();
    env = new Environment();
    tokenizer = new Tokenizer();
    evaluator = new Evaluator(env);
    interpreter = new Interpreter();
    interpreter.linkIdManager(idManager);
  }

  private <T> T getVar(String name) {
    return (T) idManager.getObject(name);
  }

  @Test
  public void testInterpret() {
    Variable<?> x = new Variable<>(5.);
    Variable<?> y = new Variable<>(10.);
    idManager.addObject(x, "x");
    idManager.addObject(y, "y");
    String input = "make :game_z sqrt + pow :game_x 2 pow :game_y 2 ";
    interpreter.interpret(input);
    Variable<Double> z = getVar("z");
    assertEquals(11.180339887498949, z.get());
  }

  @Test
  public void testLoadCode() {
    Variable<?> x = new Variable<>(5.);
    Variable<?> y = new Variable<>(10.);
    idManager.addObject(x, "x");
    idManager.addObject(y, "y");
    String input = "make :game_z sqrt + pow :game_x 2 pow :game_y 2 ";
    interpreter.loadCode(input);
    interpreter.run();
    Variable<Double> z = getVar("z");
    assertEquals(11.180339887498949, z.get());
  }

  @Test
  public void testLoadTokenizedCode() {
    Variable<?> x = new Variable<>(5.);
    Variable<?> y = new Variable<>(10.);
    idManager.addObject(x, "x");
    idManager.addObject(y, "y");
    String input = "make :game_z sqrt + pow :game_x 2 pow :game_y 2 ";
    interpreter.loadTokenizedCode(tokenizer.tokenize(input));
    interpreter.run();
    Variable<Double> z = getVar("z");
    assertEquals(11.180339887498949, z.get());
  }

  @Test
  public void testLoadParsedCode() {
    Variable<?> x = new Variable<>(5.);
    Variable<?> y = new Variable<>(10.);
    idManager.addObject(x, "x");
    idManager.addObject(y, "y");
    String input = "make :game_z sqrt + pow :game_x 2 pow :game_y 2 ";
    parser = new Parser(tokenizer.tokenize(input));
    interpreter.loadParsedCode(parser.parse(env));
    interpreter.run();
    Variable<Double> z = getVar("z");
    assertEquals(11.180339887498949, z.get());
  }

  @Test
  public void testStep() {
    String input = "make :game_x 5" + "\n" +
        "make :game_y 10" + "\n" +
        "make :game_z sqrt + pow :game_x 2 pow :game_y 2 ";
    interpreter.loadCode(input);
    assertFalse(idManager.isIdInUse("x"));
    assertFalse(idManager.isIdInUse("y"));
    assertFalse(idManager.isIdInUse("z"));
    interpreter.step();
    assertTrue(idManager.isIdInUse("x"));
    assertFalse(idManager.isIdInUse("y"));
    assertFalse(idManager.isIdInUse("z"));
    Variable<Double> xVar = getVar("x");
    assertEquals(5., xVar.get());

    interpreter.step();
    assertTrue(idManager.isIdInUse("x"));
    assertTrue(idManager.isIdInUse("y"));
    assertFalse(idManager.isIdInUse("z"));
    Variable<Double> yVar = getVar("y");
    assertEquals(10., yVar.get());

    interpreter.step();
    assertTrue(idManager.isIdInUse("x"));
    assertTrue(idManager.isIdInUse("y"));
    assertTrue(idManager.isIdInUse("z"));
    Variable<Double> zVar = getVar("z");
    assertEquals(11.180339887498949, zVar.get());

  }


}
