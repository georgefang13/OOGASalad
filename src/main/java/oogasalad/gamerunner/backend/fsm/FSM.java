package oogasalad.gamerunner.backend.fsm;

import oogasalad.gameeditor.backend.id.IdManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FSM<T> {
    private final Map<T, State> states = new HashMap<>();
    private final Map<T, T> defaultNextStates = new HashMap<>();
    private State currentState;
    private T currentStateName;
    private final StateData data = new StateData();

    FSM(IdManager<?> idManager) {
        data.put("idManager", idManager);
    }

    public void putState(T name, State state) {
        putState(name, state, null);
    }
    public void putState(T name, State state, T defaultNextState) {
        states.put(name, state);
        defaultNextStates.put(name, defaultNextState);
    }
    public void setState(T name) {
        if (currentState != null && !name.equals(currentStateName)){
            currentState.onLeave(data);
        }

        currentState = states.get(name);
        currentStateName = name;
        currentState.onInit(data);
    }

    public T getCurrentState(){
        return currentStateName;
    }
    public State getCurrentStateObject(){
        return currentState;
    }

    public List<T> getStates(){
        return new ArrayList<>(states.keySet());
    }

    public void setStateInnerValue(Object obj) {
        currentState.setInnerValue(data, obj);
    }

    public void transition() {
        setState(defaultNextStates.get(currentStateName));
    }
    public void transition(NextStateChooser<T> chooser) {
        setState(chooser.chooseNextState(currentState, data));
    }

    interface NextStateChooser <T> {
        T chooseNextState(State curState, StateData data);
    }

    static class StateData {
        private final Map<String, Object> data = new HashMap<>();
        public void put(String key, Object value) {
            data.put(key, value);
        }
        public Object get(String key) {
            return data.get(key);
        }
    }

}
