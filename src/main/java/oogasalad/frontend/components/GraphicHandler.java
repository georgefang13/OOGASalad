package oogasalad.frontend.components;

import java.util.ResourceBundle;

/**
 * @author Han
 * The purpose of this class is to handle the conversion of inputs from the user into proper JavaFX
 * coordinates that will be contained in the editor region
 */

public class GraphicHandler {

  //TODO add the SIZE_FILE and set the file names based on convention
  final static public String FILE_LOCATION = "frontend/properties/numeric";
  final static public String SIZE_FILE = FILE_LOCATION + "numeric.properties";
  private int scalingFactor;
  private int LeftBound;
  private int RightBound;
  private int TopBound;
  private int BottomBound;
  public GraphicHandler(){
    ResourceBundle sizeBundle = ResourceBundle.getBundle(SIZE_FILE);
    int windowSize = Integer.parseInt(sizeBundle.getString("WindowSize"));
    //TODO rename this AreaSize based on what the Panel will be called
    int editingAreaSize = Integer.parseInt(sizeBundle.getString("editingAreaSize"));
    scalingFactor = editingAreaSize / windowSize;
  }

  /**
   * Converts a Coordinate Point from the absolute value given the absoluteCoordinates
   * @param absoluteCoord this is the Point that contains the input Coordinates given by the
   * user
   * @return the Point that is displayed in the Editor
   */
  public Point absoluteCoordinatesToEditor(Point absoluteCoord){
    return absoluteCoord.scale(scalingFactor);
  }
  /**
   * Converts the size of the Component from Window size to Editor Size
   * @param size the size of the Component as inputted from the user
   * @return the size of the Component it should be in the Editor
   */
  public double absoluteSizeToEditor(double size){
    return size*scalingFactor;
  }

  /**
   * Checks if the given Component exists within the bounds of the editor
   * @param editorCoord editorCoord is the coordinates of the components on the editor scale
   * @param size This is the point that contains the sizes of the Component, in the x and y scale
   * @return whether or not the component is in bounds
   */
  public boolean inBounds(Point editorCoord, Point size){
    if(editorCoord.getX() - size.getX()/2 <LeftBound || editorCoord.getX() + size.getX()/2 >RightBound){
      //TODO figure out error?
      return false;
    }
    if(editorCoord.getY() - size.getY()/2<TopBound || editorCoord.getY() + size.getY()/2 > BottomBound){
      return false;
    }
    return true;
  }
}
