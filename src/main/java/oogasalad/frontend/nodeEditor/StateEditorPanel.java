package oogasalad.frontend.nodeEditor;

import java.util.List;
import javafx.scene.control.Button;

public class StateEditorPanel extends AbstractNodePanel {

  public StateEditorPanel(NodeController nodeController) {
    super(nodeController);
  }

  @Override
  protected List<Button> getNodeSelectionButtons(String fileName) {
    return List.of(
        makeButton("State",
            event -> makeNode(NODES_FOLDER + "StateNode")),
        makeButton("Save",
            event -> nodeController.saveAllContent(NODES_JSON_PATH))
    );
  }
}
