package oogasalad.frontend.nodeEditor.nodes;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.nodeEditor.tabs.CodeEditorTab;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import oogasalad.frontend.nodeEditor.NodeController;

import java.util.ArrayList;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class StateNode extends AbstractNode implements ControlNode {

  private TextField stateName;
  private GridPane buttonGrid;
  private NodeController nodeController;


  public StateNode(NodeController nodeController) {
    super();
    this.nodeController = nodeController;
    setContent();
    this.getStyleClass().add(propertyManager.getText("StateNode.StyleClass"));
  }

  /**
   * sets the content for the node
   *
   * @return void
   */
  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("StateNode.Title"));
    title.getStyleClass().add(propertyManager.getText("StateNode.TitleClass"));
    buttonGrid = new GridPane();
    this.getChildren().addAll(title, buttonGrid);
    stateName = new TextField();
    stateName.setPromptText(propertyManager.getText("StateNode.NameLabel"));
    buttonGrid.add(
        stateName, 0, 0);
    buttonGrid.add(
        makeButton(propertyManager.getText("StateNode.OnEnterLabel"), this::onInitialize), 0, 1);
    buttonGrid.add(
        makeButton(propertyManager.getText("StateNode.OnLeaveLabel"), this::onLeave), 0,
        2);
    buttonGrid.add(
        makeButton(propertyManager.getText("StateNode.OnSetValueLabel"), this::onSetValue), 0, 3);
    buttonGrid.add(
        makeButton(propertyManager.getText("StateNode.OnNextLabel"), this::onNext), 0,
        4);
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
  private void onInitialize(ActionEvent event) {
    nodeController.openAndSwitchToTab(
        new CodeEditorTab(nodeController, stateName.getText(), propertyManager.getText("StateNode.OnEnterLabel")));
  }

  /**
   * initializes a tab and switches to it for the on leave event
   *
   * @param event
   * @return void
   */
  private void onLeave(ActionEvent event) {
    nodeController.openAndSwitchToTab(
        new CodeEditorTab(nodeController, stateName.getText(), propertyManager.getText("StateNode.OnLeaveLabel")));
  }

  /**
   * initializes a tab and switches to it for the on set value event
   *
   * @param event
   * @return void
   */
  private void onSetValue(ActionEvent event) {
    nodeController.openAndSwitchToTab(
        new CodeEditorTab(nodeController, stateName.getText(), propertyManager.getText("StateNode.OnSetValueLabel")));
  }

  /**
   * initializes a tab and switches to it for the on next event
   *
   * @param event
   * @return void
   */
  private void onNext(ActionEvent event) {
    nodeController.openAndSwitchToTab(
        new CodeEditorTab(nodeController, stateName.getText(), propertyManager.getText("StateNode.OnNextLabel")));
  }
}
