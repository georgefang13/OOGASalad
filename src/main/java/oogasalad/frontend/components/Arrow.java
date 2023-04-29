package oogasalad.frontend.components;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;

/**
 * @author Han
 */
public class Arrow {

  private Dropzone start;
  private Dropzone end;
  private boolean visible;
  private Polygon triangle;
  private Line line;
  private Group arrow;

  /**
   * Constructor for
   * @param s
   * @param e
   */
  public Arrow(Dropzone s, Dropzone e){
    start = s;
    end = e;
    visible = true;
    triangle = new Polygon();
    arrow = new Group(triangle, line);
    updateArrow();
  }

  /**
   * Controls visibility of Arrow
   * @param vis
   */
  public void setVisible(boolean vis){
    visible = vis;
    updateArrow();
  }

  /**
   * Allows the arrow to be updated based on start and end locations of dropzones
   */
  public void updateArrow(){
    double startX = start.getSquare().getBoundsInLocal().getCenterX();
    double startY = start.getSquare().getBoundsInLocal().getCenterY();
    double endX = end.getSquare().getBoundsInLocal().getCenterX();
    double endY = end.getSquare().getBoundsInLocal().getCenterY();
    line = new Line(startX, startY, endX, endY);
  }

  /**
   * Arrow gets returned
   * @return
   */
  public Group getArrow(){

  }

}
