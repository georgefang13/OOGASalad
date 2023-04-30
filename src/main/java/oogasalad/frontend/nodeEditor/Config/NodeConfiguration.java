package oogasalad.frontend.nodeEditor.Config;
import com.google.gson.Gson;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class NodeConfiguration {

    Gson gson = new Gson();
    private String filePath;


    public NodeConfiguration(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
    }

    public List<AbstractNode> getNodes() throws IOException, ClassNotFoundException {
        List<AbstractNode> nodes = new ArrayList<>();
        Iterable<String> jsonNodes= fileManager.getTagsAtLevel();
        for (String jsonNode : jsonNodes) {
            String type = fileManager.ge
        }


    }

}
