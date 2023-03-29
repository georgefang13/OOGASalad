package oogasalad.backend.ownables.variables;

import oogasalad.backend.ownables.Ownable;
import java.util.ArrayList;
import java.util.List;
import oogasalad.backend.owners.Owner;

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
  public Variable() {
    this.value = null;
    this.listeners = new ArrayList<>();
  }

  /**
   * Creates a new variable with the given value.
   * @param value the initial value of the variable
   */
  public Variable(T value) {
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
   * Checks if the variable can be owned by the given owner.
   * @param potentialOwner the potential owner of the variable
   * @return true as variables can be owned by any owner
   */
  @Override
  public boolean canBeOwnedBy(Owner potentialOwner) {
    return true;
  }

  /**
   * Adds a listener to the variable.
   * @param listener the listener to add
   */
  public void addListener(VariableListener<T> listener) {
    listeners.add(listener);
  }

  /**
   * Removes a listener from the variable.
   * @param listener the listener to remove
   */
  public void removeListener(VariableListener<T> listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all listeners of a change in the variable.
   */
  private void notifyListeners() {
    for (VariableListener<T> listener : listeners) {
      listener.onChange(value);
    }
  }
}

/**
 * A listener for a variable.
 * @param <T> the type of the variable
 */
interface VariableListener<T> {

  /**
   * Called when the variable changes.
   * @param value the new value of the variable
   */
  void onChange(T value);
}
