package oogasalad.sharedDependencies.backend.ownables.gameobjects.piece;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Owner;

public abstract class Piece extends GameObject {

  /**
   * Creates a new GameObject.
   *
   * @param idManager
   * @param owner
   */
  public Piece(IdManager idManager, Owner owner) {
    super(idManager, owner);
  }
}
