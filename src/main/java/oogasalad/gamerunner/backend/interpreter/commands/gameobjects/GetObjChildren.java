package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.id.OwnableSearchStream;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

import java.util.List;

public class GetObjChildren extends OperatorToken {

    public GetObjChildren() {
        super(1, "GetObjChildren");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);

        ValueToken<Ownable> objvar = checkArgumentWithSubtype(env, t1, ValueToken.class,
                Ownable.class.getName());

        Ownable obj = objvar.VALUE;

        IdManager<Ownable> idManager = env.getIdManager();

        OwnableSearchStream stream = new OwnableSearchStream(idManager);

        List<Ownable> objs = idManager.objectStream().filter(stream.isOwnedByOwnable(obj)).toList();

        ExpressionToken vars = new ExpressionToken();

        for (Ownable obj2 : objs) {
            String id = idManager.getSimpleId(obj2);
            Token t = env.getLocalVariable(":game_" + id);
            vars.addToken(t, env);
        }
        return vars;
    }
}
