package oogasalad.frontend.nodeEditor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.nodes.EndNestNode;
import oogasalad.frontend.nodeEditor.nodes.JsonNode;
import oogasalad.frontend.nodeEditor.nodes.StartNestNode;


public class CodeEditorPanel extends AbstractNodePanel {

    protected String state, action;
    private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/";

    private Accordion accordion = new Accordion();
    private List<TitledPane> buttonFolders = new ArrayList<>();
    private Set<String> folderNames = new HashSet<>();


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
            Button button = makeButton(command);

            button.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(button, Priority.ALWAYS);

            buttons.add(button);
        }
        return buttons;
    }

    @Override
    public Accordion getAccordianFinished(String fileName) {
        String absoluteFilePath = System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH + fileName;
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
                    ((GridPane) folder.getContent()).add(button, 0, ((GridPane) folder.getContent()).getChildren().size());
                }
            }


        }
        for (TitledPane folder : buttonFolders) {
            if (!accordion.getPanes().contains(folder)) {
                accordion.getPanes().add(folder);
            }
        }

        return accordion;
    }

    public Accordion getAccordion() {
        return accordion;
    }

    private Button makeButton(Command command) {
        Button button = new Button(command.name());
        String folderName = command.folder();
//        folderNames.add(folderName);
        Tooltip tip = new Tooltip(command.description());
        tip.setShowDelay(Duration.millis(0));
        Tooltip.install(button, tip);
        setButtonActionFromCommand(command, button);
        return button;
    }

    private void setButtonActionFromCommand(Command command, Button button) {
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
