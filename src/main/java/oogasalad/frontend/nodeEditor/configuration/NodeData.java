package oogasalad.frontend.nodeEditor.configuration;

import java.io.Serializable;
import java.util.List;

// Represents the data of a node in the node editor as a Java Record
public record NodeData(String name, String type, List<String> inputs) implements Serializable {
    
}

