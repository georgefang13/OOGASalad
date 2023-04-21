package oogasalad.frontend.nodeEditor.customNodeEditor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.AbstractNode;
import oogasalad.frontend.scenes.AbstractScene;

public class NodeScene extends AbstractScene {

  private TabPane tabs;
  Map<Tab, CodeEditorPanel> tabMap;
  private NodeController nodeController;

  public NodeScene(NodeController nodeController) {
    super();
    this.nodeController = nodeController;
  }

  @Override
  public Scene makeScene() {
    tabs = new TabPane();
    tabMap = new HashMap<>();
    tabs.getTabs().add(makeTab("state editor", false, new StateEditorPanel(nodeController)));
    return new Scene(tabs);
  }

  private Tab makeTab(String name, Boolean closable, AbstractNodePanel panel) {
    Tab tab = new Tab(name, new HBox(panel.makeNodeSelectionPane(), panel.makeWorkspacePane()));
    tab.setClosable(closable);
    return tab;
  }

  public void openAndSwitchToTab(String state, String action) {
    CodeEditorPanel panel = new CodeEditorPanel(nodeController, state, action);
    for (Tab tab : tabMap.keySet()) {
      if (tabMap.get(tab).equals(panel)) {
        tabs.getSelectionModel().select(tab);
        return;
      }
    }
    Tab newTab = makeTab(state + ":" + action, true, panel);
    tabs.getTabs().add(newTab);
    tabMap.put(newTab, panel);
  }

//  public void saveContent() {
//    for (Node node : group.getChildren()) {
//      if (node instanceof AbstractNode) {
//        JsonObject stateObject = gson.fromJson(node.sendJSONContent(), JsonObject.class);
//        String stateName = stateObject.keySet().iterator().next();
//        statesObject.add(stateName, stateObject.get(stateName));
//      }
//    }
//    JsonObject contentObject = new JsonObject();
//    contentObject.add("states", statesObject);
//    try (FileWriter fileWriter = new FileWriter(NODES_JSON_FOLDER + filePath)) {
//      gson.toJson(contentObject, fileWriter);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    JsonObject allContent = new JsonObject();
//    for(Entry<Tab, AbstractNodePanel> entry : tabMap.entrySet()) {
//      JsonObject content = entry.getValue().getAllNodeContent();
//      allContent.add()
//
//      //"export.json"
//    }
//  }

  @Override
  public void setText() {
  }
}
