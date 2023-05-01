package oogasalad.gamerunner.backend.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.sharedDependencies.backend.id.IdManager;


public class FSM<T> {

  private final Map<T, State> states = new HashMap<>();
  private State currentState;
  private T currentStateName;

  private final List<T> history = new ArrayList<>();
  private final StateData data = new StateData();

  public FSM(IdManager<?> idManager) {
    data.put("idManager", idManager);
  }

  /**
   * Adds a state to the FSM.
   *
   * @param name  the name of the state
   * @param state the state object
   */
  public void putState(T name, State state) {
    putState(name, state, null);
  }

  /**
   * Adds a state to the FSM with a default next state
   *
   * @param name             the name of the state
   * @param state            the state object
   * @param nextStateChooser a function to determine the default next state
   */
  public void putState(T name, State state, NextStateChooser<T> nextStateChooser) {
    states.put(name, state);
    state.setDefaultTransition((NextStateChooser<Object>) nextStateChooser);
  }

  /**
   * Sets the current state of the FSM.
   *
   * @param name the name of the state
   */
  public void setState(T name) {
    if (currentState != null && !name.equals(currentStateName)) {
      currentState.onLeave(data);
    }

    history.add(name);

    currentState = states.get(name);
    currentStateName = name;
    currentState.onEnter(data);
  }

  public void undo(){
    if(history.size() > 1){
      history.remove(history.size() - 1);
      setState(history.get(history.size() - 1));
    }
  }

  /**
   * Gets the name of the current state of the FSM.
   *
   * @return the current state
   */
  public T getCurrentState() {
    return currentStateName;
  }

  /**
   * Gets the current state object of the FSM.
   *
   * @return the current state
   */
  public State getCurrentStateObject() {
    return currentState;
  }

  /**
   * Gets the names of all states in the FSM.
   *
   * @return the current state
   */
  public List<T> getStates() {
    return new ArrayList<>(states.keySet());
  }

  /**
   * Sets the current state's inner value.
   *
   * @param obj the value to set
   */
  public void setStateInnerValue(Object obj) {
    currentState.setInnerValue(data, obj);
  }

  /**
   * Transition to the default next state
   */
  public void transition() {
    Object next = currentState.getDefaultTransition().chooseNextState(currentState, data);
    setState((T) next);
  }

  /**
   * Transition to the next state based on the chooser
   *
   * @param chooser the chooser, returns the name of the state to move to
   */
  public void transition(NextStateChooser<T> chooser) {
    setState(chooser.chooseNextState(currentState, data));
  }

  public interface NextStateChooser<T> {

    /**
     * Chooses the next state based on the current state and data
     *
     * @param curState the current state
     * @param data     the data
     * @return the name of the next state
     */
    T chooseNextState(State curState, StateData data);
  }

  public static class StateData {

    private final Map<String, Object> data = new HashMap<>();

    /**
     * Puts a value into the data map
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, Object value) {
      data.put(key, value);
    }

    /**
     * Gets a value from the data map
     *
     * @param key the key
     * @return the value
     */
    public Object get(String key) {
      return data.get(key);
    }
  }

}
