package oogasalad.backend.ownables;

import java.util.ResourceBundle;
import oogasalad.backend.Game;
import oogasalad.backend.id.IdManageable;
import oogasalad.backend.id.IdManager;
import oogasalad.backend.owners.GameWorld;
import oogasalad.backend.owners.Owner;

/**
 * An object that can be owned by an owner.
 * All Ownables are owned by a single owner.
 * The default owner is the GameWorld.
 * This Owner can be either a Player or a GameWorld.
 * All ownables have a unique id that can be changed per the IdManager (Ex. "Player1Score").
 * @see oogasalad.backend.owners.Player
 * @see GameWorld
 * The Owner of an Ownable can be changed.
 * @author Michael Bryant
 */
public abstract class Ownable extends IdManageable {

  private static final String LABELS = "backend/reflection/exceptions"; //TODO hardcoded for English rn
  private ResourceBundle labels = ResourceBundle.getBundle(LABELS);

  /**
   * Constructs an Ownable with a given IdManager.
   * @param idManager the IdManager to use
   */
  public Ownable(IdManager idManager) {
    super(idManager);
  }


  /**
   * Checks if the Ownable can be owned by the given Owner.
   * @param potentialOwner the potential Owner of the Ownable
   * @return true if the Ownable can be owned by the given Owner, false otherwise
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner); //TODO deprecate??

}


