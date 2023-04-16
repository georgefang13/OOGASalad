package oogasalad.gamerunner.backend.fsm;

public class GameRunnerController {

  private FSMExample fsmExample;

  public GameRunnerController() {
    fsmExample = new FSMExample();
  }

  public String userResponds(String enteredText) {
    String backendResponse = fsmExample.run(enteredText);
    return backendResponse;
  }

  public String initialInstruction() {
    return fsmExample.getInstruction();
  }
}
