package oogasalad.frontend.components;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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

  private static final double HEAD_SIZE = 10;

  /**
   * Constructor for arrow that represents connections between dropzones
   * @param s
   * @param e
   */
  public Arrow(Dropzone s, Dropzone e){
    start = s;
    end = e;
    visible = true;
    triangle = new Polygon();
    updateArrow();
    start.addEdges(end);
    arrow = new Group(triangle, line);
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
    Node startSquare = start.getNode();
    Node endSquare = end.getNode();
    double startX = startSquare.getTranslateX() + start.getWidth()/2;
    double startY = startSquare.getTranslateY() + start.getHeight()/2;
    double endX = endSquare.getTranslateX() + end.getWidth()/2;
    double endY = endSquare.getTranslateY() + end.getHeight()/2;
    line = new Line(startX, startY, endX, endY);

    double length = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
    System.out.println(length);
    double angle = Math.atan2(endY - startY, endX - startX);
    double headLength = Math.min(HEAD_SIZE, length / 3);
    double headWidth = Math.min(HEAD_SIZE, headLength / 2);

    triangle.getPoints().clear();
    double xPointCalc = endX - headLength * Math.cos(angle);
    double yPointCalc = endY - headLength * Math.sin(angle);
    triangle.getPoints().addAll(
        xPointCalc,
        yPointCalc,
        endX - headLength * Math.cos(angle) - headWidth * Math.sin(angle),
        yPointCalc + headWidth * Math.cos(angle),
        xPointCalc + headWidth * Math.sin(angle),
        endY - headLength * Math.sin(angle) - headWidth * Math.cos(angle)
    );
    triangle.setRotate(Math.toDegrees(angle));
  }

  /**
   * Arrow gets returned
   * @return the arrow that contains the triangle and the line
   */
  public Group getArrow(){
    return arrow;
  }


}
