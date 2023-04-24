package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

public abstract class AbstractNode extends VBox {

  protected double x, y;
  protected double xOffset, yOffset;

  protected String color;
  private AbstractNode childNode;


//    protected List<Port> ports;

  public AbstractNode(double x, double y, double width, double height, String color) {
    this.x = x;
    this.y = y;
    this.color = color;
    setLayoutX(x);
    setLayoutY(y);
    setPrefSize(width, height);
    setColor(color);
    setToolTips();
  }

  protected abstract void setContent();


  public abstract String sendContent();
  public void setChildNode(AbstractNode node){
    this.childNode = node;
  }
  public AbstractNode getChildNode() {
    return childNode;
  }

  protected void delete() {
    Group parentGroup = (Group) getParent();
    parentGroup.getChildren().remove(this);
  }

  protected void setColor(String color) {
    setStyle("-fx-background-color: " + color);
  }

  protected void setX(double x) {
    this.x = x;
  }

  protected void setY(double y) {
    this.y = y;
  }

  protected double getX() {
    return this.x;
  }

  protected double getY() {
    return this.y;
  }

  protected String getColor() {
    return this.color;
  }

  protected double getXOffset() {
    return xOffset;
  }

  protected double getYOffset() {
    return yOffset;
  }

  protected void setXOffset(Double offset) {
    this.xOffset = offset;
  }

  protected void setYOffset(Double offset) {
    this.yOffset = offset;
  }

  protected void setToolTips() {
    Tooltip t = new Tooltip("Delete: Shift + Click");
    Tooltip.install(this, t);
  }

  public abstract void move(double x, double y);
}
