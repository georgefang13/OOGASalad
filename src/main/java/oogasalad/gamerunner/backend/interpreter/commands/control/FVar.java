package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class FVar extends OperatorToken {
    public FVar() {
        super(1, "FVar");
    }
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0);
        if (t instanceof CommandToken c){
            t = env.getLocalVariable(c.NAME);
        }
        OperatorToken var = checkArgument(env, t, OperatorToken.class);
        return new ValueToken<>(var);
    }
}
