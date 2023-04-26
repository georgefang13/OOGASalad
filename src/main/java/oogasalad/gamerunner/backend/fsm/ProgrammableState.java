package oogasalad.gamerunner.backend.fsm;

import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

public class ProgrammableState extends State {

  Object value;

  Interpreter interpreter;

  String initCode;
  String leaveCode;
  String setValueCode;

  public ProgrammableState(Interpreter interpreter, String initCode, String leaveCode,
      String setValueCode) {
    this.interpreter = interpreter;
    this.initCode = initCode;
    this.leaveCode = leaveCode;
    this.setValueCode = setValueCode;
  }

  @Override
  public void onEnter(FSM.StateData data) {
    Variable<Object> output = setStateOutput(data, null);
    interpreter.interpret(initCode);
    if (output.get() != null) {
      this.value = output.get();
    }
  }

  @Override
  public void onLeave(FSM.StateData data) {
    interpreter.interpret(leaveCode);
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public void setInnerValue(FSM.StateData data, Object value) {
    IdManager idManager = (IdManager) data.get("idManager");
    if (!idManager.isIdInUse("state_input")) {
      Variable<Object> input = new Variable<>(value);
      idManager.addObject(input, "state_input");
    } else {
      Variable<Object> input = (Variable<Object>) idManager.getObject("state_input");
      input.set(value);
    }
    Variable<Object> output = setStateOutput(data, value);
    interpreter.interpret(setValueCode);
    this.value = output.get();
  }

  /**
   * Sets the output of the state
   *
   * @param data  the state data
   * @param value the value to set the output to
   * @return the output variable
   */
  private Variable<Object> setStateOutput(FSM.StateData data, Object value) {
    IdManager idManager = (IdManager) data.get("idManager");
    Variable<Object> output;
    if (idManager.isIdInUse("state_output")) {
      output = (Variable<Object>) idManager.getObject("state_output");
    } else {
      output = new Variable<>(value);
      idManager.addObject(output, "state_output");
    }
    output.set(value);
    return output;
  }

}

