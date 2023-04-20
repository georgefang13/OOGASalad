package oogasalad.frontend.nodeEditor.customNodeEditor;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Label;
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

  }
}
