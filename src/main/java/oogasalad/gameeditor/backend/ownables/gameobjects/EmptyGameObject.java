package oogasalad.gameeditor.backend.ownables.gameobjects;

import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * An EmptyGameObject is a GameObject that can be owned by any Owner. This is used to represent an
 * empty space on the board or for testing purposes.
 *
 * @author Michael Bryant
 */
public class EmptyGameObject extends GameObject {

  public EmptyGameObject(Owner owner) {
    super(owner);
  }

}
