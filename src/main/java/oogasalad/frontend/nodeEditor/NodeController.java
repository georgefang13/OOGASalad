package oogasalad.frontend.nodeEditor;

import oogasalad.frontend.windows.NodeWindow;

public class NodeController {

  private NodeScene scene;

  public NodeController(NodeWindow nodeWindow) {
    scene = new NodeScene(this);
    nodeWindow.showScene(scene);
  }

  public void openAndSwitchToTab(String state, String action) {
    scene.openAndSwitchToTab(state, action);
  }

  public void saveAllContent(String filePath) {
    scene.saveAllContent(filePath);
  }
}
