package oogasalad.backend.ownables.gameobjects;

import oogasalad.backend.id.IdManager;
import oogasalad.backend.owners.Owner;

/**
 * An EmptyGameObject is a GameObject that can be owned by any Owner.
 * This is used to represent an empty space on the board or for testing purposes.
 * @author Michael Bryant
 */
public class EmptyGameObject extends GameObject{

  public EmptyGameObject(IdManager idManager) {
    super(idManager);
  }

  @Override
  public boolean canBeOwnedBy(Owner potentialOwner) {
    return true;
  }

}
