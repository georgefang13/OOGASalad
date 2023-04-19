package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;

public class StateNode extends DraggableAbstractNode {

  private TextField stateName, init, leave, setValue, to;

  public StateNode() {
    super(0, 0, 300, 100, "orange");
  }

  public StateNode(double x, double y, double width, double height, String color) {
    super(x, y, width, height, color);
  }

  @Override
  protected void setContent() {
    stateName = new TextField();
    stateName.setPromptText("Name");
    init = new TextField();
    init.setPromptText("Initialize");
    leave = new TextField();
    leave.setPromptText("Leave");
    setValue = new TextField();
    setValue.setPromptText("Set value");
    to = new TextField();
    to.setPromptText("Next");
    setPadding(new Insets(30));
    this.getChildren().addAll(stateName, init, leave, setValue, to);
  }

  @Override
  public String sendContent() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject moveObject = new JsonObject();
    moveObject.addProperty("init", init.getText());
    moveObject.addProperty("leave", leave.getText());
    moveObject.addProperty("setValue", setValue.getText());
    moveObject.addProperty("to", to.getText());
    JsonObject contentObject = new JsonObject();
    contentObject.add("MOVE", moveObject);
    return gson.toJson(contentObject);
  }
}
