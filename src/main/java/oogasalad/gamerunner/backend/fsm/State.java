package oogasalad.gamerunner.backend.fsm;

abstract class State {

    public void onInit(FSM.StateData data) {
        // any initialization code
    }

    public void onLeave(FSM.StateData data) {
        // any cleanup code
    }

    public abstract Object getValue();

    public void setInnerValue(FSM.StateData data, Object value){}

}
