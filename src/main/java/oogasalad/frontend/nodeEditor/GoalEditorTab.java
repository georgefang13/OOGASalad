package oogasalad.frontend.nodeEditor;

import javafx.scene.layout.HBox;

public class GoalEditorTab extends CodeEditorTab {

  public GoalEditorTab(NodeController nodeController, String name) {
    super(nodeController);
    state = "goals";
    action = name;
  }


}

