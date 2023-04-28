package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.EndNestNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.StartNestNode;

public abstract class AbstractNode extends VBox {

  public static final int INDENT_SIZE = 60;

  protected double x, y, width, height;
  protected double xOffset, yOffset;
  protected int indent;

  protected String color;

  protected NodeController nodeController;
  protected AbstractNode parentNode;
  protected AbstractNode childNode;

  public AbstractNode(NodeController nodeController, double x, double y, double width,
      double height, String color) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    this.nodeController = nodeController;
    setTranslateX(this.x);
    setTranslateY(this.y);
    setWidth(this.width);
    setHeight(this.height);
    setPrefSize(this.width, this.height);
    setColor(this.color);
    setToolTips();
  }

  protected abstract void setContent();

  public abstract void move(double x, double y);

  public abstract String getJSONString();

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

  public void setParentNode(AbstractNode node) {
    this.parentNode = node;
  }

  public AbstractNode getParentNode() {
    return parentNode;
  }

  public void setChildNode(AbstractNode node) {
    this.childNode = node;
  }

  public AbstractNode getChildNode() {
    return childNode;
  }

  public void snapTo(AbstractNode node) {
    while (node.getChildNode() != null && node.getChildNode() != this) {
      node = node.getChildNode();
    }
    adjust(this, node);
    AbstractNode temp = this;
    while (temp.getChildNode() != null) {
      AbstractNode tempOld = temp;
      temp = temp.getChildNode();
      adjust(temp, tempOld);
    }
    if (node.getChildNode() == null) {
      node.setChildNode(this);
      this.setParentNode(node);
    }
  }

  private void adjust(AbstractNode fromNode, AbstractNode toNode) {
    if (toNode instanceof StartNestNode) {
      if (fromNode instanceof EndNestNode) {
        fromNode.move(toNode.getTranslateX(), toNode.getTranslateY() + toNode.getHeight());
      } else {
        fromNode.move(toNode.getTranslateX() + INDENT_SIZE,
            toNode.getTranslateY() + toNode.getHeight());
      }
    } else if (fromNode instanceof EndNestNode) {
      fromNode.move(toNode.getTranslateX() - INDENT_SIZE,
          toNode.getTranslateY() + toNode.getHeight());
    } else {
      fromNode.move(toNode.getTranslateX(), toNode.getTranslateY() + toNode.getHeight());
    }
  }
}
