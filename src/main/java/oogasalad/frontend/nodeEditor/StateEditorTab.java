package oogasalad.frontend.nodeEditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.scene.control.Button;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;

public class StateEditorTab extends AbstractNodeEditorTab {

  public StateEditorTab(NodeController nodeController) {
    super(nodeController);
  }

  @Override
  protected List<Button> getNodeButtons() {
    return List.of(
        makeButton("State",
            event -> makeNode(NODES_FOLDER + "StateNode"))
    );
  }

  protected void makeNode(String className) {
    try {
      Class<?> clazz = Class.forName(className);
      Constructor<?> constructor = clazz.getConstructor(NodeController.class);
      AbstractNode node = (AbstractNode) constructor.newInstance(nodeController);
      addNode(node);
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
             IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
