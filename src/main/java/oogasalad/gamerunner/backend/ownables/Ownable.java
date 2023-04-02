package oogasalad.gamerunner.backend.ownables;

import java.util.ResourceBundle;
import oogasalad.gamerunner.backend.owners.GameWorld;
import oogasalad.gamerunner.backend.owners.Owner;
import oogasalad.gamerunner.backend.owners.Player;

/**
 * An object that can be owned by an owner.
 * All Ownables are owned by a single owner.
 * This Owner can be either a Player or a Game.
 * @see Player
 * @see GameWorld
 * The Owner of an Ownable can be changed.
 * @author Michael Bryant
 */
public abstract class Ownable {
  private Owner owner;
  private static final String LABELS = "backend/reflection/exceptions"; //TODO hardcoded for English rn
  private ResourceBundle labels = ResourceBundle.getBundle(LABELS);

  /**
   * Checks if the Ownable is owned by an Owner.
   * @return true if the Ownable is owned by an Owner, false otherwise
   */
  public boolean isOwned() {
    return owner != null;
  }

  /**
   * Gets the Owner of the Ownable.
   * @return the Owner of the Ownable
   */
  public Owner getOwner() {
    return owner;
  }

  /**
   * Sets the Owner of the Ownable.
   * @param newOwner the new Owner of the Ownable
   */
  public void setOwner(Owner newOwner) throws IllegalArgumentException {
    if (newOwner == null || canBeOwnedBy(newOwner)) {
      owner = newOwner;
    } else {
      throw new IllegalArgumentException(labels.getString("InvalidOwner"));
    }
  }

  /**
   * Checks if the Ownable can be owned by the given Owner.
   * @param potentialOwner the potential Owner of the Ownable
   * @return true if the Ownable can be owned by the given Owner, false otherwise
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner);
}


