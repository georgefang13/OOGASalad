package oogasalad.frontend.nodeEditor;

import javafx.scene.layout.HBox;

public class GoalEditorTab extends CodeEditorTab {

  public GoalEditorTab(NodeController nodeController) {
    super(nodeController);
    setContent(new HBox(makeNodeButtonPanel(), makeWorkspacePanel()));
  }


}

