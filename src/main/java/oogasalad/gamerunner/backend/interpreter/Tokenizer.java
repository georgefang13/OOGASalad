package oogasalad.gamerunner.backend.interpreter;


import oogasalad.gamerunner.backend.interpreter.exceptions.TokenNotRecognizedException;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Pattern;

public class Tokenizer {
    // NOTE: make ONE chooser since generally accepted behavior is that it remembers where user left it last
    // command variations per class
    private static final String LANGUAGE_RESOURCE_PATH = Tokenizer.class.getPackageName();

    // token types and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic and so should be added last)
    private final List<Map.Entry<String, Pattern>> myTokenOptions = new ArrayList<>();

    private final List<String> builtinFunctions = new ArrayList<>();
    private final List<String> syntaxTokens = new ArrayList<>();

    private String comment;

    private final List<Token> tokens = new ArrayList<>();

    public Tokenizer(){
        this("English");
    }
    public Tokenizer(String patternLanguage){
        setLanguage(patternLanguage);
    }

    /**
     *  Returns token type associated with given text.
     */
    public String getSymbol (String text) throws IllegalArgumentException {
        for (Map.Entry<String, Pattern> e : myTokenOptions) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        throw new IllegalArgumentException(String.format("Invalid command given: %s", text));
    }

    /**
     * get the list of tokens from the tokenizer
     * @return list of tokens
     */
    public List<Token> getTokens(){
        return new ArrayList<>(tokens);
    }

    // given some text, prints results of parsing it using the given language
    // note, this simple "algorithm" will not handle SLogo comments
    public List<Token> tokenize (String program) {
        // regular expression representing one or more whitespace characters (space, tab, or newline)
        final String WHITESPACE = "\\s+";

        tokens.clear();
        for (String line : program.split("\\r?\\n")) {
            line = line.replaceAll(comment, "");
            if (line.isEmpty()) continue;
            for (String symbol : line.split(WHITESPACE)) {
                if (!symbol.isEmpty()) {
                    tokens.add(extractToken(symbol, getSymbol(symbol)));
                }
            }
        }

        return getTokens();
    }
    private Token extractToken(String key, String match) throws RuntimeException {
        try {
            if (builtinFunctions.contains(match)){
                Class<?> clazz = Class.forName("slogo.model.interpreter.commands." + match);
                Constructor<?> constructor = clazz.getConstructor();
                return (Token) constructor.newInstance();
            }
            else if (syntaxTokens.contains(match)){
                return getSyntaxToken(key, match);
            }
            else {
                throw new TokenNotRecognizedException(match);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Token getSyntaxToken(String key, String match){
        return (new SyntaxTokenFactory()).createSyntaxToken(key, match);
    }

    // Returns true only if given text matches given regular expression pattern
    private boolean match (String text, Pattern regex) {
        // THIS IS THE IMPORTANT LINE
        return text != null && regex.matcher(text.trim()).matches();
    }

    /**
     * set the language of the tokenizer
     * @param language the language to set the tokenizer to
     */
    public void setLanguage(String language) {
        myTokenOptions.clear();
        builtinFunctions.clear();
        syntaxTokens.clear();

        // add in the built-in functions
        for (Map.Entry<String, Pattern> p : getPatterns(language, true)){
            myTokenOptions.add(p);
            builtinFunctions.add(p.getKey());
        }

        // add in the general syntax types
        for (Map.Entry<String, Pattern> p : getPatterns("Syntax", false)){
            myTokenOptions.add(p);
            syntaxTokens.add(p.getKey());
        }
    }

    // Add given resource file to this language's recognized types
    private List<Map.Entry<String, Pattern>> getPatterns (String language, boolean languageFile) {
        List<Map.Entry<String, Pattern>> tokens = new ArrayList<>();
        String path = LANGUAGE_RESOURCE_PATH + (languageFile ? ".languages." : ".") + language;
        ResourceBundle resources = ResourceBundle.getBundle(path);
        for (String key : Collections.list(resources.getKeys())) {
            tokens.add(new AbstractMap.SimpleEntry<>(key,
                    Pattern.compile(resources.getString(key), Pattern.CASE_INSENSITIVE)));
            if (key.equals("Comment")){
                comment = resources.getString(key);
            }
        }
        return tokens;
    }
}