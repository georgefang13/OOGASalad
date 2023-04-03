package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the quotient of two numbers
 */
public class Quotient extends OperatorToken {
    public Quotient(){
        super(2, "Quotient");
    }

    @Override
    public ValueToken<Double> evaluate(Environment env){
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(),
                "Cannot take quotient of non-number " + getArg(0) + " = " + t1);

        ValueToken<Double> x2 = checkArgumentWithSubtype(t2, ValueToken.class, Double.class.getName(),
                "Cannot take quotient of non-number " + getArg(1) + " = " + t2);

        return new ValueToken<>(x1.VALUE / x2.VALUE);
    }
}
