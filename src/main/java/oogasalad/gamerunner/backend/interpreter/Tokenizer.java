package oogasalad.gamerunner.backend.interpreter;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import oogasalad.gamerunner.backend.interpreter.exceptions.TokenNotRecognizedException;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;

public class Tokenizer {

  // NOTE: make ONE chooser since generally accepted behavior is that it remembers where user left it last
  // command variations per class
  private static final String SYNTAX_RESOURCE_PATH = "backend.interpreter.Syntax";
  private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/Commands.json";

  // token types and the regular expression patterns that recognize those types
  // note, it is a list because order matters (some patterns may be more generic and so should be added last)
  private final List<Map.Entry<String, Pattern>> myTokenOptions = new ArrayList<>();

  private final List<String> builtinFunctions = new ArrayList<>();
  private final List<String> syntaxTokens = new ArrayList<>();

  private String comment;

  private final List<Token> tokens = new ArrayList<>();

  public Tokenizer() {
    init();
  }

  public void init() {
    myTokenOptions.clear();
    builtinFunctions.clear();
    syntaxTokens.clear();

    // add in the built-in functions
    for (Map.Entry<String, Pattern> p : getCommands()) {
      myTokenOptions.add(p);
      builtinFunctions.add(p.getKey());
    }

    // add in the general syntax types
    for (Map.Entry<String, Pattern> p : getSyntaxPatterns()) {
      myTokenOptions.add(p);
      syntaxTokens.add(p.getKey());
    }
  }

  /**
   * Returns token type associated with given text.
   */
  public String getSymbol(String text) throws IllegalArgumentException {
    for (Map.Entry<String, Pattern> e : myTokenOptions) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    throw new IllegalArgumentException(String.format("Invalid command given: %s", text));
  }

  /**
   * get the list of tokens from the tokenizer
   *
   * @return list of tokens
   */
  public List<Token> getTokens() {
    return new ArrayList<>(tokens);
  }

  // given some text, prints results of parsing it using the given language
  // note, this simple "algorithm" will not handle SLogo comments
  public List<Token> tokenize(String program) {
    // regular expression representing one or more whitespace characters (space, tab, or newline)
    final String WHITESPACE = "\\s+";

    tokens.clear();
    for (String line : program.split("\\r?\\n")) {
      line = line.replaceAll(comment, "");
      if (line.isEmpty()) {
        continue;
      }
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
      if (builtinFunctions.contains(match)) {
        Class<?> clazz = Class.forName(
            "oogasalad.gamerunner.backend.interpreter.commands." + match);
        Constructor<?> constructor = clazz.getConstructor();
        return (Token) constructor.newInstance();
      } else if (syntaxTokens.contains(match)) {
        return getSyntaxToken(key, match);
      } else {
        throw new TokenNotRecognizedException(match);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Token getSyntaxToken(String key, String match) {
    return (new SyntaxTokenFactory()).createSyntaxToken(key, match);
  }

  // Returns true only if given text matches given regular expression pattern
  private boolean match(String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return text != null && regex.matcher(text.trim()).matches();
  }

  // Add given resource file to this language's recognized types
  private List<Map.Entry<String, Pattern>> getSyntaxPatterns() {
    List<Map.Entry<String, Pattern>> tokens = new ArrayList<>();

    ResourceBundle resources = ResourceBundle.getBundle(SYNTAX_RESOURCE_PATH);

    for (String key : Collections.list(resources.getKeys())) {
      tokens.add(new AbstractMap.SimpleEntry<>(key,
          Pattern.compile(resources.getString(key), Pattern.CASE_INSENSITIVE)));
      if (key.equals("Comment")) {
        comment = resources.getString(key);
      }
    }
    return tokens;
  }

  private List<Map.Entry<String, Pattern>> getCommands() {
    List<Map.Entry<String, Pattern>> tokens = new ArrayList<>();

//        String absoluteFilePath = Objects.requireNonNull(Tokenizer.class.getClassLoader().getResource(COMMANDS_RESOURCE_PATH)).getPath();
    String absoluteFilePath = System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH;

    String fileContent = "";
    // Read the entire file content
    try {
      fileContent = Files.readString(Paths.get(absoluteFilePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    JsonElement json = JsonParser.parseString(fileContent);

    JsonObject obj = json.getAsJsonObject();

    for (String key : obj.keySet()) {
      JsonObject value = (JsonObject) obj.get(key);
      String folder = value.get("folder").getAsString();
      String str = value.get("str").getAsString();
      str = sanitizeRegex(str);

      String name = (folder.isEmpty() ? "" : folder + ".") + key;

      AbstractMap.SimpleEntry<String, Pattern> entry = new AbstractMap.SimpleEntry<>(
          name,
          Pattern.compile(str, Pattern.CASE_INSENSITIVE)
      );
      tokens.add(entry);
    }

    return tokens;
  }

  String sanitizeRegex(String str) {
    String specialChars = "[]{}()\\^$.|?*+";
    for (int i = 0; i < specialChars.length(); i++) {
      str = str.replace(String.valueOf(specialChars.charAt(i)), "\\" + specialChars.charAt(i));
    }
    return str;
  }
}