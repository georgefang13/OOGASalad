package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class Call extends OperatorToken {
    public Call() {
        super(2, "Call");
    }
    public Token evaluate(Environment env) throws IllegalArgumentException{

        Token t = getArg(0).evaluate(env);

        ValueToken<OperatorToken> var = checkArgumentWithSubtype(env, t, ValueToken.class, OperatorToken.class.getName());
        OperatorToken op = var.VALUE;
        ExpressionToken args = checkArgument(env, getArg(1), ExpressionToken.class);

        Token[] argArray = new Token[args.size()];
        int i = 0;
        for (Token arg : args){
            argArray[i++] = arg.evaluate(env);
        }
        op.passArguments(argArray);

        return op.evaluate(env);
    }
}
