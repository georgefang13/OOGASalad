package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes if x && y
 */
public class SetItem extends OperatorToken {

    public SetItem(){
        super(3, "SetItem");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1);
        Token t3 = getArg(2);
        if (t2 instanceof VariableToken) {
            t2 = t2.evaluate(env);
        }
        if (!(t3 instanceof ExpressionToken)) {
            t3 = t3.evaluate(env);
        }

        ExpressionToken list = checkArgument(t2, ExpressionToken.class, "Cannot take item from non-list " + t2);

        ValueToken<Double> dindex = checkArgumentWithSubtype(t1, ValueToken.class, Double.class.getName(),
                "Cannot get index non-number from " + getArg(0) + " = " + t1);

        int index = dindex.VALUE.intValue();
        list.set(index, t3);

        return null;
    }
}
