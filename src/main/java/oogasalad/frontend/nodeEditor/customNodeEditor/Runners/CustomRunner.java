package oogasalad.frontend.nodeEditor.customNodeEditor.Runners;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DifferenceNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.SumNode;

public class CustomRunner extends Application {

  private final int DEFAULT_WIDTH = 100;
  private final int DEFAULT_HEIGHT = 100;

  @Override
  public void start(Stage primaryStage) {
    Pane pane1 = new Pane();
    pane1.setStyle("-fx-background-color: gray");
    pane1.setPrefSize(100, 200);
    Pane pane2 = new Pane();
    pane2.setPrefSize(200, 200);

    Button sumButton = new Button("sum");
    sumButton.setStyle("-fx-min-width: 100");
    //sumButton.setOnAction(event -> pane2.getChildren()
     //   .add(new SumNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "white")));
    Button differenceButton = new Button("difference");
    differenceButton.setStyle("-fx-min-width: 100");
    //differenceButton.setOnAction(event -> pane2.getChildren()
    //    .add(new DifferenceNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "blue")));
    pane1.getChildren().addAll(new VBox(sumButton, differenceButton));

    Scene scene = new Scene(new HBox(pane1, pane2), 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}