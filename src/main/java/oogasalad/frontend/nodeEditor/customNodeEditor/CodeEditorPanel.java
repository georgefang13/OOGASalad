package oogasalad.frontend.nodeEditor.customNodeEditor;

import java.util.List;
import javafx.scene.control.Button;

public class CodeEditorPanel extends AbstractNodePanel {


  public CodeEditorPanel(NodeController nodeController) {
    super(nodeController);
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
}
