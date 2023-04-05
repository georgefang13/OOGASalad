package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class Token {
    public final String TYPE;
    public final String SUBTYPE;

    public final int LINE;

    public Token(String type){
        this(type, "");
    }
    public Token(String type, String subtype){
        this(type, subtype, -1);
    }
    public Token(String type, String subtype, int line){
        TYPE = type;
        SUBTYPE = subtype;
        LINE = line;
    }

    /**
     * evaluate the token
     *
     * @param env evaluator to use
     * @return evaluated token
     */
    public Token evaluate(Environment env){
        return new ValueToken<>(0.);
    }

    @Override
    public String toString(){
        return "<" + TYPE + ">";
    }
}
