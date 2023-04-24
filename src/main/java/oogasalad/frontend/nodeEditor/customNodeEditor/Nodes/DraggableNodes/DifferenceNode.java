package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import oogasalad.frontend.nodeEditor.customNodeEditor.NodeController;

public class DifferenceNode extends DraggableAbstractNode {

  private TextField operand1, operand2;
  private Label outputLabel;

  public DifferenceNode(NodeController nodeController) {
    super(nodeController, 0, 0, 100, 100, "blue");
  }

  public DifferenceNode(NodeController nodeController, double x, double y, double width,
      double height, String color) {
    super(nodeController, x, y, width, height, color);
  }

  @Override
  protected void setContent() {
    operand1 = new TextField();
    final Region filler = new Region();
    this.setVgrow(filler, Priority.ALWAYS);
    Label title = new Label("Difference Node");
    operand2 = new TextField();
    final Region filler2 = new Region();
    outputLabel = new Label();
    operand1.textProperty().addListener((observable, oldValue, newValue) -> updateSum());
    operand2.textProperty().addListener((observable, oldValue, newValue) -> updateSum());
    this.getChildren().addAll(operand1, filler, title, operand2, filler2, outputLabel);
    updateSum();
  }

  @Override
  public String getJSONString() {
    return String.format("%s - %s = %s", operand1.getText(), operand2.getText(),
        outputLabel.getText());
  }

  private void updateSum() {
    try {
      double op1 = Double.parseDouble(operand1.getText());
      double op2 = Double.parseDouble(operand2.getText());
      outputLabel.setText(Double.toString(op1 - op2));
    } catch (NumberFormatException e) {
      outputLabel.setText("NaN");
    }
  }
}