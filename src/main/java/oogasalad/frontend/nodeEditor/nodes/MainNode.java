package oogasalad.frontend.nodeEditor.nodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.configuration.NodeData;

import java.util.ArrayList;

public class MainNode extends AbstractNode implements ControlNode {

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
  public String getNodeParseString() {
    if (this.getChildNode() != null) {
      return this.getChildNode().getNodeParseString();
    }
    return propertyManager.getText("EmptyString");
  }


  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }

  @Override
  public void snapToNode(AbstractNode node) {
  }

  @Override
  public void delete() {
  }

}
