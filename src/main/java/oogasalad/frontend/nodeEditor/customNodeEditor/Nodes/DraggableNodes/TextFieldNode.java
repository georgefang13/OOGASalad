package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldNode extends DraggableAbstractNode {

  private TextField field;

  public TextFieldNode() {
    super(0, 0, 100, 100, "White");
  }

  public TextFieldNode(double x, double y, double width, double height, String color) {
    super(x, y, width, height, color);
  }


  @Override
  protected void setContent() {
    Label title = new Label("Text Node");
    field = new TextField();
    this.getChildren().addAll(title, field);
  }

  @Override
  public String sendContent() {
    return field.getText();
  }
}
