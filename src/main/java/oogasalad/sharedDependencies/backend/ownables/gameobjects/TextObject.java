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
    private Variable var = null;
    private VariableListener listener = null;
    public TextObject(Owner owner) {
        super(owner);
        reaction = (s) -> {};
    }

    public void setReaction(Consumer<String> reaction){
        this.reaction = reaction;
    }

    public void setTextNoReaction(String text){
        this.text = text;
    }

    public void setText(String text){
        this.text = text;
        reaction.accept(text);
    }

    public String getText(){
        return text;
    }

    public void linkVariable(Variable variable){
        unLink();
        VariableListener listener = (s) -> setText(String.valueOf(s));
        variable.addListener(listener);
        var = variable;
        this.listener = listener;
    }

    public void unLink(){
        if (var != null) var.removeListener(listener);
    }
}
