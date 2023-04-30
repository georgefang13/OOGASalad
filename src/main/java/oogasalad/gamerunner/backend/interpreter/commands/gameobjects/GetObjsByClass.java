package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import java.util.List;

import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.id.OwnableSearchStream;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

public class GetObjsByClass extends OperatorToken {

    public GetObjsByClass() {
        super(1, "GetFromGameClass");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken<String> x = checkArgumentWithSubtype(env, t, ValueToken.class,
                String.class.getName());

        String[] classes = x.VALUE.split("\\.");

        IdManager<Ownable> manager = env.getIdManager();

        OwnableSearchStream searchStream = new OwnableSearchStream(manager);

        List<Ownable> objs = manager.objectStream()
                .filter(searchStream.isOfAllClasses(classes)).toList();

        Variable<List<Ownable>> var = new Variable<>(objs);
        return env.convertVariableToToken(var);
    }
}