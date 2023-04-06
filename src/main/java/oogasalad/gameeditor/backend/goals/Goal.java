package oogasalad.gameeditor.backend.goals;

import oogasalad.gameeditor.backend.id.IdManageable;
import oogasalad.gameeditor.backend.id.IdManager;

public abstract class Goal extends IdManageable {

  /**
   * Constructs an IdManageable using the given IdManager.
   *
   * @param idManager the IdManager to use #NOTE: dependency injection
   */
  public Goal(IdManager idManager) {
    super(idManager);
  }
}
