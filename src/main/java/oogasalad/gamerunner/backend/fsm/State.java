package oogasalad.gamerunner.backend.fsm;

public abstract class State {

  private FSM.NextStateChooser<Object> defaultTransition = null;

  /**
   * Called when the state is entered.
   *
   * @param data the data object
   */
  public void onEnter(FSM.StateData data) {
    // any initialization code
  }

  /**
   * Called when the state is left.
   *
   * @param data the data object
   */
  public void onLeave(FSM.StateData data) {
    // any cleanup code
  }

  /**
   * Get the inner value of the state
   *
   * @return the next state
   */
  public abstract Object getValue();

  public FSM.NextStateChooser<Object> getDefaultTransition() {
    return defaultTransition;
  }

  public void setDefaultTransition(FSM.NextStateChooser<Object> defaultTransition) {
    this.defaultTransition = defaultTransition;
  }

  /**
   * Sets the inner value of the state.
   *
   * @param data  the data object
   * @param value the value to set
   */
  public void setInnerValue(FSM.StateData data, Object value) {
  }

}
