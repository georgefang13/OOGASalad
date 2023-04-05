package oogasalad.sharedDependencies.backend.owners;

import java.util.List;
import oogasalad.gameeditor.backend.id.IdManager;

/**
 * An Owner owns Ownables.
 * It does not store the Ownables, but can retrieve them from the IdManager.
 */
public abstract class Owner {

  /**
   * The IdManager of the game for Ownables.
   */
  private IdManager idManager;

  /**
   * Constructs an Owner using the given IdManager.
   * @param idManager the IdManager to use
   */
  public Owner(IdManager idManager) {
    this.idManager = idManager;
  }

}