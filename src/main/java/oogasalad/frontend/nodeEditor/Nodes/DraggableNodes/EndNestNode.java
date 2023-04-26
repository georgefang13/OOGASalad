package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.NodeController;

public class EndNestNode extends DraggableAbstractNode {

  public EndNestNode(NodeController nodeController, double x, double y,
      double indent, double width, double height, String color) {
    super(nodeController, DEFAULT_X, DEFAULT_Y, indent, WIDTH, HEIGHT, "red");
    setContent();
    this.setStyle(
        "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: transparent; -fx-background-radius: 5px; -fx-padding: 5px;");
  }

  @Override
  public String getJSONString() {
    return "]";
  }

  @Override
  protected void setContent() {
    Label title = new Label("End");
    this.getChildren().add(title);
  }


}
