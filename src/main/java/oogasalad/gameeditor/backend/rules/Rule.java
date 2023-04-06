package oogasalad.gameeditor.backend.rules;

import oogasalad.gameeditor.backend.id.IdManageable;
import oogasalad.gameeditor.backend.id.IdManager;

public abstract class Rule extends IdManageable {

  /**
   * Constructs an IdManageable using the given IdManager.
   *
   * @param idManager the IdManager to use #NOTE: dependency injection
   */
  public Rule(IdManager idManager) {
    super(idManager);
  }
}
