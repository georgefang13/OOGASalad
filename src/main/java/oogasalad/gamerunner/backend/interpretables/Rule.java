package oogasalad.gamerunner.backend.interpretables;

import oogasalad.gamerunner.backend.interpreter.Interpreter;

/**
 * Defines a set of actions that must be followed (enforced) on every turn of the game
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class Rule extends Interpretable {

  public Rule() {
    super();
  }

  /**
   * Iterates over current instructions, interpreting them and ensuring that they are upheld by the
   * game
   *
   * @param interpreter Interpreter instance used to process the instruction Strings
   */
  public void enforce(Interpreter interpreter) {
    for (String instruction : super.getInstructions()) {
      interpreter.interpret(instruction);
    }
  }
}
