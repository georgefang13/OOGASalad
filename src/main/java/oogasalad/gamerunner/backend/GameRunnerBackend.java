package oogasalad.gamerunner.backend;

public interface GameRunnerBackend {

  void loadGame(String gameDirectory);

  void initGame();

  void clickPiece(String objectId);

  void keyDown(String key);

  void keyUp(String key);

}
