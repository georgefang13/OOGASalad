package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

public class HasClass extends OperatorToken {

    public HasClass() {
        super(2, "HasClass");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);

        ValueToken<IdManageable> obj = checkArgumentWithSubtype(env, t1, ValueToken.class, IdManageable.class.getName());
        ValueToken<String> name = checkArgumentWithSubtype(env, t2, ValueToken.class, String.class.getName());

        IdManageable i = obj.VALUE;
        return new ValueToken<>(i.usesClass(name.VALUE));
    }
}
