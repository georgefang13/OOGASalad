package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.NodeController;

public class StartNestNode extends DraggableAbstractNode {

  public StartNestNode(NodeController nodeController, double x, double y, double width,
      double height, String color) {
    super(nodeController, x, y, WIDTH, HEIGHT, "green");
    setContent();
    this.setStyle(
        "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: transparent; -fx-background-radius: 5px; -fx-padding: 5px;");
  }

  @Override
  public String getJSONString() {
    if (this.getChildNode() != null) {
      return "[" + this.getChildNode().getJSONString();
    }
    return "[";
  }

  @Override
  protected void setContent() {
    Label title = new Label("Start");
    this.getChildren().add(title);
  }
}
