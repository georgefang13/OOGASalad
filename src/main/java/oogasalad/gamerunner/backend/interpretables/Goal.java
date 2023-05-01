package oogasalad.gamerunner.backend.interpretables;

import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.Player;

/**
 * Defines a set of instructions that check whether some condition in the game has been met,
 * establishing some outcome (some player wins or loses)
 *
 * @author Ethan Horowitz
 * @author Rodrigo Bassi Guerreiro
 */
public class Goal extends Interpretable {

  public Player test(Interpreter interpreter, IdManager idmanager) {
    interpreter.interpret("del :game_state_output");

    for (String instruction : getInstructions()) {
      interpreter.interpret(instruction);
    }

    if (!idmanager.isIdInUse("state_output")) {
      return null;
    }
    Variable<Player> v = (Variable<Player>) idmanager.getObject("state_output");
    return v.get();
  }

}
