package oogasalad.frontend.windows;

import oogasalad.frontend.windows.WindowTypes.WindowType;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class WindowFactory {

  private static final MainLogger logger = MainLogger.getInstance(WindowFactory.class);

  public static AbstractWindow createWindow(WindowType windowType, String windowID,
      WindowMediator mediator) {
    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class

    switch (windowType) {
      case SPLASH_WINDOW:
        logger.debug(String.format("Created a window: ID - %s", windowID));
        return new SplashWindow(windowID, mediator);
      case EDIT_WINDOW:
        logger.debug(String.format("Created a window: ID - %s", windowID));
        return new GameEditorWindow(windowID, mediator);
      case LIBRARY_WINDOW:
        logger.debug(String.format("Created a window ID - %s", windowID));
        return new LibraryWindow(windowID, mediator);
      case MODAL_WINDOW:
        logger.debug(String.format("Created a window ID - %s", windowID));
        return new ModalWindow(windowID, mediator);
      case GAME_WINDOW:
        logger.debug(String.format("Created a window: ID - %s", windowID));
        return new GameWindow(windowID, mediator);
      default:
        logger.error(String.format("Failed to create a new window because of the wrong windowType - %s",  windowType));
        throw new IllegalArgumentException("Invalid window type: " + windowType);
    }
  }
}
