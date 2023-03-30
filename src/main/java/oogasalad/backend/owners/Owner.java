package oogasalad.backend.owners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oogasalad.backend.ownables.Ownable;
import oogasalad.backend.ownables.id.IdManager;

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


  /**
   * @return an unmodifiable list of the ownables owned by the Owner
   */
  public List getOwnables() {
   return idManager.getOwnablesForOwner(this);
  }


}
