package oogasalad.frontend.nodeEditor.nodes;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import oogasalad.frontend.nodeEditor.NodeController;

import java.util.ArrayList;

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

  @Override
  public String getNodeParseString() {
    return "";
  }

  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }


  private void onInitialize(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText(), "on enter");
  }

  private void onLeave(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText(), "on leave");
  }

  private void onSetValue(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText(), "select");
  }

  private void onNext(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText(), "next");
  }
}
