package oogasalad.gameeditor.Components;

import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author Han, Aryan
 * These are the components of the View that are playable objects in Gameplay
 */
public interface GameObjectComponent extends DraggableComponent, DisplayableComponents {

  /**
   * This is the method that allows for position of Component in the game-editor to be changed
   * @param position
   */
  void setPos(int position);

  /**
   * Change the name of the GameObject being edited
   * @param name
   */
  void setName(String name);

  /**
   * Retrieve all children that belong to the GameObject
   */
  List<Node> getChildren();

  /**
   * Toggle the gameObject on or off depending on whether the user wants the GameObject to display in game
   */
  void setPlayable(boolean playable);

}
