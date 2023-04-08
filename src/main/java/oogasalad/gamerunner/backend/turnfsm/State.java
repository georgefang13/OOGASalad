package oogasalad.gamerunner.backend.turnfsm;

abstract class State {

    public void onInit(FSM.StateData data) {
        // any initialization code
    }

    public void onLeave(FSM.StateData data) {
        // any cleanup code
    }

    public abstract Object getState();

    public void setState(Object state){}

}
