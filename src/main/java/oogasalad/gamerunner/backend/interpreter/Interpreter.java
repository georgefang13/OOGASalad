package oogasalad.gamerunner.backend.interpreter;

import java.util.List;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.GameToInterpreterAPI;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

public class Interpreter implements Runnable {

  private final Tokenizer tokenizer;
  private final Evaluator evaluator;
  private final Environment env;

  public Interpreter() {
    env = new Environment();
    tokenizer = new Tokenizer();
    evaluator = new Evaluator(env);
  }

  /**
   * Loads code into the interpreter and immediately runs it.
   *
   * @param input the code to run
   */
  public void interpret(String input) {
    loadCode(input);
    evaluator.evaluate();
  }

  /**
   * Loads a string of code into the interpreter without running it.
   *
   * @param input the code to load
   */
  public void loadCode(String input) {
    Parser p = new Parser(tokenizer.tokenize(input));
    evaluator.load(p.parse(env));
  }

  /**
   * Loads a list of tokens as code into the interpreter without running it.
   *
   * @param tokens the tokens to load
   */
  public void loadTokenizedCode(List<Token> tokens) {
    Parser p = new Parser(tokens);
    evaluator.load(p.parse(env));
  }

  /**
   * loads a parsed list of tokens into the interpreter without running it.
   *
   * @param tokens the expressions (as OperatorTokens) to load
   */
  public void loadParsedCode(List<Token> tokens) {
    evaluator.load(tokens);
  }

  /**
   * runs the code loaded by the loadCode method in a new thread.
   */
  @Override
  public void run() {
    evaluator.evaluate();
  }

  /**
   * Steps the interpreter forward one step. To be used after using the loadCode method.
   */
  public void step() {
    evaluator.step();
  }

  /**
   * Links a simulation to the interpreter so that it can post events to the simulation.
   *
   * @param game the simulation to link to
   */
  public void linkIdManager(IdManager<Ownable> game) {
    env.linkIdManager(game);
  }


  /**
   * Sets the language of the interpreter
   *
   * @param language the language to set the interpreter to
   */
  public void setLanguage(String language) {
    env.setLanguage(language);
  }

  public void linkGame(GameToInterpreterAPI game) {
    env.linkGame(game);
  }
}
