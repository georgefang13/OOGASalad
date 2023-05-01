package oogasalad.gamerunner.backend.interpreter.commands.other;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Creates a new variable with the given name and value
 */
public class Null extends OperatorToken {

    public Null() {
        super(0, "Null");
    }

    @Override
    public ValueToken<?> evaluate(Environment env) {
        return null;
    }
}
