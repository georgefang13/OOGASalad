package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.CustomNodes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.AbstractNode;

public class TextFieldNode extends AbstractNode {

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

    @Override
    public String sendContent() {
        return field.getText();
    }
}
