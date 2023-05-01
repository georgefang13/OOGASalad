package oogasalad.frontend.nodeEditor;

public class GoalEditorTab extends CodeEditorTab {

  public GoalEditorTab(NodeController nodeController, String name) {
    super(nodeController);
    state = "goals";
    action = name;
  }

}

