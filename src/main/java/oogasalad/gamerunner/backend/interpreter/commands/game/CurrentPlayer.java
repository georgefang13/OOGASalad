package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.owners.Player;

public class CurrentPlayer extends OperatorToken {

    public CurrentPlayer(){
        super(0, "CurrentPlayer");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = env.getLocalVariable(":game_turn").evaluate(env);

        ValueToken<Double> turn = checkArgumentWithSubtype(env, t, ValueToken.class, Double.class.getName());
        Player p = (Player) env.getGame().getPlayer(turn.VALUE.intValue());

        return new ValueToken<>(p);
    }
}
