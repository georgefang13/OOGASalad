package oogasalad;

import oogasalad.WindowTypeEnum.WindowType;

public class WindowFactory {


  public static AbstractWindow createWindow(WindowType windowType) {
    switch (windowType) {
      case SPLASH_WINDOW:
        return new SplashScreenWindow();
      case EDIT_WINDOW:
        return new GameEditorWindow();
      case GAME_WINDOW:
        return new GamePlayerWindow();
      default:
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
