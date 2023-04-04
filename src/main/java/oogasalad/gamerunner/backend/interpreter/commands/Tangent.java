package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the tangent of the given value.
 */
public class Tangent extends OperatorToken {
    public Tangent(){
        super(1, "Tangent");
    }

    @Override
    public Token evaluate(Environment env){
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(),
                "Cannot take tangent of non-number " + getArg(0) + " = " + t);

        return new ValueToken<>(Math.tan(x1.VALUE * Math.PI/180.));
    }
}
