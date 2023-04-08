package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

public class GetAttribute extends OperatorToken {
    public GetAttribute(){
        super(2, "GetAttribute");
    }

    @Override
    public Token evaluate(Environment env) {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<?> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class, GameObject.class.getName());
        ValueToken<?> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class, String.class.getName());

        System.out.println(x1.VALUE + ": " + x2.VALUE);

        return null;

    }
}
