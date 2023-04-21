package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import oogasalad.frontend.nodeEditor.customNodeEditor.NodeController;

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
    buttonGrid.add(createButton("Initialize", this::onInitialize), 0, 1);
    buttonGrid.add(createButton("Leave", this::onLeave), 0, 2);
    buttonGrid.add(createButton("Set value", this::onSetValue), 0, 3);
    buttonGrid.add(createButton("Next", this::onNext), 0, 4);
  }

  private Button createButton(String label, EventHandler<ActionEvent> handler) {
    Button button = new Button(label);
    button.setOnAction(handler);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    return button;
  }

  private void onInitialize(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText() + ": Initialize");
  }

  private void onLeave(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText() + ": Leave");
  }

  private void onSetValue(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText() + ": Set value");
  }

  private void onNext(ActionEvent event) {
    nodeController.openAndSwitchToTab(stateName.getText() + ": Next");
  }


  @Override
  public String sendContent() {
    return "";
  }

  public JsonObject sendJSONContent() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject moveObject = new JsonObject();
    //moveObject.addProperty("init", init.getText());
    //moveObject.addProperty("leave", leave.getText());
    //moveObject.addProperty("setValue", setValue.getText());
    //moveObject.addProperty("to", to.getText());
    JsonObject contentObject = new JsonObject();
    contentObject.add(stateName.getText(), moveObject);
    return contentObject;
  }
}
