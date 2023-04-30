package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class MovePiece extends OperatorToken {

    public MovePiece() {
        super(2, "MovePiece");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<GameObject> objVar = checkArgumentWithSubtype(env, t1, ValueToken.class,
                GameObject.class.getName());
        ValueToken<DropZone> dzVar = checkArgumentWithSubtype(env, t2, ValueToken.class,
                DropZone.class.getName());

        GameObject obj = objVar.VALUE;
        DropZone dz = dzVar.VALUE;

        env.getGame().movePiece(obj, dz);

        return null;
    }
}
