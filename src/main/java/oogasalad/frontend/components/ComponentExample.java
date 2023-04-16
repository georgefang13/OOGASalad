package oogasalad.frontend.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ComponentExample extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    ComponentsFactory factory = new ComponentsFactory();
    Pane root = new Pane();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    Component c = factory.create("GameObject");
    root.getChildren().add(c.getNode());
  }
}
