package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

public class SetObjOwner extends OperatorToken {

    public SetObjOwner(){
        super(2, "SetObjOwner");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Ownable> obj = checkArgumentWithSubtype(env, t1, ValueToken.class, Ownable.class.getName());
        ValueToken<Ownable> owner = checkArgumentWithSubtype(env, t2, ValueToken.class, Ownable.class.getName());

        env.getIdManager().setObjectOwner(obj.VALUE, owner.VALUE);
        return null;
    }
}
