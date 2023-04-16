package oogasalad.frontend.components;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComponentsManager extends Application {

  private ComponentsFactory factory;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    factory = new ComponentsFactory();

    // Create components
    Button button = (Button) factory.createComponent("Button");
    TextField textField = (TextField) factory.createComponent("TextField");
    Label label = (Label) factory.createComponent("Label");

    // Set up layout
    VBox root = new VBox();
    root.getChildren().addAll(button, textField, label);

    // Set up scene
    Scene scene = new Scene(root, 300, 200);

    // Set up stage
    primaryStage.setScene(scene);
    primaryStage.setTitle("Component Manager");
    primaryStage.show();
  }

  private static class ComponentsFactory {

    public Node createComponent(String componentType) {
      if (componentType.equals("Button")) {
        return new Button("Button");
      } else if (componentType.equals("TextField")) {
        return new TextField();
      } else if (componentType.equals("Label")) {
        return new Label("Label");
      } else {
        throw new IllegalArgumentException("Unknown component type: " + componentType);
      }
    }
  }

}




