package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

public class EndNestNode extends AbstractNode {

  public EndNestNode(double x, double y, double width, double height) {
    super(x, y, width, height);
    setContent();
    this.getStyleClass().add("control");

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
