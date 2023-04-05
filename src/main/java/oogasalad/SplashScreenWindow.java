package oogasalad;

public class SplashScreenWindow extends AbstractWindow {

  public SplashScreenWindow(WindowMediator windowController) {
    super(windowController);
    AbstractScene scene = new SplashMainScene();
    setScene(scene.makeScene());
  }


}
