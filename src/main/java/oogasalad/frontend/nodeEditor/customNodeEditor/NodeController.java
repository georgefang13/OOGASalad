package oogasalad.frontend.nodeEditor.customNodeEditor;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class NodeController {

  Map<String, Tab> tabsByName;
  private TabPane tabs;

  public NodeController(TabPane tabs) {
    this.tabs = tabs;
    this.tabsByName = new HashMap<>();
    for (Tab tab : tabs.getTabs()) {
      tabsByName.put(tab.getText(), tab);
    }
  }

  public void openAndSwitchToTab(String name) {
    Tab existingTab = tabsByName.get(name);
    if (existingTab == null) {
      Tab newTab = new Tab(name);
      tabs.getTabs().add(newTab);
      tabsByName.put(name, newTab);
    } else {
      tabs.getSelectionModel().select(existingTab);
    }
  }
}
