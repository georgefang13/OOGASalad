package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ReturnToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;

/**
 * Repeats the given expressions the given number of times
 */
public class ReturnNull extends OperatorToken {
    public ReturnNull() {
        super(0, "ReturnNull");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        return new ReturnToken(null);
    }
}
