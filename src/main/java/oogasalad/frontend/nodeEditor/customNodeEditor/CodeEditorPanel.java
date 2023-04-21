package oogasalad.frontend.nodeEditor.customNodeEditor;

import java.util.List;
import javafx.scene.control.Button;

public class CodeEditorPanel extends AbstractNodePanel {

  protected String state, action;


  public CodeEditorPanel(NodeController nodeController, String state, String action) {
    super(nodeController);
    this.state = state;
    this.action = action;
  }

  @Override
  protected List<Button> getNodeSelectionButtons() {
    return List.of(
        makeButton("Sum",
            event -> makeNode(NODES_FOLDER + "DraggableNodes.SumNode")),
        makeButton("Difference",
            event -> makeNode(NODES_FOLDER + "DraggableNodes.DifferenceNode")),
        makeButton("TextField",
            event -> makeNode(NODES_FOLDER + "DraggableNodes.TextFieldNode")),
        makeButton("Save",
            event -> saveAllNodeContent("src/main/resources/export.json"))
    );
  }

  public String getAction() {
    return action;
  }

  public String getState() {
    return state;
  }

  public boolean equals(CodeEditorPanel panel) {
    return panel.getAction().equals(this.action) && panel.getState().equals(this.state);
  }
}
