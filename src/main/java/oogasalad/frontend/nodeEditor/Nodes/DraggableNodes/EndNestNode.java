package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

public class EndNestNode extends AbstractNode {

  public EndNestNode(double x, double y, double width, double height,
      String color) {
    super( x, y, WIDTH, HEIGHT);
    setContent();
    this.setStyle(
        "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: white; -fx-background-radius: 5px; -fx-padding: 5px;");
  }

  @Override
  public String getJSONString() {
    if (this.getChildNode() != null) {
      return "]" + this.getChildNode().getJSONString();
    }
    return "]";
  }

  @Override
  protected void setContent() {
    Label title = new Label("End");
    this.getChildren().add(title);
  }

}
