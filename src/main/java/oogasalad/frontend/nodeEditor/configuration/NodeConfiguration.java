package oogasalad.frontend.nodeEditor.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.frontend.nodeEditor.Command;
import oogasalad.frontend.nodeEditor.JsonNodeParser;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.nodes.JsonNode;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */

public class NodeConfiguration {

  private Gson gson = new Gson();
  private String filePath;
  private static final String COMMANDS_RESOURCE_PATH = "src/main/resources/backend/interpreter/Commands.json";
  private static final String METABLOCKS_RESOURCE_PATH = "src/main/resources/backend/interpreter/Metablocks.json";


  public NodeConfiguration(String filePath) throws FileNotFoundException {
    this.filePath = filePath;
  }

  /**
   * Returns a list of NodeData objects that represent the nodes in the file
   * 
   * @return List<NodeData>
   */
  public List<NodeData> getNodeData() {
    List<NodeData> nodeData = new ArrayList<>();
    try (FileReader reader = new FileReader(this.filePath)) {
      JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
      for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
        JsonObject obj = entry.getValue().getAsJsonObject();
        String name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();

        List<JsonElement> jsonInputValues = obj.getAsJsonArray("inputs").asList();

        List<String> inputValues = new ArrayList<>();
        for (JsonElement jsonElement : jsonInputValues) {
          inputValues.add(jsonElement.getAsString());
        }
        NodeData data = new NodeData(name, type, inputValues);
        nodeData.add(data);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return nodeData;
  }

  /**
   * Returns a list of AbstractNode objects that represent the nodes in a load file
   * @param nodeData
   * @return
   */
  public List<AbstractNode> makeNodes(List<NodeData> nodeData) {
    List<AbstractNode> nodes = new ArrayList<>();
    JsonNodeParser parser = new JsonNodeParser();
    List<Command> commands = parser.readCommands(COMMANDS_RESOURCE_PATH);
    commands.addAll(parser.readCommands(METABLOCKS_RESOURCE_PATH));

    for (NodeData data : nodeData) {
      switch (data.type()) {
        case "JsonNode":
          Command tempCommand = null;
          for (Command command : commands) {
            if (command.name().equals(data.name())) {
              tempCommand = command;
            }
          }
          if (tempCommand == null) {
            throw new RuntimeException("Command not found");
          }
          JsonNode node = new JsonNode(tempCommand);
          node.setInputFields(data.inputs());
          nodes.add(node);
          break;
        case "ControlNode":
          try {
            Class<?> clazz = Class.forName("oogasalad.frontend.nodeEditor.nodes." + data.name());
            Constructor<?> constructor = clazz.getConstructor();
            AbstractNode controlNode = (AbstractNode) constructor.newInstance();
            nodes.add(controlNode);

          } catch (Exception e) {
            e.printStackTrace();
          }
          break;

        default:
          throw new RuntimeException("Node type not found");
      }
    }
    return nodes;
  }
}