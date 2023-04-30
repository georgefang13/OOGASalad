package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.Config.NodeData;

import java.util.ArrayList;

public class StartNestNode extends AbstractNode {

  public StartNestNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("StartNestNode.StyleClass"));
  }

  @Override
  public String getJSONString() {
    if (this.getChildNode() != null) {
      return propertyManager.getText("StartNestNode.JsonString") + this.getChildNode()
          .getJSONString();
    }
    return propertyManager.getText("StartNestNode.JsonString");
  }

  @Override
  public NodeData getNodeData() {
    return new NodeData("StartNestNode", "Control", new ArrayList<>());
  }

  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("StartNestNode.Title"));
    this.getChildren().add(title);
  }
}
