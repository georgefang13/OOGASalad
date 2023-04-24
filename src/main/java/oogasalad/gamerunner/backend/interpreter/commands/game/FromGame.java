package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

public class FromGame extends OperatorToken {

    public FromGame(){
        super(1, "FromGame");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        if (t1 instanceof VariableToken) {
            t1 = t1.evaluate(env);
        }
        ValueToken<String> varName = checkArgumentWithSubtype(env, t1, ValueToken.class, String.class.getName());

        return env.getLocalVariable(":game_" + varName.VALUE);
    }
}
