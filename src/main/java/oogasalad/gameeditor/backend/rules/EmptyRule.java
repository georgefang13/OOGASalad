package oogasalad.gameeditor.backend.rules;

import oogasalad.gameeditor.backend.id.IdManager;

/**
 * An Empty is a Rule.
 * This is used to represent an empty rule for testing purposes.
 * @author Max Meister
 */

public class EmptyRule extends Rule{

  /**
   * Constructs an IdManageable using the given IdManager.
   *
   * @param idManager the IdManager to use #NOTE: dependency injection
   */
  public EmptyRule(IdManager idManager) {
    super(idManager);
  }
}
