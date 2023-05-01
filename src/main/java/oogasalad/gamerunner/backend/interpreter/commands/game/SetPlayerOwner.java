package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

public class SetPlayerOwner extends OperatorToken {

    public SetPlayerOwner(){
        super(2, "SetPlayerOwner");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Ownable> obj = checkArgumentWithSubtype(env, t1, ValueToken.class, Ownable.class.getName());
        ValueToken<Owner> owner = checkArgumentWithSubtype(env, t2, ValueToken.class, Owner.class.getName());

        env.getIdManager().setPlayerOwner(obj.VALUE, owner.VALUE);
        return null;
    }
}
