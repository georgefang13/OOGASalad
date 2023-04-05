package oogasalad;

public class WindowFactory {
  public static AbstractWindow createWindow(String windowType) {
    switch (windowType) {
      case "SplashScreen":
        return new SplashScreenWindow();
      case "GameEditor":
        return new GameEditorWindow();
      case "GamePlayer":
        return new GamePlayerWindow();
      default:
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
