package oogasalad.frontend.nodeEditor.nodes;

import javafx.scene.control.Label;
import oogasalad.frontend.nodeEditor.configuration.NodeData;

import java.util.ArrayList;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class MainNode extends AbstractNode implements ControlNode {

  public MainNode() {
    super();
    setContent();
    this.getStyleClass().add(propertyManager.getText("MainNode.StyleClass"));
  }

  /**
   * sets the content for the node
   * @return void
   */
  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("MainNode.Title"));
    this.getChildren().add(title);
  }

  /**
   * gets the string that will be used to parse the node in the interpreter
   * @return String
   */
  @Override
  public String getNodeParseString() {
    if (this.getChildNode() != null) {
      return this.getChildNode().getNodeParseString();
    }
    return propertyManager.getText("EmptyString");
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
   * Aligns the nodes. Empty implementation for MainNode because it can't be snapped to another node (Can't be the child of another node)
   * @return void
   */
  @Override
  public void snapToNode(AbstractNode node) {
  }

  /**
   * Deletes the node. Empty implementation for MainNode because it can't be deleted
   * @return void
   */
  @Override
  public void delete() {
  }

}
