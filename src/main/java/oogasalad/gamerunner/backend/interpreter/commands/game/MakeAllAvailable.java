package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

public class MakeAllAvailable extends OperatorToken {

    public MakeAllAvailable(){
        super(1, "MakeAllAvailable");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0);

        if (!(t instanceof ExpressionToken)){
            t = t.evaluate(env);
        }

        ExpressionToken list = checkArgument(env, t, ExpressionToken.class);
        ExpressionToken available = (ExpressionToken) env.getLocalVariable(":game_available");

        for (Token element : list){
            available.addToken(element, env);
        }
        return null;
    }
}
