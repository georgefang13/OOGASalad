package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.ownables.variables.VariableListener;
import oogasalad.sharedDependencies.backend.owners.Owner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TextObject extends GameObject{
    private String text = "";
    private Consumer<String> reaction;
    private final Map<Variable, VariableListener> listeners = new HashMap<>();
    public TextObject(Owner owner) {
        super(owner);
        reaction = (s) -> {};
    }

    public void setReaction(Consumer<String> reaction){
        this.reaction = reaction;
    }

    public void setText(String text){
        this.text = text;
        reaction.accept(text);
    }

    public String getText(){
        return text;
    }

    public void linkVariable(Variable variable){
        VariableListener listener = (s) -> setText(String.valueOf(s));
        variable.addListener(listener);
        listeners.put(variable, listener);
    }

    public void unLinkVariable(Variable var){
        if (listeners.containsKey(var)){
            var.removeListener(listeners.get(var));
            listeners.remove(var);
        }
    }
}
