package oogasalad.frontend.nodeEditor.Nodes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.Config.NodeData;

public class JsonNode extends AbstractNode {

  private String name, parseStr;
  private List<String> innerBlocks, inputs;
  private List<TextField> inputFields = new ArrayList<>();

  private List<String> inputValues = new ArrayList<>();

  public JsonNode(String name, List<String> innerBlocks, String parseStr,
      List<String> inputs) {
    super();
    this.name = name;
    this.innerBlocks = innerBlocks;
    this.parseStr = parseStr;
    this.inputs = inputs;
    setContent();
    this.getStyleClass().add(propertyManager.getText("JsonNode.StyleClass"));
  }

  @Override
  protected void setContent() {
    Label title = new Label(name);
    title.getStyleClass().add(propertyManager.getText("JsonNode.Title"));
    this.getChildren().addAll(title);
    inputs.forEach(item -> {
      HBox tempInputArea = new HBox();
      Label input = new Label(item + propertyManager.getText("JsonNode.LabelDelimiter"));
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
    String[] parseSplit = parseStr.split(propertyManager.getText("JsonNode.Regex"));
    String output = String.format(parseSplit[0], inputsAsStrings.toArray());
    if (this.getChildNode() == null) {
      return output;
    }
    return (output + propertyManager.getText("SPACE") + this.getChildNode().getJSONString());
  }

  @Override
  public NodeData getNodeData(){
    this.nodeData = new NodeData(this.name, this.getClass().getSimpleName(), getInputValues());
    return this.nodeData;
  };


  private List<String> getInputValues(){
    inputValues.clear();
    for (TextField input : inputFields) {
      inputValues.add(input.getText().toString());
    }
    return inputValues;
  }

}