package oogasalad.frontend.components.gameObjectComponent;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.Component;

import java.util.List;

/**
 * @author Han, Aryan These are the components of the View that are playable objects in Gameplay
 */
public interface GameObjectComponent extends Component {
  /**
   * Retrieve all children that belong to the GameObject
   */
  List<Node> getChildren();

  /**
   * Toggle the gameObject on or off depending on whether the user wants the GameObject to display
   * in game
   */

  /**
   * This is the method that allows for visual for DisplayableComponent to be changed
   * @param imagePath the path where the image is contained to represent the Component
   */
  void setImage(String imagePath);
  ImageView getImage();

}
