package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the x^y
 */
public class Power extends OperatorToken {

    public Power(){
        super(2, "Power");
    }

    @Override
    public Token evaluate(Environment env){
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(),
                "Cannot take power of non-number " + getArg(0) + " = " + t1);

        ValueToken<Double> x2 = checkArgumentWithSubtype(t2, ValueToken.class, Double.class.getName(),
                "Cannot take power of non-number " + getArg(1) + " = " + t2);

        return new ValueToken<>(Math.pow(x1.VALUE, x2.VALUE));
    }
}
