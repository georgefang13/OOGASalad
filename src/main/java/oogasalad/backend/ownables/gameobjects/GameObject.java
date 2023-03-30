package oogasalad.backend.ownables.gameobjects;

import oogasalad.backend.Game;
import oogasalad.backend.ownables.Ownable;
import oogasalad.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public abstract class GameObject extends Ownable {


  /**
   * @see oogasalad.backend.ownables.Ownable#canBeOwnedBy(Owner)
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner);
}

