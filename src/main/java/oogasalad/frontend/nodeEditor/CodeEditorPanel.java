package oogasalad.frontend.nodeEditor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.EndNestNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.FileBasedNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.StartNestNode;

public class CodeEditorPanel extends AbstractNodePanel {

  protected String state, action;
  private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/Commands.json";


  public CodeEditorPanel(NodeController nodeController, String state, String action) {
    super(nodeController);
    this.state = state;
    this.action = action;
  }

  protected List<Button> getNodeSelectionButtons() {
    String absoluteFilePath = System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH;
    ArrayList<Button> buttons = new ArrayList<>();

    String fileContent = "";
    try {
      fileContent = Files.readString(Paths.get(absoluteFilePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
    JsonElement json = JsonParser.parseString(fileContent);
    JsonObject obj = json.getAsJsonObject();
    for (String key : obj.keySet()) {
      JsonObject value = (JsonObject) obj.get(key);
      String name = value.get("name").getAsString();
      JsonObject specs = value.get("specs").getAsJsonObject();
      String description = value.get("description").getAsString();
      JsonArray innerBlocks = specs.get("innerBlocks").getAsJsonArray();
      JsonArray outputTypes = specs.get("outputs").getAsJsonArray();
      String parseStr = specs.get("parse").getAsString();
      JsonArray inputs = specs.get("inputs").getAsJsonArray();
      for (JsonElement inp : inputs) {
        JsonObject inpObj = inp.getAsJsonObject();
        String inpName = inpObj.get("name").getAsString();
        JsonArray inpTypes = inpObj.get("type").getAsJsonArray();
      }
      Button button = new Button(name);
      Tooltip tip = new Tooltip(description);
      Tooltip.install(button, tip);
      button.setOnAction(event -> {
        try {
          DraggableAbstractNode node = new FileBasedNode(nodeController, name, innerBlocks,
              outputTypes, parseStr, inputs);
          group.getChildren().add(node);
          node.setBoundingBox(workspace.getBoundsInParent());
          for (JsonElement nestBlock : innerBlocks.asList()) {
            DraggableAbstractNode start = new StartNestNode(nodeController, 0, 0, node.getWidth(),
                node.getHeight(), "green");
            group.getChildren().add(start);
            start.setBoundingBox(workspace.getBoundsInParent());
            start.snapTo(node);
            DraggableAbstractNode end = new EndNestNode(nodeController, 0, 0, node.getWidth(),
                node.getHeight(), "red");
            group.getChildren().add(end);
            end.setBoundingBox(workspace.getBoundsInParent());
            end.snapTo(start);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
      button.setMaxWidth(Double.MAX_VALUE);
      GridPane.setHgrow(button, Priority.ALWAYS);
      buttons.add(button);
    }
    return buttons;
  }

  public String getAction() {
    return action;
  }

  public String getState() {
    return state;
  }

  public boolean equals(CodeEditorPanel panel) {
    return panel.getAction().equals(this.action) && panel.getState().equals(this.state);
  }
}
