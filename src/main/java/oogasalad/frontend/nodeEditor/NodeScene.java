package oogasalad.frontend.nodeEditor;

import static oogasalad.frontend.nodeEditor.AbstractNodePanel.NODES_JSON_PATH;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
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

  public void saveAllContent(String filePath) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject stateObject = new JsonObject();
    for (Entry<Tab, CodeEditorPanel> entry : tabMap.entrySet()) {
      String state = entry.getValue().getState();
      String action = entry.getValue().getAction();
      String content = entry.getValue().getAllNodeContent();
      if (!stateObject.has(state)) {
        JsonObject stateJson = new JsonObject();
        stateJson.addProperty(action, content);
        stateObject.add(state, stateJson);
      } else {
        JsonObject stateJson = stateObject.get(state).getAsJsonObject();
        stateJson.addProperty(action, content);
      }
    }
    try (FileWriter fileWriter = new FileWriter(NODES_JSON_PATH)) {
      gson.toJson(stateObject, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setText() {
  }
}
