package oogasalad.frontend.nodeEditor.myNodeEditor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class SerializableNodeList implements Serializable {
    private transient ObservableList<Node> nodes;

    public SerializableNodeList(ObservableList<Node> nodes) {
        this.nodes = nodes;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        List<Node> nodeList = new ArrayList<>(nodes);
        out.writeInt(nodeList.size());
        for (Node node : nodeList) {
            out.writeObject(node);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int size = in.readInt();
        List<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodeList.add((Node) in.readObject());
        }
        nodes = FXCollections.observableArrayList(nodeList);
    }

    public ObservableList<Node> getNodes() {
        return nodes;
    }
}
