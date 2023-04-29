package oogasalad.frontend.nodeEditor.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.geometry.Bounds;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.List;
import java.io.IOException;
import java.util.List;

public class NodeConfiguration {
    private List<AbstractNode> nodes;

    public NodeConfiguration(List<AbstractNode> nodes) {
        this.nodes = nodes;
    }

    public List<AbstractNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<AbstractNode> nodes) {
        this.nodes = nodes;
    }

    public String toJson(String filePath) {
        try {
            SerializationUtil.serialize(this, filePath);
            return "worked";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    public static NodeConfiguration fromJson(String filePath) {
        try {
            return SerializationUtil.deserialize(filePath, NodeConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
