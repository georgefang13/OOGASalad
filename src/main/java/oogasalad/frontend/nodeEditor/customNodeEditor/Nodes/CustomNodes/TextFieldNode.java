package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.CustomNodes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.OogaNode;

public class TextFieldNode extends OogaNode {

    private TextField field;
    public TextFieldNode(double x, double y, double width, double height, String color) {
        super(x, y, width, height, color);


    }

    @Override
    protected void setContent() {
        Label title = new Label("Text Node");
        field = new TextField();

        this.getChildren().addAll(title, field);
    }
}
