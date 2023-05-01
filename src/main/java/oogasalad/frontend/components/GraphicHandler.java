package oogasalad.frontend.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.components.gridObjectComponent.GridObject;

/**
 * @author Han The purpose of this class is to handle the conversion of inputs from the user into
 * proper JavaFX coordinates that will be contained in the editor region
 */

public class GraphicHandler {

  //TODO add the SIZE_FILE and set the file names based on convention
  final static public String FILE_LOCATION = "frontend/properties/numeric/";
  final static public String SIZE_FILE = FILE_LOCATION + "numeric";
  private int scalingFactorX, scalingFactorY;
  private int LeftBound;
  private int RightBound;
  private int TopBound;
  private int BottomBound;

  public GraphicHandler() {
    ResourceBundle sizeBundle = ResourceBundle.getBundle(SIZE_FILE);
    int windowSizeX = Integer.parseInt(sizeBundle.getString("WindowWidth"));
    int windowSizeY = Integer.parseInt(sizeBundle.getString("WindowHeight"));
    //TODO rename this AreaSize based on what the Panel will be called
    int editingWidth = Integer.parseInt(sizeBundle.getString("EditingWidth"));
    int editingHeight = Integer.parseInt(sizeBundle.getString("EditingHeight"));
    scalingFactorX = editingWidth / windowSizeX;
    scalingFactorY = editingHeight / windowSizeY;
  }

  /**
   * Converts a Coordinate Point from the absolute value given the absoluteCoordinates
   *
   * @param absoluteCoord this is the Point that contains the input Coordinates given by the user
   * @return the Point that is displayed in the Editor
   */
  public Point absoluteCoordinatesToEditor(Point absoluteCoord) {
    return absoluteCoord.scale(scalingFactorX);
  }

  /**
   * Converts the size of the Component from Window size to Editor Size
   *
   * @param size the size of the Component as inputted from the user
   * @return the size of the Component it should be in the Editor
   */
  public double absoluteSizeToEditor(double size) {
    return size * scalingFactorX;
  }

  /**
   * Checks if the given Component exists within the bounds of the editor
   *
   * @param editorCoord editorCoord is the coordinates of the components on the editor scale
   * @param size        This is the point that contains the sizes of the Component, in the x and y
   *                    scale
   * @return whether or not the component is in bounds
   */
  public boolean inBounds(Point editorCoord, Point size) {
    if (editorCoord.getX() - size.getX() / 2 < LeftBound
        || editorCoord.getX() + size.getX() / 2 > RightBound) {
      //TODO figure out error?
      return false;
    }
    if (editorCoord.getY() - size.getY() / 2 < TopBound
        || editorCoord.getY() + size.getY() / 2 > BottomBound) {
      return false;
    }
    return true;
  }

  public void moveToCenter(Component c) {
    if(c.getClass() == GridObject.class){
//      ((GridObject) c).getGrid().setTranslateX(500);
//      ((GridObject) c).getGrid().setTranslateY(250);
//      System.out.println(c.getNode().getBoundsInLocal().getCenterX());
      for (Node node : ((GridPane)c.getNode()).getChildren()) {
        node.setTranslateX(500 + node.getLayoutX());
        node.setTranslateY(250 + node.getLayoutY());
      }
    }
    else{
    c.getNode().setTranslateX(500);
    c.getNode().setTranslateY(250);
    System.out.println(c.getNode().getTranslateX());
    }

  }
}
