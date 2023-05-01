package oogasalad.frontend.nodeEditor;

import java.util.List;
import javafx.scene.control.Button;

public class StateEditorPanel extends AbstractNodePanel {

  public StateEditorPanel(NodeController nodeController) {
    super(nodeController);
  }

  /**
   * Returns a list of buttons that can be used to create nodes for the state panel
   */
  @Override
  protected List<Button> getNodeSelectionButtons(String fileName) {
    return List.of(
        makeButton("State",
            event -> makeNode(NODES_FOLDER + "StateNode"))
    );
  }
}
