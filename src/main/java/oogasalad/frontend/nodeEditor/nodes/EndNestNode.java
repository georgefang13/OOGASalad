package oogasalad.frontend.nodeEditor.nodes;

import java.util.ArrayList;
import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.configuration.NodeData;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class EndNestNode extends AbstractNode implements ControlNode {

  public EndNestNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("EndNestNode.StyleClass"));
  }

  /**
   * gets the string that will be used to parse the node in the interpreter
   * @return String
   */
  @Override
  public String getNodeParseString() {
    if (this.getChildNode() != null) {
      return propertyManager.getText("EndNestNode.JsonString") + this.getChildNode()
          .getNodeParseString();
    }
    return propertyManager.getText("EndNestNode.JsonString");
  }

  /**
   * Returns the record of NodeData for this node
   * @return NodeData
   */
  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }

  /**
   * Sets the content of the node, including the title and the input fields
   * @return void
   */
  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("EndNestNode.Title"));
    this.getChildren().add(title);
  }

}
