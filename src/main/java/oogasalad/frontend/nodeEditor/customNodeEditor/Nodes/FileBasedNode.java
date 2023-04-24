package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;

import java.util.ArrayList;
import java.util.Map;

public class FileBasedNode extends DraggableAbstractNode {

    private static final double DEFAULT_X = 0;
    private static final double DEFAULT_Y = 0;

    private static final double WIDTH = 300;
    private static final double HEIGHT = 100;


    private String myName;
    private JsonArray myInnerBlocks;
    private JsonArray myOutputTypes;
    private String myParseStr;
    private JsonArray myInputs;
    private TextField operand1, operand2;
    private Label outputLabel;
    private ArrayList<String> inputs = new ArrayList<>();

    public FileBasedNode(String name, JsonArray innerBlocks, JsonArray outputTypes, String parseStr, JsonArray inputs) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, "white");
        myName = name;
        myInnerBlocks = innerBlocks;
        myOutputTypes = outputTypes;
        myParseStr = parseStr;
        myInputs = inputs;
        setContent();

    }

    @Override
    protected void setContent() {
//        operand1 = new TextField();
//        final Region filler = new Region();
//        this.setVgrow(filler, Priority.ALWAYS);
//        Label title = new Label("Difference Node");
//        operand2 = new TextField();
//        final Region filler2 = new Region();
//        outputLabel = new Label();
//        operand1.textProperty().addListener((observable, oldValue, newValue) -> updateSum());
//        operand2.textProperty().addListener((observable, oldValue, newValue) -> updateSum());
//        this.getChildren().addAll(operand1, filler, title, operand2, filler2, outputLabel);
//        updateSum();

        Label title = new Label(myName);
        this.getChildren().addAll(title);
        myInputs.forEach(item -> {
            HBox tempInputArea = new HBox();
            JsonObject object = item.getAsJsonObject();
            Label input = new Label(object.get("name").getAsString() + ": ");
            TextField inputField = new TextField();
            tempInputArea.getChildren().addAll(input, inputField);
            this.getChildren().addAll(tempInputArea);
        });
        outputLabel = new Label();
        this.getChildren().addAll(outputLabel);
        updateOutput();

    }

    private void updateSum() {
        try {
            double op1 = Double.parseDouble(operand1.getText());
            double op2 = Double.parseDouble(operand2.getText());
            outputLabel.setText(Double.toString(op1 - op2));
        } catch (NumberFormatException e) {
            outputLabel.setText("NaN");
        }
    }

    private void updateOutput() {
        String output = "output: ";
        outputLabel.setText(output);
    }

    @Override
    public String sendContent() {
        return outputLabel.getText() + sendChildContent();
    }
}