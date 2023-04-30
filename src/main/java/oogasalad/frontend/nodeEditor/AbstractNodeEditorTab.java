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

  protected Group group;
  protected Rectangle background;
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
    group.getChildren().add(node);
    node.setBoundingBox((background.getBoundsInParent()));
  }

  protected void clearAllNodes() {
    for (Node node : group.getChildren()) {
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
    background = new Rectangle(0.75 * windowWidth, windowHeight, Color.LIGHTGRAY);
    group = new Group(background);
    group.setScaleX(1);
    group.setScaleY(1);
    addNode(new MainNode());
    StackPane content = new StackPane(new Group(group));
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
    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setPannable(true);
    scrollPane.setPrefSize(0.75 * windowWidth, windowHeight);

    scrollPane.setOnScroll(event -> {
      double deltaY = event.getDeltaY();
      double contentHeight = content.getBoundsInLocal().getHeight();
      double viewportHeight = scrollPane.getViewportBounds().getHeight();
      double vvalue = scrollPane.getVvalue();

      // Compute the new vertical scroll position based on the mouse wheel delta and the current scroll position
      double newVvalue = vvalue - deltaY / contentHeight * viewportHeight;
      scrollPane.setVvalue(newVvalue);

      // If we have scrolled to the bottom of the scroll pane, add to the height to accommodate additional content
      if (newVvalue >= 1.0 && scrollPane.getPrefHeight() < Double.MAX_VALUE) {
        scrollPane.setPrefHeight(scrollPane.getPrefHeight() + 50);
        background.setHeight(background.getHeight() + 50);
        for (Node node : group.getChildren()) {
          if (node instanceof AbstractNode) {
            AbstractNode abstractNode = (AbstractNode) node;
            abstractNode.setBoundingBox(background.getBoundsInParent());
          }
        }
      }
      event.consume();
    });
    return scrollPane;
  }

  private AbstractNode getMainNode() {
    for (Node node : group.getChildren()) {
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
