package oogasalad.frontend.nodeEditor.nodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.configuration.NodeData;

import java.util.ArrayList;

public class StartNestNode extends AbstractNode implements ControlNode {

  public StartNestNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("StartNestNode.StyleClass"));
  }

  @Override
  public String getNodeParseString() {
    if (this.getChildNode() != null) {
      return propertyManager.getText("StartNestNode.JsonString") + this.getChildNode()
          .getNodeParseString();
    }
    return propertyManager.getText("StartNestNode.JsonString");
  }

  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }

  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("StartNestNode.Title"));
    this.getChildren().add(title);
  }
}
