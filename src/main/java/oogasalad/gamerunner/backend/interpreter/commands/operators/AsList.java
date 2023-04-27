package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if !x
 */
public class AsList extends OperatorToken {

    public AsList() {
        super(1, "AsList");
    }

    @Override
    public Token evaluate(Environment env) {
        Token t = getArg(0);

        ExpressionToken exp = checkArgument(env, t, ExpressionToken.class);

        ExpressionToken ret = new ExpressionToken();
        AddItem add = new AddItem();
        for (Token tok : exp) {
            Token[] args = {tok, ret};
            add.passArguments(args);
            add.evaluate(env);
        }

        return ret;
    }
}
