package oogasalad.frontend.windows;

import oogasalad.frontend.windows.WindowTypes.WindowType;

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
      case MODAL_WINDOW:
        return new ModalWindow(mediator);
      default:
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
