package oogasalad.gamerunner.backend.ownables.gameobjects;

import oogasalad.gamerunner.backend.ownables.Ownable;
import oogasalad.gamerunner.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public abstract class GameObject extends Ownable {

  /**
   * @see Ownable#canBeOwnedBy(Owner)
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner);
}

