package oogasalad.frontend.nodeEditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;

public class StateEditorTab extends AbstractNodeEditorTab {

  public StateEditorTab(NodeController nodeController) {
    super(nodeController);
    setContent(new HBox(makeNodeButtonPanel(), makeWorkspacePanel()));
  }

  @Override
  protected List<Button> getNodeButtons(List<String> filenames) {
    return List.of(
        makeButton("State",
            event -> makeNode(NODES_FOLDER + "StateNode")),
        makeButton("Goal",
            event -> makeNode(NODES_FOLDER + "GoalNode"))
    );
  }

  public BorderPane makeWorkspacePanel() {
    BorderPane borderPane = super.makeWorkspacePanel();
//    addNode(new GoalNode(nodeController));
    return borderPane;
  }

  @Override
  public Accordion getAccordianFinished(List<String> filenames) {
    return null;
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
