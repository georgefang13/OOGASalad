package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

public class MainNode extends AbstractNode {

  private static final double DEFAULT_X = 0;
  private static final double DEFAULT_Y = 0;
  private static final double WIDTH = 300;
  private static final double HEIGHT = 100;
  private static final String COLOR = "#9BE70F";

  public MainNode(NodeController nodeController) {
    super();
    setContent();
  }

  @Override
  protected void setContent() {
    Label title = new Label("Main");
    title.setStyle("-fx-font-size: 100");
    this.getChildren().add(title);
  }

  @Override
  public String getJSONString() {
    if (this.getChildNode() != null) {
      return this.getChildNode().getJSONString();
    }
    return "";
  }

  @Override
  public void snapTo(AbstractNode node) {
  }

  @Override
  protected void delete() {
  }


}
