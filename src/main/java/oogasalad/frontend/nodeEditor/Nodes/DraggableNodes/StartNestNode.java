package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;


import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.NodeController;

public class StartNestNode extends AbstractNode {

  public StartNestNode( double x, double y, double width,
      double height, String color) {
    super(x, y, width, height);
    setContent();
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
