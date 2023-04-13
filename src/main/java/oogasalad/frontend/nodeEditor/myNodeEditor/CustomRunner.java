package oogasalad.frontend.nodeEditor.myNodeEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomRunner extends Application {

  @Override
  public void start(Stage primaryStage) {
    Pane pane1 = new Pane();
    pane1.setStyle("-fx-background-color: black");
    Button redButton = new Button("Red");
    Button blueButton = new Button("Red");
    pane1.getChildren().addAll(new VBox(redButton, blueButton));
    pane1.setPrefSize(100, 200);

    Pane pane2 = new Pane();
    pane2.setPrefSize(200, 200);
    redButton.setOnAction(event -> {
          DraggableItem temp = new DraggableItem(50, 50, 50, 50, "red");
          new DraggableController(temp);
          pane2.getChildren().add(temp);
        }
    );
    blueButton.setOnAction(
        event -> pane2.getChildren().add(new DraggableItem(50, 50, 50, 50, "blue")));
    Scene scene = new Scene(new HBox(pane1, pane2), 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}