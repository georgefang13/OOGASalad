package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the product of two numbers
 */
public class Product extends OperatorToken {
    public Product(){
        super(2, "Product");
    }

    double getNumber(ValueToken<?> t){
        if (t.VALUE instanceof Boolean b){
            return b ? 1. : 0.;
        } else if (t.VALUE instanceof Double d){
            return d;
        } else {
            throw new IllegalArgumentException("Cannot take product of non-number " + getArg(0) + " = " + t);
        }
    }

    @Override
    public ValueToken<Double> evaluate(Environment env){
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<?> x1 = checkArgument(t1, ValueToken.class, "Cannot take product of non-value " + getArg(0) + " = " + t1);
        ValueToken<?> x2 = checkArgument(t2, ValueToken.class, "Cannot take product of non-value " + getArg(1) + " = " + t2);

        double x = getNumber(x1);
        double y = getNumber(x2);

        return new ValueToken<>(x * y);
    }
}
