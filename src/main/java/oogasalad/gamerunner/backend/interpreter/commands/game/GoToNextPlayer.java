package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;

public class GoToNextPlayer extends OperatorToken {

    public GoToNextPlayer() {
        super(0, "GoToNextPlayer");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        env.getGame().increaseTurn();
        return null;
    }
}
