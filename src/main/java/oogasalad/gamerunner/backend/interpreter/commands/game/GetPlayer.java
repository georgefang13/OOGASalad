package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;

public class GetPlayer extends OperatorToken {

    public GetPlayer(){
        super(1, "GetPlayer");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0).evaluate(env);

        ValueToken<Double> pNum = checkArgumentWithSubtype(env, t, ValueToken.class, Double.class.getName());
        Owner p = env.getGame().getPlayer(pNum.VALUE.intValue());
        return new ValueToken<>(p);
    }
}
