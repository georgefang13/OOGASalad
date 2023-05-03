package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Computes if x && y
 */
public class AddAllItems extends OperatorToken {

    public AddAllItems() {
        super(2, "AddAllItems");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0);
        Token t2 = getArg(1);
        if (!(t1 instanceof ExpressionToken)) {
            t1 = t1.evaluate(env);
        }
        if (!(t2 instanceof ExpressionToken)) {
            t2 = t2.evaluate(env);
        }
        ExpressionToken from = checkArgument(env, t1, ExpressionToken.class);
        ExpressionToken to = checkArgument(env, t2, ExpressionToken.class);
        for (Token t : from) {
            to.addToken(t, env);
        }
        return null;
    }
}