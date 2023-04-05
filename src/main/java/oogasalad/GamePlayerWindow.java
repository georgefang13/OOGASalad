package oogasalad;

public class GamePlayerWindow extends AbstractWindow {

  public GamePlayerWindow(WindowMediator windowController) {
    super(windowController);
    AbstractScene scene = new SplashMainScene();
    setScene(scene.makeScene());
  }
}
