package oogasalad.gamerunner.backend.interpreter;

import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    private final List<Map<String, Token>> scope = new ArrayList<>();
    private Object game = new EmptySim();

    public Environment(){
        scope.add(new HashMap<>());
    }

    public void linkSimulation(Object game){
        this.game = game;
    }

    /////////////////// SCOPE ///////////////////

    /**
     * retrieve a variable from the scope (checks from most current scope to global scope)
     * @param name the name of the variable
     * @return the token corresponding to the variable
     */
    public Token getLocalVariable(String name){
        for (int i = scope.size() - 1; i >= 0; i--){
            if (scope.get(i).containsKey(name)){
                return scope.get(i).get(name);
            }
        }
        return new ValueToken<>(0.);
    }

    /**
     * Creates a variable to the current scope
     * @param name the name of the variable
     * @param val the token corresponding to the variable
     */
    public void addVariable(String name, Token val){
        Map<String, Token> curScope = scope.get(scope.size() - 1);
        if (curScope.containsKey(name + "-global")){
            // loop through all scopes and replace the global variable, if it doesn't exist put in global
            for (int i = scope.size() - 1; i >= 0; i--){
                if (scope.get(i).containsKey(name) || i == 0){
                    scope.get(i).put(name, val);
                    return;
                }
            }
        }
        scope.get(scope.size() - 1).put(name, val);
    }

    /**
     * Adds a new scope to the scope list (to be used when a function is called)
     */
    public void createLocalScope(){
        scope.add(new HashMap<>());
    }

    /**
     * Removes scope from the scope list (to be used after function finishes)
     */
    public void endLocalScope(){
        scope.remove(scope.get(scope.size() - 1));
    }

    /////////////////// SEND EVENTS ///////////////////


    // does nothing
    static class EmptySim {

    }
}
