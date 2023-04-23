package oogasalad.gamerunner.backend.interpretables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GoalTest {

  private Interpreter interpreter;
  private Goal goal;

  @BeforeEach
  void setup() {
    interpreter = new Interpreter();
    goal = new Goal();
  }

  /**
   * Test whether commands are being added, and whether order is being preserved
   */
  @Test
  void testAddCommands() {
    List<String> list = new ArrayList<>();
    assertEquals(goal.getInstructions(), list);

    String command1 = "make :x 2";
    list.add(command1);
    goal.addInstruction(command1);
    assertEquals(goal.getInstructions(), list);

    String command2 = "make :x 42";
    list.add(command2);
    goal.addInstruction(command2);
    assertEquals(goal.getInstructions(), list);
  }

}
