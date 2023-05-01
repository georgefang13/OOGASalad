package oogasalad.frontend.nodeEditor.nodes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.frontend.nodeEditor.Command;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
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

  public JsonNode(Command command) {
    super();
    this.name = command.name();
    this.innerBlocks = command.innerBlocks();
    this.parseStr = command.parseStr();
    this.inputs = command.inputs();
    setContent();
    this.getStyleClass().add(propertyManager.getText("JsonNode.StyleClass"));

  }

  /**
   * Sets the content of the node, including the title and the input fields
   *
   * @return void
   */
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


  /**
   * Returns the string that will be used to parse the node in the interpreter
   *
   * @return String
   */
  @Override
  public String getNodeParseString() {
    List<String> inputsAsStrings = new ArrayList<>();
    for (TextField input : inputFields) {
      inputsAsStrings.add(input.getText().toString());
    }
    String[] parseSplit = parseStr.split(propertyManager.getText("JsonNode.Regex"));
    String output = String.format(parseSplit[0], inputsAsStrings.toArray());
    if (this.getChildNode() == null) {
      return output;
    }
    return (output + propertyManager.getText("SPACE") + this.getChildNode().getNodeParseString());
  }


  /**
   * Returns the record of NodeData for this node
   *
   * @return NodeData
   */
  @Override
  public NodeData getNodeData() {
    this.nodeData = new NodeData(this.name, this.getClass().getSimpleName(), getInputValues());
    return this.nodeData;
  }

  ;


  /**
   * Sets the input fields of this node to the given values. Used when loading in an existing node
   * network
   *
   * @param values
   */
  public void setInputFields(List<String> values) {
    for (int i = 0; i < inputFields.size(); i++) {
      inputFields.get(i).setText(values.get(i));
    }
  }


  /**
   * Returns the list of input values for this node
   *
   * @return List of input values
   */
  private List<String> getInputValues() {
    inputValues.clear();
    for (TextField input : inputFields) {
      inputValues.add(input.getText().toString());
    }
    return inputValues;
  }

}