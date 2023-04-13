package oogasalad.frontend.nodeEditor.customNodeEditor;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.*;


public class SumNode extends DraggableItem {
  private TextField operand1, operand2;
  private Label outputLabel;

  public SumNode(double x, double y, double width, double height, String color) {
    super(x, y, width, height, color);

  }

  @Override
  protected void setContent() {
    operand1 = new TextField();
    final Region filler = new Region();
    this.setVgrow(filler, Priority.ALWAYS);
    Label title = new Label("Sum Node");
    operand2 = new TextField();
    final Region filler2 = new Region();
    outputLabel = new Label();
    operand1.textProperty().addListener((observable, oldValue, newValue) -> updateSum());
    operand2.textProperty().addListener((observable, oldValue, newValue) -> updateSum());
    this.getChildren().addAll(operand1,filler,title,operand2,filler2, outputLabel);
    updateSum();
  }

  private void updateSum() {
    try {
      double op1 = Double.parseDouble(operand1.getText());
      double op2 = Double.parseDouble(operand2.getText());
      outputLabel.setText(Double.toString(op1 + op2));
    } catch (NumberFormatException e) {
      outputLabel.setText("NaN");
    }
  }
}