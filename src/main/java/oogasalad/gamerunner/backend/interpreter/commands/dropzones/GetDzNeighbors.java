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

public class GetDzNeighbors extends OperatorToken {
    public GetDzNeighbors() {
        super(1, "GetDzNeighbors");
    }
    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken<DropZone> x = checkArgumentWithSubtype(env, t, ValueToken.class, DropZone.class.getName());

        Map<String, DropZone> edges = x.VALUE.getEdges();

        List<DropZone> names = new ArrayList<>(edges.values());
        Variable<List<DropZone>> var = new Variable<>(names);
        return env.convertVariableToToken(var);
    }
}