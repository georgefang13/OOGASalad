package oogasalad.frontend.windows;

import ch.qos.logback.classic.Level;
import oogasalad.Main;
import oogasalad.frontend.windows.WindowTypes.WindowType;
import oogasalad.logging.MainLogger;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class WindowFactory {

  private static final MainLogger logger = MainLogger.getInstance(WindowFactory.class);

  public static AbstractWindow createWindow(WindowType windowType, String windowID,
      WindowMediator mediator) {
    logger.setLogLevel(Level.DEBUG);

    switch (windowType) {
      case SPLASH_WINDOW:
        logger.debug(String.format("Created a window: ID - %s", windowID));
        return new SplashWindow(windowID, mediator);
      case EDIT_WINDOW:
        logger.debug(String.format("Created a window: ID - %s", windowID));
        return new GameEditorWindow(windowID, mediator);
      case GAME_WINDOW:
        logger.debug(String.format("Created a window: ID - %s", windowID));
        return new GamePlayerWindow(windowID, mediator);
      case MODAL_WINDOW:
        logger.debug(String.format("Created a window ID - %s", windowID));
        return new ModalWindow(windowID, mediator);
      default:
        logger.error(String.format("Failed to create a new window because of the wrong windowType - %s",  windowType));
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
