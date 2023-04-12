package oogasalad.gamerunner.backend.interpretables;

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
  public boolean test(Interpreter interpreter, IdManager idmanager){
    interpreter.interpret("del :game_output_state");

    for (String instruction : getInstructions()) {
      interpreter.interpret(instruction);
    }

    if (!idmanager.isIdInUse("output_state")){
      return false;
    }
    Variable v = (Variable) idmanager.getObject("output_state");
    return v.get().equals(true);
  }

}
