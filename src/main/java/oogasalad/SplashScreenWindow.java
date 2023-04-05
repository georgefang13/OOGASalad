package oogasalad;

public class SplashScreenWindow extends AbstractWindow {

  public SplashScreenWindow() {
    AbstractScene scene = new SplashMainScene();
    setScene(scene.makeScene());
  }


}
