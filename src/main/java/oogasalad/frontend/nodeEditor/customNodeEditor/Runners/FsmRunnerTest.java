package oogasalad.frontend.nodeEditor.customNodeEditor.Runners;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.CustomNodes.TextFieldNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DifferenceNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.SumNode;

public class FsmRunnerTest extends Application {

  private final int DEFAULT_WIDTH = 100;
  private final int DEFAULT_HEIGHT = 100;

  @Override
  public void start(Stage primaryStage) {
    Pane pane1 = new Pane();
    pane1.setStyle("-fx-background-color: gray");
    pane1.setPrefSize(100, 200);
    Pane pane2 = new Pane();
    pane2.setMinSize(primaryStage.getWidth()-pane1.getWidth(), primaryStage.getHeight());
    pane2.setStyle("-fx-border-color: maroon");

    Button sumButton = new Button("sum");
    sumButton.setStyle("-fx-min-width: 100");
    sumButton.setOnAction(event -> pane2.getChildren()
        .add(new SumNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "white")));
    Button differenceButton = new Button("difference");
    differenceButton.setStyle("-fx-min-width: 100");
    differenceButton.setOnAction(event -> pane2.getChildren()
        .add(new DifferenceNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "blue")));
    Button textButton = new Button("Text");
    textButton.setStyle("-fx-min-width: 100");
    textButton.setOnAction(event -> pane2.getChildren()
            .add(new TextFieldNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "white")));
    pane1.getChildren().addAll(new VBox(sumButton, differenceButton, textButton));


    HBox allColumns = new HBox();
    ScrollPane setupColumn = new ScrollPane();
    setupColumn.setStyle("-fx-background-color: red");
    setupColumn.setMinWidth(pane2.getWidth()/3);
    setupColumn.setMinHeight(pane2.getHeight());
    ScrollPane playerColumn = new ScrollPane();
    playerColumn.setStyle("-fx-background-color: blue");
    playerColumn.setMinWidth(pane2.getWidth()/3);
    playerColumn.setMinHeight(pane2.getHeight());

    ScrollPane gameColumn = new ScrollPane();
    gameColumn.setStyle("-fx-background-color: white");
    gameColumn.setMinWidth(pane2.getWidth()/3);
    gameColumn.setMinHeight(pane2.getHeight());



    allColumns.getChildren().addAll(setupColumn, playerColumn, gameColumn);
    pane2.getChildren().add(allColumns);
    Scene scene = new Scene(new HBox(pane1, pane2), 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}