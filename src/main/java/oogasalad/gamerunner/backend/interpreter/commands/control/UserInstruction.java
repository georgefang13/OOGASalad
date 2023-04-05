package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * User defined instruction
 */
public class UserInstruction extends OperatorToken {

    private final ExpressionToken argTokens;
    private final ExpressionToken exprs;

    public UserInstruction(String name, ExpressionToken argTokens, ExpressionToken exprs) {
        super(argTokens.size(), name);
        this.argTokens = argTokens;
        this.exprs = exprs;
    }

    @Override
    public Token evaluate(Environment env) {
        env.createLocalScope();
        for (int i = 0; i < argTokens.size(); i++){
            VariableToken var = (VariableToken) argTokens.get(i);
            ValueToken<?> val = (ValueToken<?>) getArg(i).evaluate(env);
            env.addVariable(var.NAME, val);
        }
        Token t = exprs.evaluate(env);
        env.endLocalScope();
        return t;
    }
}
