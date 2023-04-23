package oogasalad.Controller;

import oogasalad.frontend.windows.WindowController;

/**
 * @author Han This is a smaller controller that allows for the panel to direclty call on functions
 * from the Window Controller instead of having to call on Window. This is implements Chain of
 * Responsibility design pattern
 */
public class PanelController {

  private WindowController controller;

  /**
   * Initialize with the new Panel so the panel can communicate with windowController
   *
   * @param windowController the windowController that changes the scene for the Window
   */
  public PanelController(WindowController windowController) {
    controller = windowController;
  }

  public void switchScenes(String WindowID, String SceneID) {
    //controller.getWindow(WindowID).switchToScene(SceneID);
  }
}
