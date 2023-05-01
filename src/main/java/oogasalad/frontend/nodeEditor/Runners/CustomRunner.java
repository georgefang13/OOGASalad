//package oogasalad.frontend.nodeEditor.Runners;
//
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.ScrollPane.ScrollBarPolicy;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//import oogasalad.frontend.nodeEditor.NodeController;
//import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;
//import oogasalad.frontend.nodeEditor.Nodes.JsonNode;
//import oogasalad.frontend.windows.NodeWindow;
//
///**
// * Scrolling/panning based on
// * https://stackoverflow.com/questions/61195436/javafx-pan-and-zoom-with-draggable-nodes-inside
// */
//
//public class CustomRunner extends Application {
//
//  public static final String NODES_FOLDER = "oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableNodes.";
//
//  private Group group;
//  private ImageView workspace;
//  private int buttonRow;
//  private ScrollPane myScrollBoy = new ScrollPane();
//  private GridPane nodeSelectionPane;
//
//  private static final String COMMANDS_RESOURCE_PATH = "/src/main/resources/backend/interpreter/Commands.json";
//
//  @Override
//  public void start(Stage primaryStage) {
//    NodeWindow nodeWindow = new NodeWindow();
//    NodeController nodeController = new NodeController(nodeWindow);
//    nodeWindow.close();
//    primaryStage.setResizable(false);
//    primaryStage.setWidth(1200);
//    primaryStage.setHeight(700);
//    primaryStage.setTitle("Node Editor");
//
//    nodeSelectionPane = new GridPane();
//    nodeSelectionPane.setStyle("-fx-background-color: gray");
//    nodeSelectionPane.setMinSize(primaryStage.getWidth() / 4, primaryStage.getHeight() / 4);
//
//
//    workspace = new ImageView(
//            new Image(getClass().getResourceAsStream("/frontend/images/GameEditor/grid.png")));
//    workspace.setFitWidth(5 * primaryStage.getWidth());
//    workspace.setFitHeight(5 * primaryStage.getHeight());
//    group = new Group(workspace);
//    double defaultXScale = 0.15;
//    double defaultYScale = 0.15;
//    group.setScaleX(defaultXScale);
//    group.setScaleY(defaultYScale);
//    StackPane content = new StackPane(new Group(group));
//    content.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
//    content.setOnScroll(e -> {
//      if (e.isShortcutDown() && e.getDeltaY() != 0) {
//        if (e.getDeltaY() < 0) {
//          group.setScaleX(Math.max(group.getScaleX() - 0.1, 0.15));
//        } else {
//          group.setScaleX(Math.min(group.getScaleX() + 0.1, 3.0));
//        }
//        group.setScaleY(group.getScaleX());
//        e.consume();
//      }
//    });
//
////    createNode("Sum", NODES_FOLDER + "SumNode");
////    createNode("Difference", NODES_FOLDER + "DifferenceNode");
////    createNode("TextField", NODES_FOLDER + "TextFieldNode");
//
//    Button sendButton = new Button("Submit");
//    sendButton.setOnAction(event -> {
//      System.out.println(sendAllNodeContent());
//    });
//
//    ScrollPane scrollPane = new ScrollPane(content);
//    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
//    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
//    scrollPane.setPannable(true);
//    scrollPane.setFitToWidth(true);
//    scrollPane.setFitToHeight(true);
//    scrollPane.setMaxWidth(850);
//    myScrollBoy.setContent(nodeSelectionPane);
//    primaryStage.setScene(new Scene(new HBox(myScrollBoy, scrollPane)));
//    primaryStage.show();
//    initFromFile(nodeController);
//
//  }
//
//  private void initFromFile(NodeController nodeController){
//    String absoluteFilePath = System.getProperty("user.dir") + COMMANDS_RESOURCE_PATH;
//
//    String fileContent = "";
//    // Read the entire file content
//    try {
//      fileContent = Files.readString(Paths.get(absoluteFilePath));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    JsonElement json = JsonParser.parseString(fileContent);
//
//    JsonObject obj = json.getAsJsonObject();
//
//    for (String key : obj.keySet()) {
//      JsonObject value = (JsonObject) obj.get(key);
//      String name = value.get("name").getAsString();
//      JsonObject specs = value.get("specs").getAsJsonObject();
//      JsonArray innerBlocks = specs.get("innerBlocks").getAsJsonArray();
//      JsonArray outputTypes = specs.get("outputs").getAsJsonArray();
//      String parseStr = specs.get("parse").getAsString();
//      JsonArray inputs = specs.get("inputs").getAsJsonArray();
//      for (JsonElement inp : inputs) {
//        JsonObject inpObj = inp.getAsJsonObject();
//        String inpName = inpObj.get("name").getAsString();
//        JsonArray inpTypes = inpObj.get("type").getAsJsonArray();
//      }
//      Button button = new Button(name);
//      button.setOnAction(event -> {
//        try {
//            AbstractNode node = new JsonNode(name, innerBlocks, outputTypes, parseStr, inputs);
//            group.getChildren().add(node);
//            node.setBoundingBox(workspace.getBoundsInParent());
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      });
//
//      button.setMaxWidth(Double.MAX_VALUE);
//      GridPane.setHgrow(button, Priority.ALWAYS);
//      nodeSelectionPane.add(button, 0, buttonRow);
//      buttonRow += 1;
//    }
//  }
//
//  private void createNode(String buttonName, String className) {
//    try {
//      Class<?> clazz = Class.forName(className);
//      Constructor<?> constructor = clazz.getConstructor();
//      Button button = new Button(buttonName);
//      button.setMaxWidth(Double.MAX_VALUE);
//      GridPane.setHgrow(button, Priority.ALWAYS);
//      button.setOnAction(event -> {
//        try {
//          DraggableAbstractNode node = (DraggableAbstractNode) constructor.newInstance();
//          group.getChildren().add(node);
//          node.setBoundingBox(workspace.getBoundsInParent());
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//          e.printStackTrace();
//        }
//      });
//      nodeSelectionPane.add(button, 0, buttonRow);
//      buttonRow += 1;
//    } catch (ClassNotFoundException | NoSuchMethodException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public String sendAllNodeContent() {
//    String returnable = "";
////    for (Node node: group.getChildren()) {
////    for (Node node: gameColumn.getChildren()) {
//    Node node = group.getChildren().get(1);
//    System.out.println(node);
//    if (node instanceof AbstractNode) {
////      returnable += ((AbstractNode) node).sendContent();
//      returnable += "\n";
//    }
////      }
//    return returnable;
//  }
//
//
//}