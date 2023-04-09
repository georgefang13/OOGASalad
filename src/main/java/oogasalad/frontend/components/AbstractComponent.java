package oogasalad.frontend.components;

import javafx.scene.Node;

/**
 * @author Han
 * AbstractComponent is the abstraction that all Components are built off of.
 */
public class AbstractComponent {


  protected int ID;
  protected Node node;
  public AbstractComponent(int id) {
    ID = id;
  }
  public AbstractComponent(int num, Node container){
    ID = num;
    node = container;
  }

}
