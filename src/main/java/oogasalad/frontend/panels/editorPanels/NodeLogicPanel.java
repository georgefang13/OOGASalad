package oogasalad.frontend.panels.editorPanels;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.Panel;

public class NodeLogicPanel extends Pane implements Panel {

  private final int DEFAULT_WIDTH = 100;
  private final int DEFAULT_HEIGHT = 100;

  /**
   * Constructor for the environment panel
   */
  public NodeLogicPanel() {
    super();
  }

  public Panel makePanel() {
    Pane pane1 = new Pane();
    pane1.setStyle("-fx-background-color: gray");
    pane1.setPrefSize(100, 200);
    Pane pane2 = new Pane();
    pane2.setPrefSize(200, 200);

    Button sumButton = new Button("sum");
    sumButton.setStyle("-fx-min-width: 100");
    //sumButton.setOnAction(event -> pane2.getChildren()
    //    .add(new SumNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "white")));
    Button differenceButton = new Button("difference");
    differenceButton.setStyle("-fx-min-width: 100");
    //differenceButton.setOnAction(event -> pane2.getChildren()
    //    .add(new DifferenceNode(50, 50, DEFAULT_WIDTH, DEFAULT_HEIGHT, "lightpink")));
    pane1.getChildren().addAll(new VBox(sumButton, differenceButton));

    this.getChildren().addAll(pane1, pane2);

    return this;
  }

  @Override
  public Node asNode() {
    return this;
  }

  @Override
  public void refreshPanel() {}

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void save() {

  }
}
