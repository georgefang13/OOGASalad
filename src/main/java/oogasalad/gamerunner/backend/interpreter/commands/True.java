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
public class True extends OperatorToken {

    public True() {
        super(0, "True");
    }

    @Override
    public ValueToken<?> evaluate(Environment env) {
        return new ValueToken<>(true);
    }
}
