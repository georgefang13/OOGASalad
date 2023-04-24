package oogasalad.frontend.nodeEditorNodes.DraggableNodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;

public class StateNode extends DraggableAbstractNode {

  private TextField stateName;
  private GridPane buttonGrid;

  public StateNode(NodeController nodeController) {
    super(nodeController, 0, 0, 300, 100, "orange");
  }

  public StateNode(NodeController nodeController, double x, double y, double width, double height,
      String color) {
    super(nodeController, x, y, width, height, color);
  }

  @Override
  protected void setContent() {
    buttonGrid = new GridPane();
    buttonGrid.setHgap(10);
    buttonGrid.setPadding(new Insets(30));
    this.getChildren().add(buttonGrid);

    stateName = new TextField();
    stateName.setPromptText("Name");
    buttonGrid.add(stateName, 0, 0);
    buttonGrid.add(makeButton("on enter", this::onInitialize), 0, 1);
    buttonGrid.add(makeButton("on leave", this::onLeave), 0, 2);
    buttonGrid.add(makeButton("select", this::onSetValue), 0, 3);
    buttonGrid.add(makeButton("next", this::onNext), 0, 4);
  }

  @Override
  public String getJSONString() {
    return null;
  }

  private Button makeButton(String label, EventHandler<ActionEvent> handler) {
    Button button = new Button(label);
    button.setOnAction(handler);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    return button;
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
