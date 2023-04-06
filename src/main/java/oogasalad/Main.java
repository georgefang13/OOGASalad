package oogasalad;


import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.windowscene.WindowController;
import oogasalad.windowscene.WindowMediator;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    WindowMediator mediator = new WindowController();
  }
}
