package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

public class GetObjDz extends OperatorToken {

    public GetObjDz(){
        super(1, "GetObjDz");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0).evaluate(env);

        ValueToken<Ownable> dzvar = checkArgumentWithSubtype(env, t, ValueToken.class, Ownable.class.getName());
        DropZone dz = env.getGame().getPieceLocation(dzvar.VALUE);
        if (dz == null) return null;
        return new ValueToken<>(dz);
    }
}
