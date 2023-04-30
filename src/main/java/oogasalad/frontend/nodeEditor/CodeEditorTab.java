package oogasalad.frontend.nodeEditor;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.nodes.EndNestNode;
import oogasalad.frontend.nodeEditor.nodes.JsonNode;
import oogasalad.frontend.nodeEditor.nodes.StartNestNode;


public class CodeEditorTab extends AbstractNodeEditorTab {
  protected Group group;
  protected ImageView workspace;

  protected String state, action;
  private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/";


  public CodeEditorTab(NodeController nodeController, String state, String action) {
    super(nodeController);
    this.state = state;
    this.action = action;
  }

  protected List<Button> getNodeButtons() {
    //"Commands.json"
    //temp.addAll(getNodeButtons("Metablocks.json"));
    String absoluteFilePath = System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH + "Commands.json";
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
            start.snapToNode(node);
            AbstractNode end = new EndNestNode();
            group.getChildren().add(end);
            end.setBoundingBox(workspace.getBoundsInParent());
            end.snapToNode(start);
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

  public boolean equals(CodeEditorTab panel) {
    return panel.getAction().equals(this.action) && panel.getState().equals(this.state);
  }
}
