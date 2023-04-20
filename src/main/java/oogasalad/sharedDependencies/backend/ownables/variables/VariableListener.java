package oogasalad.sharedDependencies.backend.ownables.variables;

/**
 * A listener for a variable.
 *
 * @param <T> the type of the variable
 */
public interface VariableListener<T> {

  /**
   * Called when the variable changes.
   *
   * @param value the new value of the variable
   */
  void onChange(T value);
}
