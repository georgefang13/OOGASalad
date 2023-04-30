package oogasalad.frontend.nodeEditor;

import static oogasalad.frontend.nodeEditor.AbstractNodeEditorTab.USER_CODE_FILES_PATH;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.configuration.NodeConfiguration;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.scenes.AbstractScene;

public class NodeScene extends AbstractScene {

  private TabPane tabs;
  Map<Tab, CodeEditorTab> tabMap;
  private NodeController nodeController;
  public static final String CONFIG_JSON_PATH = "src/main/resources/nodeCode/config.json";


  public NodeScene(NodeController nodeController) {
    super();
    this.nodeController = nodeController;
    tabs.getTabs().add(makeTab("state editor", false, new StateEditorTab(nodeController)));
    setTheme();
  }

  @Override
  public Scene makeScene() {
    tabs = new TabPane();
    tabMap = new HashMap<>();
    return new Scene(tabs);
  }

  private Tab makeTab(String name, Boolean closable, AbstractNodeEditorTab panel) {
    Tab tab = new Tab(name, new HBox(panel.makeNodeButtonPanel(), panel.makeWorkspacePanel()));
    tab.setClosable(closable);
    return tab;
  }

  public void openAndSwitchToTab(String state, String action) {
    CodeEditorTab panel = new CodeEditorTab(nodeController, state, action);
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
    for (Entry<Tab, CodeEditorTab> entry : tabMap.entrySet()) {
      String state = entry.getValue().getState();
      String action = entry.getValue().getAction();
      String content = entry.getValue().getMainNodeParseString();
      List<AbstractNode> listOfNodes = entry.getValue().getMainNodeChildren();
      makeConfigFile(listOfNodes);

      if (!stateObject.has(state)) {
        JsonObject stateJson = new JsonObject();
        stateJson.addProperty(action, content);
        stateObject.add(state, stateJson);
      } else {
        JsonObject stateJson = stateObject.get(state).getAsJsonObject();
        stateJson.addProperty(action, content);
      }
    }
    try (FileWriter fileWriter = new FileWriter(USER_CODE_FILES_PATH)) {
      gson.toJson(stateObject, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  //private void makeInterpreterFile(List<AbstractNode> nodes)


  private void makeConfigFile(List<AbstractNode> nodes) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject stateObject = new JsonObject();
    Integer i = 0;
    for (AbstractNode node : nodes) {
      NodeData data = node.getNodeData();
      JsonObject stateJson = new JsonObject();
      JsonArray array = new JsonArray();
      String name = data.name();
      String type = data.type();
      data.inputs().forEach(array::add);
      stateJson.addProperty("name", name);
      stateJson.addProperty("type", type);
      stateJson.add("inputs", array);
      stateObject.add(i.toString(), stateJson);
      i++;
    }

    try (FileWriter fileWriter = new FileWriter(CONFIG_JSON_PATH)) {
      gson.toJson(stateObject, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void setText() {
  }

  public void loadAllContent(String filePath) {
    try {
      CodeEditorTab panel = tabMap.get(tabs.getSelectionModel().getSelectedItem());
      panel.clearAllNodes();
      NodeConfiguration config = new NodeConfiguration(filePath);
      List<NodeData> nodeData = config.getNodeData();
      List<AbstractNode> nodes = config.makeNodes(nodeData);

      AbstractNode tempParent = null;

      tempParent = nodes.get(0);
      nodes.remove(0);

      for (AbstractNode node : nodes) {
        panel.addNode(node);
        node.snapToNode(tempParent);
        tempParent = node;
      }


    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
