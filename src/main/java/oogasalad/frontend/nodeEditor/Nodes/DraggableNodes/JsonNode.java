package oogasalad.frontend.nodeEditor.Nodes.DraggableNodes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

public class JsonNode extends AbstractNode {

  private String name, parseStr;
  private List<String> innerBlocks, inputs;
  private List<TextField> inputFields = new ArrayList<>();

  public JsonNode(String name, List<String> innerBlocks, String parseStr,
      List<String> inputs) {
    super();
    this.name = name;
    this.innerBlocks = innerBlocks;
    this.parseStr = parseStr;
    this.inputs = inputs;
    setContent();
    this.getStyleClass().add("cum-bucket");

  }

  @Override
  protected void setContent() {
    Label title = new Label(name);
    this.getChildren().addAll(title);
    inputs.forEach(item -> {
      HBox tempInputArea = new HBox();
      Label input = new Label(item + ": ");
      TextField inputField = new TextField();
      tempInputArea.getChildren().addAll(input, inputField);
      this.getChildren().addAll(tempInputArea);
      inputFields.add(inputField);
    });
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
}