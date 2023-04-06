package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes the square root of a number
 */
public class SquareRoot extends OperatorToken {

    public SquareRoot(){
        super(1, "SquareRoot");
    }

    @Override
    public Token evaluate(Environment env){
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(), env);

        return new ValueToken<>(Math.sqrt(x1.VALUE));
    }
}
