package oogasalad.gamerunner.backend.interpreter.commands.gameobjects;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;

public class AddDropZone extends OperatorToken {

    // classes [], location, image, highlightImg, width, height
    public AddDropZone() {
        super(6, "AddDropZone");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {

        Token t1 = getArg(0);
        Token t2 = getArg(1).evaluate(env);
        Token t3 = getArg(2).evaluate(env);
        Token t4 = getArg(3).evaluate(env);
        Token t5 = getArg(4).evaluate(env);
        Token t6 = getArg(5).evaluate(env);

        ExpressionToken classes = checkArgument(env, t1, ExpressionToken.class);
        ValueToken<DropZone> location = checkArgumentWithSubtype(env, t2, ValueToken.class, DropZone.class.getName());
        ValueToken<String> img = checkArgumentWithSubtype(env, t3, ValueToken.class, String.class.getName());
        ValueToken<String> highlight = checkArgumentWithSubtype(env, t4, ValueToken.class, String.class.getName());
        ValueToken<Double> width = checkArgumentWithSubtype(env, t5, ValueToken.class, Double.class.getName());
        ValueToken<Double> height = checkArgumentWithSubtype(env, t6, ValueToken.class, Double.class.getName());

        DropZone dz = new DropZone();

        for (Token t : classes){
            ValueToken<String> cls = checkArgumentWithSubtype(env, t, ValueToken.class, String.class.getName());
            dz.addClass(cls.VALUE);
        }

        env.getGame().addDropZone(dz, location.VALUE, img.VALUE, highlight.VALUE, width.VALUE, height.VALUE);

        return null;
    }
}
