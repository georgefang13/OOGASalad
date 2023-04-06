package oogasalad.gameeditor.frontend.ViewObjects.Components.GameObjectComponent;

import java.util.List;
import javafx.scene.Node;
import oogasalad.gameeditor.frontend.ViewObjects.Components.DisplayableComponents.DisplayableComponent;
import oogasalad.gameeditor.frontend.ViewObjects.Components.DraggableComponent.DraggableComponent;

/**
 *
 * @author Han, Aryan
 * These are the components of the View that are playable objects in Gameplay
 */
public interface GameObjectComponent extends DraggableComponent, DisplayableComponent {


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
