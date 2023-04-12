package oogasalad.frontend.windows;

import oogasalad.frontend.windows.WindowTypes.WindowType;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class WindowFactory {


  public static AbstractWindow createWindow(WindowType windowType, String windowID, WindowMediator mediator) {
    switch (windowType) {
      case SPLASH_WINDOW:
        return new SplashWindow(windowID,mediator);
      case EDIT_WINDOW:
        return new GameEditorWindow(windowID,mediator);
      case GAME_WINDOW:
        return new GamePlayerWindow(windowID,mediator);
      case MODAL_WINDOW:
        return new ModalWindow(windowID,mediator);
      default:
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
