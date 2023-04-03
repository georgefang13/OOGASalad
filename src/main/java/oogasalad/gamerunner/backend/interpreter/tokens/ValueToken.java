package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class ValueToken<T> extends Token {
    public final T VALUE;
    public ValueToken(T value){
        super("Value", value.getClass().getName());
        VALUE = value;
    }

    @Override
    public Token evaluate(Environment env){
        return this;
    }


    @Override
    public String toString(){
        return "<" + TYPE + " " + VALUE + ">";
    }
}
