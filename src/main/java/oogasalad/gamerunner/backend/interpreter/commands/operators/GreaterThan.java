package oogasalad.gamerunner.backend.interpreter.commands.operators;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if x > y
 */
public class GreaterThan extends OperatorToken {

    public GreaterThan(){
        super(2, "GreaterThan");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(), env);
        ValueToken<Double> x2 = checkArgumentWithSubtype(t2, ValueToken.class, Double.class.getName(), env);

        return new ValueToken<>(x1.VALUE > x2.VALUE);
    }
}
