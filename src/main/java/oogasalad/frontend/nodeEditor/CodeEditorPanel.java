package oogasalad.frontend.nodeEditor;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.Nodes.EndNestNode;
import oogasalad.frontend.nodeEditor.Nodes.JsonNode;
import oogasalad.frontend.nodeEditor.Nodes.StartNestNode;


public class CodeEditorPanel extends AbstractNodePanel {

  protected String state, action;
  private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/";


  public CodeEditorPanel(NodeController nodeController, String state, String action) {
    super(nodeController);
    this.state = state;
    this.action = action;
  }

  protected List<Button> getNodeSelectionButtons(String fileName) {
    String absoluteFilePath = System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH + fileName;
    ArrayList<Button> buttons = new ArrayList<>();
    JsonNodeParser parser = new JsonNodeParser();
    List<Command> commands = parser.readCommands(absoluteFilePath);
    for (Command command : commands) {
      Button button = new Button(command.name());
      Tooltip tip = new Tooltip(command.description());
      tip.setShowDelay(Duration.millis(0));
      Tooltip.install(button, tip);
      button.setOnAction(event -> {
        try {
          AbstractNode node = new JsonNode(command.name(), command.innerBlocks(),
              command.parseStr(), command.inputs());
          group.getChildren().add(node);
          node.setBoundingBox(workspace.getBoundsInParent());
          for (String nestBlock : command.innerBlocks()) {
            AbstractNode start = new StartNestNode();
            group.getChildren().add(start);
            start.setBoundingBox(workspace.getBoundsInParent());
            start.snapTo(node);
            AbstractNode end = new EndNestNode();
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
