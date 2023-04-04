package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class VariableToken extends Token{
    public final String NAME;
    public VariableToken(String name) {
        super("Variable", name);
        NAME = name;
    }

    @Override
    public Token evaluate(Environment env){
        return env.getLocalVariable(NAME);
    }

    @Override
    public String toString(){
        return "<" + TYPE + " " + NAME + ">";
    }
}
