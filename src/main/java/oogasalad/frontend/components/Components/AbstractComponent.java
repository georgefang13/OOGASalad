package oogasalad.frontend.components.Components;

import javafx.scene.Node;

/**
 * @author Han
 * AbstractComponent is the abstraction that all Components are built off of.
 */
public class AbstractComponent {

  protected int ID;
  protected Node node;

  public AbstractComponent(int num, Node container){
    ID = num;
    node = container;
  }
}
