package oogasalad.gamerunner.backend.interpreter;

import java.util.ArrayList;
import java.util.List;

import oogasalad.gamerunner.backend.interpreter.commands.control.FVar;
import oogasalad.gamerunner.backend.interpreter.commands.control.MakeUserInstruction;
import oogasalad.gamerunner.backend.interpreter.commands.control.UserInstruction;
import oogasalad.gamerunner.backend.interpreter.exceptions.InvalidSyntaxException;
import oogasalad.gamerunner.backend.interpreter.tokens.BracketToken;
import oogasalad.gamerunner.backend.interpreter.tokens.CommandToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OpenCloseToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ParenthesisToken;
import oogasalad.gamerunner.backend.interpreter.tokens.SetToken;
import oogasalad.gamerunner.backend.interpreter.tokens.TempFunctionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;

public class Parser {
    private final List<Token> tokens;
    private Token lastToken = null;
    private int index = 0;
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    public Parser(ExpressionToken expr) {
        this.tokens = new ArrayList<>();
        for (Token t : expr) {
            tokens.add(t);
        }
    }
    private Token next() {
        return tokens.get(index++);
    }
    private boolean hasNext() {
        return index < tokens.size();
    }

    /**
     * Parses the tokens into a list of expressions
     * @param env the environment to parse the tokens in
     * @return the list of expressions
     */
    public List<Token> parse(Environment env) {
        ArrayList<Token> expressions = new ArrayList<>();
        env.createLocalScope();
        while (hasNext()) {
            Token t = parseExpression(env);
            expressions.add(t);
        }
        env.endLocalScope();
        return expressions;
    }
    private Token parseExpression(Environment env) {
        Token t = next();

        if (t instanceof OperatorToken op) {

            if (lastToken instanceof FVar) {
                lastToken = op;
                return op;
            }

            lastToken = t;
            Token tok = parseOperator(env, op);
            if (tok instanceof MakeUserInstruction m){
                m.evaluate(env);
            }
            return tok;
        }
        else if (t instanceof CommandToken c) {
            return parseCommandToken(c, env);
        }
        else if (t instanceof BracketToken b) {
            return parseBracketToken(b, env);
        }
        else if (t instanceof ParenthesisToken p) {
            return parseSet(p, env);
        }
        return t;
    }

    private Token parseCommandToken(CommandToken c, Environment env){

        if (lastToken instanceof MakeUserInstruction){
            lastToken = c;
            return c;
        }

        if (env.getLocalVariable(c.NAME) instanceof UserInstruction m){
            if (lastToken instanceof FVar){
                lastToken = m;
                return m;
            }
            TempFunctionToken func = new TempFunctionToken(m.getNumArgs(), c.NAME);
            lastToken = parseOperator(env, func);
            return lastToken;
        }
        else {
            throw new InvalidSyntaxException("Command \"" + c.NAME + "\" not defined");
        }
    }

    private ExpressionToken parseBracketToken(BracketToken b, Environment env){
        if (!b.isOpen()) throw new InvalidSyntaxException("Unexpected closing bracket");
        ExpressionToken expr = new ExpressionToken();
        createExpression(expr, env);
        Parser tempParser = new Parser(expr);
        List<Token> tokens = tempParser.parse(env);
        expr.passTokens(tokens);
        return expr;
    }

    private OperatorToken parseOperator(Environment env, OperatorToken m) {
//        System.out.println("PARSING OPERATOR " + m);
        Token[] args = new Token[m.getNumArgs()];
        for (int i = 0; i < args.length; i++) {
            if (!hasNext()) throw new InvalidSyntaxException("Not enough arguments for operator " + m.SUBTYPE);
            args[i] = parseExpression(env);
        }
//        System.out.println("ARGS TO " + m + ": " + Arrays.toString(args));
        m.passArguments(args);
        return m;
    }

    private SetToken parseSet(ParenthesisToken p, Environment env) {
        if (!p.isOpen()) throw new InvalidSyntaxException("Unexpected closing parenthesis");
        if (!hasNext()) throw new InvalidSyntaxException("Set not closed");
        Token t = next();

        if (t instanceof OperatorToken op){
            SetToken set = new SetToken(op);

            if (op.getNumArgs() == 0) throw new InvalidSyntaxException("Set called with operator " + op + " that takes zero arguments");

            ExpressionToken expr = new ExpressionToken();
            createExpression(expr, env);
            Parser tempParser = new Parser(expr);
            List<Token> tokens = tempParser.parse(env);
            set.passTokens(tokens);

            return set;
        }
        else {
            throw new InvalidSyntaxException("Set not called with operator");
        }
    }

    /**
     * Gets all code inside brackets
     * @param expr the ExpressionToken to add the tokens to
     */
    private void createExpression(ExpressionToken expr,  Environment env) {
        int openBrackets = 1;
        while (openBrackets > 0 && hasNext()) {
            Token t = next();
            if (t instanceof OpenCloseToken b){
                if (b.isOpen()) {
                    openBrackets++;
                    expr.addToken(t, env);
                } else if (--openBrackets > 0){
                    expr.addToken(t, env);
                }
            } else {
                expr.addToken(t, env);
            }
        }
        if(openBrackets > 0) throw new InvalidSyntaxException("Bracket not closed: " + openBrackets);
    }

}
