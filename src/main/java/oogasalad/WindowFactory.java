package oogasalad;

import oogasalad.WindowTypeEnum.WindowType;

public class WindowFactory {


  public static AbstractWindow createWindow(WindowType windowType, WindowMediator mediator) {
    switch (windowType) {
      case SPLASH_WINDOW:
        return new SplashScreenWindow(mediator);
      case EDIT_WINDOW:
        return new GameEditorWindow(mediator);
      case GAME_WINDOW:
        return new GamePlayerWindow(mediator);
      default:
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
