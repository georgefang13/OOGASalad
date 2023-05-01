package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

public class IsDropZoneEmpty extends OperatorToken {

    public IsDropZoneEmpty() {
        super(1, "IsDropZoneEmpty");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken<DropZone> dz = checkArgumentWithSubtype(env, t, ValueToken.class,
                DropZone.class.getName());

        return new ValueToken<>(dz.VALUE.getAllObjects().size() == 0);
    }
}
