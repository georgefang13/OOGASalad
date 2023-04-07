package oogasalad.frontend.components;

import java.util.ResourceBundle;

/**
 * @author Han
 * The purpose of this class is to handle the conversion of inputs from the user into proper JavaFX
 * coordinates that will be contained in the editor region
 */
public class GraphicHandler {

  //TODO add the SIZE_FILE and set the file names based on convention
  final static public String FILE_LOCATION = "";
  final static public String SIZE_FILE = FILE_LOCATION + "";
  private ResourceBundle SizeBundle;
  private int windowSize;
  //TODO rename this AreaSize based on what the Panel will be called
  private int editingAreaSize;
  private int scalingFactor;
  public GraphicHandler(){
    SizeBundle = ResourceBundle.getBundle(SIZE_FILE);
    windowSize = Integer.parseInt(SizeBundle.getString("WindowSize"));
    editingAreaSize = Integer.parseInt(SizeBundle.getString("editingAreaSize"));
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
   *
   * @param coord
   * @return
   */
  public boolean inBounds(int coord){
    return false;
  }
}
