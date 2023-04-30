package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FollowDropZonePath extends OperatorToken {
    public FollowDropZonePath() {
        super(2, "FollowDropZonePath");
    }
    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1);

        if (t2 instanceof VariableToken) {
            t2 = t2.evaluate(env);
        }

        ValueToken<DropZone> x = checkArgumentWithSubtype(env, t1, ValueToken.class, DropZone.class.getName());
        ExpressionToken y = checkArgument(env, t2, ExpressionToken.class);

        DropZone d = x.VALUE;

        List<String> path = (List<String>) y.export(env);

        DropZone next = d.followPath(path);

        if (next == null) return null;

        Variable<DropZone> var = new Variable<>(next);
        return env.convertVariableToToken(var);
    }
}