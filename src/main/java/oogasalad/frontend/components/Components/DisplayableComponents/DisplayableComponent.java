package oogasalad.frontend.components.Components.DisplayableComponents;

import oogasalad.frontend.components.Components.Component;

/**
 * @author Han, Aryan
 * This is the component for all View Elements that are visible inside the View editor.
 */
public interface DisplayableComponent extends Component {

  /**
   * This is the method that allows for Components that To be displayable
   * @param visible
   */
  void setVisible(boolean visible);
  /**
   *This is the method that allows for Components to be assigned a z value. The ZIndez dictates which
   * Components are visible above each other. For example, if a Board has a z value of 1, and the
   * GameObject Bishop has a z value of 2, the Bishop will be viewed on top
   */
  void setZIndex(int zIndex);

  /**
   * This is the method that allows for visual for DisplayableComponent to be changed
   * @param imagePath the path where the image is contained to represent the Component
   */
  void setView(String imagePath);
  /**
   * This is the method that allows Component in the game-editor to be resized
   * @param size the size of the ImageView that represents the Component
   */
  void setSize(int size);

}
