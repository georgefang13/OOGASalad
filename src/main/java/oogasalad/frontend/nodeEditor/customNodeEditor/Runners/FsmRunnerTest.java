package oogasalad.frontend.nodeEditor.customNodeEditor.Runners;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes.TextFieldNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.AbstractNode;

public class FsmRunnerTest extends Application {

  private final int DEFAULT_WIDTH = 100;
  private final int DEFAULT_HEIGHT = 100;

  private Pane pane2;
  private HBox allColumns;

  @Override
  public void start(Stage primaryStage) {
    Pane pane1 = new Pane();
    pane1.setStyle("-fx-background-color: gray");
    pane1.setPrefSize(100, 400);
    pane2 = new Pane();
    pane2.setMinSize(500, primaryStage.getHeight());
    pane2.setPrefWidth(500);
    pane2.setStyle("-fx-border-color: maroon");


    allColumns = new HBox();
    ScrollPane setupColumn = new ScrollPane();
    setupColumn.setStyle("-fx-background-color: red");
    setupColumn.setMinWidth(pane2.getWidth()/3);
    setupColumn.setMinHeight(pane2.getHeight());
    VBox box = new VBox();
    box.setPrefSize(150, 400);
    setupColumn.setContent(box);
    box.getChildren().add(new Label("Setup"));


    ScrollPane playerColumn = new ScrollPane();
    playerColumn.setStyle("-fx-background-color: blue");
    playerColumn.setMinWidth(pane2.getWidth()/3);
    playerColumn.setMinHeight(pane2.getHeight());
    VBox box1 = new VBox();
    box1.setPrefSize(150, 400);
    playerColumn.setContent(box1);
    box1.getChildren().add(new Label("Player"));



    ScrollPane gameColumn = new ScrollPane();
    gameColumn.setStyle("-fx-background-color: green");
    gameColumn.setMinWidth(pane2.getWidth()/3);
    gameColumn.setMinHeight(pane2.getHeight());
    VBox box2 = new VBox();
    box2.setPrefSize(150, 400);
    gameColumn.setContent(box2);
    box2.getChildren().add(new Label("Game"));






    allColumns.getChildren().addAll(setupColumn, playerColumn, gameColumn);
    pane2.getChildren().add(allColumns);

    Button sumButton = new Button("sum");
    sumButton.setStyle("-fx-min-width: 100");
    //sumButton.setOnAction(event -> box.getChildren()
    //    .add(new SumNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "white")));
    Button differenceButton = new Button("difference");
    differenceButton.setStyle("-fx-min-width: 100");
    //differenceButton.setOnAction(event -> box1.getChildren()
    //    .add(new DifferenceNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "blue")));
    Button textButton = new Button("Text");
    textButton.setStyle("-fx-min-width: 100");
    textButton.setOnAction(event -> box2.getChildren().add(new TextFieldNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "white")));
    Button submitButton = new Button("Submit");
    submitButton.setStyle("-fx-min-width: 100");
    submitButton.setOnAction(event -> System.out.println(sendAllNodeContent()));
    pane1.getChildren().addAll(new VBox(sumButton, differenceButton, textButton, submitButton));




    Scene scene = new Scene(new HBox(pane1, pane2), 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public String sendAllNodeContent() {
    String returnable = "";
    for (Node node: allColumns.getChildren()) {
//    for (Node node: gameColumn.getChildren()) {
      ScrollPane columnTop = (ScrollPane) node;
      VBox column = (VBox) columnTop.getContent();

      for (Node node1: column.getChildren()) {
        if (node1 instanceof AbstractNode) {
          //returnable += ((AbstractNode) node1).sendContent();
          returnable += "\n";
        }
      }

    }
    return returnable;
  }
}