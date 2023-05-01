package oogasalad.frontend.nodeEditor.tabs;

import oogasalad.frontend.nodeEditor.NodeController;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class GoalTab extends CodeEditorTab {

  public GoalTab(NodeController nodeController, String name) {
    super(nodeController);
    state = "goals";
    action = name;
  }

}

