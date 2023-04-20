package oogasalad.gamerunner.backend.interpreter.commands;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Creates a new variable with the given name and value
 */
public class False extends OperatorToken {

    public False() {
        super(0, "False");
    }

    @Override
    public ValueToken<?> evaluate(Environment env) {
        return new ValueToken<>(false);
    }
}
