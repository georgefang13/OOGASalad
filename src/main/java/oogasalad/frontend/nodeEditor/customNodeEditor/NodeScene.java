package oogasalad.frontend.nodeEditor.customNodeEditor;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.scenes.AbstractScene;

public class NodeScene extends AbstractScene {

  private TabPane tabs;
  Map<String, Tab> tabMap;
  private NodeController nodeController;

  public NodeScene(NodeController nodeController) {
    super();
    this.nodeController = nodeController;
  }

  @Override
  public Scene makeScene() {
    tabs = new TabPane();
    tabMap = new HashMap<>();
    tabs.getTabs().add(makeTab("State Editor", false, new StateEditorPanel(nodeController)));
    return new Scene(tabs);
  }

  private Tab makeTab(String name, Boolean closable, AbstractNodePanel panel) {
    Tab tab = new Tab(name, new HBox(panel.makeNodeSelectionPane(), panel.makeWorkspacePane()));
    tab.setClosable(closable);
    tabMap.put(tab.getText(), tab);
    return tab;
  }

  public void openAndSwitchToTab(String name) {
    Tab existingTab = tabMap.get(name);
    if (existingTab == null) {
      Tab newTab = makeTab(name, true, new CodeEditorPanel(nodeController));
      tabs.getTabs().add(newTab);
      tabMap.put(name, newTab);
    } else {
      tabs.getSelectionModel().select(existingTab);
    }
  }

  @Override
  public void setText() {
  }
}
