package oogasalad.frontend.nodeEditor.nodes;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.nodeEditor.GoalEditorTab;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import oogasalad.frontend.nodeEditor.NodeController;

import java.util.ArrayList;

public class GoalNode extends AbstractNode implements ControlNode {

  private TextField goalName;
  private GridPane buttonGrid;
  private NodeController nodeController;


  public GoalNode(NodeController nodeController) {
    super();
    this.nodeController = nodeController;
    setContent();
    this.getStyleClass().add(propertyManager.getText("GoalNode.StyleClass"));
  }

  /**
   * sets the content for the node
   *
   * @return void
   */
  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("GoalNode.Title"));
    title.getStyleClass().add(propertyManager.getText("GoalNode.TitleClass"));
    buttonGrid = new GridPane();
    this.getChildren().addAll(title, buttonGrid);
    goalName = new TextField();
    goalName.setPromptText(propertyManager.getText("GoalNode.NameLabel"));
    buttonGrid.add(
        goalName, 0, 0);
    buttonGrid.add(
        makeButton(propertyManager.getText("GoalNode.GoalLabel"), this::goal), 0, 1);
  }

  /**
   * gets the string that will be used to parse the node in the interpreter
   *
   * @return String
   */
  @Override
  public String getNodeParseString() {
    return "";
  }

  /**
   * Returns the record of NodeData for this node
   *
   * @return NodeData
   */
  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }


  /**
   * initializes a tab and switches to it for the on enter event
   *
   * @param event
   * @return void
   */
  private void goal(ActionEvent event) {
    GoalEditorTab goalTab = new GoalEditorTab(nodeController, goalName.getText());
    nodeController.openAndSwitchToTab(goalTab);
  }


}
