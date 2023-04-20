package oogasalad.gamerunner.backend.interpreter.commands.operators;

import java.util.ArrayList;
import java.util.List;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
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

        IdManager<Ownable> game = env.getGame();

        List<String> objIds = game.getIdsOfObjectsOfClass(classes);
        List<Ownable> objs = new ArrayList<>();
        for (String s : objIds){
            objs.add(game.getObject(s));
        }
        Variable<List<Ownable>> var = new Variable<>(objs);
        return env.convertVariableToToken(var);
    }
}