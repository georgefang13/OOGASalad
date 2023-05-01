package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropZoneHasId extends OperatorToken {
    public DropZoneHasId() {
        super(2, "DropZoneHasId");
    }
    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<String> id = checkArgumentWithSubtype(env, t1, ValueToken.class, String.class.getName());
        ValueToken<DropZone> dz = checkArgumentWithSubtype(env, t2, ValueToken.class, DropZone.class.getName());

        return new ValueToken<>(dz.VALUE.hasObject(id.VALUE));
    }
}