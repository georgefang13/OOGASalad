package oogasalad.frontend.nodeEditor.nodes;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.nodeEditor.tabs.GameObjectTab;
import oogasalad.frontend.nodeEditor.tabs.GoalTab;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import oogasalad.frontend.nodeEditor.NodeController;

import java.util.ArrayList;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class GameObjectNode extends AbstractNode implements ControlNode {

  private TextField gameObjectName;
  private GridPane buttonGrid;
  private NodeController nodeController;


  public GameObjectNode(NodeController nodeController) {
    super();
    this.nodeController = nodeController;
    setContent();
    this.getStyleClass().add(propertyManager.getText("GameObjectNode.StyleClass"));
  }

  /**
   * sets the content for the node
   *
   * @return void
   */
  @Override
  protected void setContent() {
    Label title = new Label(propertyManager.getText("GameObjectNode.Title"));
    title.getStyleClass().add(propertyManager.getText("GameObjectNode.TitleClass"));
    buttonGrid = new GridPane();
    this.getChildren().addAll(title, buttonGrid);
    gameObjectName = new TextField();
    gameObjectName.setPromptText(propertyManager.getText("GameObjectNode.NameLabel"));
    buttonGrid.add(
        gameObjectName, 0, 0);
    buttonGrid.add(
        makeButton(propertyManager.getText("GameObjectNode.GoalLabel"), this::goal), 0, 1);
  }

  /**
   * gets the string that will be used to parse the node in the interpreter
   *
   * @return String
   */
  @Override
  public String getNodeParseString() {
    return "";
  }

  /**
   * Returns the record of NodeData for this node
   *
   * @return NodeData
   */
  @Override
  public NodeData getNodeData() {
    return new NodeData(getClass().getSimpleName(), getClass().getInterfaces()[0].getSimpleName(),
        new ArrayList<>());
  }


  /**
   * initializes a tab and switches to it for the on enter event
   *
   * @param event
   * @return void
   */
  private void goal(ActionEvent event) {
    GameObjectTab tab = new GameObjectTab(nodeController, gameObjectName.getText());
    nodeController.openAndSwitchToTab(tab);
  }


}
