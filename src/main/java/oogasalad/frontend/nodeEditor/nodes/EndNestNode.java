package oogasalad.frontend.nodeEditor.nodes;

import java.util.ArrayList;
import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.configuration.NodeData;

public class EndNestNode extends AbstractNode implements ControlNode {

  public EndNestNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("EndNestNode.StyleClass"));
  }

  @Override
  public String getNodeParseString() {
    if (this.getChildNode() != null) {
      return propertyManager.getText("EndNestNode.JsonString") + this.getChildNode()
          .getNodeParseString();
    }
    return propertyManager.getText("EndNestNode.JsonString");
  }

  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }

  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("EndNestNode.Title"));
    this.getChildren().add(title);
  }

}
