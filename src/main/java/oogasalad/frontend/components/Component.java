package oogasalad.frontend.components;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Map;

/**
 * @author Han, Aryan This is the base component interface that all components inherit. A component
 * is a fundamental View building block used to populate Panels
 */
public interface Component {

  /**
   * In order to add and remove Components, each Component needs a JavaFX node to reference to
   *
   * @return the Node to add and drop the Component
   */
  Node getNode();
  void setNode(Node node);

  /**
   * an ID to indentify the Component
   *
   * @return the Int to represent the ID
   */
  String getID();

  /**
   * a method to set the ID of the Component
   *
   * @param id the Int to represent the ID
   */
  void setID(String id);

  /**
   * This is the method that allows us to lock and unlock if the component can be dragged.
   */
  void setDraggable(boolean draggable);

  /**
   * This is the method that allows for the DraggableComponent to be set as the ActiveComponent.
   *
   * @param active is if the component is active
   */
  void setActiveSelected(boolean active);

  /**
   * This is the method that allows for Components that To be displayable
   * @param visible whether the Component is visible
   */
  void setVisible(boolean visible);

  /**
   *This is the method that allows for Components to be assigned a z value. The ZIndez dictates which
   * Components are visible above each other. For example, if a Board has a z value of 1, and the
   * GameObject Bishop has a z value of 2, the Bishop will be viewed on top
   */
  void setZIndex(int zIndex);

  /**
   * This is the method that allows Component in the game-editor to be resized
   * @param size the size of the ImageView that represents the Component
   */
  void setSize(double size);

  void followMouse();

  void setName(String newName);
  String getName();
  void setValuesfromMap(Map<String, String> map);
  Map<String, String> getParameters();
}
