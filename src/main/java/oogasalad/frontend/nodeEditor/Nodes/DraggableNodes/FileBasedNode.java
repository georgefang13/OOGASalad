package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.NodeController;

public class FileBasedNode extends DraggableAbstractNode {

  private static final double DEFAULT_X = 0;
  private static final double DEFAULT_Y = 0;
  private static final double INDENT = 50;

  private static final double WIDTH = 300;
  private static final double HEIGHT = 100;

  private String name;
  private StartNestNode start;
  private EndNestNode end;
  private JsonArray innerBlocks;
  private JsonArray outputTypes;
  private String parseStr;
  private JsonArray inputs;
  private List<TextField> inputFields = new ArrayList<>();
  private Label outputLabel;

  public FileBasedNode(NodeController nodeController, String name, JsonArray innerBlocks,
      JsonArray outputTypes, String parseStr, JsonArray inputs) {
    super(nodeController, DEFAULT_X, DEFAULT_Y, INDENT, WIDTH, HEIGHT, "white");
    this.name = name;
    this.innerBlocks = innerBlocks;
    this.outputTypes = outputTypes;
    this.parseStr = parseStr;
    this.inputs = inputs;
    setContent();
    this.setStyle(
        "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: transparent; -fx-background-radius: 5px; -fx-padding: 5px;");
  }

  @Override
  protected void setContent() {
    Label title = new Label(name);
    this.getChildren().addAll(title);
    inputs.forEach(item -> {
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
    for (JsonElement nestBlock : innerBlocks.asList()) {
      if (start == null) {
        //start = new StartNestNode();
      }
      //end = new EndNestNode();

    }
  }

  @Override
  public String getJSONString() {
    List<String> inputsAsStrings = new ArrayList<>();
    for (TextField input : inputFields) {
      inputsAsStrings.add(input.getText().toString());
    }
    String output = String.format(parseStr, inputsAsStrings.toArray());
    if (this.getChildNode() == null) {
      return output;
    }
    return (output + " " + this.getChildNode().getJSONString());
  }

  private void updateOutput() {
    String output = "output: ";
    outputLabel.setText(output);
  }

  public String getName() {
    return name;
  }
}