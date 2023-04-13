package oogasalad.sharedDependencies.backend.ownables.gameobjects.piece;

import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Owner;

public abstract class Piece extends GameObject {

  /**
   * Creates a new GameObject.
   *
   * @param owner
   */
  public Piece(Owner owner) {
    super(owner);
  }
}
