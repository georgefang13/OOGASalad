package oogasalad.Controller;

import java.util.Map;
import oogasalad.frontend.windows.AbstractWindow;

/**
 * @author Han This is the Controller to send GameItems back to the Backend to keep track of
 */
public class GameItemsController {

  private AbstractWindow window;
  private ConvertingStrategy convert;

  /**
   * Initializes the GameItemsController with the Window with the Window it's attached to
   */
  public GameItemsController(AbstractWindow newWindow) {
    window = newWindow;
  }

  /**
   * Method is called in order to send information about a newly constructed   object that was made
   * in the front end sent to the backend. The controller sends to the backend for the backend to
   * input these into a file
   *
   * @param type The class the object belongs to
   * @param map  The params of the object
   **/
  public void sendObject(String type, Map<String, String> map) {

  }

  ;

  /**
   * Method is called to update information about a modified object in teh front end. The controller
   * sends updates to the Backend by giving the type and params for identification
   *
   * @param type   The class the object belongs to
   * @param params The updated params of the object
   **/
  public void updateObjectProperties(String type, String params) {

  }

  /**
   * Method is called in order to send a request to the backend to delete an object.
   *
   * @param type   The class the object belongs to
   * @param params The params of the object
   **/
  public void deleteObject(String type, String params) {

  }
}
