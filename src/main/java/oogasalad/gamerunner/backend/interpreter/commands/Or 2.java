package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes if x || y
 */
public class Or extends OperatorToken {

    public Or(){
        super(2, "Or");
    }

    @Override
    public Token evaluate(Environment env){
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Boolean> x1 = checkArgumentWithSubtype(t1, ValueToken.class, Boolean.class.getName(),
                "Cannot take OR of non-boolean from " + getArg(0) + " = " + t1);

        ValueToken<Boolean> x2 = checkArgumentWithSubtype(t2, ValueToken.class, Boolean.class.getName(),
                "Cannot take OR of non-boolean from " + getArg(1) + " = " + t2);

        return new ValueToken<>(x1.VALUE || x2.VALUE);
    }
}
