package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the negation of a number
 */
public class Minus extends OperatorToken {

    public Minus(){
        super(1, "Minus");
    }

    @Override
    public Token evaluate(Environment env) {
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(),
                "Cannot negate non-number " + getArg(0) + " = " + t);

        return new ValueToken<>(-x1.VALUE);
    }
}
