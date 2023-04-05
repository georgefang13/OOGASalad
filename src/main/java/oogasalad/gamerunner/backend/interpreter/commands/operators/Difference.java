package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the difference of two numbers
 */
public class Difference extends OperatorToken {

    public Difference(){
        super(2, "Difference");
    }

    @Override
    public ValueToken<Double> evaluate(Environment env) throws IllegalArgumentException{

        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(),
                "Cannot take difference of non-number from " + getArg(0) + " = " + t1);

        ValueToken<Double> x2 = checkArgumentWithSubtype(t2, ValueToken.class, Double.class.getName(),
                "Cannot take difference of non-number from " + getArg(1) + " = " + t2);

        return new ValueToken<>(x1.VALUE - x2.VALUE);
    }
}
