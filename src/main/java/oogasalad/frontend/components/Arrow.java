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
public class Arrow {

  private Dropzone start;
  private Dropzone end;
  private boolean visible;
  private Polygon triangle;
  private ImageView head;
  private Line line;
  private Group arrow;

  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("\\frontend\\properties\\Defaults\\Arrow");

  /**
   * Constructor for arrow that represents connections between dropzones
   * @param s
   * @param e
   */
  public Arrow(Dropzone s, Dropzone e){
    start = s;
    end = e;
    visible = true;
    updateArrow();
    start.addEdges(end);
    arrow = new Group(line, head);
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
    double startX = startSquare.getTranslateX() + start.getWidth()/2;
    double startY = startSquare.getTranslateY() + start.getHeight()/2;
    double endX = endSquare.getTranslateX() + end.getWidth()/2;
    double endY = endSquare.getTranslateY() + end.getHeight()/2;
    line = new Line(startX, startY, endX, endY);
    line.setVisible(visible);
    createArrowHead(startX, startY, endX, endY);
  }

  private void createArrowHead(double startX, double startY, double endX, double endY) {
    double angle = Math.atan2(endY - startY, endX - startX);
    System.out.println(angle);
    head = new ImageView(BUNDLE.getString("Head"));
    head.setVisible(visible);
    double width = Integer.parseInt(BUNDLE.getString("Width"));
    double height = Integer.parseInt(BUNDLE.getString("Height"));
    head.setFitWidth(width);
    head.setFitHeight(height);
    head.setTranslateX(endX - width/2);
    head.setTranslateY(endY - height/2);
    head.setRotate(Math.toDegrees(angle));
  }

  /**
   * Arrow gets returned
   * @return the arrow that contains the triangle and the line
   */
  public Group getArrow(){
    return arrow;
  }


}
