package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Owner;

public class AddPiece extends OperatorToken {

    // parent, classes [], location, image, width, height
    public AddPiece() {
        super(6, "AddPiece");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t0 = getArg(0).evaluate(env);
        Token t1 = getArg(1);
        Token t2 = getArg(2).evaluate(env);
        Token t3 = getArg(3).evaluate(env);
        Token t4 = getArg(4).evaluate(env);
        Token t5 = getArg(5).evaluate(env);

        ValueToken<Owner> parent = checkArgumentWithSubtype(env, t0, ValueToken.class, Owner.class.getName());
        ExpressionToken classes = checkArgument(env, t1, ExpressionToken.class);
        ValueToken<DropZone> dz = checkArgumentWithSubtype(env, t2, ValueToken.class, DropZone.class.getName());
        ValueToken<String> img = checkArgumentWithSubtype(env, t3, ValueToken.class, String.class.getName());
        ValueToken<Double> wid = checkArgumentWithSubtype(env, t4, ValueToken.class, Double.class.getName());
        ValueToken<Double> hi = checkArgumentWithSubtype(env, t4, ValueToken.class, Double.class.getName());


        GameObject go = new GameObject(parent.VALUE);

        for (Token t : classes){
            String cls = ((ValueToken<String>) t.evaluate(env)).VALUE;
            go.addClass(cls);
        }

        env.getGame().addObject(go, dz.VALUE, img.VALUE, wid.VALUE, hi.VALUE);
        return null;
    }
}
