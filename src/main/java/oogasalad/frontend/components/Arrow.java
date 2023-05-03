package oogasalad.frontend.components;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;

/**
 * @author Han
 */
public class Arrow implements Subscriber {

  private Dropzone start;
  private Dropzone end;
  private boolean visible;
  private Polygon triangle;
  private ImageView head;
  private Line line;
  private Group arrow;
  private double startX;
  private double startY;
  private double endX;
  private double endY;

  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("frontend.properties.Defaults.Arrow");

  /**
   * Constructor for arrow that represents connections between dropzones
   * @param s
   * @param e
   */
  public Arrow(Dropzone s, Dropzone e){
    start = s;
    end = e;
    visible = true;
    arrow = new Group();
    updateArrow();
    start.addEdges(end);
    start.addSubscriber(this);
    end.addSubscriber(this);
  }

  /**
   * Controls visibility of Arrow
   * @param vis is visible
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
    startX = startSquare.getTranslateX() + start.getSquareWidth()/2;
    startY = startSquare.getTranslateY() + start.getSquareHeight()/2;
    endX = endSquare.getTranslateX() + end.getSquareWidth()/2;
    endY = endSquare.getTranslateY() + end.getSquareHeight()/2;
    System.out.println(startX);
    System.out.println(startY);
    arrow.getChildren().clear();
    line = new Line(startX, startY, endX, endY);
    arrow.getChildren().add(line);
    line.setVisible(visible);
    createArrowHead();
  }

  private void createArrowHead() {
    double angle = Math.atan2(endY - startY, endX - startX);
    head = new ImageView(BUNDLE.getString("Head"));
    head.setVisible(visible);
    double width = Integer.parseInt(BUNDLE.getString("Width"));
    double height = Integer.parseInt(BUNDLE.getString("Height"));
    head.setFitWidth(width);
    head.setFitHeight(height);
    head.setTranslateX(endX - width/2);
    head.setTranslateY(endY - height/2);
    head.setRotate(Math.toDegrees(angle));
    arrow.getChildren().add(head);
  }

  /**
   * Arrow gets returned
   * @return the arrow that contains the triangle and the line
   */
  public Group getArrow(){
    return arrow;
  }

  /**
   * updates arrow
   */
  @Override
  public void update() {
    updateArrow();
  }

  /**
   * The starting X point
   * @return double
   */
  public double getStartX(){
    return startX;
  }
  /**
   * The starting Y point
   * @return double
   */
  public double getStartY(){
    return startY;
  }
  /**
   * The ending X point
   * @return double
   */
  public double getEndX(){
    return endX;
  }
  /**
   * The ending Y point
   * @return double
   */
  public double getEndY(){
    return endY;
  }
}
