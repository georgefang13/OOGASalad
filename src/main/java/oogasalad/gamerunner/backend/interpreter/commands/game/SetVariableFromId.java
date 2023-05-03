package oogasalad.gamerunner.backend.interpreter.commands.game;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Creates a new variable with the given name and value
 */
public class SetVariableFromId extends OperatorToken {

    public SetVariableFromId() {
        super(2, "SetVariableFromId");
    }

    @Override
    public ValueToken<?> evaluate(Environment env) {
        ValueToken<String> v = checkArgumentWithSubtype(env, getArg(0).evaluate(env), ValueToken.class, String.class.getName());

        Token t = getArg(1);
        if (t != null && !(t instanceof ExpressionToken)) {
            t = t.evaluate(env);
        }

        env.addVariable(":game_" + v.VALUE, t);
        return null;
    }

}
