//package oogasalad.frontend.nodeEditor;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.control.ScrollPane.ScrollBarPolicy;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.StackPane;
//import oogasalad.frontend.managers.PropertyManager;
//import oogasalad.frontend.managers.StandardPropertyManager;
//import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
//import oogasalad.frontend.nodeEditor.nodes.MainNode;
//
//public abstract class
//AbstractNodePanel extends Tab {
//
//  public static final String NODES_FOLDER = "oogasalad.frontend.nodeEditor.nodes.";
//  public static final String NODES_JSON_PATH = "src/main/resources/nodeCode/save.json";
//  public static final String CONFIG_JSON_PATH = "src/main/resources/nodeCode/config.json";
//
//  protected Group group;
//  protected ImageView workspace;
//  protected double windowWidth, windowHeight;
//  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();
//
//  protected NodeController nodeController;
//
//  public AbstractNodePanel(NodeController nodeController) {
//    this.nodeController = nodeController;
//    windowWidth = propertyManager.getNumeric("WindowWidth");
//    windowHeight = propertyManager.getNumeric("WindowHeight");
//  }
//
//  /**
//   * Returns a list of buttons that can be used to create nodes
//   * @param fileName
//   * @return List<Button>
//   */
//  protected abstract List<Button> getNodeSelectionButtons(String fileName);
//
//
//  /**
//   * Returns the string that will be used to parse the nodes in the interpreter
//   * Finds the MainNode and calls getNodeParseString on it which will recursively call the method on all of its children
//   * @return String
//   */
//  public String getAllNodeContent() {
//    String code = "";
//    for (Node node : group.getChildren()) {
//      if (node instanceof MainNode) {
//        code += (((AbstractNode) node).getNodeParseString());
//      }
//    }
//    return code;
//  }
//
//  /**
//   * Returns a list of all of the nodes that are children of the MainNode
//   * @return List<AbstractNode>
//   */
//  public List<AbstractNode> getAllNodes() {
//    List<AbstractNode> nodes = new ArrayList<>();
//    for (Node node : group.getChildren()) {
//      if (node instanceof MainNode) {
//        AbstractNode tempNode = (AbstractNode) node;
//        while (tempNode.getChildNode() != null) {
//          nodes.add(tempNode);
//          tempNode = tempNode.getChildNode();
//        }
//        nodes.add(tempNode);
//        return nodes;
//      }
//    }
//    return nodes;
//  }
//
//
//  /**
//   * Returns a button with the given name and handler
//   * @param buttonName
//   * @param handler
//   * @return Button
//   */
//  protected Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
//    Button button = new Button(buttonName);
//    button.setOnAction(handler);
//    button.setMaxWidth(Double.MAX_VALUE);
//    GridPane.setHgrow(button, Priority.ALWAYS);
//    return button;
//  }
//
//  /**
//   * Uses reflection to create a node with the given class name
//   * @param className
//   */
//  protected void makeNode(String className) {
//    try {
//      Class<?> clazz = Class.forName(className);
//      Constructor<?> constructor = clazz.getConstructor(NodeController.class);
//      AbstractNode node = (AbstractNode) constructor.newInstance(nodeController);
//      putNode(node);
//    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
//             IllegalAccessException | InvocationTargetException e) {
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   * Adds the given node to the group
//   * @param node
//   * @return void
//   */
//  protected void putNode(AbstractNode node) {
//    group.getChildren().add(node);
//    node.setBoundingBox((workspace.getBoundsInParent()));
//  }
//
//  /**
//   * Removes all of the nodes from the group
//   * @return void
//   */
//  protected void clearNodes() {
//    List<AbstractNode> nodesToRemove = new ArrayList<>();
//    for (Node node : group.getChildren()) {
//      if (node instanceof AbstractNode) {
//        nodesToRemove.add((AbstractNode) node);
//      }
//    }
//    for (AbstractNode node : nodesToRemove) {
//      node.delete();
//    }
//  }
//
//  /**
//   * Returns a scroll pane with all of the buttons that can be used to create nodes
//   * @return ScrollPane
//   */
//  public ScrollPane makeNodeSelectionPane() {
//    List<Button> buttons = getNodeSelectionButtons("Commands.json");
//    ArrayList<Button> temp = new ArrayList<>(buttons);
//    temp.addAll(getNodeSelectionButtons("Metablocks.json"));
//
//    ScrollPane scrollPane = new ScrollPane();
//    GridPane pane = new GridPane();
//    pane.setStyle("-fx-background-color: gray"); // @TODO: move to css
//    for (Button button : temp) {
//      pane.add(button, 0, temp.indexOf(button));
//    }
//    pane.setMinSize(windowWidth / 4, windowHeight);
//    if (this instanceof CodeEditorPanl){
//      scrollPane.setContent(getAccordianFinished("Commands.json"));
//    }else {
//      scrollPane.setContent(pane);
//    }
//    scrollPane.setMinSize(windowWidth / 4, windowHeight);
//    return scrollPane;
//  }
//
//
//  /**
//   * Returns an accordion with all of the buttons that can be used to create nodes
//   * @return Accordion
//   */
//  public Accordion getAccordion(){
//    return null;
//  }
//
//  /**
//   * Returns an accordion with all of the buttons that can be used to create nodes
//   * @return Accordion
//   */
//  public Accordion getAccordianFinished(String fileName) {
//    return null;
//  }
//
//
//  /**
//   * Returns a BorderPane with the nodeEditor workspace
//   * @return BorderPane
//   */
//  public BorderPane makeWorkspacePane() {
//    double defaultXScale = 0.15;
//    double defaultYScale = 0.15;
//    workspace = new ImageView(
//        new Image(getClass().getResourceAsStream("/frontend/images/GameEditor/grid.png")));
//    group = new Group(workspace);
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
//    ScrollPane scrollPane = new ScrollPane(content);
//    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
//    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
//    scrollPane.setPannable(true);
//    scrollPane.setFitToWidth(true);
//    scrollPane.setFitToHeight(true);
//    workspace.setFitWidth(5 * windowWidth);
//    workspace.setFitHeight(5 * windowHeight);
//
//    MainNode node = new MainNode();
//    group.getChildren().add(node);
//    node.setBoundingBox(workspace.getBoundsInParent());
//
//    BorderPane borderPane = new BorderPane();
//    MenuBar menuBar = new MenuBar();
//    Menu fileMenu = new Menu("File");
//    MenuItem saveMenuItem = new MenuItem("Save");
//    MenuItem loadMenuItem = new MenuItem("Load");
//    fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);
//    menuBar.getMenus().add(fileMenu);
//    borderPane.setCenter(scrollPane);
//    borderPane.setTop(menuBar);
//    saveMenuItem.setOnAction(event -> nodeController.saveAllContent(NODES_JSON_PATH));
//    loadMenuItem.setOnAction(event -> nodeController.loadAllContent("src/main/resources/nodeCode/config.json"));
//    return borderPane;
//  }
//
//
//}
