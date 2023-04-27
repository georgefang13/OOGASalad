package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.EndNestNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.StartNestNode;

public abstract class AbstractNode extends VBox {

  public static final int INDENT_SIZE = 20;

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
    if (this.parentNode != null) {
      if (!(this instanceof EndNestNode)) {
        if (this.parentNode instanceof StartNestNode) {
          move(this.parentNode.getTranslateX() + INDENT_SIZE, getTranslateY());
          incrementIndent();
        } else {
          move(this.parentNode.getTranslateX(), getTranslateY());
        }
      } else {
        if (this.parentNode instanceof StartNestNode) {
          move(this.parentNode.getTranslateX(), getTranslateY());
        } else {
          move(this.parentNode.getTranslateX() - INDENT_SIZE, getTranslateY());
          decrementIndent();
        }
      }
    }
  }

  public AbstractNode getParentNode() {
    return parentNode;
  }

  public void setChildNode(DraggableAbstractNode node) {
    this.childNode = node;
  }

  public AbstractNode getChildNode() {
    return childNode;
  }

  public double getIndent() {
    return indent;
  }

  public void incrementIndent() {
    if (this.indent == 0) {
      this.indent += INDENT_SIZE;
    }
  }

  public void decrementIndent() {
    if (this.indent == 0) {
      this.indent -= INDENT_SIZE;
    }
  }
}
