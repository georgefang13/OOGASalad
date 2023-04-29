package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class ReturnToken extends Token {

    public final Token VALUE;

    public ReturnToken(Token value) {
        super("Return", value == null ? null : value.getClass().getName());
        VALUE = value;
    }

    @Override
    public Token evaluate(Environment env) {
        return this;
    }

    @Override
    public Object export(Environment env) {
        return VALUE;
    }

    @Override
    public Token copy(){
        return VALUE.copy();
    }

    @Override
    public String toString() {
        return "<" + TYPE + " " + VALUE + ">";
    }
}
