package oogasalad.frontend.nodeEditor.customNodeEditor;

import javafx.scene.layout.VBox;

public abstract class DraggableItem extends VBox {

  protected double x, y;
  protected double xOffset, yOffset;

  protected String color;

  public DraggableItem(double x, double y, double width, double height, String color) {
    this.x = x;
    this.y = y;
    this.color = color;
    setLayoutX(x);
    setLayoutY(y);
    setPrefSize(width, height);
    setColor(color);
    setContent();
//    this.setStyle("-fx-border-color: black");

    setOnDragDetected(event -> {
      startFullDrag();
      event.consume();
    });
    setOnMousePressed(e -> {
      xOffset = e.getSceneX() - (getTranslateX());
      yOffset = e.getSceneY() - (getTranslateY());
    });
    setOnMouseDragged(e -> {
      setTranslateX(e.getSceneX() - xOffset);
      setTranslateY(e.getSceneY() - yOffset);
    });
  }

  protected abstract void setContent();

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
}
