package oogasalad.frontend.nodeEditor.customNodeEditor.Runners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes.StateNode;

/**
 * Scrolling/panning based on
 * https://stackoverflow.com/questions/61195436/javafx-pan-and-zoom-with-draggable-nodes-inside
 */

public class NodeExperiment extends Application {

  public static final String NODES_FOLDER = "oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.";

  private Group group;
  private ImageView workspace;
  private int buttonRow;
  private GridPane nodeSelectionPane;


  @Override
  public void start(Stage primaryStage) {
    primaryStage.setResizable(false);
    primaryStage.setWidth(1200);
    primaryStage.setHeight(700);
    primaryStage.setTitle("Node Editor");

    nodeSelectionPane = new GridPane();
    nodeSelectionPane.setStyle("-fx-background-color: gray");
    nodeSelectionPane.setMinSize(primaryStage.getWidth() / 4, primaryStage.getHeight() / 4);

    workspace = new ImageView(
        new Image(getClass().getResourceAsStream("/frontend/images/GameEditor/grid.png")));
    workspace.setFitWidth(5 * primaryStage.getWidth());
    workspace.setFitHeight(5 * primaryStage.getHeight());
    group = new Group(workspace);
    double defaultXScale = 0.15;
    double defaultYScale = 0.15;
    group.setScaleX(defaultXScale);
    group.setScaleY(defaultYScale);
    StackPane content = new StackPane(new Group(group));
    content.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    content.setOnScroll(e -> {
      if (e.isShortcutDown() && e.getDeltaY() != 0) {
        if (e.getDeltaY() < 0) {
          group.setScaleX(Math.max(group.getScaleX() - 0.1, 0.15));
        } else {
          group.setScaleX(Math.min(group.getScaleX() + 0.1, 3.0));
        }
        group.setScaleY(group.getScaleX());
        e.consume();
      }
    });

    createNode("State", NODES_FOLDER + "DraggableNodes.StateNode");
    createNode("Sum", NODES_FOLDER + "DraggableNodes.SumNode");
    createNode("Difference", NODES_FOLDER + "DraggableNodes.DifferenceNode");
    createNode("TextField", NODES_FOLDER + "DraggableNodes.TextFieldNode");

    Button sendButton = new Button("Submit");
    sendButton.setOnAction(event -> {
      System.out.println(sendAllNodeContent());
    });

    nodeSelectionPane.add(sendButton, 0, buttonRow);

    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setPannable(true);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    primaryStage.setScene(new Scene(new HBox(nodeSelectionPane, scrollPane)));
    primaryStage.show();

  }

  private void createNode(String buttonName, String className) {
    try {
      Class<?> clazz = Class.forName(className);
      Constructor<?> constructor = clazz.getConstructor();
      Button button = new Button(buttonName);
      button.setMaxWidth(Double.MAX_VALUE);
      GridPane.setHgrow(button, Priority.ALWAYS);
      button.setOnAction(event -> {
        try {
          DraggableAbstractNode node = (DraggableAbstractNode) constructor.newInstance();
          group.getChildren().add(node);
          node.setBoundingBox(workspace.getBoundsInParent());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      });
      nodeSelectionPane.add(button, 0, buttonRow);
      buttonRow += 1;
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  public String sendAllNodeContent() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonObject statesObject = new JsonObject();
    for (Node node : group.getChildren()) {
      if (node instanceof StateNode) {
        StateNode stateNode = (StateNode) node;
        JsonObject stateObject = gson.fromJson(stateNode.sendJSONContent(), JsonObject.class);
        String stateName = stateObject.keySet().iterator().next();
        statesObject.add(stateName, stateObject.get(stateName));
      }
    }
    JsonObject contentObject = new JsonObject();
    contentObject.add("states", statesObject);
    return gson.toJson(contentObject);
  }
}