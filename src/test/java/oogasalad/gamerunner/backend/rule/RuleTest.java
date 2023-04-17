package oogasalad.gamerunner.backend.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oogasalad.gamerunner.backend.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for Rule class and subclasses
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class RuleTest {

  Rule rule;

  @BeforeEach
  void setup() {
    rule = new Rule();
  }

  @Test
  void testAddInstruction() {
    List<String> instructions = new ArrayList<>();
    assertEquals(instructions, rule.getInstructions());

    rule.addInstruction("make :score 1");
    rule.addInstruction("make :score 2");

    instructions = Arrays.asList("make :score 1", "make :score 2");

    int index = 0;
    for (String instruction : rule.getInstructions()) {
      assertEquals(instruction, instructions.get(index));
      index++;
    }
  }
}
