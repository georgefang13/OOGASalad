package oogasalad.frontend.nodeEditor.Config;

import com.google.gson.Gson;
import java.io.FileNotFoundException;

public class NodeConfiguration {

    Gson gson = new Gson();
    private String filePath;


    public NodeConfiguration(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
    }

//    public List<AbstractNode> getNodes() throws IOException, ClassNotFoundException {
//
//    }

}
