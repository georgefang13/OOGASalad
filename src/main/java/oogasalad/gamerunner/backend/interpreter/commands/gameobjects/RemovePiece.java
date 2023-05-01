package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class RemovePiece extends OperatorToken {

    public RemovePiece() {
        super(1, "RemovePiece");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken<GameObject> objVar = checkArgumentWithSubtype(env, t, ValueToken.class,
                GameObject.class.getName());

        env.getGame().removePiece(objVar.VALUE);

        return null;
    }
}
