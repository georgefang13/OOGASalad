package oogasalad.backend.ownables.gameobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import oogasalad.backend.Game;
import oogasalad.backend.id.IdManageable;
import oogasalad.backend.id.IdManager;
import oogasalad.backend.ownables.Ownable;
import oogasalad.backend.ownables.variables.Variable;
import oogasalad.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public abstract class GameObject extends Ownable {

  /**
   * Creates a new GameObject.
   */
  public GameObject(IdManager idManager) {
    super(idManager);
  }

  /**
   * @see oogasalad.backend.ownables.Ownable#canBeOwnedBy(Owner)
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner);

}

