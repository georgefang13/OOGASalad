package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes if x != y
 */
public class NotEqual extends OperatorToken {

    public NotEqual(){
        super(2, "NotEqual");
    }

    @Override
    public Token evaluate(Environment env){
        boolean b = getArg(0).equals(getArg(1), env);
        return new ValueToken<>(!b);
    }
}
