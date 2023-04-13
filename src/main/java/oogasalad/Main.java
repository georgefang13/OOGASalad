package oogasalad;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.frontend.windows.WindowController;
import oogasalad.frontend.windows.WindowMediator;


/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    WindowMediator mediator = new WindowController();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
