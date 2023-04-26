package oogasalad.gamerunner.backend.interpreter.commands.game;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.Parser;
import oogasalad.gamerunner.backend.interpreter.Tokenizer;
import oogasalad.gamerunner.backend.interpreter.commands.control.MakeUserInstruction;
import oogasalad.gamerunner.backend.interpreter.commands.control.UserInstruction;
import oogasalad.gamerunner.backend.interpreter.tokens.*;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.List;

public class GetRule extends OperatorToken {

    public GetRule(){
        super(3, "GetRule");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0).evaluate(env);
        Token t2 = getArg(1).evaluate(env);
        Token t3 = getArg(2);

        if (!(t3 instanceof ExpressionToken)) t3 = t3.evaluate(env);

        ValueToken<Ownable> obj = checkArgumentWithSubtype(env, t, ValueToken.class, Ownable.class.getName());
        ValueToken<String> rulevar = checkArgumentWithSubtype(env, t2, ValueToken.class, String.class.getName());
        ExpressionToken inputs = checkArgument(env, t3, ExpressionToken.class);

        String rule = env.getGame().getRules().getRuleFromObject(obj.VALUE, rulevar.VALUE);

        Tokenizer tz = new Tokenizer();
        List<Token> toks = new Parser(tz.tokenize(rule)).parse(env);

        MakeUserInstruction op = (MakeUserInstruction) toks.get(0);
        op.modifyScope(false);

        UserInstruction ui = op.evaluate(env);

        Token[] args = new Token[inputs.size()];
        for (int i = 0; i < inputs.length(); i++){
            Token arg = inputs.get(i);
            if (!(arg instanceof ExpressionToken)) arg = arg.evaluate(env);

            args[i] = arg;
        }
        ui.passArguments(args);
        return ui.evaluate(env);

    }
}
