package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class Return extends OperatorToken {
    public Return() {
        super(1, "Return");
    }
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0);
        if (t != null && !(t instanceof ExpressionToken)) t = t.evaluate(env);
        return new ReturnToken(t);
    }
}
