package oogasalad.gameeditor.frontend.ViewObjects.Components.DraggableComponent;

import oogasalad.gameeditor.frontend.ViewObjects.Components.Component;

/**
 * @author Han, Aryan
 * This is the component for all View Elements that can be dragged with a mouse
 */
public interface DraggableComponent extends Component {

  /**
   * This is the method that has to be implemented in every lambda function in order to make this
   * object draggable.
   */
  void followMouse();

  /**
   * This is the method that allows us to lock and unlock if the component can be dragged.
   */
  void setDraggable(boolean draggable);
  /**
   * This is the method that allows for the DraggableComponent to be set as the ActiveComponent.
   * @param active is if the component is active
   */
  void setActiveSelected(boolean active);
}
