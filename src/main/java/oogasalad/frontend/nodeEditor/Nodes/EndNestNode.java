package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.control.Label;

public class EndNestNode extends AbstractNode {

  public EndNestNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("EndNestNode.StyleClass"));
  }

  @Override
  public String getJSONString() {
    if (this.getChildNode() != null) {
      return propertyManager.getText("EndNestNode.JsonString") + this.getChildNode()
          .getJSONString();
    }
    return propertyManager.getText("EndNestNode.JsonString");
  }

  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("EndNestNode.Title"));
    this.getChildren().add(title);
  }

}
