package oogasalad.sharedDependencies.backend.ownables.variables;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * An Ownable variable that can be changed and listened to.
 *
 * @param <T> the type of the variable
 * @author Michael Bryant
 */
public class Variable<T> extends Ownable {

  // TODO: separate valid classes into properties file
  private final Set<Class<?>> validClasses = new HashSet<>(
      Arrays.asList(String.class, Integer.class));

  private T value;
  private final List<VariableListener<T>> listeners;

  /**
   * Creates a new variable with no value.
   */
  public Variable(Owner owner) {
    this(null, owner);
  }

  /**
   * Creates a new variable with the given value.
   *
   * @param value the initial value of the variable
   * @param owner the owner of the ownable
   */
  public Variable(T value, Owner owner) {
    super(owner);
    this.value = value;
    this.listeners = new ArrayList<>();
  }

  /**
   * Creates a new variable with the given value and no (null) owner.
   *
   * @param value the initial value of the variable
   */
  public Variable(T value) {
    this(value, null);
  }

  /**
   * Gets the current value of the variable.
   *
   * @return the current value of the variable
   */
  public T get() {
    return value;
  }

  /**
   * Sets the value of the variable and notifies all listeners.
   *
   * @param value the new value of the variable
   */
  public void set(T value) {
    this.value = value;
    notifyListeners();
  }

  /**
   * Adds a listener to the variable.
   *
   * @param listener the listener to add
   */
  public void addListener(VariableListener<T> listener) {
    listeners.add(listener);
  }

  /**
   * Removes a listener from the variable, if it is there.
   *
   * @param listener the listener to remove
   */
  public void removeListener(VariableListener<T> listener) {
    if (listeners.contains(listener)) {
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
   *
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

  @Override
  public String toString(){
    return "Var<" + value + ">";
  }

  public String getType() {
    return value.getClass().getSimpleName();
  }
}

