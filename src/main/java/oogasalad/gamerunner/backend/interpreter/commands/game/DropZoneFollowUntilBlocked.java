package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropZoneFollowUntilBlocked extends OperatorToken {
    public DropZoneFollowUntilBlocked() {
        super(3, "DropZoneFollowUntilBlocked");
    }
    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env); // DropZone
        Token t2 = getArg(1);               // Expression
        Token t3 = getArg(2).evaluate(env); // blocked function

        if (! (t2 instanceof ExpressionToken)){
            t2 = t2.evaluate(env);
        }

        ValueToken<DropZone> x = checkArgumentWithSubtype(env, t1, ValueToken.class, DropZone.class.getName());
        ExpressionToken expr = checkArgument(env, t2, ExpressionToken.class);
        ValueToken<OperatorToken> z = checkArgumentWithSubtype(env, t3, ValueToken.class, OperatorToken.class.getName());

        DropZone d = x.VALUE;
        OperatorToken op = z.VALUE;

        List<String> path = (List<String>) expr.export(env);

        List<String> allZones = d.findSpotsUntilBlocked(path, (dz) -> {
            op.passArguments(new Token[]{new ValueToken<>(dz)});
            Token result = op.evaluate(env);
            ValueToken<Boolean> b = checkArgumentWithSubtype(env, result, ValueToken.class, Boolean.class.getName());
            return b.VALUE;
        });

        List<DropZone> zones = new ArrayList<>();
        for (String s : allZones){
            Token var = env.getLocalVariable(":game_" + s);
            ValueToken<DropZone> v = checkArgumentWithSubtype(env, var, ValueToken.class, DropZone.class.getName());
            zones.add(v.VALUE);
        }

        Variable<List<DropZone>> var = new Variable<>(zones);
        return env.convertVariableToToken(var);
    }
}