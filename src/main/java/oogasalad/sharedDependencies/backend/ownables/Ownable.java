package oogasalad.sharedDependencies.backend.ownables;


import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;


/**
 * An object that can be owned by an owner. All Ownables are owned by a single owner. The default
 * owner is the GameWorld. This Owner can be either a Player or a GameWorld. All ownables have a
 * unique id that can be changed per the IdManager (Ex. "Player1Score").
 *
 * @author Michael Bryant
 * @author Max Meister
 * @see Player This Owner can be either a Player or a Game.
 * @see Player
 * @see GameWorld The Owner of an Ownable can be changed.
 */
public abstract class Ownable extends IdManageable {

  private Owner owner;

  /**
   * Constructs an Ownable with a given IdManager.
   *
   * @param owner for the ownable
   */
  public Ownable(Owner owner) {
    this.owner = owner;
  }

  /**
   * Gets the owner of the ownable.
   *
   * @return the owner of the ownable
   */
  public Owner getOwner() {
    return owner;
  }

  /**
   * Sets the owner of the ownable.
   *
   * @param owner the new owner of the ownable
   */
  public void setOwner(Owner owner) {
    this.owner = owner;
  }


}
