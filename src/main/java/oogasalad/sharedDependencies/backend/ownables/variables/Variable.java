package oogasalad.sharedDependencies.backend.ownables.variables;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

/**
 * An Ownable variable that can be changed and listened to.
 * @param <T> the type of the variable
 * @author Michael Bryant
 */
public class Variable<T> extends Ownable {

  private T value;
  private final List<VariableListener<T>> listeners;

  /**
   * Creates a new variable with no value.
   */
  public Variable(IdManager idManager) {
    this(idManager, null);
  }

  /**
   * Creates a new variable with the given value.
   * @param value the initial value of the variable
   */
  public Variable(IdManager idManager, T value) {
    super(idManager);
    this.value = value;
    this.listeners = new ArrayList<>();
  }

  /**
   * Gets the current value of the variable.
   * @return the current value of the variable
   */
  public T get() {
    return value;
  }

  /**
   * Sets the value of the variable and notifies all listeners.
   * @param value the new value of the variable
   */
  public void set(T value) {
    this.value = value;
    notifyListeners();
  }

  /**
   * Adds a listener to the variable.
   * @param listener the listener to add
   */
  public void addListener(VariableListener<T> listener) {
    listeners.add(listener);
  }

  /**
   * Removes a listener from the variable, if it is there.
   * @param listener the listener to remove
   */
  public void removeListener(VariableListener<T> listener) {
    if(listeners.contains(listener)) {
      listeners.remove(listener);
    }
  }

  /**
   * Clears all listeners from the variable.
   */
  public void clearListeners() {
    listeners.clear();
  }

  /**
   * Gets an uneditable list of all listeners.
   * @return an uneditable list of all listeners
   */
  public List getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  /**
   * Notifies all listeners of a change in the variable.
   */
  private void notifyListeners() {
    for (VariableListener<T> listener : listeners) {
      listener.onChange(value);
    }
  }

  /**
   * Copies the variable.
   * @param idManager the id manager to use for the copy
   * @return the copy of the variable
   */
  public Variable<T> copy(IdManager idManager){
    return new Variable<>(idManager, value);
  }

}

