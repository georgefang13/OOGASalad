package oogasalad;

public class SplashScreenWindow extends AbstractWindow {

  public SplashScreenWindow() {
    super();
    AbstractScene scene = new SplashMainScene();
    setScene(scene.makeScene());
  }


}
