package oogasalad.frontend.nodeEditor.nodes;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import oogasalad.frontend.nodeEditor.configuration.NodeData;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */

public abstract class AbstractNode extends VBox implements DraggableNode {

  protected Bounds boundingBox;

  protected double x, y, xOffset, yOffset, width, height, indent;
  protected NodeData nodeData;

  protected AbstractNode parentNode, childNode;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  public AbstractNode() {
    setPropertyValues();
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

  /**
   * Returns the string that will be used to parse the node
   *
   * @return String
   */
  public abstract String getNodeParseString();


  /**
   * Returns the record of NodeData for this node
   *
   * @return NodeData
   */
  public abstract NodeData getNodeData();

  /**
   * Snaps this node to the given node and also does the same for all of its children Additionally
   * checks if it is a control node to determine how to snap
   *
   * @param node the node to snap to
   * @return void
   */
  @Override
  public void snapToNode(AbstractNode node) {
    while (node.getChildNode() != null && node.getChildNode() != this) {
      node = node.getChildNode();
    }
    alignNodes(this, node);
    AbstractNode temp = this;
    while (temp.getChildNode() != null) {
      AbstractNode tempOld = temp;
      temp = temp.getChildNode();
      alignNodes(temp, tempOld);
    }
    if (node.getChildNode() == null) {
      node.setChildNode(this);
      this.setParentNode(node);
    }

  }


  /**
   * Aligns the given nodes vertically If it is a control node, use the indent size to align Uses
   * Platform.runLater to ensure that the layout is rendered before aligning This is because we set
   * the height of the node to be -1 so that if can change to fit the content
   *
   * @param fromNode the node to align
   * @param toNode   the target node to align to
   * @return void
   */
  @Override
  public void alignNodes(AbstractNode fromNode, AbstractNode toNode) {
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), event -> {
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

    }));
    timeline.play();
  }

  /**
   * Sets the onDragDetected event for this node This is used to start the full drag
   *
   * @return void
   */
  @Override
  public void onDragDetected() {
    this.setOnDragDetected(event -> {
      startFullDrag();
      event.consume();
    });
  }

  /**
   * Sets the onMousePressed event for this node If shift is pressed, delete the node
   *
   * @return void
   */
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

  /**
   * Sets the onMouseDragged event for this node If the node is dragged, move it to the new
   * location
   *
   * @return void
   */
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
            this.getChildNode().snapToNode(this);
          }
          clearLinks();
          e.consume();
        });
  }

  /**
   * Sets the onMouseReleased event for this node If the node is released, and this nodes is
   * intersecting with another node, snap to that node
   *
   * @return void
   */
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
            snapToNode((AbstractNode) node);
            e.consume();
            return;
          }
        }
      }
      e.consume();
    });
  }

  /**
   * Moves the node to the given location If the node is out of bounds, clamp it to the bounds
   *
   * @param newX the new x location
   * @param newY the new y location
   * @return void
   */
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

  /**
   * Sets the bounding box for this node
   *
   * @param bounds the bounding box
   * @return void
   */
  public void setBoundingBox(Bounds bounds) {
    boundingBox = bounds;
  }

  /**
   * Sets the parent node for this node
   *
   * @param node the parent node
   * @return void
   */
  public void setParentNode(AbstractNode node) {
    this.parentNode = node;
  }

  /**
   * Returns the parent node for this node
   *
   * @return AbstractNode
   */
  public AbstractNode getParentNode() {
    return parentNode;
  }

  /**
   * Sets the child node for this node
   *
   * @param node the child node
   * @return void
   */
  public void setChildNode(AbstractNode node) {
    this.childNode = node;
  }

  /**
   * Returns the child node for this node
   *
   * @return AbstractNode
   */
  public AbstractNode getChildNode() {
    return childNode;
  }

  /**
   * Sets the content of the node
   *
   * @return void
   */
  protected abstract void setContent();

  /**
   * Deletes this node and removes it as a child of its parent
   *
   * @return void
   */
  public void delete() {
    clearLinks();
    Group parentGroup = (Group) getParent();
    parentGroup.getChildren().remove(this);
  }

  /**
   * Sets the tool tip for this node
   *
   * @return void
   */
  protected void setToolTips() {
    Tooltip t = new Tooltip(propertyManager.getText("AbstractNode.ToolTip"));
    Tooltip.install(this, t);
  }

  /**
   * Clears the links for this node
   *
   * @return void
   */
  protected void clearLinks() {
    if (this.getParentNode() != null) {
      this.parentNode.setChildNode(null);
      this.setParentNode(null);
    }
  }

  /**
   * Creates a button with the given label and handler
   *
   * @param label   the label for the button
   * @param handler the handler for the button
   * @return Button
   */
  protected Button makeButton(String label, EventHandler<ActionEvent> handler) {
    Button button = new Button(label);
    button.setOnAction(handler);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    return button;
  }

  /**
   * Sets the properties for this node
   *
   * @return void
   */
  private void setNodeFormatProperties() {
    setTranslateX(this.x);
    setTranslateY(this.y);
    setWidth(this.width);
    setHeight(this.height);
    setPrefSize(this.width, this.height);
    setToolTips();
  }

  /**
   * Sets the drag properties for this node
   *
   * @return void
   */
  private void setNodeDragProperties() {
    onDragDetected();
    onMousePressed();
    onMouseDragged();
    onMouseReleased();
  }

  /**
   * Sets the property values for this node
   *
   * @return void
   */
  private void setPropertyValues() {
    this.x = propertyManager.getNumeric("AbstractNode.DefaultX");
    this.y = propertyManager.getNumeric("AbstractNode.DefaultY");
    this.width = propertyManager.getNumeric("AbstractNode.Width");
    this.height = propertyManager.getNumeric("AbstractNode.Height");
    this.indent = propertyManager.getNumeric("AbstractNode.IndentSize");
  }

}