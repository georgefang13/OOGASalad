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

        ExpressionToken x2 = checkArgument(t2, ExpressionToken.class, "Cannot take item from non-list " + t2);

        ValueToken<Double> d = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(),
                "Cannot get index non-number from " + getArg(0) + " = " + t1);

        int index = d.VALUE.intValue();

        if (x2.size() <= index){
            throw new IllegalArgumentException("Cannot get index " + index + " from list of size " + x2.size());
        }

        return x2.get(index).evaluate(env);
    }
}
