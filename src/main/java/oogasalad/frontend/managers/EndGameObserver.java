package oogasalad.frontend.managers;

import java.util.Observable;
import java.util.Observer;
public class EndGameObserver extends Observable {
    private boolean value;

    public void setValue(boolean value) {
        this.value = value;
        setChanged(); // mark the observable as changed
        notifyObservers(value); // notify all observers with the new value
    }
}
