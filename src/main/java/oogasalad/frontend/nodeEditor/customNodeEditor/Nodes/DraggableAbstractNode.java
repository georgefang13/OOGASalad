package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import javafx.geometry.Bounds;
import oogasalad.frontend.nodeEditor.customNodeEditor.Draggable;

public abstract class DraggableAbstractNode extends AbstractNode implements Draggable {

  private Bounds boundingBox;

  public DraggableAbstractNode(double x, double y, double width, double height, String color) {
    super(x, y, width, height, color);
    onDragDetected();
    onMousePressed();
    onMouseDragged();
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
          double scaleFactor = this.getParent().getScaleX();
          double newX = e.getSceneX() / scaleFactor - xOffset;
          double newY = e.getSceneY() / scaleFactor - yOffset;
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
          e.consume();
        });
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
}
