package oogasalad.gamerunner.backend.interpreter;

import oogasalad.gamerunner.backend.interpreter.exceptions.TokenNotRecognizedException;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

public class SyntaxTokenFactory {
    /**
     * Creates a syntax token based on the command type
     * @param value the value of the token
     * @param commandType the type of the token
     * @return the token
     */
    public Token createSyntaxToken(String value, String commandType){
        return switch (commandType) {
            case "Command" -> new CommandToken(value);
            case "Variable" -> new VariableToken(value);
            case "Constant" -> new ValueToken<>(Double.parseDouble(value));
            case "SquareBracket" -> new BracketToken(commandType, value);
            case "Parentheses" -> new ParenthesisToken(value);
            default -> throw new TokenNotRecognizedException(value);
        };
    }
}
