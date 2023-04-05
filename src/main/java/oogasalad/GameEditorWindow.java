package oogasalad;

public class GameEditorWindow extends AbstractWindow {

  public GameEditorWindow(WindowMediator windowController) {
    super(windowController);
    AbstractScene scene = new SplashMainScene();
    setScene(scene.makeScene());
  }
}
