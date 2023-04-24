package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;

public class TextFieldNode extends DraggableAbstractNode {

  private TextField field;

  public TextFieldNode(NodeController nodeController) {
    super(nodeController, 0, 0, 100, 100, "White");
  }

  public TextFieldNode(NodeController nodeController, double x, double y, double width,
      double height, String color) {
    super(nodeController, x, y, width, height, color);
  }

  @Override
  protected void setContent() {
    Label title = new Label("Text Node");
    field = new TextField();
    this.getChildren().addAll(title, field);
  }

  @Override
  public String getJSONString() {
    return field.getText();
  }
}
