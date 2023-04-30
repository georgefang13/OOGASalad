package oogasalad.gamerunner.backend.interpreter.commands.dropzones;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropZonePaths extends OperatorToken {
    public DropZonePaths() {
        super(1, "DropZonePaths");
    }
    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken<DropZone> x = checkArgumentWithSubtype(env, t, ValueToken.class, DropZone.class.getName());

        Map<String, DropZone> items = x.VALUE.getEdges();
        List<String> names = new ArrayList<>(items.keySet());
        Variable<List<String>> var = new Variable<>(names);
        return env.convertVariableToToken(var);
    }
}