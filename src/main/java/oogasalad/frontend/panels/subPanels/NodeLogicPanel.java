package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.VisualPanel;

public class NodeLogicPanel extends VisualPanel {

  private final int DEFAULT_WIDTH = 100;
  private final int DEFAULT_HEIGHT = 100;

  /**
   * Constructor for the environment panel
   */
  public NodeLogicPanel() {
    super();
  }

  public HBox createNodeLogicEnvironment() {
    Pane root = new Pane();
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

    root.getChildren().addAll(pane1, pane2);

    return new HBox(pane1, pane2);
  }

  @Override
  public Panel makePanel() {
    return null;
  }

  @Override
  public Panel refreshPanel() {
    return null;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void save() {

  }
}
