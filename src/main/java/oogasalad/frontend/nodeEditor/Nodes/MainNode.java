package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.Config.NodeData;

import java.util.ArrayList;

public class MainNode extends AbstractNode {

  public MainNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("MainNode.StyleClass"));
  }

  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("MainNode.Title"));
    this.getChildren().add(title);
  }

  @Override
  public String getJSONString() {
    if (this.getChildNode() != null) {
      return this.getChildNode().getJSONString();
    }
    return propertyManager.getText("EmptyString");
  }


  @Override
  public NodeData getNodeData() {
    return new NodeData("MainNode", "Control", new ArrayList<>());
  }

  @Override
  public void snapTo(AbstractNode node) {
  }

  @Override
  public void delete() {
  }

}
