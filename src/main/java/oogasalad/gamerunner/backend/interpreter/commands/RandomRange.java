package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Generates a random number between two numbers
 */
public class RandomRange extends OperatorToken {

    public RandomRange(){
        super(2, "RandomRange");
    }

    @Override
    public ValueToken<Double> evaluate(Environment env){
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Double> min = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(),
                "Cannot take random range with min of non-number " + getArg(0) + " = " + t1);

        ValueToken<Double> max = checkArgumentWithSubtype(t2, ValueToken.class, Double.class.getName(),
                "Cannot take random range with max of non-number " + getArg(1) + " = " + t2);

        double rand = Math.random() * (max.VALUE - min.VALUE) + min.VALUE;
        return new ValueToken<>(rand);
    }
}