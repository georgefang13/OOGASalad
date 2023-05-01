package oogasalad;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.frontend.windows.WindowController;
import oogasalad.frontend.windows.WindowMediator;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;


/**
 * Main.
 *
 * @author Connor Wells
 * @author Owen MacKenzie
 */
public class Main extends Application {
  private static final MainLogger logger = MainLogger.getInstance(Main.class);

  @Override
  public void start(Stage primaryStage) throws Exception {
    logger.setLogLevel(Level.INFO);
    logger.info("Initiated a Session -----");

    WindowMediator mediator = new WindowController();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
