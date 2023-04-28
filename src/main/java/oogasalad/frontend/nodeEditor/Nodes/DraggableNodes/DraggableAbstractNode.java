package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import oogasalad.frontend.nodeEditor.Draggable;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

public abstract class DraggableAbstractNode extends AbstractNode implements Draggable {

  private Bounds boundingBox;
  protected static final double DEFAULT_X = 0;
  protected static final double DEFAULT_Y = 0;
  protected static final double WIDTH = 300;
  protected static final double HEIGHT = 100;

  public DraggableAbstractNode(NodeController nodeController, double x, double y, double width,
      double height, String color) {
    super(nodeController, x, y, width, height, color);
    onDragDetected();
    onMousePressed();
    onMouseDragged();
    onMouseReleased();
  }

  @Override
  protected abstract void setContent();

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
            //this.getChildNode().move(newX, newY + this.getHeight());
            this.getChildNode().snapTo(this);
          }
          clearLinks();
          e.consume();
        });
  }

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

  @Override
  protected void delete() {
    clearLinks();
    super.delete();
  }

  public void setBoundingBox(Bounds bounds) {
    boundingBox = bounds;
  }

  protected void clearLinks() {
    if (this.getParentNode() != null) {
      this.parentNode.setChildNode(null);
      this.setParentNode(null);
    }
  }
}
