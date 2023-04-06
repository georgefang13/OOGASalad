package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public abstract class GameObject extends Ownable {

  /**
   * Creates a new GameObject.
   */
  public GameObject(IdManager idManager, Owner owner) {
    super(idManager, owner);
  }

}

