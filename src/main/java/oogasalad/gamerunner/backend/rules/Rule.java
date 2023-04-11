package oogasalad.gamerunner.backend.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.gamerunner.backend.interpreter.Interpreter;

/**
 * @author Rodrigo Bassi Guerreiro
 */
public class Rule {
  List<String> myInstructions;

  public Rule() {
    myInstructions = new ArrayList<>();
  }

  public void addInstruction(String instruction) {
    myInstructions.add(instruction);
  }

  public Iterable<String> getInstructions() {
    return Collections.unmodifiableList(myInstructions);
  }

  public void enforce(Interpreter interpreter) {
    for (String instruction : myInstructions) {
      interpreter.interpret(instruction);
    }
  }
}
