package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

public class SetObjImage extends OperatorToken {

    public SetObjImage(){
        super(2, "SetObjImage");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<Ownable> obj = checkArgumentWithSubtype(env, t1, ValueToken.class, Ownable.class.getName());
        ValueToken<String> img = checkArgumentWithSubtype(env, t2, ValueToken.class, String.class.getName());

        env.getGame().setObjectImage(obj.VALUE, img.VALUE);
        return null;
    }
}
