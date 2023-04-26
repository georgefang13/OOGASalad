package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;

public abstract class AbstractNode extends VBox {

  protected final double INDENT_SIZE = 30;

  protected double x, y, width, height;
  protected double indent;
  protected double xOffset, yOffset;

  protected String color;

  protected NodeController nodeController;
  private DraggableAbstractNode childNode;

//    protected List<Port> ports;

  public AbstractNode(NodeController nodeController, double x, double y, double indent,
      double width,
      double height, String color) {
    this.x = x;
    this.y = y;
    this.indent = indent;
    this.width = width;
    this.height = height;
    this.color = color;
    this.nodeController = nodeController;
    System.out.println(this.x + this.indent);
    setLayoutX(this.x + this.indent);
    setLayoutY(this.y);
    setPrefSize(this.width, this.height);
    setColor(this.color);
    setToolTips();
  }

  protected abstract void setContent();

  public abstract void move(double x, double y);

  public abstract String getJSONString();

  public void setChildNode(DraggableAbstractNode node) {
    this.childNode = node;
  }

  public DraggableAbstractNode getChildNode() {
    return childNode;
  }

  protected void delete() {
    Group parentGroup = (Group) getParent();
    parentGroup.getChildren().remove(this);
  }

  protected void setColor(String color) {
    setStyle("-fx-background-color: " + color);
  }

  protected void setToolTips() {
    Tooltip t = new Tooltip("Delete: Shift + Click");
    Tooltip.install(this, t);
  }

  public double getIndent() {
    return indent;
  }

  public double getIncrementIndent() {
    System.out.println(indent + INDENT_SIZE + " GET");
    return indent + INDENT_SIZE;
  }
  public double getDecrementIndent() {
    return indent - INDENT_SIZE;
  }
}
