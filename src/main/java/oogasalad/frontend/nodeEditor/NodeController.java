package oogasalad.frontend.nodeEditor;

import javafx.scene.Scene;
import oogasalad.frontend.windows.NodeWindow;

public class NodeController {

  private NodeScene scene;

  public NodeController(NodeWindow nodeWindow) {
    scene = new NodeScene(this);
    nodeWindow.showScene(scene);
  }

  public NodeController() {
    scene = new NodeScene(this);
  }


  public void openAndSwitchToTab(String state, String action) {
    scene.openAndSwitchToTab(state, action);
  }

  public void saveAllContent(String filePath) {
    scene.saveAllContent(filePath);
  }

  public Scene getScene() {
    return scene.getScene();
  }
}
