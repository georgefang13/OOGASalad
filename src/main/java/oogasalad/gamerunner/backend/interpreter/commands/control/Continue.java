package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class Continue extends OperatorToken {
    public Continue() {
        super(0, "Continue");
    }
    public Token evaluate(Environment env) throws IllegalArgumentException{
        return this;
    }
}
