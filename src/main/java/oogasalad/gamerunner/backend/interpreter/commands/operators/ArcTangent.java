package oogasalad.gamerunner.backend.interpreter.commands.operators;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the arctan of the given value.
 */
public class ArcTangent extends OperatorToken {
    public ArcTangent(){
        super(1, "ArcTangent");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> x1 = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(), env);

        return new ValueToken<>(Math.atan(x1.VALUE)*180/Math.PI);
    }
}
