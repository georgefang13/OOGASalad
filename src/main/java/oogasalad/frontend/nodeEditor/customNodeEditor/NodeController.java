package oogasalad.frontend.nodeEditor.customNodeEditor;

import oogasalad.frontend.windows.NodeWindow;

public class NodeController {

  private NodeScene scene;

  public NodeController(NodeWindow nodeWindow) {
    scene = new NodeScene(this);
    nodeWindow.showScene(scene);
  }

  public void openAndSwitchToTab(String name) {
    scene.openAndSwitchToTab(name);
  }
}
