package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes if x && y
 */
public class Item extends OperatorToken {

    public Item(){
        super(2, "Item");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);

        Token t2 = getArg(1);
        if (t2 instanceof VariableToken) {
            t2 = t2.evaluate(env);
        }

        ExpressionToken x2 = checkArgument(env, t2, ExpressionToken.class);

        ValueToken<Double> d = checkArgumentWithSubtype(env, t1, ValueToken.class, Double.class.getName()
        );

        int index = d.VALUE.intValue();

        if (x2.size() <= index){
            throw new IllegalArgumentException("Cannot get index " + index + " from list of size " + x2.size());
        }

        return x2.get(index).evaluate(env);
    }
}
