package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public class GameObject extends Ownable {

  /**
   * Creates a new GameObject.
   */
  public GameObject(IdManager idManager) {
    super(idManager);
  }

}

