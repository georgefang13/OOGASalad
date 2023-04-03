package oogasalad.gamerunner.backend.interpreter;

public class Interpreter implements Runnable {
    private final Tokenizer tokenizer;
    private final Evaluator evaluator;
    private final Environment env;

    public Interpreter(){
        env = new Environment();
        tokenizer = new Tokenizer();
        evaluator = new Evaluator(env);
    }

    /**
     * Loads code into the interpreter and immediately runs it.
     * @param input the code to run
     */
    public void interpret(String input){
        loadCode(input);
        evaluator.evaluate();
    }

    /**
     * Loads code into the interpreter without running it.
     * @param input the code to load
     */
    public void loadCode(String input){
        Parser p = new Parser(tokenizer.tokenize(input));
        evaluator.load(p.parse(env));
    }

    /**
     * runs the code loaded by the loadCode method in a new thread.
     */
    @Override
    public void run(){
        evaluator.evaluate();
    }

    /**
     * Steps the interpreter forward one step. To be used after using the loadCode method.
     */
    public void step(){
        evaluator.step();
    }

    /**
     * Links a simulation to the interpreter so that it can post events to the simulation.
     * @param game the simulation to link to
     */
    public void link(Object game){
        env.linkSimulation(game);
    }

    /**
     * Sets the language of the interpreter
     * @param language the language to set the interpreter to
     */
    public void setLanguage(String language){
        tokenizer.setLanguage(language);
    }
}
