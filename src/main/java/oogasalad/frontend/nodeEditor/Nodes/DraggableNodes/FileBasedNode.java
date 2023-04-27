package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.NodeController;

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
    private List<TextField> inputFields = new ArrayList<>();
    private Label outputLabel;
    private ArrayList<String> inputs = new ArrayList<>();

    public FileBasedNode(NodeController nodeController, String name, JsonArray innerBlocks, JsonArray outputTypes, String parseStr, JsonArray inputs) {
        super(nodeController,DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, "white");
        myName = name;
        myInnerBlocks = innerBlocks;
        myOutputTypes = outputTypes;
        myParseStr = parseStr;
        myInputs = inputs;
        setContent();
        this.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: white; -fx-background-radius: 5px; -fx-padding: 5px;");

    }

    @Override
    protected void setContent() {
        Label title = new Label(myName);
        this.getChildren().addAll(title);
        myInputs.forEach(item -> {
            HBox tempInputArea = new HBox();
            JsonObject object = item.getAsJsonObject();
            Label input = new Label(object.get("name").getAsString() + ": ");
            TextField inputField = new TextField();
            tempInputArea.getChildren().addAll(input, inputField);
            this.getChildren().addAll(tempInputArea);
            inputFields.add(inputField);
        });
        outputLabel = new Label();
        this.getChildren().addAll(outputLabel);
        updateOutput();

    }

    @Override
    public String getJSONString() {
        List<String> inputsAsStrings = new ArrayList<>();
        for (TextField input:inputFields) {
            inputsAsStrings.add(input.getText().toString());
        }
        String output = String.format(myParseStr,inputsAsStrings.toArray());
        if (this.getChildNode() == null){
            return output;
        }
        return (output + " " + this.getChildNode().getJSONString());
    }

    private void updateOutput() {
        String output = "output: ";
        outputLabel.setText(output);
    }

    public String getName() {
        return myName;
    }

    protected void checkInner(){
        setJumpNode(null);
        for (int i = 0; i < myInnerBlocks.size(); i++) {
            setEndJumpNode(makeNest());

        }
    }

//    @Override
//    public String sendContent() {
//        return outputLabel.getText() + sendChildContent();
//    }
}