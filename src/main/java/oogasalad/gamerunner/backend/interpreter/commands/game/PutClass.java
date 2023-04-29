package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class PutClass extends OperatorToken {

    public PutClass() {
        super(2, "PutClass");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<String> name = checkArgumentWithSubtype(env, t1, ValueToken.class,
                String.class.getName());
        ValueToken<Ownable> objVar = checkArgumentWithSubtype(env, t2, ValueToken.class,
                GameObject.class.getName());

        Ownable obj = objVar.VALUE;
        env.getGame().putClass(obj, name.VALUE);

        return null;
    }
}
