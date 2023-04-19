package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import oogasalad.frontend.nodeEditor.customNodeEditor.Draggable;

public abstract class DraggableAbstractNode extends AbstractNode implements Draggable {

  private Bounds boundingBox;

  public DraggableAbstractNode(double x, double y, double width, double height, String color) {
    super(x, y, width, height, color);
    onDragDetected();
    onMousePressed();
    onMouseDragged();
    onMouseReleased();
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
          double x = e.getSceneX();
          double y = e.getSceneY();
          double scaleFactor = this.getParent().getScaleX();
          double newX = x / scaleFactor - xOffset;
          double newY = y / scaleFactor - yOffset;
          move(newX, newY);

          e.consume();

        });
  }

  public void onMouseReleased() {
    this.setOnMouseReleased(e -> {
      e.setDragDetect(false);
      /*
       * check if it is on top of another node
       * if so, snap to it
       * if not, do nothing
       */
      for (Node node : this.getParent().getChildrenUnmodifiable()) {
        if (node instanceof AbstractNode && node != this) {
          if (this.getBoundsInParent().intersects(node.getBoundsInParent())) {
            snapTo((AbstractNode) node);
          }
        }
      }
      e.consume();
    });
  }

  protected void snapTo(AbstractNode node) {
    // System.out.println("node is " + node);
    while (node.getChildNode() != null && node.getChildNode() != this) {
      node = node.getChildNode();
      // System.out.println("node is " + node);
    }
    this.setTranslateX(node.getTranslateX());
    this.setTranslateY(node.getTranslateY() + node.getHeight());
    node.setChildNode(this);
  }

  public void setBoundingBox(Bounds bounds) {
    boundingBox = bounds;
  }

  @Override
  protected void setContent() {
  }

  @Override
  public String sendContent() {
    return null;
  }

  public String sendChildContent() {
    System.out.println("this is " + this);
    System.out.println("child node is " + this.getChildNode());
    if (this.getChildNode() == null) {
      return "";
    }
    return "\n" + this.getChildNode().sendContent();
  }

  @Override
  public void move(double newX, double newY) {

    if (boundingBox.contains(newX, newY, getWidth(), getHeight())) {
      setTranslateX(newX);
      setTranslateY(newY);
      if (this.getChildNode() != null) {
        this.getChildNode().move(newX, newY + this.getHeight());
      }
    } else {
      double clampedX = Math.min(Math.max(newX, boundingBox.getMinX()),
          boundingBox.getMaxX() - getWidth());
      double clampedY = Math.min(Math.max(newY, boundingBox.getMinY()),
          boundingBox.getMaxY() - getHeight());
      setTranslateX(clampedX);
      setTranslateY(clampedY);
    }
  }
}
