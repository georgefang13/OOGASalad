package oogasalad.frontend.nodeEditor.tabs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.nodes.AbstractNode;
import oogasalad.frontend.nodeEditor.nodes.MainNode;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */

public abstract class AbstractNodeEditorTab extends Tab {

  public static final String NODES_FOLDER = "oogasalad.frontend.nodeEditor.nodes.";
  public static final String NODE_EDITOR_FILES_PATH = "src/main/resources/nodeEditorFiles/";
  public static final String INTERPRETER_FILES_PATH = NODE_EDITOR_FILES_PATH + "interpreter/";
  public static final String USER_CODE_FILES_PATH = NODE_EDITOR_FILES_PATH + "userCode/";
  public static final List<String> FILENAMES = new ArrayList<>(List.of("Commands", "Metablocks"));

  protected Group nodeGroup;
  protected Rectangle background;
  protected StackPane nodeGroupStackPane;
  protected ScrollPane nodeWorkspace;
  protected double windowWidth, windowHeight;

  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  protected NodeController nodeController;

  protected double panelSizeRatio;

  public AbstractNodeEditorTab(NodeController nodeController) {
    this.nodeController = nodeController;
    windowWidth = propertyManager.getNumeric("WindowWidth");
    windowHeight = propertyManager.getNumeric("WindowHeight");
    panelSizeRatio = propertyManager.getNumeric("AbstractNodeEditorTab.PanelSizeRatio");
  }

  /**
   * Returns a list of buttons that can be used to create nodes
   *
   * @return List<Button>
   */
  protected abstract List<Button> getNodeButtons(List<String> filenames);

  /**
   * Returns the string that will be used to parse the nodes in the interpreter Finds the MainNode
   * and calls getNodeParseString on it which will recursively call the method on all of its
   * children
   *
   * @return String
   */
  public String getMainNodeParseString() {
    return getMainNode().getNodeParseString();
  }

  /**
   * Returns a list of all of the nodes that are children of the MainNode
   *
   * @return List<AbstractNode>
   */
  public List<AbstractNode> getMainNodeChildren() {
    return getChildrenNodes(getMainNode());
  }


  /**
   * Adds the given node to the group
   *
   * @param node
   * @return void
   */
  protected void addNode(AbstractNode node) {
    nodeGroup.getChildren().add(node);
    node.setBoundingBox(background.getBoundsInParent());
  }

  /**
   * Removes all of the nodes from the group
   *
   * @return void
   */
  public void clearNodes() {
    List<AbstractNode> nodesToRemove = new ArrayList<>();
    for (Node node : nodeGroup.getChildren()) {
      if (node instanceof AbstractNode) {
        nodesToRemove.add((AbstractNode) node);
      }
    }
    for (AbstractNode node : nodesToRemove) {
      node.delete();
    }
  }

  /**
   * Returns a button with the given name and handler
   *
   * @param buttonName
   * @param handler
   * @return Button
   */
  protected Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button = new Button(buttonName);
    button.setOnAction(handler);
    button.setMaxWidth(Double.MAX_VALUE);
    GridPane.setHgrow(button, Priority.ALWAYS);
    return button;
  }

  /**
   * Returns a scroll pane with all of the buttons that can be used to create nodes
   *
   * @return ScrollPane
   */
  public ScrollPane makeNodeButtonPanel() {
    ScrollPane scrollPane = new ScrollPane();
    GridPane pane = new GridPane();
    pane.setMinSize(panelSizeRatio * windowWidth, windowHeight);
    List<Button> buttons = getNodeButtons(FILENAMES);
    for (Button button : buttons) {
      pane.add(button, 0, buttons.indexOf(button));
    }
    if (this instanceof CodeEditorTab) {
      scrollPane.setContent(getAccordianFinished(FILENAMES));
    } else {
      scrollPane.setContent(pane);
    }
    scrollPane.setMinSize(panelSizeRatio * windowWidth, windowHeight);
    return scrollPane;
  }

  /**
   * Returns an accordion with all of the buttons that can be used to create nodes
   *
   * @return Accordion
   */
  public Accordion getAccordion() {
    return null;
  }

  /**
   * Returns an accordion with all of the buttons that can be used to create nodes
   *
   * @return Accordion
   */
  public Accordion getAccordianFinished(List<String> fileNames) {
    return null;
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
      nodeController.saveInterpreterFiles("");
      //nodeController.saveUserCodeFiles();
    });
    loadMenuItem.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open File");
      File selectedFile = fileChooser.showOpenDialog(new Stage());
      if (selectedFile != null) {
        String filePath = selectedFile.getAbsolutePath();
        nodeController.loadAllContent(filePath);
      }
    });
    fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);
    menuBar.getMenus().add(fileMenu);
    return menuBar;
  }

  private ScrollPane makeWorkspace() {
    double defaultScale = propertyManager.getNumeric("AbstractNodeEdtiorTab.DefaultScale");
    double maxScale = propertyManager.getNumeric("AbstractNodeEdtiorTab.MaxScale");
    double step = propertyManager.getNumeric("AbstractNodeEdtiorTab.ZoomStep");
    double zoomRatio = maxScale / defaultScale;
    background = new Rectangle(zoomRatio * (1 - panelSizeRatio) * windowWidth,
        zoomRatio * windowHeight,
        Color.LIGHTGRAY);
    nodeGroup = new Group(background);
    nodeGroup.setScaleX(defaultScale);
    nodeGroup.setScaleY(nodeGroup.getScaleX());
    nodeGroupStackPane = new StackPane(new Group(nodeGroup));
    nodeGroupStackPane.setOnScroll(event -> zoomOnScroll(event, defaultScale, maxScale, step));
    nodeWorkspace = new ScrollPane(nodeGroupStackPane);
    nodeWorkspace.setHbarPolicy(ScrollBarPolicy.NEVER);
    nodeWorkspace.setPannable(true);
    nodeWorkspace.setOnScroll(event -> verticallyExtendNodeWorkspace(event,
        propertyManager.getNumeric("AbstractNodeEdtiorTab.ExtendSize")));
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
    }
    updateBoundingBoxAllNodes();
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
