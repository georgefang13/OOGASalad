package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import oogasalad.frontend.nodeEditor.NodeController;

public class StartNestNode extends DraggableAbstractNode {

  public StartNestNode(NodeController nodeController, double x,
      double y, double width, double height, String color) {
    super(nodeController, x, y, width, height, color);
  }

  @Override
  public String getJSONString() {
    return "[";
  }

  @Override
  protected void setContent() {

  }
}
