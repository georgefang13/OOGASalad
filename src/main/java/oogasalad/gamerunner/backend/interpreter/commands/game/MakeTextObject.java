package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.TextObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.function.Consumer;

public class MakeTextObject extends OperatorToken {

    // var, location, classes[], reaction
    public MakeTextObject() {
        super(4, "MakeTextObject");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t0 = getArg(0).evaluate(env);
        Token t1 = getArg(1).evaluate(env);
        Token t2 = getArg(2);
        Token t3 = getArg(3).evaluate(env);

        Variable v = t0.getLink();

        assert v != null;

        if (t2 instanceof VariableToken){
            t2 = t2.evaluate(env);
        }

        ValueToken<DropZone> loc = checkArgumentWithSubtype(env, t1, ValueToken.class, DropZone.class.getName());
        ValueToken<String> clsList = checkArgumentWithSubtype(env, t2, ValueToken.class, String.class.getName());

        OperatorToken op;
        if (t3 instanceof OperatorToken o){
            op = o;
        }
        else {
            ValueToken<OperatorToken> z = checkArgumentWithSubtype(env, t3, ValueToken.class, OperatorToken.class.getName());
            op = z.VALUE;
        }

        TextObject obj = new TextObject(v.getOwner());

        for (String cls : clsList.VALUE.split("\\.")){
            obj.addClass(cls);
        }

        env.getGame().addTextObject(obj, loc.VALUE);

        Consumer<String> reaction = (String s) -> {
            s = s.replace("%20", " ");
            op.passArguments(new Token[]{new ValueToken<>(s)});
            Token result = op.evaluate(env);
            ValueToken<String> b = checkArgumentWithSubtype(env, result, ValueToken.class, String.class.getName());
            obj.setTextNoReaction(b.VALUE.replace("%20", " "));
            env.getGame().updateTextObject(obj);
        };

        obj.setReaction(reaction);
        obj.linkVariable(v);

        obj.setText(String.valueOf(v.get()));

        return null;
    }
}
