package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class GetId extends OperatorToken {

    public GetId() {
        super(1, "GetId");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken<GameObject> obj = checkArgumentWithSubtype(env, t, ValueToken.class,
                GameObject.class.getName());

        return new ValueToken<>(env.getIdManager().getId(obj.VALUE));
    }
}
