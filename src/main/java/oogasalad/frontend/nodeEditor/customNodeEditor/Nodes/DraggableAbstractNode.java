package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import oogasalad.frontend.nodeEditor.customNodeEditor.Draggable;

public class DraggableAbstractNode extends AbstractNode implements Draggable {

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
          setTranslateX(e.getSceneX() / scaleFactor - xOffset);
          setTranslateY(e.getSceneY() / scaleFactor - yOffset);
          e.consume();
        });
  }

  @Override
  protected void setContent() {
  }

  @Override
  public String sendContent() {
    return null;
  }
}