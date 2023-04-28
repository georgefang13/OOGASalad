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
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.EndNestNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.JsonNode;
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
    List<Button> buttons = new ArrayList<>();
    JsonNodeParser parser = new JsonNodeParser();
    List<Command> commands = parser.readCommands(absoluteFilePath);
    for (Command command : commands) {
      Button button = new Button(command.name());
      Tooltip tip = new Tooltip(command.description());
      Tooltip.install(button, tip);
      button.setOnAction(event -> {
        try {
          AbstractNode node = new JsonNode(command.name(), command.innerBlocks(),
              command.outputTypes(), command.parseStr(), command.inputs());
          group.getChildren().add(node);
          node.setBoundingBox(workspace.getBoundsInParent());
          for (String nestBlock : command.innerBlocks()) {
            AbstractNode start = new StartNestNode(0, 0, node.getWidth(),
                node.getHeight(), "green");
            group.getChildren().add(start);
            start.setBoundingBox(workspace.getBoundsInParent());
            start.snapTo(node);
            AbstractNode end = new EndNestNode(0, 0, node.getWidth(),
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
