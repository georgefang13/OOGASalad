package oogasalad.frontend.nodeEditor.tabs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import oogasalad.frontend.nodeEditor.Command;
import oogasalad.frontend.nodeEditor.JsonNodeParser;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.nodes.EndNestNode;
import oogasalad.frontend.nodeEditor.nodes.JsonNode;
import oogasalad.frontend.nodeEditor.nodes.StartNestNode;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class CodeEditorTab extends AbstractNodeEditorTab {

  protected String state, action;
  private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/";

  private Accordion accordion = new Accordion();
  private List<TitledPane> buttonFolders;
  private Set<String> folderNames;

  public CodeEditorTab(NodeController nodeController, String state, String action) {
    super(nodeController);
    this.state = state;
    this.action = action;
    buttonFolders = new ArrayList<>();
    folderNames = new HashSet<>();
    setContent(new HBox(makeNodeButtonPanel(), makeWorkspacePanel()));
  }

  public CodeEditorTab(NodeController nodeController){
    super(nodeController);
    buttonFolders = new ArrayList<>();
    folderNames = new HashSet<>();
    setContent(new HBox(makeNodeButtonPanel(), makeWorkspacePanel()));

  }


  /**
   * Returns a list of buttons that can be used to create nodes
   *
   * @return List<Button>
   */
  protected List<Button> getNodeButtons(List<String> filenames) {
    ArrayList<Button> buttons = new ArrayList<>();

    for (String filename: filenames) {

      String absoluteFilePath =
          System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH + filename + ".json";
      JsonNodeParser parser = new JsonNodeParser();
      List<Command> commands = parser.readCommands(absoluteFilePath);
      for (Command command : commands) {
        Button button = makeButton(command);

        button.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(button, Priority.ALWAYS);
        buttons.add(button);
      }
    }
    return buttons;
  }

  /**
   * Creates an accordion that contains all of the buttons that can be used to create nodes Acts as
   * a container of folders that contain different types of nodes so that they're easier to find
   *
   * @param
   * @return Accordion
   */
  @Override
  public Accordion getAccordianFinished(List<String> fileNames) {
    for (String fileName: fileNames) {

      String absoluteFilePath =
          System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH + fileName + ".json";
      ArrayList<Button> buttons = new ArrayList<>();
      JsonNodeParser parser = new JsonNodeParser();
      List<Command> commands = parser.readCommands(absoluteFilePath);
      folderNames.clear();
      for (Command command : commands) {
        Button button = makeButton(command);
        if (!folderNames.contains(command.folder())) {
          TitledPane folder = new TitledPane(command.folder(), new GridPane());
          folder.setAnimated(true);
          folder.setCollapsible(true);
          folder.setMaxWidth(Double.MAX_VALUE);
          GridPane.setHgrow(folder, Priority.ALWAYS);
          buttonFolders.add(folder);
          accordion.getPanes().add(folder);
          folderNames.add(command.folder());
        }

        button.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(button, Priority.ALWAYS);

        for (TitledPane folder : buttonFolders) {
          if (folder.getText().equals(command.folder())) {
            ((GridPane) folder.getContent()).add(button, 0,
                ((GridPane) folder.getContent()).getChildren().size());
          }
        }


      }
      for (TitledPane folder : buttonFolders) {
        if (!accordion.getPanes().contains(folder)) {
          accordion.getPanes().add(folder);
        }
      }
    }

    return accordion;
  }

  /**
   * Returns the accordion for this panel
   */
  public Accordion getAccordion() {
    return accordion;
  }

  /**
   * Creates a button that can be used to create a node
   *
   * @param command
   * @return Button
   */
  private Button makeButton(Command command) {
    Button button = new Button(command.name());
    Tooltip tip = new Tooltip(command.description());
    tip.setShowDelay(Duration.millis(50));
    Tooltip.install(button, tip);
    setButtonActionFromCommand(command, button);
    return button;
  }

  /**
   * Sets the action of the button to create a node from a command record
   *
   * @param command
   * @param button
   * @return void
   */
  private void setButtonActionFromCommand(Command command, Button button) {
    button.setOnAction(event -> {
      try {
        AbstractNode node = new JsonNode(command.name(), command.innerBlocks(),
            command.parseStr(), command.inputs());
        addNode(node);
        for (String nestBlock : command.innerBlocks()) {
          AbstractNode start = new StartNestNode();
          addNode(start);
          start.snapToNode(node);
          AbstractNode end = new EndNestNode();
          addNode(end);
          end.snapToNode(start);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Returns the fsm action this panel is for
   *
   * @return String
   */
  public String getAction() {
    return action;
  }

  /**
   * Returns the fsm state this panel is for
   *
   * @return String
   */
  public String getState() {
    return state;
  }

  /**
   * Returns whether or not this panel is for the same fsm state and action as the given panel
   *
   * @param panel
   * @return boolean
   */
  public boolean equals(CodeEditorTab panel) {
    return panel.getAction().equals(this.action) && panel.getState().equals(this.state);
  }
}
