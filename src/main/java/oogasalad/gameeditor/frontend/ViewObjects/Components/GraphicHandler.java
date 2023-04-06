package oogasalad.gameeditor.frontend.ViewObjects.Components;

import java.util.ResourceBundle;

/**
 * @author Han
 * The purpose of this class is to handle the conversion of inputs from the user into proper JavaFX
 * coordinates that will be contained in the editor region
 */
public class GraphicHandler {

  ResourceBundle SizeBundle;
  int windowSize;
  //TODO rename this AreaSize based on what the Panel will be called
  int editingAreaSize;
  public GraphicHandler(){

  }

  /**
   * Converts a Coordinate Point from the absolute value given the absoluteCoordinates
   * @param absoluteCoord this is the Point that contains the input Coordinates given by the
   * user
   * @return the Point that is displayed in the Editor
   */
  public Point absoluteCoordinatesToEditor(Point absoluteCoord){

  }

  /**
   * Converts the size of the Component from Window size to Editor Size
   * @param size the size of the Component as inputted from the user
   * @return the size of the Component it should be in the Editor
   */
  public double absoluteSizeToEditor(double size){

  }

  /**
   *
   * @param coord
   * @return
   */
  public boolean inBounds(int coord){

  }
}
