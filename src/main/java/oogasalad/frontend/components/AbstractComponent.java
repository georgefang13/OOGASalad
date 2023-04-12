package oogasalad.frontend.components;

import javafx.scene.Node;

/**
 * @author Han and Aryan
 * AbstractComponent is the abstraction that all Components are built off of.
 */
public class AbstractComponent implements Component {

  protected int ID;
  protected Node node;
  public AbstractComponent(int id) {
    ID = id;
  }
  public AbstractComponent(int num, Node container){
    ID = num;
    node = container;
  }

  @Override
  public Node getNode() {
    return node;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public void setID(int id) {
    ID = id;
  }

  @Override
  public void setDefault() {}

}
