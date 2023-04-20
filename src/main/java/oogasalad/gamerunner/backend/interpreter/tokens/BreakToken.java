package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class BreakToken extends Token {

    public BreakToken() {
        super("Break");
    }
    @Override
    public Token evaluate(Environment env) {
        return null;
    }

}
