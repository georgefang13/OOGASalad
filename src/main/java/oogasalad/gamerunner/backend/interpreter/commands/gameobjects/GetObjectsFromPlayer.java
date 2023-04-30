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
import oogasalad.sharedDependencies.backend.owners.Player;

public class GetObjectsFromPlayer extends OperatorToken {

    public GetObjectsFromPlayer() {
        super(2, "GetObjectsFromPlayer");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Player> player = checkArgumentWithSubtype(env, t1, ValueToken.class,
                Player.class.getName());
        ValueToken<String> x = checkArgumentWithSubtype(env, t2, ValueToken.class,
                String.class.getName());

        String[] classes = x.VALUE.split("\\.");

        IdManager<Ownable> manager = env.getIdManager();
        OwnableSearchStream searchStream = new OwnableSearchStream(manager);

        List<Ownable> objs = manager.objectStream()
                .filter(searchStream.isOwnedByOwner(player.VALUE))
                .filter(searchStream.isOfAllClasses(classes)).toList();

        Variable<List<Ownable>> var = new Variable<>(objs);
        return env.convertVariableToToken(var);
    }
}