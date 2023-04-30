package oogasalad.frontend.nodeEditor.configuration;

import java.io.Serializable;
import java.util.List;

public record NodeData(String name, String type, List<String> inputs) implements Serializable {
    
}

