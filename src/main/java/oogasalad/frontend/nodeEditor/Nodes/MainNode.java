package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.control.Label;

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
  public void snapTo(AbstractNode node) {
  }

  @Override
  protected void delete() {
  }

}
