package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import oogasalad.sharedDependencies.backend.filemanagers.Configurable;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner Represents an object such as a game
 * piece
 *
 * @author Michael Bryant
 */
public class GameObject extends Ownable implements Configurable {

  /**
   * Creates a new GameObject.
   */
  public GameObject(Owner owner) {
    super(owner);
  }

  @Override
  public void fromConfigFile(String path) {
    // Does nothing
  }

  @Override
  public String getAsJson() {
    return null;
  }
}

