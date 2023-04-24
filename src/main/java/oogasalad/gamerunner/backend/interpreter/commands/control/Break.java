package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class Break extends OperatorToken {
    public Break() {
        super(0, "Break");
    }
    public Token evaluate(Environment env) throws IllegalArgumentException{
        return this;
    }
}
