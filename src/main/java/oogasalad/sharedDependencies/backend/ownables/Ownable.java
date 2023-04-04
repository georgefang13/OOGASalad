package oogasalad.sharedDependencies.backend.ownables;


import oogasalad.gameeditor.backend.id.IdManageable;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;


/**
 * An object that can be owned by an owner.
 * All Ownables are owned by a single owner.
 * The default owner is the GameWorld.
 * This Owner can be either a Player or a GameWorld.
 * All ownables have a unique id that can be changed per the IdManager (Ex. "Player1Score").
 * @see Player
 * This Owner can be either a Player or a Game.
 * @see Player
 * @see GameWorld
 * The Owner of an Ownable can be changed.
 * @author Michael Bryant
 */
public abstract class Ownable extends IdManageable {


  /**
   * Constructs an Ownable with a given IdManager.
   * @param idManager the IdManager to use
   */
  public Ownable(IdManager idManager) {
    super(idManager);
  }


}


