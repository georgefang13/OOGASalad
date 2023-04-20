package oogasalad.frontend.components;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
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
    Component c = factory.create("TextObject");
    root.getChildren().add(c.getNode());
  }
}
