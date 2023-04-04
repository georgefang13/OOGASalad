package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Creates a new function with the given name, arguments, and expressions
 */
public class MakeUserInstruction extends OperatorToken {
    public MakeUserInstruction() {
        super(3, "MakeUserInstruction");
    }

    public Token evaluate(Environment env) {

        CommandToken name = checkArgument(getArg(0), CommandToken.class, "Cannot create function with non-variable name " + getArg(0));
        ExpressionToken argTokens = checkArgument(getArg(1), ExpressionToken.class, "Cannot create function with non-expression arguments " + getArg(1));
        ExpressionToken exprs = checkArgument(getArg(2), ExpressionToken.class, "Cannot create function with non-expression expressions " + getArg(2));

        for (int i = 0; i < argTokens.size(); i++){
            checkArgument(argTokens.get(i), VariableToken.class, "Cannot create function with non-variable argument " + argTokens.get(i));
        }

        UserInstruction instruction = new UserInstruction(name.NAME, argTokens, exprs);

        env.addVariable(name.NAME, instruction);
        return null;
    }
}
