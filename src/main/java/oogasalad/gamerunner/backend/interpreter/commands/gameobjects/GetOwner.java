package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

public class GetOwner extends OperatorToken {

    public GetOwner() {
        super(1, "PlayerOwner");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t = getArg(0).evaluate(env);

        ValueToken obj = checkArgument(env, t, ValueToken.class);

        if (! (obj.VALUE instanceof Ownable ownable)){
            Variable link = obj.getLink();
            if (link == null){
                throwError(new RuntimeException("Variable " + obj + " does not link to an Ownable"));
                return null;
            }
            return new ValueToken<>(link.getOwner());
        }

        return new ValueToken<>(ownable.getOwner());
    }
}
