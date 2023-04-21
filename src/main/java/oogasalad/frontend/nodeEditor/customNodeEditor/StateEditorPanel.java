package oogasalad.frontend.nodeEditor.customNodeEditor;

import java.util.List;
import javafx.scene.control.Button;

public class StateEditorPanel extends AbstractNodePanel {

  public StateEditorPanel(NodeController nodeController) {
    super(nodeController);
  }

  @Override
  protected List<Button> getNodeSelectionButtons() {
    return List.of(
        makeButton("State",
            event -> makeNode(NODES_FOLDER + "DraggableNodes.StateNode")),
        makeButton("Save",
            event -> saveAllNodeContent("src/main/resources/export.json"))
    );
  }
}
