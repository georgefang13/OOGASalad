package oogasalad;


import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.windowscene.controllers.WindowController;
import oogasalad.windowscene.controllers.WindowMediator;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    WindowMediator mediator = new WindowController();
  }
}
