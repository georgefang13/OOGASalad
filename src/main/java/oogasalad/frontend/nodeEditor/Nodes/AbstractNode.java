package oogasalad.frontend.nodeEditor.Nodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.nodeEditor.Draggable;

import java.io.Serializable;

public abstract class AbstractNode extends VBox implements Draggable {

  protected Bounds boundingBox;

  protected double x, y, xOffset, yOffset, width, height, indent;

  protected AbstractNode parentNode, childNode;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  public AbstractNode() {
    this.x = propertyManager.getNumeric("AbstractNode.DefaultX");
    this.y = propertyManager.getNumeric("AbstractNode.DefaultY");
    this.width = propertyManager.getNumeric("AbstractNode.Width");
    this.height = propertyManager.getNumeric("AbstractNode.Height");
    this.indent = propertyManager.getNumeric("AbstractNode.IndentSize");
    setNodeFormatProperties();
    setNodeDragProperties();
    this.getStyleClass().add(propertyManager.getText("AbstractNode.StyleClass"));
  }

  public AbstractNode(double x, double y, double width,
      double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    setNodeFormatProperties();
    setNodeDragProperties();
  }

  public abstract String getJSONString();

  @Override
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

  @Override
  public void adjust(AbstractNode fromNode, AbstractNode toNode) {
    if (toNode instanceof StartNestNode) {
      if (fromNode instanceof EndNestNode) {
        fromNode.move(toNode.getTranslateX(), toNode.getTranslateY() + toNode.getHeight());
      } else {
        fromNode.move(toNode.getTranslateX() + indent,
            toNode.getTranslateY() + toNode.getHeight());
      }
    } else if (fromNode instanceof EndNestNode) {
      fromNode.move(toNode.getTranslateX() - indent,
          toNode.getTranslateY() + toNode.getHeight());
    } else {
      fromNode.move(toNode.getTranslateX(), toNode.getTranslateY() + toNode.getHeight());
    }
  }

  @Override
  public void onDragDetected() {
    this.setOnDragDetected(event -> {
      startFullDrag();
      event.consume();
    });
  }

  @Override
  public void onMousePressed() {
    this.setOnMousePressed(
        e -> {
          e.setDragDetect(false);
          double scaleFactor = this.getParent().getScaleX();
          xOffset = (e.getSceneX() - getTranslateX() * scaleFactor) / scaleFactor;
          yOffset = (e.getSceneY() - getTranslateY() * scaleFactor) / scaleFactor;
          if (e.isShiftDown()) {
            this.delete();
          }
          e.consume();
        });
  }

  @Override
  public void onMouseDragged() {
    this.setOnMouseDragged(
        e -> {
          e.setDragDetect(false);
          if (this.getParent() == null) {
            return;
          }
          double scaleFactor = this.getParent().getScaleX();
          double newX = e.getSceneX() / scaleFactor - xOffset;
          double newY = e.getSceneY() / scaleFactor - yOffset;
          this.move(newX, newY);
          if (this.getChildNode() != null) {
            this.getChildNode().snapTo(this);
          }
          clearLinks();
          e.consume();
        });
  }

  @Override
  public void onMouseReleased() {
    this.setOnMouseReleased(e -> {
      e.setDragDetect(false);
      if (this.getParent() == null) {
        return;
      }
      for (Node node : this.getParent().getChildrenUnmodifiable()) {
        if (node instanceof AbstractNode && node != this) {
          if (this.getBoundsInParent().intersects(node.getBoundsInParent())
              && this.getChildNode() != node) {
            snapTo((AbstractNode) node);
            e.consume();
            return;
          }
        }
      }
      e.consume();
    });
  }

  @Override
  public void move(double newX, double newY) {
    if (boundingBox.contains(newX, newY, getWidth(), getHeight())) {
      setTranslateX(newX);
      setTranslateY(newY);
    } else {
      double clampedX = Math.min(Math.max(newX, boundingBox.getMinX()),
          boundingBox.getMaxX() - getWidth());
      double clampedY = Math.min(Math.max(newY, boundingBox.getMinY()),
          boundingBox.getMaxY() - getHeight());
      setTranslateX(clampedX);
      setTranslateY(clampedY);
    }
  }

  public void setBoundingBox(Bounds bounds) {
    boundingBox = bounds;
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

  protected abstract void setContent();

  protected void delete() {
    clearLinks();
    Group parentGroup = (Group) getParent();
    parentGroup.getChildren().remove(this);
  }

  protected void setToolTips() {
    Tooltip t = new Tooltip(propertyManager.getText("AbstractNode.ToolTip"));
    Tooltip.install(this, t);
  }

  protected void clearLinks() {
    if (this.getParentNode() != null) {
      this.parentNode.setChildNode(null);
      this.setParentNode(null);
    }
  }

  protected Button makeButton(String label, EventHandler<ActionEvent> handler) {
    Button button = new Button(label);
    button.setOnAction(handler);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    return button;
  }


  private void setNodeFormatProperties() {
    setTranslateX(this.x);
    setTranslateY(this.y);
    setWidth(this.width);
    setHeight(this.height);
    setPrefSize(this.width, this.height);
    setToolTips();
  }

  private void setNodeDragProperties() {
    onDragDetected();
    onMousePressed();
    onMouseDragged();
    onMouseReleased();
  }

}