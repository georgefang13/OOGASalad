package oogasalad.gameeditor.frontend.components.Components;

import javafx.scene.Node;

/**
 * @author Han, Aryan
 * This is the base component interface that all components inherit. A component is a fundamental View
 * building block used to populate Panels
 */
public interface Component {

  /**
   * In order to add and remove Components, each Component needs a JavaFX node to reference to
   * @return the Node to add and drop the Component
   */
  Node getNode();

  /**
   * an ID to indentify the Component
   * @return the Int to represent the ID
   */
  int getID();

  /**
   * a method to set the ID of the Component
   * @param id the Int to represent the ID
   */
  void setID(int id);
}
