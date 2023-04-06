package oogasalad.gameeditor.backend.goals;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.rules.Rule;

/**
 * An Empty is a Goal.
 * This is used to represent an empty goal for testing purposes.
 * @author Max Meister
 */

public class EmptyGoal extends Goal {

  /**
   * Constructs an IdManageable using the given IdManager.
   *
   * @param idManager the IdManager to use #NOTE: dependency injection
   */
  public EmptyGoal(IdManager idManager) {
    super(idManager);
  }
}
