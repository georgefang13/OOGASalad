package oogasalad.windowscene.controllers;

import oogasalad.windowscene.AbstractWindow;
import oogasalad.windowscene.controllers.WindowTypes.WindowType;
import oogasalad.windowscene.gameeditor.GameEditorWindow;
import oogasalad.windowscene.gameplayer.GamePlayerWindow;
import oogasalad.windowscene.splash.SplashWindow;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class WindowFactory {


  public static AbstractWindow createWindow(WindowType windowType, WindowMediator mediator) {
    switch (windowType) {
      case SPLASH_WINDOW:
        return new SplashWindow(mediator);
      case EDIT_WINDOW:
        return new GameEditorWindow(mediator);
      case GAME_WINDOW:
        return new GamePlayerWindow(mediator);
      default:
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
