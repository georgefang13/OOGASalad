package oogasalad.gamerunner.backend.interpretables;

import oogasalad.gameeditor.backend.id.IdManageable;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

/**
 * Defines a set of instructions that check whether some condition in the game has been met,
 * establishing some outcome (some player wins or loses)
 *
 * @author Ethan Horowitz
 * @author Rodrigo Bassi Guerreiro
 */
public class Goal extends Interpretable {
  public int test(Interpreter interpreter, IdManager idmanager){
    interpreter.interpret("del :game_output_state");

    for (String instruction : getInstructions()) {
      interpreter.interpret(instruction);
    }

    if (!idmanager.isIdInUse("output_state")){
      return -1;
    }
    Variable<Double> v = (Variable<Double>) idmanager.getObject("output_state");
    return v.get().intValue();
  }

}
