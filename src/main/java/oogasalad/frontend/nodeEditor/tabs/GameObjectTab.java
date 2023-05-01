package oogasalad.frontend.nodeEditor.tabs;

import oogasalad.frontend.nodeEditor.NodeController;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class GameObjectTab extends CodeEditorTab {

  public GameObjectTab(NodeController nodeController, String name) {
    super(nodeController);
    state = "gameObject";
    action = name;
  }

}

