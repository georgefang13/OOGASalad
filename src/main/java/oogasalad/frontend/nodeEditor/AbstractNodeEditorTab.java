package oogasalad.frontend.nodeEditor;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.nodes.MainNode;

public abstract class AbstractNodeEditorTab extends Tab {

  public static final String NODES_FOLDER = "oogasalad.frontend.nodeEditor.nodes.";
  public static final String NODE_EDITOR_FILES_PATH = "src/main/resources/nodeEditorFiles/";
  public static final String INTERPRETER_FILES_PATH = NODE_EDITOR_FILES_PATH + "interpreter/";
  public static final String USER_CODE_FILES_PATH = NODE_EDITOR_FILES_PATH + "userCode/";

  protected Group nodeGroup;
  protected Rectangle background;
  protected StackPane nodeGroupStackPane;
  protected ScrollPane nodeWorkspace;
  protected double windowWidth, windowHeight;

  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  protected NodeController nodeController;

  public AbstractNodeEditorTab(NodeController nodeController) {
    this.nodeController = nodeController;
    windowWidth = propertyManager.getNumeric("WindowWidth");
    windowHeight = propertyManager.getNumeric("WindowHeight");
    setContent(new HBox(makeNodeButtonPanel(), makeWorkspacePanel()));
  }

  protected abstract List<Button> getNodeButtons();

  public String getMainNodeParseString() {
    return getMainNode().getNodeParseString();
  }

  public List<AbstractNode> getMainNodeChildren() {
    return getChildrenNodes(getMainNode());
  }


  protected void addNode(AbstractNode node) {
    nodeGroup.getChildren().add(node);
    node.setBoundingBox((background.getBoundsInParent()));
  }

  protected void clearAllNodes() {
    for (Node node : nodeGroup.getChildren()) {
      if (node instanceof AbstractNode) {
        ((AbstractNode) node).delete();
      }
    }
  }

  protected Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button = new Button(buttonName);
    button.setOnAction(handler);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    return button;
  }

  public ScrollPane makeNodeButtonPanel() {
    ScrollPane scrollPane = new ScrollPane();
    GridPane pane = new GridPane();
    pane.setMinSize(windowWidth * 0.25, windowHeight);
    List<Button> buttons = getNodeButtons();
    for (Button button : buttons) {
      pane.add(button, 0, buttons.indexOf(button));
    }
    scrollPane.setMinSize(windowWidth * 0.25, windowHeight);
    scrollPane.setContent(pane);
    return scrollPane;
  }


  public BorderPane makeWorkspacePanel() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(makeMenuBar());
    borderPane.setCenter(makeWorkspace());
    addNode(new MainNode());
    return borderPane;
  }

  private MenuBar makeMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    MenuItem saveMenuItem = new MenuItem("Save");
    MenuItem loadMenuItem = new MenuItem("Load");
    saveMenuItem.setOnAction(event -> {
      //nodeController.saveInterpreterFiles();
      //nodeController.saveUserCodeFiles();
    });
    loadMenuItem.setOnAction(event -> {
      //nodeController.loadAllContent();
    });
    fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);
    menuBar.getMenus().add(fileMenu);
    return menuBar;
  }

  private ScrollPane makeWorkspace() {
    double defaultScale = propertyManager.getNumeric("AbstractNodeEdtiorTab.DefaultScale");
    double maxScale = propertyManager.getNumeric("AbstractNodeEdtiorTab.MaxScale");
    double step = propertyManager.getNumeric("AbstractNodeEdtiorTab.ZoomStep");
    background = new Rectangle(0.75 * windowWidth, windowHeight, Color.LIGHTGRAY);
    nodeGroup = new Group(background);
    nodeGroup.setScaleX(defaultScale);
    nodeGroup.setScaleY(nodeGroup.getScaleX());
    nodeGroupStackPane = new StackPane(new Group(nodeGroup));
    nodeGroupStackPane.setOnScroll(event -> zoomOnScroll(event, defaultScale, maxScale, step));
    nodeWorkspace = new ScrollPane(nodeGroupStackPane);
    nodeWorkspace.setHbarPolicy(ScrollBarPolicy.NEVER);
    nodeWorkspace.setPannable(true);
    nodeWorkspace.setMinSize(0.75 * windowWidth, windowHeight);
    nodeWorkspace.setOnScroll(event -> verticallyExtendNodeWorkspace(event, 50));
    return nodeWorkspace;
  }

  private void zoomOnScroll(ScrollEvent event, double defaultScale, double maxScale, double step) {
    {
      if (event.isShortcutDown() && event.getDeltaY() != 0) {
        if (event.getDeltaY() < 0) {
          nodeGroup.setScaleX(Math.max(nodeGroup.getScaleX() - step, defaultScale));
        } else {
          nodeGroup.setScaleX(Math.min(nodeGroup.getScaleX() + step, maxScale));
        }
        nodeGroup.setScaleY(nodeGroup.getScaleX());
        event.consume();
      }
    }
  }

  private void verticallyExtendNodeWorkspace(ScrollEvent event, double extendSize) {
    double deltaY = event.getDeltaY();
    double contentHeight = nodeGroupStackPane.getBoundsInLocal().getHeight();
    double viewportHeight = nodeWorkspace.getViewportBounds().getHeight();
    double vvalue = nodeWorkspace.getVvalue();
    double newVvalue = vvalue - deltaY / contentHeight * viewportHeight;
    nodeWorkspace.setVvalue(newVvalue);
    if (newVvalue >= 1.0 && nodeWorkspace.getPrefHeight() < Double.MAX_VALUE) {
      nodeWorkspace.setPrefHeight(nodeWorkspace.getPrefHeight() + extendSize);
      background.setHeight(background.getHeight() + extendSize);
      updateBoundingBoxAllNodes();
    }
    event.consume();
  }

  private void updateBoundingBoxAllNodes() {
    for (Node node : nodeGroup.getChildren()) {
      if (node instanceof AbstractNode) {
        AbstractNode abstractNode = (AbstractNode) node;
        abstractNode.setBoundingBox(background.getBoundsInParent());
      }
    }
  }

  private AbstractNode getMainNode() {
    for (Node node : nodeGroup.getChildren()) {
      if (node instanceof MainNode) {
        return (AbstractNode) node;
      }
    }
    return null;
  }

  private List<AbstractNode> getChildrenNodes(AbstractNode parentNode) {
    List<AbstractNode> nodes = new ArrayList<>();
    AbstractNode tempNode = parentNode;
    while (tempNode != null) {
      nodes.add(tempNode);
      tempNode = tempNode.getChildNode();
    }
    return nodes;
  }


}
