package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

public class MakeAvailable extends OperatorToken {

    public MakeAvailable(){
        super(1, "MakeAvailable");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0).evaluate(env);

        ExpressionToken available = (ExpressionToken) env.getLocalVariable(":game_available");
        available.addToken(t, env);
        return null;
    }
}
