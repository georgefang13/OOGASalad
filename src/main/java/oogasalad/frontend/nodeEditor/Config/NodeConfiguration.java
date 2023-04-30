package oogasalad.frontend.nodeEditor.Config;

import com.google.gson.*;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;

public class NodeConfiguration {

    Gson gson = new Gson();
    private String filePath;


    public NodeConfiguration(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
    }

    public List<NodeData> getNodeData() throws IOException, ClassNotFoundException {
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

}
