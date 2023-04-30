package oogasalad.frontend.nodeEditor.Config;

import com.google.gson.*;
import oogasalad.frontend.nodeEditor.Command;
import oogasalad.frontend.nodeEditor.JsonNodeParser;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.Nodes.JsonNode;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;

public class NodeConfiguration {

    Gson gson = new Gson();
    private String filePath;
    private static final String COMMANDS_RESOURCE_PATH = "user.dir/src/main/resources/backend/interpreter/Commands.json";


    public NodeConfiguration(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
    }

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

    public List<AbstractNode> makeNodes(List<NodeData> nodeData) {
        List<AbstractNode> nodes = new ArrayList<>();
        JsonNodeParser parser = new JsonNodeParser();
        List<Command> commands = parser.readCommands(COMMANDS_RESOURCE_PATH);

        for (NodeData data : nodeData) {
            switch (data.type()) {
                case "jsonNode":
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
                case "control" :
                    try {
                        Class<?> clazz = Class.forName("oogasalad.frontend.nodeEditor.Nodes." + data.name());
                        Constructor<?> constructor = clazz.getConstructor();
                        AbstractNode controlNode = (AbstractNode) constructor.newInstance();
                        nodes.add(controlNode);

                    } catch (Exception e){
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
