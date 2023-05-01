package oogasalad.gamerunner.backend.interpreter.commands.other;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

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
