package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the natural log (ln) of a number
 */
public class NaturalLog extends OperatorToken {

    public NaturalLog(){
        super(1, "NaturalLog");
    }

    @Override
    public Token evaluate(Environment env) {
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(),
                "Cannot take natural log of non-number " + getArg(0) + " = " + t);

        return new ValueToken<>(Math.log(x1.VALUE));
    }
}
