package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the sine of the given value.
 */
public class Sine extends OperatorToken {
    public Sine(){
        super(1, "Sine");
    }

    @Override
    public Token evaluate(Environment env){
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(),
                "Cannot take sine of non-number " + getArg(0) + " = " + t);

        return new ValueToken<>(Math.sin(x1.VALUE * Math.PI/180.));
    }
}
