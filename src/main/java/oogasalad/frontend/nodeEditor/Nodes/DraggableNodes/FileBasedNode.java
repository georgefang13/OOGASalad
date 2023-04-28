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

  private String name;
  private JsonArray innerBlocks;
  private JsonArray outputTypes;
  private String parseStr;
  private JsonArray inputs;
  private List<TextField> inputFields = new ArrayList<>();
  private Label outputLabel;

  public FileBasedNode(NodeController nodeController, String name, JsonArray innerBlocks,
      JsonArray outputTypes, String parseStr, JsonArray inputs) {
    super(nodeController, DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT, "white");
    this.name = name;
    this.innerBlocks = innerBlocks;
    this.outputTypes = outputTypes;
    this.parseStr = parseStr;
    this.inputs = inputs;
    this.width = WIDTH;
    this.height = HEIGHT;
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
  }

  @Override
  public String getJSONString() {
    List<String> inputsAsStrings = new ArrayList<>();
    for (TextField input : inputFields) {
      inputsAsStrings.add(input.getText().toString());
    }
    String[] parseSplit = parseStr.split("\\[ %s \\]");
    String output = String.format(parseSplit[0], inputsAsStrings.toArray());
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