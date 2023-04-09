package oogasalad.gamerunner.backend.interpreter.commands.operators;

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

        ValueToken<Boolean> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class, Boolean.class.getName());
        ValueToken<Boolean> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class, Boolean.class.getName());

        return new ValueToken<>(x1.VALUE || x2.VALUE);
    }
}
