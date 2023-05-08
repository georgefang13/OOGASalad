package oogasalad.frontend.nodeEditor.tabs;

import static oogasalad.frontend.nodeEditor.tabs.AbstractNodeEditorTab.INTERPRETER_FILES_PATH;
import static oogasalad.frontend.nodeEditor.tabs.AbstractNodeEditorTab.USER_CODE_FILES_PATH;

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
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.configuration.NodeConfiguration;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.scenes.SceneMediator;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */

public class NodeScene extends AbstractScene {

  private String gameName;
  private TabPane tabs;
  Map<Tab, CodeEditorTab> tabMap;
  private NodeController nodeController;

  private final String GAME_FILEPATH = "data/games/";
  private final String GAME_NODE_SAVE_PATH = "data/games/%s/NodeConfig/";

  private SceneMediator mySceneController;


  public NodeScene(NodeController nodeController, SceneMediator sceneController){
    super();
    this.nodeController = nodeController;
    tabs.getTabs().add(makeTab("state editor", false, new StateEditorTab(nodeController)));
    setTheme();
    this.mySceneController = sceneController;
    gameName = mySceneController.getGameName().toLowerCase(); //todo
    System.out.println("succesfully added gamename to node scene:");
    System.out.println(gameName);
  }

  /**
   * Creates the scene that will be displayed in the main window
   *
   * @return Scene
   */
  @Override
  public Scene makeScene() {
    tabs = new TabPane();
    tabMap = new HashMap<>();
    return new Scene(tabs);
  }

  /**
   * Creates a tab with the given name, closable, and panel
   *
   * @param name
   * @param closable
   * @param tab
   * @return Tab
   */
  private Tab makeTab(String name, Boolean closable, AbstractNodeEditorTab tab) {
    tab.setText(name);
    tab.setClosable(closable);
    return tab;
  }

  /**
   * Opens a new tab with the given state and action
   *
   * @return void
   */
  public void openAndSwitchToTab(CodeEditorTab panel) {
    for (Tab tab : tabMap.keySet()) {
      if (tabMap.get(tab).equals(panel)) {
        tabs.getSelectionModel().select(tab);
        return;
      }
    }
    Tab newTab = makeTab(panel.getState() + ":" + panel.getAction(), true, panel);
    tabs.getTabs().add(newTab);
    tabMap.put(newTab, panel);
  }

  /**
   * Saves all of the content in the current tab to the given file path
   *
   * @param filePath
   * @return void
   */
  public void saveAllContent(String filePath) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject fullObject = new JsonObject();
    JsonObject stateObject = new JsonObject();
    JsonObject rulesObjectArr = new JsonObject();
    JsonArray goalArray = new JsonArray();
    JsonArray gameObjectsArray = new JsonArray();
    for (Entry<Tab, CodeEditorTab> entry : tabMap.entrySet()) {
      CodeEditorTab tab = entry.getValue();
      if (tab instanceof GoalTab) {
        String state = entry.getValue().getState();
        String action = entry.getValue().getAction();
        List<AbstractNode> listOfNodes = entry.getValue().getMainNodeChildren();
        String configName = state + action;
        makeConfigFile(listOfNodes, configName);
        goalArray.add(tab.getMainNodeParseString());
      } else if(tab instanceof GameObjectTab) {
        String state = entry.getValue().getState();
        String action = entry.getValue().getAction();
        String content = entry.getValue().getMainNodeParseString();
        List<AbstractNode> listOfNodes = entry.getValue().getMainNodeChildren();
        String configName = state + action;
        makeConfigFile(listOfNodes, configName);
        if (!rulesObjectArr.has(state)) {
          JsonObject ruleJson = new JsonObject();
          ruleJson.addProperty(action, content);
          rulesObjectArr.add(state, ruleJson);
        } else {
          JsonObject ruleJson = rulesObjectArr.get(state).getAsJsonObject();
          ruleJson.addProperty(action, content);
        }
//        gameObjectsArray.add(tab.getMainNodeParseString());
      }else {
        String state = entry.getValue().getState();
        String action = entry.getValue().getAction();
        String content = entry.getValue().getMainNodeParseString();
        List<AbstractNode> listOfNodes = entry.getValue().getMainNodeChildren();
        String configName = state + action;
        makeConfigFile(listOfNodes, configName);
        if (!stateObject.has(state)) {
          JsonObject stateJson = new JsonObject();
          stateJson.addProperty(action, content);
          stateObject.add(state, stateJson);
        } else {
          JsonObject stateJson = stateObject.get(state).getAsJsonObject();
          stateJson.addProperty(action, content);
        }
      }
    }
    fullObject.add("states", stateObject);
    fullObject.add("goal", goalArray);
    try (FileWriter fileWriter = new FileWriter(GAME_FILEPATH + gameName + "/saveFSM.json")) {
      gson.toJson(fullObject, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (FileWriter fileWriter = new FileWriter(GAME_FILEPATH + gameName + "/saveRules.json")) {
      JsonObject rulesObject = new JsonObject();
      rulesObject.add("Rules", rulesObjectArr);
      gson.toJson(rulesObjectArr, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  /**
   * Makes a config file that contains all of the nodes in the current tab that allows us to reload
   * the nodes later
   *
   * @param nodes
   * @param fileName
   * @return void
   */
  private void makeConfigFile(List<AbstractNode> nodes, String fileName) {
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
      try (FileWriter fileWriter = new FileWriter(String.format(GAME_NODE_SAVE_PATH,gameName) + fileName + ".json")) {
        gson.toJson(stateObject, fileWriter);
      } catch (IOException e) {
        e.printStackTrace();
      }

  }

  /**
   * Returns the scene that is being used by the NodeController
   *
   * @return Scene
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * sets the text of the scene
   *
   * @return void
   */
  @Override
  public void setText() {
  }

  /**
   * Loads in all of the nodes from the given file path
   *
   * @param filePath
   */
  public void loadAllContent(String filePath) {
    try {
      CodeEditorTab panel = tabMap.get(tabs.getSelectionModel().getSelectedItem());
      panel.clearNodes();
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
