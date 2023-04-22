package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class MovePieceAs extends OperatorToken {

    public MovePieceAs() {
        super(3, "MovePieceAs");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);
        Token t3 = getArg(2).evaluate(env);

        ValueToken<GameObject> obj = checkArgumentWithSubtype(env, t1, ValueToken.class,
                GameObject.class.getName());
        ValueToken<DropZone> dz = checkArgumentWithSubtype(env, t2, ValueToken.class,
                DropZone.class.getName());
        ValueToken<String> name = checkArgumentWithSubtype(env, t3, ValueToken.class,
                String.class.getName());

        env.getGame().movePiece(obj.VALUE, dz.VALUE, name.VALUE);
        return null;
    }
}
