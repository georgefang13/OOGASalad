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
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class, Double.class.getName());
        ValueToken<Double> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class, Double.class.getName());

        return new ValueToken<>((double)x1.VALUE != x2.VALUE);
    }
}
