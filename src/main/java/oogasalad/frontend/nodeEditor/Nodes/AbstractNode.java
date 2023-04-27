package oogasalad.frontend.nodeEditor.Nodes;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.DraggableAbstractNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.EndNestNode;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.Nestable;
import oogasalad.frontend.nodeEditor.Nodes.DraggableNodes.StartNestNode;

public abstract class AbstractNode extends VBox {

  protected double x, y;
  protected double xOffset, yOffset;

  protected String color;

  protected NodeController nodeController;
  private DraggableAbstractNode childNode;
  private Nestable jumpNode;
  private Nestable endJumpNode;



//    protected List<Port> ports;

  public AbstractNode(NodeController nodeController, double x, double y, double width,
      double height, String color) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.nodeController = nodeController;
    setLayoutX(x);
    setLayoutY(y);
    setPrefSize(width, height);
    setColor(color);
    setToolTips();
  }

  protected abstract void setContent();

  public abstract void move(double x, double y);

  public abstract String getJSONString();

  public void setChildNode(DraggableAbstractNode node) {
    this.childNode = node;
  }

  public DraggableAbstractNode getChildNode() {
    return childNode;
  }

  protected void delete() {
    Group parentGroup = (Group) getParent();
    parentGroup.getChildren().remove(this);
  }

  protected void setColor(String color) {
    setStyle("-fx-background-color: " + color);
  }

  protected void setToolTips() {
    Tooltip t = new Tooltip("Delete: Shift + Click");
    Tooltip.install(this, t);
  }

  public Nestable getJumpNode() {
    return jumpNode;
  }

  public NodeController getNodeController() {
    return nodeController;
  }

  public String getColor() {
    return color;
  }

  public void setJumpNode(Nestable node) {
    this.jumpNode = node;
  }

  protected Nestable makeNest(){
    if (this.getJumpNode() != null) {
      setJumpNode( new StartNestNode(getNodeController(), getTranslateX(), getTranslateY(), getWidth(), getHeight(), getColor()));
    }
    return new EndNestNode(getNodeController(), getTranslateX(), getTranslateY(), getWidth(), getHeight(), getColor());
  }

  public void setEndJumpNode(Nestable endNestNode) {
    this.endJumpNode = endNestNode;
  }


}
