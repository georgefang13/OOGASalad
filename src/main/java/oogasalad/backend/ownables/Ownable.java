package oogasalad.backend.ownables;

import java.util.ResourceBundle;
import oogasalad.backend.Game;
import oogasalad.backend.owners.GameWorld;
import oogasalad.backend.owners.Owner;

/**
 * An object that can be owned by an owner.
 * All Ownables are owned by a single owner.
 * This Owner can be either a Player or a Game.
 * All ownables have a unique id that can be changed (Ex. "Player1Score").
 * @see oogasalad.backend.owners.Player
 * @see GameWorld
 * The Owner of an Ownable can be changed.
 * @author Michael Bryant
 */
public abstract class Ownable {

  /**
   * The single Owner of the Ownable.
   */
  private Owner owner;

  /**
   * The id of the Ownable.
   * This is used to identify the Ownable in the frontend and backend.
   * Default string plus a number Ex. "BoardGraph1"
   * Can be changed -- but should be unique.
   */
  private String id;

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
   * Gets the id of the Ownable.
   * @return the id of the Ownable
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the Ownable. Called by Game
   * @param newId the new id of the Ownable
   */
  public void setId(String newId) {
    id = newId;
  }

  /**
   * Checks if the Ownable can be owned by the given Owner.
   * @param potentialOwner the potential Owner of the Ownable
   * @return true if the Ownable can be owned by the given Owner, false otherwise
   */
  public abstract boolean canBeOwnedBy(Owner potentialOwner);

  /**
   * Gets the default id of the Ownable.
   * Ex. "BoardGraph"
   * Used to generate Ids
   * @return the default id of the Ownable
   */
  public String getDefaultId() {
    return this.getClass().getSimpleName();
  }
}


